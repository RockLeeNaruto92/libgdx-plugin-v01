package libgdxpluginv01.views;


import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.dnd.UIElementDragListener;
import libgdxpluginv01.models.UIElementPaletteContentProvider;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class PaletteView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "libgdxpluginv01.views.PaletteView";
	
	private TreeViewer viewer;
	private UIController uiController;

	/**
	 * The constructor.
	 */
	public PaletteView() {
		uiController = UIController.getInstance();
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		int operations = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[]{LocalSelectionTransfer.getTransfer()};
		viewer.addDragSupport(operations, transferTypes, new UIElementDragListener(viewer));
		viewer.setContentProvider(new PaletteTreeContentProvider());
		viewer.setLabelProvider(new PaletteTreeLabelProvider());
		viewer.setInput(UIElementPaletteContentProvider.INSTANCE.getModel());
	}
	
	@Override
	public void setFocus() {
	}
	
	/**
	 * 
	 */
}