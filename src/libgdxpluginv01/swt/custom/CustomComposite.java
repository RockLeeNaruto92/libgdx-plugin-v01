package libgdxpluginv01.swt.custom;

import libgdxpluginv01.controller.UIController;

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
}