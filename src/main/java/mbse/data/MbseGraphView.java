package mbse.data;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.TransferHandler;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxResources;
import com.mxgraph.view.mxGraph;

/**
 * Main class for MBSE Graph Visualization
 * @author
 *
 */
public class MbseGraphView extends JFrame {

	public JComboBox getLayoutSelection() {
		return layoutSelection;
	}

	public void setLayoutSelection(JComboBox layoutSelection) {
		this.layoutSelection = layoutSelection;
	}
	
	

	private JPanel contentPane;

	private JButton btnZoomIn;
	private JButton btnZoomOut;
	private JButton btnZoomFit;
	private JSlider horizontalSpacingSlide, verticalSpacingSlide;

	// graphical component for MbseGraph
	protected mxGraphComponent graphComponent;

	private JPopupMenu popupmenu;

	private JComboBox layoutSelection;
	
	private JCheckBox changeStyle;

	private JMenuItem cut;

	private JMenuItem copy;

	private JMenuItem paste;
	
	private JMenuItem lock;

	private JMenuItem displayAsLeaf;
	
	private String path ="/flow-chart.png";

	public MbseGraphView() {
		super("MBSE Graph Visualizer");

		// sets size
		this.setSize(800, 600);
		// centers the frame
		this.setLocationRelativeTo(null);
		// define behavior of close button
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setIconImage(new ImageIcon(getClass().getResource("/flow-chart.png")).getImage());
		
		initUI();
	}

	private void initUI() {
		// Construction et injection de la barre d'outils
		contentPane = (JPanel) this.getContentPane();
		
        
        contentPane.add(createPopupMenu());

		contentPane.add(createMainToolBar(), BorderLayout.NORTH);
		contentPane.add(createSecondaryToolBar(), BorderLayout.EAST);
	}


	/**
	 * Handling the popup menu on right click
	 * @return Component
	 */
	private Component createPopupMenu() {
		popupmenu = new JPopupMenu("Edit");
        
		displayAsLeaf = new JMenuItem("display as leafs");
		displayAsLeaf.setIcon(new ImageIcon(getClass().getResource("/tree-structure.png")));
        copy = new JMenuItem("Copy");  
        paste = new JMenuItem("Paste"); 
        
        lock = new JMenuItem("Lock / Unlock", new ImageIcon(getClass().getResource("/padlock.png")));
        popupmenu.add(displayAsLeaf);
        popupmenu.add(lock);
        return popupmenu;
	}
	
	public void mnuUndoListener( ActionEvent event ) {
        JOptionPane.showMessageDialog( this, "Undo!" );
    }
	
	private Component createSecondaryToolBar() {
		JToolBar toolBar = new JToolBar("Layout Properties",JToolBar.VERTICAL);

		// empeche la barre d'etre boug�e
		toolBar.setFloatable(false);
		
		//
		toolBar.add(new JLabel("Select layout"));
		layoutSelection = new JComboBox<Object>();
		
		//layoutSelection
		toolBar.add(layoutSelection);

		toolBar.add(new JLabel("Set horizontal spacing"));

		horizontalSpacingSlide = new JSlider(JSlider.HORIZONTAL,5,100,20);
		horizontalSpacingSlide.setPaintTicks(true);
		horizontalSpacingSlide.setPaintLabels(true);
		horizontalSpacingSlide.setMinorTickSpacing(10);
		horizontalSpacingSlide.setMajorTickSpacing(20);
		horizontalSpacingSlide.setName("HorizontalSpacing");
		//horizontalSpacingSlide.addChangeListener(changeListener);
		toolBar.add(horizontalSpacingSlide);

		toolBar.add(new JLabel("Set vertical spacing"));

		verticalSpacingSlide = new JSlider(JSlider.HORIZONTAL,5,100,20);
		verticalSpacingSlide.setPaintTicks(true);
		verticalSpacingSlide.setPaintLabels(true);
		verticalSpacingSlide.setMinorTickSpacing(10);
		verticalSpacingSlide.setMajorTickSpacing(20);
		verticalSpacingSlide.setName("VerticalSpacing");
		//verticalSpacingSlide.addChangeListener(changeListener);
		toolBar.add(verticalSpacingSlide);

		JButton btnSaveDiagram = new JButton("Save diagram");
		//btnSaveDiagram.addActionListener(actionListener);
		toolBar.add(btnSaveDiagram);

		return toolBar;
	}

	private Component createMainToolBar() {
		// La barre d'outils � proprement parler
		JToolBar toolBar = new JToolBar();

		// empeche la barre d'etre boug�e
		toolBar.setFloatable(false);

		btnZoomIn = new JButton("ZoomIn", new ImageIcon(getClass().getResource("/zoom_in.png")));
		//btnZoomIn.addActionListener(actionListener);
		toolBar.add(btnZoomIn);

		btnZoomOut = new JButton("ZoomOut", new ImageIcon(getClass().getResource("/zoom_out.png")));
		//btnZoomOut.addActionListener(actionListener);
		toolBar.add(btnZoomOut);

		btnZoomFit = new JButton("ZoomFit", new ImageIcon(getClass().getResource("/zoom_fit.png")));
		//btnZoomFit.addActionListener(actionListener);
		toolBar.add(btnZoomFit);
		
		changeStyle = new JCheckBox("UML Style");
		ActionListener actionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		          boolean selected = abstractButton.getModel().isSelected();
		          if(selected) {
mxCell node = (mxCell) ((MbseGraphModel) graphComponent.getGraph()).saveForLater;
		        	  
		        	  graphComponent.getGraph().getModel().setStyle(node, "fillColor=#ffffff");
		          }
		          else {
mxCell node = (mxCell) ((MbseGraphModel) graphComponent.getGraph()).saveForLater;
		        	  
		        	  graphComponent.getGraph().getModel().setStyle(node, "fillColor=#fff00f");
		          }
		          // abstractButton.setText(newLabel);
		        	  //graphComponent.getGraph().getModel()
		        	  
		        }
		      };
		changeStyle.addActionListener(actionListener);
		toolBar.add(changeStyle);

		return toolBar;
	}

	public void addMbseGraphComponent(MbseGraphModel model) {
		graphComponent = new mxGraphComponent(model);
		
		
		
		contentPane.add(graphComponent);
	}

	public void addInputControl(ActionListener actionListener, ChangeListener changeListener, MouseListener mouseListener) {
		graphComponent.getGraphControl().addMouseListener(mouseListener);
		
		btnZoomIn.addActionListener(actionListener);
		
		layoutSelection.addActionListener(actionListener);
		
		// right click menu
		displayAsLeaf.addActionListener(actionListener);
		
		horizontalSpacingSlide.addChangeListener(changeListener);
		verticalSpacingSlide.addChangeListener(changeListener);
	}

	public void displayPopupMenu(int x, int y) {
		// TODO Auto-generated method stub
		popupmenu.show(graphComponent, x, y);
		
	}
	
	
}
