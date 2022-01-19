package mbse.data;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeListener;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

/**
 * Main class for MBSE Graph Visualization
 * 
 * @author
 *
 */
public class MbseGraphView extends JFrame {

	public JComboBox<Object> getLayoutSelection() {
		return layoutSelection;
	}

	public void setLayoutSelection(JComboBox<Object> layoutSelection) {
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

	private JComboBox<Object> layoutSelection;

	public JCheckBox changeStyle;

	private JMenuItem lock;

	private JMenuItem displayAsLeaf;

	/**
	 * Contains the path to icon that will be displayed in the frame.
	 * Image file (png) shall be included in resources package
	 */
	private final String ICO_STRING = "/flow-chart.png";
	private static final String FRAME_TITLE = "MBSE Graph Visualizer";

	private final int FRAME_WIDTH = 800;
	private final int FRAME_HEIGHT = 600;

	private JButton btnSelect;

	private JButton btnPan;

	private JButton btnSaveAs;

	private JCheckBox sameOrigin;

	/**
	 * Documentation of the MbseGraphView
	 */
	public MbseGraphView() {
		super(FRAME_TITLE);

		// sets size
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		// centers the frame
		this.setLocationRelativeTo(null);
		// define behavior of close button
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// set image on frame and OS toolbar
		this.setIconImage(new ImageIcon(getClass().getResource(ICO_STRING)).getImage());

		initUI();
	}

	/**
	 * Construction of main user interface called once by constructor
	 */
	private void initUI() {
		// Construction et injection de la barre d'outils
		contentPane = (JPanel) this.getContentPane();

		contentPane.add(createPopupMenu());

		contentPane.add(createMainToolBar(), BorderLayout.NORTH);
		contentPane.add(createSecondaryToolBar(), BorderLayout.EAST);
	}

	/**
	 * Handling the popup menu on right click
	 * 
	 * @return Component
	 */
	private Component createPopupMenu() {
		popupmenu = new JPopupMenu("Edit");

		displayAsLeaf = new JMenuItem("display as leafs");
		displayAsLeaf.setIcon(new ImageIcon(getClass().getResource("/tree-structure.png")));

		lock = new JMenuItem("Lock / Unlock", new ImageIcon(getClass().getResource("/padlock.png")));
		popupmenu.add(displayAsLeaf);
		popupmenu.add(lock);
		return popupmenu;
	}

	private Component createSecondaryToolBar() {
		JToolBar toolBar = new JToolBar("Layout Properties", JToolBar.VERTICAL);

		// empeche la barre d'etre boug�e
		toolBar.setFloatable(true);

		toolBar.add(new JLabel("Set horizontal spacing"));

		horizontalSpacingSlide = new JSlider(JSlider.HORIZONTAL, 5, 100, 20);
		horizontalSpacingSlide.setPaintTicks(true);
		horizontalSpacingSlide.setPaintLabels(true);
		horizontalSpacingSlide.setMinorTickSpacing(10);
		horizontalSpacingSlide.setMajorTickSpacing(20);
		horizontalSpacingSlide.setName("HorizontalSpacing");
		// horizontalSpacingSlide.addChangeListener(changeListener);
		toolBar.add(horizontalSpacingSlide);

		toolBar.add(new JLabel("Set vertical spacing"));

		verticalSpacingSlide = new JSlider(JSlider.HORIZONTAL, 5, 100, 20);
		verticalSpacingSlide.setPaintTicks(true);
		verticalSpacingSlide.setPaintLabels(true);
		verticalSpacingSlide.setMinorTickSpacing(10);
		verticalSpacingSlide.setMajorTickSpacing(20);
		verticalSpacingSlide.setName("VerticalSpacing");
		// verticalSpacingSlide.addChangeListener(changeListener);
		toolBar.add(verticalSpacingSlide);

		toolBar.addSeparator();
		sameOrigin = new JCheckBox("Same origin for edges");
		toolBar.add(sameOrigin);

		return toolBar;
	}

	private Component createMainToolBar() {
		// La barre d'outils � proprement parler
		JToolBar toolBar = new JToolBar();

		// empeche la barre d'etre boug�e
		toolBar.setFloatable(false);

		btnSelect = new JButton(new ImageIcon(getClass().getResource("/select.gif")));
		// btnZoomIn.addActionListener(actionListener);
		toolBar.add(btnSelect);

		btnPan = new JButton(new ImageIcon(getClass().getResource("/pan.gif")));
		// btnZoomIn.addActionListener(actionListener);
		toolBar.add(btnPan);

		toolBar.addSeparator();

		btnZoomIn = new JButton(new ImageIcon(getClass().getResource("/zoomin.gif")));
		// btnZoomIn.addActionListener(actionListener);
		toolBar.add(btnZoomIn);

		btnZoomOut = new JButton(new ImageIcon(getClass().getResource("/zoomout.gif")));
		// btnZoomOut.addActionListener(actionListener);
		toolBar.add(btnZoomOut);

		btnZoomFit = new JButton(new ImageIcon(getClass().getResource("/zoomactual.gif")));
		// btnZoomFit.addActionListener(actionListener);
		toolBar.add(btnZoomFit);
		toolBar.addSeparator();

		changeStyle = new JCheckBox("UML Style");
		toolBar.add(changeStyle);
		toolBar.addSeparator();

		btnSaveAs = new JButton(new ImageIcon(getClass().getResource("/saveas.gif")));
		toolBar.add(btnSaveAs);

		toolBar.addSeparator();

		toolBar.add(new JLabel("Select layout"));
		layoutSelection = new JComboBox<Object>();
		layoutSelection.setPreferredSize(layoutSelection.getPreferredSize());

		// layoutSelection
		toolBar.add(layoutSelection);

		return toolBar;
	}

	public void addMbseGraphComponent(MbseGraphModel model) {
		graphComponent = new mxGraphComponent(model);

		contentPane.add(graphComponent);
	}

	public void addInputControl(ActionListener actionListener, ChangeListener changeListener,
			MouseListener mouseListener) {
		graphComponent.getGraphControl().addMouseListener(mouseListener);

		btnZoomIn.addActionListener(actionListener);
		changeStyle.addActionListener(actionListener);

		layoutSelection.addActionListener(actionListener);

		// right click menu
		displayAsLeaf.addActionListener(actionListener);

		horizontalSpacingSlide.addChangeListener(changeListener);
		verticalSpacingSlide.addChangeListener(changeListener);
	}

	public void displayPopupMenu(int x, int y) {
		popupmenu.show(graphComponent, x, y);
	}

	public void resizeCombox() {
		// layoutSelection.setMaximumSize(layoutSelection.getPreferredSize());
	}

}
