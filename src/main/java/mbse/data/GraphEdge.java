package mbse.data;

public class GraphEdge {

    public GraphNode source;
    public GraphNode target;

    GraphEdge(GraphNode _source, GraphNode _target) {
        source = _source;
        target = _target;
    }
}
