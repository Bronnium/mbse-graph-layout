package mbse.data;

import java.util.List;
import java.util.UUID;

import com.mxgraph.util.mxPoint;

public class GraphEdge {

    public GraphNode source;
    public GraphNode target;
    public String id;

    GraphEdge(GraphNode _source, GraphNode _target) {
        source = _source;
        target = _target;
        id = UUID.randomUUID().toString();
    }

    public void setGeometry(List<mxPoint> list) {
        System.out.println("before");

        System.out.println(list.toString());
    }
}
