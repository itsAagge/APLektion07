package graphalgorithms;

import java.util.*;

public class GraphAlgorithms {
    public static void main(String[] args) {
        Graph<Integer> g = new EdgeListGraph<>();

        g.addVertex(15);
        g.addVertex(38);
        g.addVertex(6);
        g.addVertex(123);
        g.addVertex(66);

        g.addEdge(15, 38, 10);
        g.addEdge(15, 6, 23);
        g.addEdge(15, 66, 90);
        g.addEdge(38, 123, 55);
        g.addEdge(38, 66, 2);
        g.addEdge(6, 123, 7);
        g.addEdge(6, 66, 8);
        g.addEdge(123, 66, 76);

        g.printGraph();
        System.out.println();

        System.out.println("DFS: Depth-First traversal starting in 123:");
        System.out.println(dfs(g, 123));
        System.out.println();

        System.out.println("DFS traversal using a stack starting in 123:");
        //System.out.println(dfsStack(g, 123));
        System.out.println();

        System.out.println("BFS: Breth-First traversal starting in 123:");
        //System.out.println(bfs(g, 123));
        System.out.println();

//        System.out.println("Graph is connected?");
//        System.out.println(isGraphConnected(g));
//        System.out.println();

//        System.out.println("Vertex 123 and 15 are connected:");
//        System.out.println(hasGraphPath(g, 123, 15));
//        System.out.println();

        System.out.println("Minimum spanning tree:");
        System.out.println(mst(g));
        System.out.println();

        System.out.println("Dijkstra starting in 123:");
        System.out.println(dijkstra(g, 123));
        System.out.println();
    }

    /**
     * Return a list with the vertices of the specified graph
     * found by a Depth-First traversal (DFS) of the graph starting at the specified vertex.
     * Throw exception if the vertex is not in the graph.
     */
    public static <V> List<V> dfs(Graph<V> graph, V v) {
        if (!graph.vertices().contains(v)) throw new RuntimeException("The vertex is not in the graph");

        ArrayList<V> visited = new ArrayList<>();
        dfs(graph, v, visited);
        return visited;
    }

    private static <V> void dfs(Graph<V> graph, V v, List<V> visited) {
        visited.add(v);
        for (V w : graph.neighbors(v)) {
            if (!visited.contains(w)) {
                dfs(graph, w, visited);
            }
        }
    }

    /**
     * Return a list with the vertices of the specified graph
     * found by a Depth-First traversal (DFT) of the graph starting at the specified vertex.
     * Throw exception if the vertex is not in the graph.
     */
    public static <V> List<V> dfsStack(Graph<V> graph, V v) {
        // TODO
        return null;
    }

    /**
     * Return a list with the vertices of the specified graph
     * found by a Breath-First traversal (BFT) of the graph stating at the specified vertex.
     * Throw exception if the vertex is not in the graph.
     */
    public static <V> List<V> bfs(Graph<V> graph, V v) {
        // TODO
        return null;
    }

    /**
     * Return a minimum spanning tree (MST).
     */
    public static <V> List<Edge<V>> mst(Graph<V> graph) {
        List<LinkedHashSet<V>> cSets = new ArrayList<>();
        for (V vertex : graph.vertices()) {
            cSets.add(new LinkedHashSet<>(List.of(vertex)));
        }
        Queue<Edge<V>> q = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        q.addAll(graph.edges());
        List<Edge<V>> t = new ArrayList<>();
        while (t.size() < graph.vertices().size() - 1) {
            Edge<V> e = q.remove();
            V u = e.getU();
            V v = e.getV();
            HashSet<V> uSet = new LinkedHashSet<>();
            HashSet<V> vSet = new LinkedHashSet<>();
            for (LinkedHashSet<V> cSet : cSets) {
                if (cSet.contains(u)) uSet = cSet;
                if (cSet.contains(v)) vSet = cSet;
            }
            if (!uSet.equals(vSet)) {
                t.add(e);
                uSet.addAll(vSet);
                cSets.remove(vSet);
            }
        }
        return t;
    }

    /**
     * Return a map containing (vertex, weight) pairs,
     * where weight is the total weight of the shortest path
     * from the specified vertex v to the vertex in the pair.
     */
    public static <V> Map<V, Integer> dijkstra(Graph<V> graph, V v) {
        // TODO
        return null;
    }
}
