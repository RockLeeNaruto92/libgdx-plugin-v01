package libgdxpluginv01.swt.custom;

import libgdxpluginv01.controller.UIController;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class CustomComposite extends Composite{
	
	public CustomComposite(Composite parent, int style) {
		super(parent, style);
		addMouseListener();
	}
	
	public CustomComposite(Composite parent, int style, Point location){
		super(parent, style);
		
		FormData data = new FormData();
		setLayoutData(data);
		
		setLocation(location);
		addMouseListener();
	}
	
	public CustomComposite(Composite parent, int style, Point location, Point size){
		super(parent, style);
		
		FormData data = new FormData();
		setLayoutData(data);
		
		setLocation(location);
		setSize(size);
		addMouseListener();
	}
	
	public void setLocation(Point location){
		FormData data = (FormData)getLayoutData();
		
		if (data == null) return;
		data.left = new FormAttachment(getParent(), location.x);
		data.top = new FormAttachment(getParent(), location.y);
		setLayoutData(data);
	}
	
	public void setSize(Point size){
		FormData data = (FormData)getLayoutData();
		data.width = size.x;
		data.height = size.y;
		setLayoutData(data);
	}
	
	private void addMouseListener(){
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("UIController.clicked up");
				UIController.clicked = false;
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("UIController.clicked down");
				UIController.clicked = true;
				Point cursorLocation = getDisplay().getCursorLocation();
				setClickedPoint(cursorLocation);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent e) {
				if (UIController.clicked){
					Point cursorLocation = getDisplay().getCursorLocation();
					Point newPoint = getParent().toControl(cursorLocation.x, cursorLocation.y);
					
					
					FormData data = (FormData)getLayoutData();
					data.left = new FormAttachment(getParent(), data.left.offset + newPoint.x - UIController.clickedPoint.x);
					data.top = new FormAttachment(getParent(), data.top.offset + newPoint.y - UIController.clickedPoint.y);
					UIController.clickedPoint = newPoint;
					System.out.println(UIController.clickedPoint);
					setLayoutData(data);
					
					
					refresh();
				}
			}
		});

		this.addMouseTrackListener(new MouseTrackListener() {
			
			@Override
			public void mouseHover(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("hover");
				UIController.cursor = new Cursor(getDisplay(), SWT.CURSOR_SIZEALL);
				setCursor(UIController.cursor);
			}
			
			@Override
			public void mouseExit(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Exit");
				UIController.cursor = new Cursor(getDisplay(), SWT.CURSOR_ARROW);
				setCursor(UIController.cursor);
			}
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Mouse enter");
				UIController.cursor = new Cursor(getDisplay(), SWT.CURSOR_SIZEALL);
				setCursor(UIController.cursor);
			}
		});
	}
	
	private void setClickedPoint(Point p){
		UIController.clickedPoint = getParent().toControl(p);
	}
	
	private void refresh(){
		Control[] changed = new Control[]{this};
		
		getParent().layout(changed);
	}
}