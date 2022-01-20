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
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphComponent.mxGraphControl;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph.mxICellVisitor;

public class MbseGraphController {

	private static final Logger log = Logger.getLogger(GraphVisualizer.class.getName());

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
		ActionListener actionListenerBox = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				if (selected) {
					// mxCell node = (mxCell) ((MbseGraphModel) model).saveForLater;
					// Object[] allEdge = model.getChildEdges(model.getDefaultParent());
					Object[] allVertex = model.getChildCells(model.getDefaultParent(), true, false);
					System.out.println(allVertex.length);
					model.setCellStyle("tom_sawyer", allVertex);
					// model.getModel().setStyle(allEdge, "tom_sawyer");
				} else {
					// mxCell node = (mxCell) ((MbseGraphModel) model).saveForLater;
					Object[] allVertex = model.getChildCells(model.getDefaultParent(), true, false);
					System.out.println(allVertex.length);
					model.setCellStyle("saeml", allVertex);
					// model.getModel().setStyle(node, "saeml");
				}
				// abstractButton.setText(newLabel);
				// graphComponent.getGraph().getModel()

			}
		};
		view.changeStyle.addActionListener(actionListenerBox);

		actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("test du actionlistener");
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
				rightClickMenu(e);
			}
			/*
			 * public void mousePressed(MouseEvent e)
			 * {
			 * // Handles context menu on the Mac where the trigger is on mousepressed
			 * mouseReleased(e);
			 * }
			 * 
			 * public void mouseReleased(MouseEvent e)
			 * {
			 * if (e.isPopupTrigger())
			 * {
			 * System.out.println("click");//showGraphPopupMenu(e);
			 * 
			 * popupmenu.show(graphComponent, e.getX(), e.getY());
			 * //graphComponent.getGraph().getSelectionCell()
			 * System.out.println("Selected cell:"+graphComponent.getGraph().
			 * getSelectionCell());
			 * }
			 * else
			 * System.out.println("when ?");
			 * }
			 */

		};

		view.addInputControl(actionListener, changeListener, mouseListener);
	}

	private void rightClickMenu(MouseEvent e) {
		// System.out.println("Selected cell:"+e);

		System.out.println(e.getComponent());

		if (e.getComponent() instanceof mxGraphControl) {
			mxGraphControl graphControl = (mxGraphControl) e.getComponent();

			mxGraphComponent graphComponent = graphControl.getGraphContainer();
			if (graphComponent.getGraph().getSelectionCell() != null) {
				// selectedCell = (mxCell) graphComponent.getGraph().getSelectionCell();
				view.displayPopupMenu(e.getX(), e.getY());
			} else {
				System.out.println("Cell not selected");
			}
		} else {
			System.out.println("not mxGraphComponent");
		}
		// popupmenu.show(graphComponent, e.getX(), e.getY());
	}

	private void changedState(ChangeEvent event) {
		System.out.println("change listnener triggered" + event.getSource());
		if (event.getSource() instanceof JSlider) {
			JSlider slider = (JSlider) event.getSource();

			// spacing value is applied only when slider is released
			if (slider.getValueIsAdjusting())
				return;

			// int spacing = slider.getValue();

			if (slider.getName().equals("HorizontalSpacing")) {
				// ((MbseLayout) currentAppliedLayout).setHorizontalSpacing(spacing);
				// model.getAppliedLayout().setHorizontalSpacing(spacing);

			} else // verticalSpacing
			{
				// model.getAppliedLayout().setVerticalSpacing(spacing);
				// ((MbseLayout) currentAppliedLayout).setVerticalSpacing(spacing);
			}

			model.getAppliedLayout().execute(model.getDefaultParent());
		}
	}

	private void linkBtnAndLabel(ActionEvent event) {
		// model.incX();
		// view.setText(Integer.toString(model.getX()));

		if (event.getSource() instanceof JButton) {
			// changeAppliedStyle();
			// System.out.println("action listnener triggered: " + event.getSource());

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
				default:
					System.out.println("unknown: " + item.getText());
			}
			// RateauLayout layout = new RateauLayout();
			// model.setAppliedLayout(layout);
			// model.getAppliedLayout().execute(selectedCell);
		}

		/*
		 * public void setSpacing(ChangeEvent event) {
		 * JSlider slider = (JSlider) event.getSource();
		 * 
		 * // spacing value is applied only when slider is released
		 * if (slider.getValueIsAdjusting())
		 * return;
		 * 
		 * int spacing = slider.getValue();
		 * 
		 * if (slider.getName().equals("HorizontalSpacing"))
		 * {
		 * ((MbseLayout) currentAppliedLayout).setHorizontalSpacing(spacing);
		 * 
		 * }
		 * else // verticalSpacing
		 * {
		 * ((MbseLayout) currentAppliedLayout).setVerticalSpacing(spacing);
		 * }
		 * 
		 * model.executeLayout();
		 * 
		 * }
		 */
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
			model.appliedLayout.execute(model.getDefaultParent());
		} finally {

			mxMorphing morph = new mxMorphing(view.graphComponent, 20, 1.2, 20);
			morph.addListener(mxEvent.DONE, new mxIEventListener() {

				@Override
				public void invoke(Object sender, mxEventObject evt) {
					model.getModel().endUpdate();
				}
			});
			morph.startAnimation();
			// model.getModel().endUpdate();
		}

	}

	private void collapseAction() {
		Object selected = model.getSelectionCell();
		model.getModel().beginUpdate();
		try {
			// System.out.println(model.getChildVertices(selected));
			mxCell cell = (mxCell) selected;

			// Object[] connections = model.getConnections(cell, null, false);

			for (Object obj : model.getOutgoingEdges(selected)) {
				System.out.println(obj);
			}

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
			// System.out.println("outgoing" + model.getOutgoingEdges(selected).length);

			// System.out.println(cell.getChildCount());
			// Object[] children = model.getChildCells(selected);
			// System.out.println(children.length);
			// model.getModel().setVisible(selected, false);
			// model.getModel().setCollapsed(selected, true);
			model.appliedLayout.execute(model.getDefaultParent());
		} finally {
			mxMorphing morph = new mxMorphing(view.graphComponent, 20, 1.2, 20);
			morph.addListener(mxEvent.DONE, new mxIEventListener() {

				@Override
				public void invoke(Object sender, mxEventObject evt) {
					model.getModel().endUpdate();
				}
			});
			morph.startAnimation();
			// view.graphComponent.
		}
	}

	private void changeAppliedStyle() {
		if (model.getAppliedStyle().equals("saeml")) {
			model.setAppliedStyle("tom_sawyer");
		} else {
			model.setAppliedStyle("saeml");
		}
		updateStyle();
	}

	private void updateStyle() {
		model.getModel().beginUpdate();
		try {
			// model.getcels
			Object[] cells = new Object[] {};
			// cells[0] = model.saveForLater;

			model.setCellStyle(model.getAppliedStyle(), cells);
		} finally {
			model.getModel().endUpdate();
		}
	}

}
