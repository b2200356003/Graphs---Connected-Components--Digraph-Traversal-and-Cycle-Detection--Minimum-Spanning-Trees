import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class DiGraph {
    public int V;
    public ArrayList<Integer>[] adjList;
    public Stack<Integer> postOrder;
    boolean[] visited ;
    int[] pathVisit;
    int[] componentNum ;
    public DiGraph(int V){
        this.V = V ;
        postOrder = new Stack<>();
        adjList = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adjList[i]=new ArrayList<>();
        }
        visited = new boolean[V];
        pathVisit = new int[V];
        componentNum = new int[V];
    }

    public void addEdge(int u, int v) {
        adjList[u].add(v);
    }


    public void dfs(int i,int count) {
        this.visited[i] = true;
        this.componentNum[i] = count;
        for (int b : adjList[i]) {
            if (!visited[b]) {
                dfs(b, count);
            }
        }
    }

    public void dfs(int i){
        this.visited[i] = true;
        for (int b : adjList[i])
            if (!visited[b]) {
                dfs(b);
            }
        this.postOrder.push(i);
    }

    public List<List<Integer>> printCycles() {
        boolean[] visited = new boolean[V];
        boolean[] recursionStack = new boolean[V];
        List<List<Integer>> cycles = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i, visited, recursionStack, new ArrayList<>(), cycles);
            }
        }
            for (List<Integer> cycle : cycles) {
                Collections.sort(cycle);
        }
        return cycles;
    }

    private void dfs(int u, boolean[] visited, boolean[] recursionStack, List<Integer> path, List<List<Integer>> cycles) {
        visited[u] = true;
        recursionStack[u] = true;
        path.add(u);
        for (int v : adjList[u]) {
            if (!visited[v]) {
                dfs(v, visited, recursionStack, path, cycles);
            } else if (recursionStack[v]) {
                List<Integer> cycle = new ArrayList<>();
                for (int i = path.size() - 1; i >= 0; i--) {
                    int node = path.get(i);
                    cycle.add(node+1);
                    if (node == v) {
                        break;
                    }
                }
                Collections.reverse(cycle);
                cycles.add(cycle);
            }
        }
        recursionStack[u] = false;
        path.remove(path.size() - 1);


    }




}
