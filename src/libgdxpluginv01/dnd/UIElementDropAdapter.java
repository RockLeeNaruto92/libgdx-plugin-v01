package libgdxpluginv01.dnd;

import libgdxpluginv01.editors.EditorInterface;
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
