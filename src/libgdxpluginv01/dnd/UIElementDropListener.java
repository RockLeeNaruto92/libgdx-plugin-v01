package libgdxpluginv01.dnd;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

public class UIElementDropListener extends ViewerDropAdapter{
	
	private final Viewer viewer;
	
	public UIElementDropListener(Viewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}
	
	@Override
	public void drop(DropTargetEvent event) {
		// TODO Auto-generated method stub
		super.drop(event);
	}

	@Override
	public boolean performDrop(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateDrop(Object arg0, int arg1, TransferData arg2) {
		// TODO Auto-generated method stub
		return false;
	}

}
