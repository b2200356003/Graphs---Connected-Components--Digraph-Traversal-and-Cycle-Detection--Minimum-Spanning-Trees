import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Kingdom {

    // TODO: You should add appropriate instance variables.
    DiGraph graph;
    DiGraph graph2;
    DiGraph reverseGraph;

    public void initializeKingdom(String filename) {
        // Read the txt file and fill your instance variables
        try {
            Scanner scanner = new Scanner(new File(filename));
            Scanner scanner2 = new Scanner(new File(filename));

            String[] lineList ;

            int row = 0;
            int temp = 0;
            while (scanner2.hasNextLine()){
                temp = scanner2.nextLine().split(" ").length;
            }
            graph2 = new DiGraph(Integer.valueOf(temp));
            graph = new DiGraph(Integer.valueOf(temp));
            reverseGraph = new DiGraph(Integer.valueOf(temp));

            while (scanner.hasNextLine()){
                lineList=scanner.nextLine().split(" ");
                for (int i = 0; i < lineList.length; i++) {
                    if (Integer.valueOf(lineList[i])==1) {
                        graph.addEdge(row, i);
                        graph2.addEdge(row,i);
                        graph.addEdge(i,row);
                        reverseGraph.addEdge(i,row);
                    }
                }
                row++;
            }

            scanner.close();


        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Colony> getColonies() {
        List<Colony> colonies = new ArrayList<>();
        // Identify the colonies using the given input file.

        for (int i = 0; i < reverseGraph.V; i++) {
            if (!reverseGraph.visited[i])
                reverseGraph.dfs(i);
        }



        int count = 0;
        for (int i = 0; i < reverseGraph.postOrder.size(); i++) {
            if (!graph.visited[reverseGraph.postOrder.get(i)]) {
                graph.dfs(reverseGraph.postOrder.get(i), count);
                count++;
            }
        }


        graph2.componentNum=graph.componentNum;

        for (int i = 0; i < count; i++) {
            Colony tempCo = new Colony();
            List<Integer> temp = new ArrayList<>();
            for (int j = 0; j < graph2.componentNum.length; j++) {
                if (graph2.componentNum[j]==i) {
                    temp.add(j+1);
                }
            }

            for (int element : temp) {
                element = element - 1 ;
                tempCo.roadNetwork.put(element,graph2.adjList[element]);
            }

            tempCo.cities=temp;
            colonies.add(tempCo);
        }


        return colonies;
    }

    public void printColonies(List<Colony> discoveredColonies) {
        // Print the given list of discovered colonies conforming to the given output format.
        // TODO: Your code here
        System.out.println("Discovered colonies are:");
        for (int i = 0; i < discoveredColonies.size(); i++) {
            System.out.println("Colony " + (i+1) + ": " + discoveredColonies.get(i).cities);
        }
    }
}
