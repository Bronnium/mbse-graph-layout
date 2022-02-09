package mbse.data;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

public class GraphVisualizer {

	private static final Logger log = Logger.getLogger(GraphVisualizer.class.getName());

	/**
	 * Application uses a MVC pattern
	 * 
	 * @param nodes
	 * @param edges
	 */
	public GraphVisualizer(Set<GraphNode> nodes, Set<GraphEdge> edges) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			createApplication(nodes, edges);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed set look and feel user interface.", e);
		}

	}

	private void createApplication(Set<GraphNode> nodes, Set<GraphEdge> edges) {
		// create an instance of a MBSE model
		MbseGraphModel mbseGraphModel = new MbseGraphModel(nodes, edges);

		// creates an instance of a MBSE Controller
		MbseGraphController mbseGraphController = new MbseGraphController(mbseGraphModel);

		// create an instance of a MBSE View
		MbseGraphView mbseGraphView = new MbseGraphView(mbseGraphController);

		mbseGraphController.setView(mbseGraphView);

		mbseGraphController.displayView();
	}
}
