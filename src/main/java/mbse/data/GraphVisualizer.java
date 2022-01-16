package mbse.data;

import java.util.LinkedList;

import javax.swing.UIManager;

public class GraphVisualizer {
	
	private static LinkedList<D2Element> vertexList;
	private static LinkedList<D2Line> edgeList;
	
	public static void main(String[] args) {
		dummyData();
		// https://www.flaticon.com/free-icon/flow-chart_648929?term=tree%20graph&page=1&position=5&page=1&position=5&related_id=648929&origin=search
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		
		// create an instance of a MBSE model
		MbseGraphModel mbseGraphModel = new MbseGraphModel(vertexList, edgeList);

		// create an instance of a Mbse View
		MbseGraphView mbseGraphView = new MbseGraphView();

		// creates an instance of a MBSE Controller
		MbseGraphController mbseGraphController = new MbseGraphController(mbseGraphModel,mbseGraphView);

		mbseGraphController.displayView();
	}

	private static void dummyData() {
vertexList = new LinkedList<D2Element>();
		
		vertexList.add(new D2Element("0", "Function"));
		vertexList.add(new D2Element("1", "Function 1"));
		vertexList.add(new D2Element("1-1", "Function 1-1"));
		vertexList.add(new D2Element("1-2", "Function 1-2"));
		vertexList.add(new D2Element("2", "Function 2"));
		
		edgeList = new LinkedList<D2Line>();
		edgeList.add(new D2Line("a", "", "0", "1"));
		edgeList.add(new D2Line("b", "", "1", "1-1"));
		edgeList.add(new D2Line("c", "", "1", "1-2"));
		edgeList.add(new D2Line("d", "", "0", "2"));
	}
	
	
}
