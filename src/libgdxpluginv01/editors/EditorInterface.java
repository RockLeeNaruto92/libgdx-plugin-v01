package libgdxpluginv01.editors;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.dnd.UIElementDropAdapter;
import libgdxpluginv01.models.Element;
import libgdxpluginv01.models.uielements.UIElementType;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class EditorInterface {
	private ScrolledComposite root;
	private Canvas dragComposite;
	private LocalSelectionTransfer transfer;
	private UIController uiController;

	public EditorInterface(Composite parent) {
		createScrolledLayout(parent);
		createDragComposite();
		
		uiController = UIController.getInstance();
		transfer = LocalSelectionTransfer.getTransfer();
	}
	
	private void createDragComposite(){
		dragComposite = new Canvas(root, SWT.NONE);
		dragComposite.setLayout(new FormLayout());
		
		dragComposite.getShell().setMinimumSize(Parameter.DEFAULT_DESIGN_PART_MIN_SIZE);
		dragComposite.setSize(Parameter.DEFAULT_DESIGN_PART_MIN_SIZE);
		addDropListener(dragComposite);
		addKeyListener(dragComposite);
	}

	private void createScrolledLayout(Composite parent) {
		int style = SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL;
		root = new ScrolledComposite(parent, style);
		root.setLayout(new FillLayout());
	}
	
	private void addDropListener(Composite composite){
		final DropTarget dropTarget = new DropTarget(dragComposite, DND.DROP_MOVE | DND.DROP_COPY);
		dropTarget.setTransfer(new Transfer[] {LocalSelectionTransfer.getTransfer()});
		dropTarget.addDropListener(new UIElementDropAdapter(dragComposite, this));
	}
	
	private void addKeyListener(Composite composite){
//		Shell shell = composite.getShell();
//		shell.addKeyListener(new KeyListener() {
//			
//			@Override
//			public void keyReleased(KeyEvent arg0) {
//				System.out.println("key up");
//			}
//			
//			@Override
//			public void keyPressed(KeyEvent arg0) {
//				System.out.println("key down: " + arg0.character);
//			}
//		});
		Display d = composite.getDisplay();
		d.addFilter(SWT.KeyDown, new Listener() {

            public void handleEvent(Event e) {
                if(((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 'f'))
                {
                    System.out.println("From Display I am the Key down !!" + e.keyCode);
                }
            }
        });
	}
	
	public void drop(DropTargetEvent e){
		UIElementType type = identifyType();
		Control[] changed = null;
		
		Point location = dragComposite.toControl(e.x, e.y);
		
		Control container = uiController.createUIElement(dragComposite, type, location);
		changed = new Control[] {container};
		
		dragComposite.layout(changed);
	}
	
	private UIElementType identifyType(){
		final Element droppedObj = (Element)((StructuredSelection)transfer.getSelection()).getFirstElement();
		String name = droppedObj.getName();
		
		if (name.equals("Label"))
			return UIElementType.LABEL;
		
		if (name.equals("Checkbox"))
			return UIElementType.CHECKBOX;
		
		if (name.equals("Slider"))
			return UIElementType.SLIDER;
		
		// TO DO
		return null;
	}
}
