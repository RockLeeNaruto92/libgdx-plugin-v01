package libgdxpluginv01.dnd;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

public class UIElementDragListener implements DragSourceListener{
	private final Viewer viewer;
	private LocalSelectionTransfer transfer;
	
	public UIElementDragListener(Viewer viewer){
		this.viewer = viewer;
		transfer = LocalSelectionTransfer.getTransfer();
	}
	
	@Override
	public void dragFinished(DragSourceEvent arg0) {
		
	}

	@Override
	public void dragSetData(DragSourceEvent arg0) {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		transfer.setSelection(selection);
	}

	@Override
	public void dragStart(DragSourceEvent arg0) {
		
	}
	
}
