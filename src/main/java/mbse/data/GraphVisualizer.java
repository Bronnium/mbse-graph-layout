package mbse.data;

import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

public class GraphVisualizer {
	private static final Logger log = Logger.getLogger(GraphVisualizer.class.getName());

	public GraphVisualizer() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed set look and feel user interface.", e);
		}

		// create an instance of a MBSE model
		MbseGraphModel mbseGraphModel = new MbseGraphModel();

		// create an instance of a Mbse View
		MbseGraphView mbseGraphView = new MbseGraphView();

		// creates an instance of a MBSE Controller
		MbseGraphController mbseGraphController = new MbseGraphController(mbseGraphModel, mbseGraphView);

		mbseGraphController.displayView();
	}

	public GraphVisualizer(HashSet<GraphNode> nodes, HashSet<GraphEdge> edges) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed set look and feel user interface.", e);
		}

		// create an instance of a MBSE model
		MbseGraphModel mbseGraphModel = new MbseGraphModel(nodes, edges);

		// create an instance of a Mbse View
		MbseGraphView mbseGraphView = new MbseGraphView();

		// creates an instance of a MBSE Controller
		MbseGraphController mbseGraphController = new MbseGraphController(mbseGraphModel, mbseGraphView);

		mbseGraphController.displayView();
	}
}
