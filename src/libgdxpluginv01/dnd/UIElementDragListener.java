package libgdxpluginv01.dnd;

import java.awt.Event;

import libgdxpluginv01.models.Element;
import libgdxpluginv01.models.Group;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

public class UIElementDragListener implements DragSourceListener{
	private final Viewer viewer;
	
	public UIElementDragListener(Viewer viewer){
		this.viewer = viewer;
	}
	
	@Override
	public void dragFinished(DragSourceEvent arg0) {
		
	}

	@Override
	public void dragSetData(DragSourceEvent arg0) {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object selectObj = selection.getFirstElement();
		
		if (selectObj instanceof Element){
			arg0.data = ((Element)selectObj).getName();
		}
	}

	@Override
	public void dragStart(DragSourceEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
