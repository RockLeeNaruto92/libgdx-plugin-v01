package libgdxpluginv01.dnd;

import libgdxpluginv01.models.Element;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

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
			Label newLabel = new Label(target, SWT.NONE);
			System.out.println(event.x + "-" + event.y + "- i " + i);
			newLabel.setText("new Label " + i++);
			FormData data = new FormData(40, 100);
//			data.left = new FormAttachment(0, i++ * 100);
			data.left = new FormAttachment(0,i * 40);
			newLabel.setLayoutData(data);
			changed = new Control[] {newLabel};
		}
		
		if (changed != null){
			target.layout(changed);
		}
		super.drop(event);
	}
}
