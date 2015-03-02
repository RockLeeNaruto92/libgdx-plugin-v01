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
		addMouseListener();
	}
	
	public CustomComposite(Composite parent, int style, Point location){
		super(parent, style);
		
		FormData data = new FormData();
		setLayoutData(data);
		
		setLocation(location);
		addMouseListener();
		addPaintListener();
	}
	
	public CustomComposite(Composite parent, int style, Point location, Point size){
		super(parent, style);
		
		FormData data = new FormData();
		setLayoutData(data);
		
		setLocation(location);
		setSize(size);
		addMouseListener();
		addPaintListener();
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
	
	public void addPaintListener(){
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
				FormData data = (FormData)getLayoutData();
				
				e.gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
				e.gc.drawRectangle(Parameter.R, Parameter.R, data.width - 1 - 2 * Parameter.R, data.height - 1 - 2 * Parameter.R);
				// draw circle
				e.gc.drawOval(0, 0, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(0, data.height - 1 - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(data.width - 1 - 2 * Parameter.R, 0, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(data.width - 1 - 2 * Parameter.R, data.height - 1 - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				
				Point centerPoint = new Point(data.width / 2, data.height / 2);
				e.gc.drawOval(0, centerPoint.y - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(centerPoint.x - 2 * Parameter.R, 0, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(data.width - 1 - 2 * Parameter.R, centerPoint.y - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(centerPoint.x - 2 * Parameter.R, data.height - 1 - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(centerPoint.x - 2 * Parameter.R, centerPoint.y - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
			}
		});
	}
	
	private void addMouseListener(){
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
//				System.out.println("UIController.clicked up");
				UIController.clicked = false;
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
//				System.out.println("UIController.clicked down");
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
	
	public void onMouseDown(MouseEvent e){
//		System.out.println("UIController.clicked down");
		UIController.clicked = true;
		Point cursorLocation = getDisplay().getCursorLocation();
		setClickedPoint(cursorLocation);
	}
	
	public void onMouseUp(MouseEvent e){
//		System.out.println("UIController.clicked up");
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
//			System.out.println(UIController.clickedPoint);
			setLayoutData(data);
			
			
			refresh();
		}
	}
	
	public void onMouseHover(MouseEvent e){
//		System.out.println("hover");
		UIController.cursor = new Cursor(getDisplay(), SWT.CURSOR_SIZEALL);
		setCursor(UIController.cursor);
	}
	
	public void onMouseExit(MouseEvent e){
//		System.out.println("Exit");
		UIController.cursor = new Cursor(getDisplay(), SWT.CURSOR_ARROW);
		setCursor(UIController.cursor);
	}
	
	public void onMouseEnter(MouseEvent e){
//		System.out.println("Mouse enter");
		UIController.cursor = new Cursor(getDisplay(), SWT.CURSOR_SIZEALL);
		setCursor(UIController.cursor);
	}
}