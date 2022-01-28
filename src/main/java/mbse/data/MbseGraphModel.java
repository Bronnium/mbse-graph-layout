package mbse.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxEdgeStyle;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxLayoutManager;

public class MbseGraphModel extends mxGraph {

    protected mxGraphLayout appliedLayout;

    protected HashMap<Object, GraphEdge> edgesTable = new HashMap<>();

    protected HashMap<Object, GraphNode> nodesTable = new HashMap<>();

    private static String defaultStyle = "tom_sawyer";

    public String[] availableLayouts = new String[] { "Hierarchical layout" };

    public MbseGraphModel(boolean option) {
        super();

        setBorder(20);
        setAutoOrigin(true);
        appliedLayout = new mxStackLayout(this, true);

        // appliedLayout.setEdgeStyleEnabled(false);

        Map<String, Object> EdgeStyle = getStylesheet().getDefaultEdgeStyle();
        EdgeStyle.put(mxConstants.STYLE_EDGE, mxEdgeStyle.ElbowConnector);
        EdgeStyle.put(mxConstants.STYLE_STROKECOLOR, "red");
        EdgeStyle.put(mxConstants.STYLE_STROKEWIDTH, 2);

        // Sets global styles
        Map<String, Object> style = getStylesheet().getDefaultEdgeStyle();
        style.put(mxConstants.STYLE_EDGE, mxEdgeStyle.EntityRelation);
        style.put(mxConstants.STYLE_ROUNDED, true);

        style = getStylesheet().getDefaultVertexStyle();
        // style.put(mxConstants.STYLE_FILLCOLOR, "#ffffff");
        style.put(mxConstants.STYLE_SHAPE, "swimlane");
        style.put(mxConstants.STYLE_STARTSIZE, 30);

        Map<String, Object> style2 = style;
        style2.clear();
        style2.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style2.put(mxConstants.STYLE_STROKECOLOR, "none");
        style2.put(mxConstants.STYLE_FILLCOLOR, "none");
        style2.put(mxConstants.STYLE_FOLDABLE, false);
        getStylesheet().putCellStyle("column", style2);

        MbseGraphModel g = this;
        mxLayoutManager layoutMgr = new mxLayoutManager(this) {

            mxStackLayout layout = new mxStackLayout(g, true);

            public mxIGraphLayout getLayout(Object parent) {

                mxCell cell = (mxCell) parent;
                if (!cell.isCollapsed()) {
                    if (cell.getParent() != graph.getCurrentRoot()) {
                        // layout.resizeParent = true;
                        // layout.horizontal = false;
                        // layout.set = 10;
                    } else {
                        // layout.resizeParent = true;
                        // layout.horizontal = true;
                        // layout.spacing = 40;
                    }

                    return layout;
                }

                return null;
            }
        };
        // layoutMgr.

        // https://jgraph.github.io/mxgraph/javascript/examples/layers.html - filtrage
        // des données visibles
        // https://jgraph.github.io/mxgraph/javascript/examples/lod.html - plus je zoom
        // plus je découvre

        // https://jgraph.github.io/mxgraph/javascript/examples/folding.html

        Object parent = getDefaultParent();
        getModel().beginUpdate();
        try {

            Object col1 = insertVertex(parent, null, "", 0, 0, 120, 0, "column");

            mxCell v1 = (mxCell) insertVertex(col1, null, "1", 0, 0, 100, 30);
            v1.setCollapsed(true);

            Object v11 = insertVertex(v1, null, "1.1", 0, 0, 80, 30);
            ((mxCell) v11).setCollapsed(true);

            Object v111 = insertVertex(v11, null, "1.1.1", 0, 0, 60, 30);
            Object v112 = insertVertex(v11, null, "1.1.2", 0, 0, 60, 30);

            Object v12 = insertVertex(v1, null, "1.2", 0, 0, 80, 30);

            Object col2 = insertVertex(parent, null, "", 0, 0, 120, 0, "column");

            Object v2 = insertVertex(col2, null, "2", 0, 0, 100, 30);
            ((mxCell) v2).setCollapsed(true);

            Object v21 = insertVertex(v2, null, "2.1", 0, 0, 80, 30);
            ((mxCell) v21).setCollapsed(true);

            Object v211 = insertVertex(v21, null, "2.1.1", 0, 0, 60, 30);
            Object v212 = insertVertex(v21, null, "2.1.2", 0, 0, 60, 30);

            Object v22 = insertVertex(v2, null, "2.2", 0, 0, 80, 30);

            Object v3 = insertVertex(col2, null, "3", 0, 0, 100, 30);
            ((mxCell) v3).setCollapsed(true);

            Object v31 = insertVertex(v3, null, "3.1", 0, 0, 80, 30);
            ((mxCell) v31).setCollapsed(true);

            Object v311 = insertVertex(v31, null, "3.1.1", 0, 0, 60, 30);
            Object v312 = insertVertex(v31, null, "3.1.2", 0, 0, 60, 30);

            Object v32 = insertVertex(v3, null, "3.2", 0, 0, 80, 30);

            insertEdge(parent, null, "", v111, v211);
            insertEdge(parent, null, "", v112, v212);
            insertEdge(parent, null, "", v112, v22);

            insertEdge(parent, null, "", v12, v311);
            insertEdge(parent, null, "", v12, v312);
            insertEdge(parent, null, "", v12, v32);
        } finally {
            getModel().endUpdate();
        }
    }

    /**
     * Model - basic data
     */
    public MbseGraphModel() {
        super();

        setBorder(20);
        setAutoOrigin(true);
        setDropEnabled(false); // drop elements on edge is disabled
        setCellsEditable(false); // elements in graph shall not be changed
        setAllowLoops(false); //
        setAllowDanglingEdges(false);

        MbseGraphStyles mbseStyles = new MbseGraphStyles();

        Hashtable<String, Hashtable<String, Object>> styles = mbseStyles.getAvailableStyles();

        styles.forEach(
                (k, v) -> this.getStylesheet().putCellStyle(k, v));

        appliedLayout = new mxCompactTreeLayout(this, false);

        getModel().beginUpdate();
        try {
            Object root = insertVertex(null, "treeRoot", "Root", 0, 0, 60, 40, defaultStyle);
            Object v1 = insertVertex(null, "v1", "Child 1", 0, 0, 60, 40, defaultStyle);
            insertEdge(null, null, "", root, v1);
            Object v2 = insertVertex(null, "v2", "Child 2", 0, 0, 60, 40, defaultStyle);
            insertEdge(null, null, "", root, v2);
            Object v3 = insertVertex(null, "v3", "Child 3", 0, 0, 60, 40, defaultStyle);
            insertEdge(null, null, "", root, v3);
            Object v11 = insertVertex(null, "v11", "Child 1.1", 0, 0, 60, 40, defaultStyle);
            insertEdge(null, null, "", v1, v11);
            Object v12 = insertVertex(null, "v12", "Child 1.2", 0, 0, 60, 40, defaultStyle);
            insertEdge(null, null, "", v1, v12);
            Object v21 = insertVertex(null, "v21", "Child 2.1", 0, 0, 60, 40, defaultStyle);
            insertEdge(null, null, "", v2, v21);
            Object v22 = insertVertex(null, "v22", "Child 2.2", 0, 0, 60, 40, defaultStyle);
            insertEdge(null, null, "", v2, v22);
            Object v221 = insertVertex(null, "v221", "Child 2.2.1", 0, 0, 60, 40, defaultStyle);
            insertEdge(null, null, "", v22, v221);
            Object v222 = insertVertex(null, "v222", "Child 2.2.2", 0, 0, 60, 40, defaultStyle);
            insertEdge(null, null, "", v22, v222);
            Object v31 = insertVertex(null, "v31", "Child 3.1", 0, 0, 60, 40, defaultStyle);
            insertEdge(null, null, "", v3, v31);
        } finally {
            getModel().endUpdate();
        }
    }

    public MbseGraphModel(HashSet<GraphNode> nodes, HashSet<GraphEdge> edges) {
        super();

        setBorder(20);
        setAutoOrigin(true);
        setDropEnabled(false); // drop elements on edge is disabled
        setCellsEditable(false); // elements in graph shall not be changed
        setAllowLoops(false); //
        setAllowDanglingEdges(false);

        MbseGraphStyles mbseStyles = new MbseGraphStyles();

        Hashtable<String, Hashtable<String, Object>> styles = mbseStyles.getAvailableStyles();

        styles.forEach(
                (k, v) -> this.getStylesheet().putCellStyle(k, v));

        appliedLayout = new mxCompactTreeLayout(this, false);

        getModel().beginUpdate();
        try {
            for (GraphNode node : nodes) {
                Object obj = insertVertex(null, node.id, node.name, 0, 0, 60, 40, defaultStyle);
                nodesTable.put(obj, node);
            }

            for (GraphEdge edge : edges) {
                Object source = getD2ElementByGUID(edge.source.id);
                Object target = getD2ElementByGUID(edge.target.id);
                Object obj = insertEdge(null, edge.id, "", source, target);
                edgesTable.put(obj, edge);
            }
        } finally {
            getModel().endUpdate();
        }
    }

    protected Object getD2ElementByGUID(String GUID) {
        return ((mxGraphModel) (getModel())).getCell(GUID);
    }

    public mxGraphLayout getAppliedLayout() {
        return appliedLayout;
    }

    public void setAppliedLayout(mxGraphLayout appliedLayout) {
        this.appliedLayout = appliedLayout;
    }

    public String getAppliedStyle() {
        return defaultStyle;
    }

    public void setAppliedStyle(String string) {
        defaultStyle = string;
    }

    public boolean isCellFoldableObject(Object cell) {
        return getOutgoingEdges(cell).length > 0;

    }

}
