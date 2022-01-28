package mbse.data;

import java.util.HashSet;

public class Main {

    private static HashSet<GraphNode> nodes = new HashSet<>();
    private static HashSet<GraphEdge> edges = new HashSet<>();

    public static void main(String[] args) {
        createDummyData();
        new GraphVisualizer(nodes, edges);
        // new GraphVisualizer();
    }

    public static void createDummyData() {
        GraphNode root = new GraphNode("BAS");
        GraphNode v1 = new GraphNode("Child 1");
        GraphNode v2 = new GraphNode("Child 2");
        GraphNode v3 = new GraphNode("Child 3");
        GraphNode v11 = new GraphNode("Child 1.1");

        // IRPGraphElement - vertex
        nodes.add(root);
        nodes.add(v1);
        nodes.add(v2);
        nodes.add(v3);
        nodes.add(v11);

        // IRPGraphElement - edge
        GraphEdge e1 = new GraphEdge(root, v1);
        edges.add(e1);
        GraphEdge e2 = new GraphEdge(root, v2);
        edges.add(e2);
        GraphEdge e3 = new GraphEdge(root, v3);
        edges.add(e3);
        GraphEdge e4 = new GraphEdge(v1, v11);
        edges.add(e4);

    }
}
