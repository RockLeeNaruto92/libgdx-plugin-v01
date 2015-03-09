package libgdxpluginv01.swt.custom;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.controller.UIController;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class CustomComposite extends Canvas{
	
	public CustomComposite(Composite parent, int style) {
		super(parent, style);
	}
	
	public CustomComposite(Composite parent, int style, Point location){
		super(parent, style);
		
		setLayoutData(new FormData());
		
		setLocation(location);
	}
	
	public CustomComposite(Composite parent, int style, Point location, Point size){
		super(parent, style);
		
		FormData data = new FormData();
		setLayoutData(data);
		
		setLocation(location);
		setSize(size);
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
	
	public void setClickedPoint(Point p){
		UIController.clickedPoint = getParent().toControl(p);
	}
	
	public void refresh(){
		Control[] changed = new Control[]{this};
		
		getParent().layout(changed);
	}
	
	public void onMouseDown(MouseEvent e){
		UIController.clicked = true;
		Point cursorLocation = getDisplay().getCursorLocation();
		setClickedPoint(cursorLocation);
	}
	
	public void onMouseUp(MouseEvent e){
		UIController.clicked = false;
	}
	
	public void onMouseDoubleClick(MouseEvent e){
	}
	
	public void onMouseMove(MouseEvent e){
		if (UIController.clicked){
			Point cursorLocation = getDisplay().getCursorLocation();
			Point newPoint = getParent().toControl(cursorLocation.x, cursorLocation.y);
			
			
			FormData data = (FormData)getLayoutData();
			data.left = new FormAttachment(getParent(), data.left.offset + newPoint.x - UIController.clickedPoint.x);
			data.top = new FormAttachment(getParent(), data.top.offset + newPoint.y - UIController.clickedPoint.y);
			UIController.clickedPoint = newPoint;
			setLayoutData(data);
			
			
			refresh();
		}
	}
	
	public void onMouseHover(MouseEvent e){
		UIController.cursor = new Cursor(getDisplay(), SWT.CURSOR_SIZEALL);
		setCursor(UIController.cursor);
	}
	
	public void onMouseExit(MouseEvent e){
		UIController.cursor = new Cursor(getDisplay(), SWT.CURSOR_ARROW);
		setCursor(UIController.cursor);
	}
	
	public void onMouseEnter(MouseEvent e){
		UIController.cursor = new Cursor(getDisplay(), SWT.CURSOR_SIZEALL);
		setCursor(UIController.cursor);
	}
}