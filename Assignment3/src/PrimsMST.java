import java.util.ArrayList;
import java.util.PriorityQueue;

public class PrimsMST
{
    public boolean[] marked; // MST vertices
    public ArrayList<Trail> safeList; // MST edges
    public PriorityQueue<Trail> pq; // PQ of edges
    public static int total;
    public PrimsMST(EdgeWeightedGraph G)
    {
        total=0;
        pq = new PriorityQueue<Trail>();
        safeList = new ArrayList<Trail>();
        marked = new boolean[G.V];
        visit(G, 0);

        while (!pq.isEmpty())
        {
            Trail e = pq.poll();
            Location A = e.either();
            int v = A.id, w = e.other(A).id;
            if (marked[v] && marked[w]) {continue;}
            safeList.add(e);
            if (!marked[v]) {
                visit(G, v);
            }
            if (!marked[w]) {
                visit(G, w);
            }
        }

    }
    public void visit(EdgeWeightedGraph G, int v)
    {
            marked[v] = true;
            for (Trail e : G.adj(v)) {
                if (!marked[e.destination.id] || !marked[e.source.id]) {
                    pq.add(e);
                }
            }
    }
}