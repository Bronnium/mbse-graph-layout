package mbse.data;

import java.util.ArrayList;
import java.util.List;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraph.mxICellVisitor;

public class RootLayout extends mxCompactTreeLayout {

	private static final double OFFSET_X = 0.25;
	private static final int OFFSET_Y = 50;

	public RootLayout(mxGraph model) {
		super(model, true);
	}

	@Override
	public void execute(Object parent) {
		super.execute(parent);

		ArrayList<Object> roots = new ArrayList<Object>();

		graph.traverse(parent, true, new mxICellVisitor() {
			public boolean visit(Object vertex, Object edge) {
				roots.add(vertex);
				return true;
			}
		});
		// remove first object in the list (correspond to selected node)
		roots.remove(0);

		mxGeometry parentGeo = ((mxCell) parent).getGeometry();

		graph.getModel().beginUpdate();
		try {
			for (int i = 0; i < roots.size(); i++) {
				Object currentCell = roots.get(i);

				mxGeometry geo = (mxGeometry) graph.getCellGeometry(currentCell).clone();
				geo.setX(parentGeo.getCenterX());
				geo.setY(parentGeo.getCenterY() + parentGeo.getHeight() + OFFSET_Y * i);

				graph.getModel().setGeometry(currentCell, geo);
			}

			rearrangeEdges();

		} finally {
			graph.getModel().endUpdate();
		}
	}

	/**
	 * reroute edges of leaf nodes: 3 points are created
	 */
	private void rearrangeEdges() {
		Object[] edges = graph.getOutgoingEdges(parent);

		for (Object edge : edges) {

			if (edge instanceof mxCell) {
				mxCell edgeCell = (mxCell) edge;
				mxGeometry source = edgeCell.getSource().getGeometry();
				mxGeometry target = edgeCell.getTarget().getGeometry();

				double x = 0, y = 0;
				List<mxPoint> newPoints = new ArrayList<mxPoint>(3);

				// first point
				x = source.getX() + source.getWidth() * OFFSET_X; // 25% of x of the source
				y = source.getY() + source.getHeight();
				newPoints.add(new mxPoint(x, y));

				// second point
				// x is identical
				y = target.getCenterY();
				newPoints.add(new mxPoint(x, y));

				// third point
				x = target.getX();
				// y is identical
				newPoints.add(new mxPoint(x, y));

				setEdgePoints(edge, newPoints);
			}

		}
	}

}
