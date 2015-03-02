package libgdxpluginv01.dnd;

import libgdxpluginv01.models.Element;
import libgdxpluginv01.models.uielements.CLabel;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class UIElementDropAdapter extends DropTargetAdapter{
	private LocalSelectionTransfer transfer;
	private Composite target;
	static int i = 0;
	
	public UIElementDropAdapter(Composite target) {
		super();
		this.target = target;
		transfer = LocalSelectionTransfer.getTransfer();
	}

	@Override
	public void dragOver(DropTargetEvent event) {
		// TODO Auto-generated method stub
		super.dragOver(event);
	}

	@Override
	public void drop(DropTargetEvent event) {
		final Element droppedObj = (Element)((StructuredSelection)transfer.getSelection()).getFirstElement();
		Control[] changed = null;
		
		System.out.println(droppedObj.getName());
		
		if (droppedObj.getName().equals("Label")){
			Point cursorLocation = target.getDisplay().getCursorLocation();
			Point location = target.toControl(cursorLocation);
			CLabel label = new CLabel(target, location);
			System.out.println("Location: " + location);
			
			changed = new Control[] {label.getContainer()};
		}
		
		if (changed != null){
			target.layout(changed);
		}
		super.drop(event);
	}
}
