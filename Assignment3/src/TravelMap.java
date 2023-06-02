import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class TravelMap {

    // Maps a single Id to a single Location.
    public Map<Integer, Location> locationMap = new HashMap<>();

    // List of locations, read in the given order
    public List<Location> locations = new ArrayList<>();

    // List of trails, read in the given order
    public List<Trail> trails = new ArrayList<>();

    // TODO: You are free to add more variables if necessary.

    public void initializeMap(String filename) {
        DocumentBuilderFactory dBfactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = dBfactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = builder.parse(new File(filename));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        NodeList nList = document.getElementsByTagName("Location");

        for (int i = 0; i < nList.getLength(); i++)
        {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                //Print each Location's name
                Element element = (Element) node;
                Location temp = new Location(element.getElementsByTagName("Name").item(0).getTextContent(),Integer.valueOf(element.getElementsByTagName("Id").item(0).getTextContent()));
                locations.add(temp);
                locationMap.put(i,temp);
            }
        }

        NodeList nList2 = document.getElementsByTagName("Trail");

        for (int i = 0; i < nList2.getLength(); i++)
        {
            Node node = nList2.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                //Print each Location's name
                Element element = (Element) node;
                Trail temp = new Trail(locations.get(Integer.valueOf(element.getElementsByTagName("Source").item(0).getTextContent())),locations.get(Integer.valueOf(element.getElementsByTagName("Destination").item(0).getTextContent())),Integer.valueOf(element.getElementsByTagName("Danger").item(0).getTextContent()));
                trails.add(temp);
            }
        }


    }


    public List<Trail> getSafestTrails(){
        List<Trail> safestTrails = new ArrayList<>();
        EdgeWeightedGraph G = new EdgeWeightedGraph(locations.size());
        for (int i = 0; i < trails.size(); i++) {
            G.addEdge(trails.get(i));
        }

        PrimsMST mst = new PrimsMST(G);
        for (int i = 0; i < mst.safeList.size(); i++) {
            safestTrails.add(mst.safeList.get(i));
        }
        return safestTrails;
    }

    public void printSafestTrails(List<Trail> safestTrails) {
        // Print the given list of safest trails conforming to the given output format.
        int total = 0;
        System.out.println("Safest trails are:");
        for (int i = 0; i < safestTrails.size(); i++) {
            System.out.println("The trail from " + safestTrails.get(i).source.name + " to " + safestTrails.get(i).destination.name + " with danger " + safestTrails.get(i).danger);
            total+=safestTrails.get(i).danger;
        }
        System.out.println("Total danger: " + total);
    }
}
