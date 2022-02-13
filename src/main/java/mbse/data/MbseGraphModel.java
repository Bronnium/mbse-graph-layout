package mbse.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;

public class MbseGraphModel extends mxGraph {

    protected mxGraphLayout appliedLayout;

    protected HashMap<Object, GraphEdge> edgesTable = new HashMap<>();

    protected HashMap<Object, GraphNode> nodesTable = new HashMap<>();

    private String appliedStyle = "tom_sawyer";

    public String[] availableLayouts = new String[] { "Breakdown Structure Layout" };

    // https://jgraph.github.io/mxgraph/javascript/examples/layers.html - filtrage
    // des données visibles
    // https://jgraph.github.io/mxgraph/javascript/examples/lod.html - plus je zoom
    // plus je découvre

    // https://jgraph.github.io/mxgraph/javascript/examples/folding.html

    /**
     * Model - basic data used for PBS and FBS diagrams
     */
    public MbseGraphModel() {
        super();

        this.appliedStyle = "tom_sawyer";

        setBorder(20);
        setAutoOrigin(true);
        setDropEnabled(false); // drop elements on edge is disabled
        setCellsEditable(false); // elements in graph shall not be changed
        setAllowLoops(false); //
        setAllowDanglingEdges(false);

        MbseGraphStyles mbseStyles = new MbseGraphStyles();

        Map<String, Map<String, Object>> styles = (Map) mbseStyles.getAvailableStyles();

        styles.forEach(
                (k, v) -> this.getStylesheet().putCellStyle(k, v));

        appliedLayout = new mxCompactTreeLayout(this, false);
    }

    public void addDataToModel(Set<GraphNode> nodes, Set<GraphEdge> edges) {
        getModel().beginUpdate();
        try {
            for (GraphNode node : nodes) {
                Object obj = insertVertex(null, node.id, node.name, 0, 0, 60, 40, appliedStyle);
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

    public MbseGraphModel(Set<GraphNode> nodes, Set<GraphEdge> edges) {
        this();

        addDataToModel(nodes, edges);
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
        return appliedStyle;
    }

    public void setAppliedStyle(String style) {
        this.appliedStyle = style;
    }

    public boolean isCellFoldableObject(Object cell) {
        return getOutgoingEdges(cell).length > 0;

    }

    public void updateGraphModel() {
        Object[] allVertex = getChildCells(getDefaultParent(), true, false);
        setCellStyle(appliedStyle, allVertex);
    }

}
