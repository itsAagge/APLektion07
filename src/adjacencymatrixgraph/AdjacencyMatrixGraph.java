package adjacencymatrixgraph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Adjacency matrix implementation of the graph interface.
 */
public class AdjacencyMatrixGraph<V> implements Graph<V> {
    // List with all the vertices in the graph.
    private Map<V, Integer> vertices;
    // 2-dim array with all the edges in the graph.
    private Edge<V>[][] matrix;
    private int matrixCapacity;
    private int matrixSize; // equal to vertices.size()

    //-----------------------------------------------------

    /** Construct an empty AdjacencyMatrixGraph. */
    public AdjacencyMatrixGraph(int matrixCapacity) {
        vertices = new LinkedHashMap<>();
        @SuppressWarnings("unchecked")
        Edge<V>[][] emptyMatrix = new Edge[matrixCapacity][matrixCapacity];
        matrix = emptyMatrix;
        this.matrixCapacity = matrixCapacity;
        matrixSize = 0;
    }

    /** Return a list with the vertices in the graph. */
    @Override
    public List<V> vertices() {
        return new ArrayList<>(vertices.keySet());
    }

    /** Return a list with the edges in the graph. */
    @Override
    public List<Edge<V>> edges() {
        List<Edge<V>> edges = new ArrayList<>();
        for (Edge<V>[] rows : matrix) {
            for (Edge<V> edge : rows) {
                if (edge != null && !edges.contains(edge)) {
                    edges.add(edge);
                }
            }
        }
        return edges;
    }

    /**
     * Return a list with the neighbors of the specified vertex.
     * Pre: The vertex is in the graph.
     */
    @Override
    public List<V> neighbors(V v) {
        List<V> neighbors = new ArrayList<>();
        int index = vertices.get(v);
        for (Edge<V> edge : matrix[index]) {
            if (edge != null) {
                if (edge.getU().equals(v)) neighbors.add(edge.getV());
                else if (edge.getV().equals(v)) neighbors.add(edge.getU());
            }
        }
        return neighbors;
    }

    /**
     * Return the degree for the specified vertex.
     * Pre: The vertex is in the graph.
     */
    @Override
    public int degree(V v) {
        int count = 0;
        int index = vertices.get(v);
        for (Edge<V> edge : matrix[index]) {
            if (edge != null) count++;
        }
        return count;
    }

    /**
     * Return the incident edges to the specified vertex.
     * Pre: The vertex is in the graph.
     */
    @Override
    public List<Edge<V>> incidentEdges(V v) {
        List<Edge<V>> indicentEdges = new ArrayList<>();
        int index = vertices.get(v);
        for (Edge<V> edge : matrix[index]) {
            if (edge != null) indicentEdges.add(edge);
        }
        return indicentEdges;
    }

    /**
     * Return true, if the specified vertices are neighbors.
     * Pre: The vertices are vertices in the graph.
     */
    @Override
    public boolean areAdjacent(V u, V v) {
        int indexOfU = vertices.get(u);
        int indexOfV = vertices.get(v);
        return matrix[indexOfU][indexOfV] != null;
    }

    /** Print the vertices and the edges. */
    @Override
    public void printGraph() {
        for (V v : vertices.keySet()) {
            System.out.print("Vertex: " + v + "   ");
            int vRow = vertices.get(v);
            for (int col = 0; col < matrixSize; col++) {
                System.out.printf("%-12s", matrix[vRow][col]);
            }
            System.out.println();
        }
    }

    /**
     * Add a vertex to the graph.
     * Pre: The vertex is not in the graph before this addition.
     */
    @Override
    public void addVertex(V v) {
        assert !vertices.containsKey(v);
        if (matrixSize == matrixCapacity) extendMatrix();
        vertices.put(v, matrixSize++);
    }

    private void extendMatrix() {
        matrixCapacity *= 2;
        @SuppressWarnings("unchecked")
        Edge<V>[][] newMatrix = new Edge[matrixCapacity][matrixCapacity];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        matrix = newMatrix;
    }

    /**
     * Add an edge with the specified weight between the specified vertices to the graph.
     * Pre: Before addition, the vertices are in the graph, and the edge is not in the graph.
     * Pre: The weight is not negative.
     */
    @Override
    public Edge<V> addEdge(V u, V v, int weight) {
        int indexOfU = vertices.get(u);
        int indexOfV = vertices.get(v);
        Edge<V> edge = new Edge<>(u, v, weight);
        matrix[indexOfU][indexOfV] = edge;
        matrix[indexOfV][indexOfU] = edge;
        return edge;
    }

    /**
     * Add an edge with weight 0 between the specified vertices to the graph.
     * Pre: Before addition, the vertices are in the graph, and the edge is not in the graph.
     */
    @Override
    public Edge<V> addEdge(V u, V v) {
        return addEdge(u,v,0);
    }

    /**
     * Remove the specified vertex from the graph.
     * Pre: The vertex is in the graph, and the vertex has no incident edges.
     */
    @Override
    public void removeVertex(V v) {
        assert vertices.containsKey(v);
        int indexOfRemoved = vertices.get(v);
        for (int i = 0; i < matrix.length - 1; i++) {
            if (i > indexOfRemoved) matrix[i] = matrix[i + 1];
            for (int j = indexOfRemoved; j < matrix.length - 1; j++) {
                matrix[i][j] = matrix[i][j + 1];
            }
        }
        matrix[matrixCapacity - 1] = new Edge[matrixCapacity];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][matrixCapacity - 1] = null;
        }

        vertices.remove(v);
        for (Integer value : vertices.values()) {
            if (value > indexOfRemoved) {
                value--;
            }
        }
    }

    /**
     * Remove the edge between the specified vertices from the graph.
     * Pre: The vertices are vertices in the graph,
     *   and The graph has an edge between the vertices.
     */
    @Override
    public void removeEdge(V u, V v) {
        int indexOfU = vertices.get(u);
        int indexOfV = vertices.get(v);
        matrix[indexOfU][indexOfV] = null;
        matrix[indexOfV][indexOfU] = null;
    }
}
