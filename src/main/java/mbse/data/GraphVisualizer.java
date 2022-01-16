package mbse.data;

import javax.swing.UIManager;

public class GraphVisualizer {
	public static void main(String[] args) {

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		// create an instance of a MBSE model
		MbseGraphModel mbseGraphModel = new MbseGraphModel();

		// create an instance of a Mbse View
		MbseGraphView mbseGraphView = new MbseGraphView();

		// creates an instance of a MBSE Controller
		MbseGraphController mbseGraphController = new MbseGraphController(mbseGraphModel,mbseGraphView);

		mbseGraphController.displayView();
	}
}
