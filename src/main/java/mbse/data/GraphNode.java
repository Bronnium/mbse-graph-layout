package mbse.data;

import java.util.UUID;

import com.mxgraph.model.mxGeometry;

public class GraphNode {

    public String id;
    public String name;

    public double x, y;

    GraphNode(String _name) {
        name = _name;
        id = UUID.randomUUID().toString();
        x = y = 0;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setGeometry(mxGeometry geometry) {
        System.out.println("before: " + x + ", " + y);
        x = geometry.getX();
        y = geometry.getY();
        System.out.println(geometry.toString());
    }
}
