package mbse.data;

import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

public class GraphVisualizer {
	private static final Logger log = Logger.getLogger(GraphVisualizer.class.getName());
	private static HashSet<String> nodes;
	private static HashSet edges;

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			log.log(Level.SEVERE, "Failed set look and feel user interface.", e);
		}

		// create an instance of a MBSE model
		MbseGraphModel mbseGraphModel = new MbseGraphModel(true);

		// create an instance of a Mbse View
		MbseGraphView mbseGraphView = new MbseGraphView();

		// creates an instance of a MBSE Controller
		MbseGraphController mbseGraphController = new MbseGraphController(mbseGraphModel, mbseGraphView);

		mbseGraphController.displayView();
	}

	public static void createDummyData() {
		nodes = new HashSet<>();

		nodes.add("root");
		nodes.add("Child 1");
		nodes.add("Child 2");
		nodes.add("Child 3");

		edges = new HashSet<>();

	}
}
