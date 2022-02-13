package mbse.data;

import java.util.ArrayList;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;

public class StructureLayout extends mxCompactTreeLayout {

    public StructureLayout(mxGraph graph, boolean horizontal) {
        super(graph, horizontal);
    }

    public StructureLayout(mxGraph graph) {
        super(graph);
    }

    public StructureLayout(mxGraph graph, boolean horizontal, boolean invert) {
        super(graph, horizontal, invert);
    }

    @Override
    public void execute(Object parent) {
        // Execute the CompactTreeLayout
        super.execute(parent);

        // Modify the edges to ensure they exit the source cell at the midpoint
        // get all the vertexes
        Object[] vertexes = ((mxGraphModel) graph.getModel()).getChildVertices(graph.getModel(),
                graph.getDefaultParent());

        for (int i = 0; i < vertexes.length; i++) {
            mxICell parentCell = ((mxICell) (vertexes[i]));

            // For each edge of the vertex
            for (int j = 0; j < parentCell.getEdgeCount(); j++) {
                mxICell edge = parentCell.getEdgeAt(j);
                // Only consider edges that are from the cell
                if (edge.getTerminal(true) != parentCell) {
                    continue;
                }
                mxRectangle parentBounds = getVertexBounds(parentCell);

                mxRectangle childBounds = getVertexBounds(edge.getTerminal(false));

                double x = 0, y = 0;

                ArrayList<mxPoint> newPoints = new ArrayList<>(4);

                x = parentBounds.getCenterX();
                y = parentBounds.getY() + parentBounds.getHeight();
                newPoints.add(new mxPoint(x, y));

                double centerYOffset = (childBounds.getY() - y) / 2;
                y = y + centerYOffset;
                newPoints.add(new mxPoint(x, y));

                x = childBounds.getCenterX();
                newPoints.add(new mxPoint(x, y));

                y = childBounds.getY();
                newPoints.add(new mxPoint(x, y));

                setEdgePoints(edge, newPoints);
            }

        }

    }

}