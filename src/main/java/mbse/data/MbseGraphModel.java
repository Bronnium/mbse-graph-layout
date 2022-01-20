package mbse.data;

import java.util.Hashtable;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

public class MbseGraphModel extends mxGraph {

    protected mxGraphLayout appliedLayout;

    private static String defaultStyle = "tom_sawyer";

    public String[] availableLayouts = new String[] { "Effective Java", "Head First Java",
            "Thinking in Java", "Java for Dummies" };

    public MbseGraphModel(boolean option) {
        super();

        setAutoOrigin(true);

        Hashtable<String, Object> style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_OPACITY, 50);

        style.put(mxConstants.STYLE_ROUNDED, true);
        style.put(mxConstants.STYLE_IMAGE, "file:/C:\\dev\\bell.png");
        style.put(mxConstants.STYLE_IMAGE_HEIGHT, 100); // padding de l'image
        style.put(mxConstants.STYLE_IMAGE_WIDTH, 100); // padding de l'image
        style.put(mxConstants.STYLE_FOLDABLE, 1); // padding de l'image
        // style.put(mxConstants.DEFAULT_IMAGESIZE, 50);
        // style.put(mxConstants.STYLE_INDICATOR_IMAGE, "file:/C:\\dev\\bell.png");
        // style.put(mxConstants.STYLE_IMAGE,
        // "https://clipartmag.com/images/vintage-airplane-silhouette-8.png");
        style.put(mxConstants.STYLE_IMAGE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP);
        style.put(mxConstants.STYLE_IMAGE_ALIGN, mxConstants.ALIGN_CENTER);
        // style.put(mxConstants., mxConstants.ALIGN_CENTER);
        // style.put(mxConstants.STYLE_SPACING_TOP, 5);
        style.put(mxConstants.STYLE_FILLCOLOR, "#e0f4ef");
        style.put(mxConstants.STYLE_STROKECOLOR, "#69cdb5");
        style.put(mxConstants.STYLE_STROKEWIDTH, 3);
        style.put(mxConstants.STYLE_FONTFAMILY, "Verdana");
        style.put(mxConstants.STYLE_SPACING_TOP, 25);
        style.put(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD);
        // style.put(mxConstants.STYLE_FONTCOLOR, "#FF0000");
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_LABEL);
        style.put(mxConstants.STYLE_INDICATOR_SHAPE, mxConstants.SHAPE_LABEL);

        // stylesheet.putCellStyle("ROUNDED", style);

        this.getStylesheet().putCellStyle("imageTest", style);
        appliedLayout = new DefaultMbseLayout(this);
        // appliedLayout = new CallStackLayout(this);
        Object parent = getDefaultParent();

        Object root = insertVertex(parent, "treeRoot", "Root", 0, 0, 60, 40);

        String styleEnfant1 = mxConstants.STYLE_FILLCOLOR + "=#00ff00";
        // mxConstants.shape
        String styleEnfant2 = mxConstants.STYLE_FILLCOLOR + "=#ffffff";
        mxCell v1 = (mxCell) insertVertex(parent, "v1", "Child 1", 0, 0, 60, 40, styleEnfant1);
        insertEdge(parent, null, "", root, v1);

        // https://jgraph.github.io/mxgraph/javascript/examples/images.html

        // border color =#69cdb5 2 ou 3px epaisseur
        // interieur = #e0f4ef
        String styleArrondi = mxConstants.STYLE_ROUNDED + "=1;";
        // styleArrondi+=styleEnfant1;
        styleArrondi += mxConstants.STYLE_FILLCOLOR + "=#e0f4ef;";

        styleArrondi += mxConstants.STYLE_IMAGE + "=file:/C:\\dev\\bell.png;" + mxConstants.STYLE_IMAGE_VERTICAL_ALIGN
                + "=" + mxConstants.ALIGN_RIGHT + ";";
        // graph.getView().getState(cell).getStyle().replace(mxConstants.STYLE_IMAGE,
        // new ImageIcon(
        // GraphViewer.class.getResource("/com/mxgraph/examples/swing/images/cube_green.png")));

        /*
         * mxStylesheet stylesheet = this.getStylesheet();
         * 
         * Hashtable<String, Object> style = new Hashtable<String, Object>();
         * style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
         * style.put(mxConstants.STYLE_OPACITY, 50);
         * style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
         * stylesheet.putCellStyle("ROUNDED", style);
         */

        /*
         * Style = graph.getStylesheet().getDefaultVertexStyle();
         * style[mxConstants.STYLE_SHAPE] = mxConstants.STYLE_IMAGE;
         * style[mxConstants.STYLE_IMAGE] = 'images/sourceIcon_Sample2.png;';
         */

        styleArrondi += mxConstants.STYLE_STROKECOLOR + "=#69cdb5;" + mxConstants.STYLE_STROKEWIDTH + "=2;";
        System.out.println(styleArrondi);
        Object v2 = insertVertex(parent, "v2", "Child 2", 0, 0, 60, 40, styleArrondi);
        insertEdge(parent, null, "", root, v2);

        Object v3 = insertVertex(parent, "v3", "Child 3", 0, 0, 60, 40, styleEnfant2);
        insertEdge(parent, null, "", root, v3);

        Object v11 = insertVertex(parent, "v11", "Child 1.1", 0, 0, 160, 140, "imageTest");
        insertEdge(parent, null, "", v1, v11);

        Object v12 = insertVertex(parent, "v12", "Child 1.2", 0, 0, 60, 40);
        insertEdge(parent, null, "", v1, v12);

        Object v21 = (mxCell) insertVertex(parent, "v21", "Child 2.1", 0, 0, 60, 40);
        System.out.println(this.getCellStyle(v21));
        insertEdge(parent, null, "", v2, v21);

        Object v22 = insertVertex(parent, "v22", "Child 2.2", 0, 0, 60, 40);
        insertEdge(parent, null, "", v2, v22);

        Object v221 = insertVertex(parent, "v221", "Child 2.2.1", 0, 0, 60, 40);
        insertEdge(parent, null, "", v22, v221);

        Object v222 = insertVertex(parent, "v222", "Child 2.2.2", 0, 0, 60, 40);
        insertEdge(parent, null, "", v22, v222);

        Object v31 = insertVertex(parent, "v31", "Child 3.1", 0, 0, 60, 40);
        insertEdge(parent, null, "", v3, v31);

    }

    /**
     * Model - basic data
     */
    public MbseGraphModel() {
        super();

        MbseGraphStyles mbseStyles = new MbseGraphStyles("/styles.xml");

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
