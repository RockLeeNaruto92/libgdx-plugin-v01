package libgdxpluginv01.dnd;

import libgdxpluginv01.editors.EditorInterface;

import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.widgets.Composite;

public class UIElementDropAdapter extends DropTargetAdapter{
	private EditorInterface editorInterface;
	private Composite target;
	static int i = 0;
	
	public UIElementDropAdapter(Composite target, EditorInterface editor) {
		super();
		this.target = target;
		this.editorInterface = editor;
	}

	@Override
	public void dragOver(DropTargetEvent event) {
		// TODO Auto-generated method stub
		super.dragOver(event);
	}

	@Override
	public void drop(DropTargetEvent event) {
		editorInterface.drop(event);
		
		super.drop(event);
	}
}
