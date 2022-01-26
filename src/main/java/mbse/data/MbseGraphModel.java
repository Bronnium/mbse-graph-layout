package mbse.data;

import java.util.Hashtable;
import java.util.Map;

import javax.swing.SwingConstants;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxEdgeStyle;
import com.mxgraph.view.mxGraph;

public class MbseGraphModel extends mxGraph {

    protected mxGraphLayout appliedLayout;

    private static String defaultStyle = "tom_sawyer";

    public String[] availableLayouts = new String[] { "Hierarchical layout" };

    public MbseGraphModel(boolean option) {
        super();

        setBorder(20);
        setAutoOrigin(true);
        setCellsMovable(true);
        appliedLayout = new MbseHierarchicalLayout(this, SwingConstants.WEST);

        // appliedLayout.setEdgeStyleEnabled(false);

        Map<String, Object> EdgeStyle = getStylesheet().getDefaultEdgeStyle();
        EdgeStyle.put(mxConstants.STYLE_EDGE, mxEdgeStyle.ElbowConnector);
        EdgeStyle.put(mxConstants.STYLE_STROKECOLOR, "red");
        EdgeStyle.put(mxConstants.STYLE_STROKEWIDTH, 2);

        // https://jgraph.github.io/mxgraph/javascript/examples/layers.html - filtrage
        // des données visibles
        // https://jgraph.github.io/mxgraph/javascript/examples/lod.html - plus je zoom
        // plus je découvre

        Object root = getDefaultParent();
        getModel().beginUpdate();
        try {
            mxCell container = (mxCell) insertVertex(root, "treeRoot", "System", 20, 20, 300, 300,
                    "shape=swimlane;startSize=20;");

            mxCell inPort = (mxCell) insertVertex(container, null, "in", 0, 0.5, 10, 10);
            inPort.getGeometry().setOffset(new mxPoint(-5, -5));
            inPort.getGeometry().setRelative(true);
            /*
             * mxCell outPort1 = (mxCell) insertVertex(root, "null", "", 1, 0.33, 10, 10);
             * outPort1.getGeometry().setOffset(new mxPoint(-5, -5));
             * outPort1.getGeometry().setRelative(true);
             * mxCell outPort2 = (mxCell) insertVertex(root, "null", "", 1, 0.66, 10, 10);
             * outPort2.getGeometry().setOffset(new mxPoint(-5, -5));
             * outPort2.getGeometry().setRelative(true);
             */
            mxCell v1 = (mxCell) insertVertex(container, "f1", "Function 1", 0, 0, 60, 40);

            /*
             * mxCell inPortF1 = (mxCell) insertVertex(v1, "null", "", 0, 0.5, 10, 10);
             * inPortF1.getGeometry().setOffset(new mxPoint(-5, -5));
             * inPortF1.getGeometry().setRelative(true);
             * 
             * mxCell outPortF1 = (mxCell) insertVertex(v1, "null", "", 1, 0.5, 10, 10);
             * outPortF1.getGeometry().setOffset(new mxPoint(-5, -5));
             * outPortF1.getGeometry().setRelative(true);
             */
            insertEdge(null, null, "", container, v1,
                    "startArrow=oval;endArrow=block;startFill=0;endFill=0;");

            mxCell v2 = (mxCell) insertVertex(container, "f2", "Function 2", 0, 0, 60, 40);

            /*
             * mxCell inPortF2 = (mxCell) insertVertex(v2, "null", "", 0, 0.5, 10, 10);
             * inPortF2.getGeometry().setOffset(new mxPoint(-5, -5));
             * inPortF2.getGeometry().setRelative(true);
             * 
             * mxCell outPortF2 = (mxCell) insertVertex(v2, "null", "", 1, 0.5, 10, 10);
             * outPortF2.getGeometry().setOffset(new mxPoint(-5, -5));
             * outPortF2.getGeometry().setRelative(true);
             */
            insertEdge(null, null, "", v1, v2);
            insertEdge(null, null, "", v2, container);
            // insertEdge(null, null, "", outPortF2, outPort2);
            /*
             * 
             * 
             * Object v2 = insertVertex(root, "f2", "Function 2", 0, 0, 60, 40);
             * 
             * Object v3 = insertVertex(null, "v3", "Child 3", 0, 0, 60, 40);
             * insertEdge(null, null, "", root, v3);
             * 
             * Object v11 = insertVertex(null, "v11", "Child 1.1", 0, 0, 60, 40);
             * insertEdge(null, null, "", v1, v11);
             * 
             * Object v12 = insertVertex(null, "v12", "Child 1.2", 0, 0, 60, 40);
             * insertEdge(null, null, "", v1, v12);
             * 
             * Object v21 = (mxCell) insertVertex(null, "v21", "Child 2.1", 0, 0, 60, 40);
             * insertEdge(null, null, "", v2, v21);
             * 
             * Object v22 = insertVertex(null, "v22", "Child 2.2", 0, 0, 60, 40);
             * insertEdge(null, null, "", v2, v22);
             * 
             * Object v221 = insertVertex(null, "v221", "Child 2.2.1", 0, 0, 60, 40);
             * insertEdge(null, null, "", v22, v221);
             * 
             * Object v222 = insertVertex(null, "v222", "Child 2.2.2", 0, 0, 60, 40);
             * insertEdge(null, null, "", v22, v222);
             * 
             * Object v31 = insertVertex(null, "v31", "Child 3.1", 0, 0, 60, 40);
             * insertEdge(null, null, "", v3, v31);
             */
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

        setCellsMovable(false);

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
