package libgdxpluginv01.editors;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.dnd.UIElementDropAdapter;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class EditorInterface {
	private ScrolledComposite root;
	private Canvas dragComposite;

	public EditorInterface(Composite parent) {
		createScrolledLayout(parent);
		createDragComposite();
	}
	
	private void createDragComposite(){
		dragComposite = new Canvas(root, SWT.NONE);
		dragComposite.setLayout(new FormLayout());
		
		dragComposite.getShell().setMinimumSize(Parameter.DEFAULT_DESIGN_PART_MIN_SIZE);
		dragComposite.setSize(Parameter.DEFAULT_DESIGN_PART_MIN_SIZE);
		addDropListener(dragComposite);
	}

	private void createScrolledLayout(Composite parent) {
		int style = SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL;
		root = new ScrolledComposite(parent, style);
		root.setLayout(new FillLayout());
	}
	
	private void addDropListener(Composite composite){
		final DropTarget dropTarget = new DropTarget(dragComposite, DND.DROP_MOVE | DND.DROP_COPY);
		dropTarget.setTransfer(new Transfer[] {LocalSelectionTransfer.getTransfer()});
		dropTarget.addDropListener(new UIElementDropAdapter(dragComposite));
	}
}
