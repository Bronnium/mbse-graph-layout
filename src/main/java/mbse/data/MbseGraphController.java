package mbse.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphComponent.mxGraphControl;

public class MbseGraphController {

	private MbseGraphView view;
	private MbseGraphModel model;
	
	private ActionListener actionListener;
	private ChangeListener changeListener;

	private MouseListener mouseListener;
	
	public MbseGraphController(MbseGraphModel mbseGraphModel, MbseGraphView mbseGraphView) {
		model = mbseGraphModel;
		view = mbseGraphView;


		view.addMbseGraphComponent(model);
		addViewControls();
		for (String item : model.availableLayouts) {
			view.getLayoutSelection().addItem(item);
		}
		view.resizeCombox();

	}

	/**
	 * Display the user interface
	 */
	public void displayView() {
		model.getAppliedLayout().execute(model.getDefaultParent());

		view.setVisible(true);
	}

	/**
	 * Allows to create all listeners and pass for the view.
	 */
	public void addViewControls() {
		actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {                  
				linkBtnAndLabel(actionEvent);
			}
		};                


		changeListener = new ChangeListener(){
			public void stateChanged(ChangeEvent event) {
				changedState(event);
			}
		};


		// Installs the popup menu in the graph component
		mouseListener = new MouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent e) {
				rightClickMenu(e);
			}
			/*
			public void mousePressed(MouseEvent e)
			{
				// Handles context menu on the Mac where the trigger is on mousepressed
				mouseReleased(e);
			}

			public void mouseReleased(MouseEvent e)
			{
				if (e.isPopupTrigger())
				{
					System.out.println("click");//showGraphPopupMenu(e);

					popupmenu.show(graphComponent, e.getX(), e.getY());
					//graphComponent.getGraph().getSelectionCell()
					System.out.println("Selected cell:"+graphComponent.getGraph().getSelectionCell());
				}
				else
					System.out.println("when ?");
			}
			*/


		};


		view.addInputControl(actionListener, changeListener,mouseListener);
	}

	private void rightClickMenu(MouseEvent e) {
		//System.out.println("Selected cell:"+e);
		
		System.out.println(e.getComponent());
		
		if (e.getComponent() instanceof mxGraphControl) {
			mxGraphControl graphControl = (mxGraphControl) e.getComponent();
			
			mxGraphComponent graphComponent = graphControl.getGraphContainer();
			if (graphComponent.getGraph().getSelectionCell() != null)
			{
				//selectedCell = (mxCell) graphComponent.getGraph().getSelectionCell();
				view.displayPopupMenu(e.getX(), e.getY());
			}
			else
			{
				System.out.println("Cell not selected");
			}
		}
		else
		{
			System.out.println("not mxGraphComponent");
		}
		//popupmenu.show(graphComponent, e.getX(), e.getY());
	}
	private void changedState(ChangeEvent event) {
		System.out.println("change listnener triggered"+event.getSource());
		if (event.getSource() instanceof JSlider) {
			JSlider slider = (JSlider) event.getSource();


			// spacing value is applied only when slider is released
			if (slider.getValueIsAdjusting())
				return;

			//int spacing = slider.getValue();

			if (slider.getName().equals("HorizontalSpacing")) 
			{
				//((MbseLayout) currentAppliedLayout).setHorizontalSpacing(spacing);
				//model.getAppliedLayout().setHorizontalSpacing(spacing);

			}
			else // verticalSpacing
			{
				//model.getAppliedLayout().setVerticalSpacing(spacing);
				//((MbseLayout) currentAppliedLayout).setVerticalSpacing(spacing);
			}

			model.getAppliedLayout().execute(model.getDefaultParent());
		}
	}

	private void linkBtnAndLabel(ActionEvent event){
		//model.incX();                
		//view.setText(Integer.toString(model.getX()));
		System.out.println("action listnener triggered"+event.getSource());
/*
		if (event.getSource() instanceof JMenuItem) {
			JMenuItem leaf = (JMenuItem) event.getSource();
			RateauLayout layout = new RateauLayout();
			model.setAppliedLayout(layout);
			model.getAppliedLayout().execute(selectedCell);
		}
		*/
		/*public void setSpacing(ChangeEvent event) {
			JSlider slider = (JSlider) event.getSource();

			// spacing value is applied only when slider is released
			if (slider.getValueIsAdjusting())
				return;

			int spacing = slider.getValue();

			if (slider.getName().equals("HorizontalSpacing")) 
			{
				((MbseLayout) currentAppliedLayout).setHorizontalSpacing(spacing);

			}
			else // verticalSpacing
			{
				((MbseLayout) currentAppliedLayout).setVerticalSpacing(spacing);
			}

			model.executeLayout();

		}
		 */
	} 

}
