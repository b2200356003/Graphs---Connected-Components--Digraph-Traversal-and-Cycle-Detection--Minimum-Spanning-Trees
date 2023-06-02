import java.lang.reflect.Array;
import java.util.ArrayList;

public class EdgeWeightedGraph {
    public final int V; // vertice(Location) number
    public ArrayList<Trail>[] adj;


    public EdgeWeightedGraph(int V) {
       this.V = V;
       adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i]=new ArrayList<>();
        }
    }

    public void addEdge(Trail e)
    {
        Location A = e.either();
        int v = A.id;
        int w = e.other(A).id;
        adj[v].add(e);
        adj[w].add(e);
    }

    public ArrayList<Trail> adj(int v)
    { return adj[v]; }


}
