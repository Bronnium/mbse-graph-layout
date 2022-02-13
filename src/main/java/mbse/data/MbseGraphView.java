package mbse.data;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.JToolBar;

import com.mxgraph.swing.mxGraphComponent;

/**
 * Main class for MBSE Graph Visualization
 * 
 * @author
 *
 */
public class MbseGraphView extends JFrame {

	private JPanel contentPane;

	private JButton btnZoomIn;
	private JButton btnZoomOut;
	private JButton btnZoomFit;

	private JSpinner horizontalSpacingSlide;
	private JSpinner verticalSpacingSlide;

	// graphical component for MbseGraph
	protected mxGraphComponent graphComponent;

	private JPopupMenu popupmenu;

	private JComboBox<Object> layoutSelection;

	protected JCheckBox changeStyle;

	private JMenuItem displayAsLeaf;

	/**
	 * Contains the path to icon that will be displayed in the frame.
	 * Image file (png) shall be included in resources package
	 */
	private final static String ICO_STRING = "/flow-chart.png";
	private final static String FRAME_TITLE = "MBSE Graph Visualizer";

	private final int FRAME_WIDTH = 800;
	private final int FRAME_HEIGHT = 600;

	private JButton btnSaveAs;

	private JCheckBox sameOrigin;

	private JMenuItem collapse;

	private JMenuItem expand;

	private JButton btnExport = new JButton("Export to", new ImageIcon(getClass().getResource("/export.png")));

	private MbseGraphController controller;

	/**
	 * Documentation of the MbseGraphView
	 */
	public MbseGraphView(MbseGraphController controller) {
		super(FRAME_TITLE);

		this.controller = controller;

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
		// secondary toolbar for layout parameters
		contentPane.add(createSecondaryToolBar(), BorderLayout.SOUTH);
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

		collapse = new JMenuItem("Collapse", new ImageIcon(getClass().getResource("/collapse.gif")));
		expand = new JMenuItem("Expand", new ImageIcon(getClass().getResource("/expand.gif")));

		popupmenu.add(displayAsLeaf);
		popupmenu.addSeparator();
		popupmenu.add(collapse);
		popupmenu.add(expand);
		return popupmenu;
	}

	private Component createSecondaryToolBar() {
		JToolBar toolBar = new JToolBar("Layout Properties");

		// empeche la barre d'etre bougée
		toolBar.setFloatable(true);

		toolBar.add(new JLabel("Set horizontal spacing"));

		horizontalSpacingSlide = new JSpinner();
		horizontalSpacingSlide.setValue(10);
		horizontalSpacingSlide.setName("HorizontalSpacing");
		toolBar.add(horizontalSpacingSlide);

		toolBar.add(new JLabel("Set vertical spacing"));

		verticalSpacingSlide = new JSpinner();
		verticalSpacingSlide.setValue(20);
		verticalSpacingSlide.setName("VerticalSpacing");
		toolBar.add(verticalSpacingSlide);

		toolBar.addSeparator();
		sameOrigin = new JCheckBox("Same origin for edges");

		sameOrigin.addActionListener(e -> controller.sameOriginControl(sameOrigin.isSelected()));
		toolBar.add(sameOrigin);

		return toolBar;
	}

	private Component createMainToolBar() {
		// La barre d'outils a proprement parler
		JToolBar toolBar = new JToolBar();

		// empeche la barre d'etre bougée
		toolBar.setFloatable(false);

		btnZoomIn = new JButton(new ImageIcon(getClass().getResource("/zoomin.gif")));
		btnZoomIn.setToolTipText("zoom in");
		toolBar.add(btnZoomIn);
		btnZoomOut = new JButton(new ImageIcon(getClass().getResource("/zoomout.gif")));
		btnZoomOut.setToolTipText("zoom out");
		toolBar.add(btnZoomOut);
		btnZoomFit = new JButton(new ImageIcon(getClass().getResource("/zoomactual.gif")));
		btnZoomFit.setToolTipText("zoom fit");
		toolBar.add(btnZoomFit);

		createAndAddZoomControls();

		toolBar.addSeparator();

		changeStyle = new JCheckBox("Classic style");
		changeStyle.addActionListener(e -> controller.changeStyle(changeStyle.isSelected()));
		toolBar.add(changeStyle);

		toolBar.addSeparator();

		btnSaveAs = new JButton(new ImageIcon(getClass().getResource("/saveas.gif")));
		btnSaveAs.setToolTipText("export as image");
		toolBar.add(btnSaveAs);

		toolBar.addSeparator();

		toolBar.add(new JLabel("Select layout"));
		layoutSelection = new JComboBox<Object>();
		layoutSelection.setPreferredSize(layoutSelection.getPreferredSize());

		toolBar.add(layoutSelection);

		toolBar.addSeparator();
		toolBar.add(btnExport);

		return toolBar;
	}

	public void addMbseGraphComponent(MbseGraphModel model) {
		graphComponent = new MbseGraphComponent(model);
		contentPane.add(graphComponent);
	}

	public void addInputControl(MouseListener mouseListener) {
		graphComponent.getGraphControl().addMouseListener(mouseListener);

		horizontalSpacingSlide
				.addChangeListener(cl -> controller.changedSpacing((int) verticalSpacingSlide.getValue(), true));
		verticalSpacingSlide
				.addChangeListener(cl -> controller.changedSpacing((int) verticalSpacingSlide.getValue(), false));

		btnSaveAs.addActionListener(al -> controller.exportImage());
		btnExport.addActionListener(al -> controller.exportGraphGeometry());

		// right click menu
		displayAsLeaf.addActionListener(al -> controller.layoutOnSelectedGroup());
		collapse.addActionListener(al -> controller.collapseAction());
		expand.addActionListener(al -> controller.expandAction());
	}

	public void displayPopupMenu(int x, int y) {
		popupmenu.show(graphComponent, x, y);
	}

	private void createAndAddZoomControls() {
		btnZoomIn.addActionListener((e) -> graphComponent.zoomIn());
		btnZoomOut.addActionListener((e) -> graphComponent.zoomOut());
		btnZoomFit.addActionListener((e) -> graphComponent.zoomActual());
	}

}
