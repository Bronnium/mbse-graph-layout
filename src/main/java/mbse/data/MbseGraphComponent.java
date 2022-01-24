package mbse.data;

import java.awt.event.MouseEvent;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class MbseGraphComponent extends mxGraphComponent {

    public MbseGraphComponent(mxGraph graph) {
        super(graph);

        setPanning(true);
        setConnectable(false);
    }

    /**
     * change the behavior of panning mouse wheel click is enough for panning
     */
    @Override
    public boolean isPanningEvent(MouseEvent event) {
        return (event != null) ? event.getButton() == MouseEvent.BUTTON2 : false;
    }

}