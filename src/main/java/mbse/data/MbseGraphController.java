package mbse.data;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphComponent.mxGraphControl;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph.mxICellVisitor;

public class MbseGraphController {

	private static final Logger log = Logger.getLogger(MbseGraphController.class.getName());

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
		model.setCellsMovable(true);
		model.getAppliedLayout().execute(model.getDefaultParent());
		model.setCellsMovable(false);
		view.setVisible(true);
	}

	/**
	 * Allows to create all listeners and pass for the view.
	 */
	public void addViewControls() {

		createAndAddZoomControls();

		createChangeStyleControl();

		actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				linkBtnAndLabel(actionEvent);
			}
		};

		changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				changedState(event);
			}
		};

		// Installs the popup menu in the graph component
		mouseListener = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					rightClickMenu(e);
				}

			}
		};

		view.addInputControl(actionListener, changeListener, mouseListener);
	}

	private void createChangeStyleControl() {
		ActionListener actionListenerBox = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {

				JCheckBox checkBox = (JCheckBox) actionEvent.getSource();

				if (checkBox.isSelected()) {
					Object[] allVertex = model.getChildCells(model.getDefaultParent(), true, false);
					model.setCellStyle("saeml", allVertex);
				} else {
					Object[] allVertex = model.getChildCells(model.getDefaultParent(), true, false);
					model.setCellStyle("tom_sawyer", allVertex);
				}
			}
		};

		view.changeStyle.addActionListener(actionListenerBox);
	}

	/**
	 * 
	 */
	private void createAndAddZoomControls() {

		ActionListener zoomActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
					case "zoomIn":
						view.graphComponent.zoomIn();
						break;
					case "zoomOut":
						view.graphComponent.zoomOut();
						break;
					case "zoomFit":
						view.graphComponent.zoomActual();
						break;
					default:
						log.log(Level.INFO, "Unknown action command.", e.getActionCommand());
				}
			}
		};

		view.addZoomControls(zoomActionListener);
	}

	private void rightClickMenu(MouseEvent e) {

		if (e.getComponent() instanceof mxGraphControl) {
			mxGraphControl graphControl = (mxGraphControl) e.getComponent();

			mxGraphComponent graphComponent = graphControl.getGraphContainer();
			if (graphComponent.getGraph().getSelectionCell() != null) {
				view.displayPopupMenu(e.getX(), e.getY());
			}
		}
	}

	private void changedState(ChangeEvent event) {
		System.out.println("change listnener triggered" + event.getSource());
		if (event.getSource() instanceof JSlider) {
			JSlider slider = (JSlider) event.getSource();

			// spacing value is applied only when slider is released
			if (slider.getValueIsAdjusting())
				return;

			int spacing = slider.getValue();

			if (slider.getName().equals("HorizontalSpacing")) {
				// ((MbseLayout) currentAppliedLayout).setHorizontalSpacing(spacing);
				mxCompactTreeLayout layout = ((mxCompactTreeLayout) model.getAppliedLayout());
				layout.setLevelDistance(spacing);
				// model.getAppliedLayout().setHorizontalSpacing(spacing);

			} else // verticalSpacing
			{
				// model.getAppliedLayout().setVerticalSpacing(spacing);
				// ((MbseLayout) currentAppliedLayout).setVerticalSpacing(spacing);
			}

			model.setCellsMovable(true);
			model.getAppliedLayout().execute(model.getDefaultParent());
			model.setCellsMovable(false);
		}
	}

	private void linkBtnAndLabel(ActionEvent event) {

		if (event.getSource() instanceof JButton) {

			// Create a file chooser
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

				} catch (IOException e) {
					log.log(Level.SEVERE, "Failed to create image file.", e);
				}
			} else {

			}

		}

		if (event.getSource() instanceof JMenuItem) {

			JMenuItem item = (JMenuItem) event.getSource();
			switch (item.getText()) {
				case "Collapse":
					collapseAction();
					break;
				case "Expand":
					expandAction();
					break;
				case "display as leafs":
					layoutOnSelectedGroup();
					break;
				default:
					System.out.println("unknown: " + item.getText());
			}
		}
	}

	private void layoutOnSelectedGroup() {
		model.getModel().beginUpdate();
		try {
			RootLayout rootLayout = new RootLayout(model);
			model.setCellsMovable(true);
			rootLayout.execute(model.getSelectionCell());
			model.setCellsMovable(false);
		} finally {
			model.getModel().endUpdate();
		}
	}

	private void expandAction() {
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
			model.setCellsMovable(true);
			model.appliedLayout.execute(model.getDefaultParent());
			model.setCellsMovable(false);
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

	private void collapseAction() {
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
			model.setCellsMovable(true);
			model.appliedLayout.execute(model.getDefaultParent());
			model.setCellsMovable(false);
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
}
