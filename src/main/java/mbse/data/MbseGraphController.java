package mbse.data;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphComponent.mxGraphControl;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph.mxICellVisitor;

public class MbseGraphController implements MbseGraphControllerInterface {

	private static final Logger log = Logger.getLogger(MbseGraphController.class.getName());

	private MbseGraphView view;

	public MbseGraphView getView() {
		return view;
	}

	public void setView(MbseGraphView view) {
		this.view = view;
	}

	private MbseGraphModel model;

	/**
	 * Constructor responsible for defining state of the class
	 */
	public MbseGraphController(MbseGraphModel mbseGraphModel, MbseGraphView mbseGraphView) {
		model = mbseGraphModel;
		view = mbseGraphView;
	}

	public MbseGraphController(MbseGraphModel mbseGraphModel) {
		model = mbseGraphModel;
	}

	/**
	 * Display the user interface
	 */
	public void displayView() {
		view.addMbseGraphComponent(model);
		addViewControls();
		model.getAppliedLayout().execute(model.getDefaultParent());
		view.setVisible(true);
	}

	/**
	 * Allows to create all listeners and pass for the view.
	 */
	public void addViewControls() {
		// Installs the popup menu in the graph component
		MouseAdapter mouseListener = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					rightClickMenu(e);
				}
			}
		};

		view.addInputControl(mouseListener);
	}

	/**
	 * When multiples edges exits from edge, the same exit point is used
	 * 
	 * @param sameExit - boolean
	 */
	@Override
	public void sameOriginControl(boolean sameExit) {
		if (sameExit) {
			model.setAppliedLayout(new StructureLayout(model, false));
		} else {
			model.setAppliedLayout(new mxCompactTreeLayout(model, false));
		}

		updateLayout();
	}

	/**
	 * Internal function to execute selected layout on the model
	 */
	private void updateLayout() {
		model.getModel().beginUpdate();
		try {
			model.getAppliedLayout().execute(model.getDefaultParent());
		} finally {
			model.getModel().endUpdate();
		}
	}

	public void rightClickMenu(MouseEvent e) {
		if (e.getComponent() instanceof mxGraphControl) {
			mxGraphControl graphControl = (mxGraphControl) e.getComponent();

			mxGraphComponent graphComponent = graphControl.getGraphContainer();
			if (graphComponent.getGraph().getSelectionCell() != null) {
				view.displayPopupMenu(e.getX(), e.getY());
			}
		}
	}

	@Override
	public void changedSpacing(int spacing, boolean horizontal) {

		if (horizontal) {
			mxCompactTreeLayout layout = ((mxCompactTreeLayout) model.getAppliedLayout());
			layout.setLevelDistance(spacing);

		} else // verticalSpacing
		{
			mxCompactTreeLayout layout = ((mxCompactTreeLayout) model.getAppliedLayout());
			layout.setNodeDistance(spacing);
		}

		updateLayout();
	}

	/**
	 * Creates a PNG image of the graph present in the view to the specified
	 * location and opens file if succeed.
	 */
	@Override
	public void exportImage() {
		final JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
		fc.setFileFilter(filter);

		int returnVal = fc.showOpenDialog(view);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			BufferedImage image = mxCellRenderer.createBufferedImage(model, null, 1, Color.WHITE, true, null);
			try {
				ImageIO.write(image, "PNG", file);
				JOptionPane.showMessageDialog(view, "File has been created");
				java.awt.Desktop.getDesktop().open(file);
			} catch (IOException e) {
				log.log(Level.SEVERE, "Failed to create image file.", e);
			}
		}
	}

	/**
	 * Export the graphical properties coordinates and size of vertexes and edges
	 */
	public void exportGraphGeometry() {
		Object[] result = model.getChildCells(model.getDefaultParent(), true, true);
		for (Object obj : result) {
			mxCell cell = (mxCell) obj;
			if (cell.isVertex()) {
				GraphNode node = model.nodesTable.get(cell);
				node.setGeometry(cell.getGeometry());
			} else {
				GraphEdge edge = model.edgesTable.get(cell);
				edge.setGeometry(cell.getGeometry().getPoints());
			}
		}
	}

	@Override
	public void layoutOnSelectedGroup() {
		model.getModel().beginUpdate();
		try {
			RootLayout rootLayout = new RootLayout(model);
			rootLayout.execute(model.getSelectionCell());
		} finally {
			mxMorphing morph = new mxMorphing(view.graphComponent, 20, 1.5, 20);
			morph.addListener(mxEvent.DONE, new mxIEventListener() {

				@Override
				public void invoke(Object sender, mxEventObject evt) {
					model.getModel().endUpdate();
				}
			});
			morph.startAnimation();
			view.graphComponent.refresh();
		}

	}

	@Override
	public void expandAction() {
		Object selected = model.getSelectionCell();

		model.getModel().beginUpdate();
		try {
			ArrayList<Object> cellsAffected = new ArrayList<>();
			model.traverse(selected, true, new mxICellVisitor() {
				@Override
				public boolean visit(Object vertex, Object edge) {
					// We do not want to hide/show the vertex that was clicked by the user to do not
					// add it to the list of cells affected.
					if (vertex != selected) {
						cellsAffected.add(vertex);
					}

					// Do not stop recursing when vertex is the cell the user clicked. Need to keep
					// going because this may be an expand.
					// Do stop recursing when the vertex is already collapsed.
					return vertex == selected || model.isCellFoldableObject(vertex);
				}
			});

			model.toggleCells(true, cellsAffected.toArray(), true/* includeEdges */);
			model.appliedLayout.execute(model.getDefaultParent());
		} finally {
			// 10, 1.7, 20
			mxMorphing morph = new mxMorphing(view.graphComponent, 10, 1.7, 20);
			morph.addListener(mxEvent.DONE, new mxIEventListener() {

				@Override
				public void invoke(Object sender, mxEventObject evt) {
					model.getModel().endUpdate();
				}

			});
			morph.startAnimation();
			view.graphComponent.refresh();

		}

	}

	@Override
	public void collapseAction() {
		Object selected = model.getSelectionCell();
		model.getModel().beginUpdate();
		try {
			ArrayList<Object> cellsAffected = new ArrayList<>();
			model.traverse(selected, true, new mxICellVisitor() {
				@Override
				public boolean visit(Object vertex, Object edge) {
					// We do not want to hide/show the vertex that was clicked by the user to do not
					// add it to the list of cells affected.
					if (vertex != selected) {
						cellsAffected.add(vertex);
					}

					// Do not stop recursing when vertex is the cell the user clicked. Need to keep
					// going because this may be an expand.
					// Do stop recursing when the vertex is already collapsed.
					return vertex == selected || model.isCellFoldableObject(vertex);
				}
			});

			model.toggleCells(false, cellsAffected.toArray(), true/* includeEdges */);
			model.appliedLayout.execute(model.getDefaultParent());
		} finally {
			// Create morphing with defaut parameters, can be tested with: 10, 1.7, 20
			mxMorphing morph = new mxMorphing(view.graphComponent);
			morph.addListener(mxEvent.DONE, new mxIEventListener() {

				@Override
				public void invoke(Object sender, mxEventObject evt) {
					model.getModel().endUpdate();
				}
			});
			morph.startAnimation();
			view.graphComponent.refresh();
		}
	}

	/**
	 * Change style and displays images in boxes
	 * 
	 * @param style - SaeML if true, Tom Sawyer otherwise
	 */
	@Override
	public void changeStyle(boolean style) {
		if (style) {
			model.setAppliedStyle("saeml");
		} else {
			model.setAppliedStyle("tom_sawyer");
		}
		model.updateGraphModel();
	}
}
