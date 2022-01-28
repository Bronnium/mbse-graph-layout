package mbse.data;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.view.mxGraph;

public class MbseHierarchicalLayout extends mxHierarchicalLayout {

    public MbseHierarchicalLayout(mxGraph graph) {
        super(graph);
    }

    MbseHierarchicalLayout(mxGraph graph, int orientation) {
        super(graph, orientation);
    }

    @Override
    public void execute(Object parent) {
        super.execute(parent);
    }

}