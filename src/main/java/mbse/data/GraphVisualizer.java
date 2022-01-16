package mbse.data;

import javax.swing.UIManager;

import jdk.jfr.internal.LogLevel;
import jdk.jfr.internal.LogTag;
import jdk.jfr.internal.Logger;

public class GraphVisualizer {
	public static void main(String[] args) {

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e1)
		{
			Logger.log(LogTag.JFR_EVENT, LogLevel.DEBUG, e1.getMessage());
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
