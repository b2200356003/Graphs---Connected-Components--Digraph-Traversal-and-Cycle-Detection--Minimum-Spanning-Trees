import java.util.*;

public class TrapLocator {
    public List<Colony> colonies;

    public TrapLocator(List<Colony> colonies) {
        this.colonies = colonies;
    }

    public List<List<Integer>> revealTraps() {

        // Trap positions for each colony, should contain an empty array if the colony is safe.
        // I.e.:
        // 0 -> [2, 15, 16, 31]
        // 1 -> [4, 13, 22]
        // 3 -> []
        // ...
        List<List<Integer>> traps = new ArrayList<>();
        List<List<Integer>> tempTraps = new ArrayList<>();
        // Identify the time traps and save them into the traps variable and then return it.
        // TODO: Your code here

        int V  = 1;
        for(Colony c: colonies){
            for (int a : c.cities){
                V++;
            }
        }

        DiGraph graph = new DiGraph(V);
        for(Colony c: colonies){
            for (int key : c.roadNetwork.keySet()) {
                for (int adj:c.roadNetwork.get(key)) {
                    graph.addEdge(key,adj);
                }
            }
        }

        traps=graph.printCycles();

        boolean find = false;
        int count = 0;
        for (Colony co : colonies){
            List<Integer> tempListA = new ArrayList<>();
            for (int i = 0; i < co.cities.size(); i++) {
                for(List<Integer> tempList : traps){
                    for(Integer element : tempList){
                        if (co.cities.get(i)==element){
                            tempListA=tempList;
                            count++;
                            find=true;
                            break;
                        }
                    }
                    if (find)
                        break;
                }
                if(find){
                    break;
                }
            }
            traps.add(tempListA);
            find=false;
        }

        traps=traps.subList(count,traps.size());

        return traps;

    }

    public void printTraps(List<List<Integer>> traps) {
        // For each colony, if you have encountered a time trap, then print the cities that create the trap.
        // If you have not encountered a time trap in this colony, then print "Safe".
        // Print the your findings conforming to the given output format.
        // TODO: Your code here

        System.out.println("Danger exploration conclusions:");

        for (int i = 0; i < traps.size(); i++) {
            if (!traps.get(i).isEmpty())
                System.out.println("Colony " + (i+1) + ": Dangerous. Cities on the dangerous path: " + traps.get(i));
            else {
                System.out.println("Colony " + (i+1) + ": Safe");
            }
        }
    }

}
