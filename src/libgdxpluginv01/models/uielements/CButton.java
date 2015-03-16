package libgdxpluginv01.models.uielements;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.controller.UIController;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

public class CButton extends UIElement {
	private boolean checked, disabled, over, up;
	
	private ButtonStyle style;
	
	public CButton(Composite root, Point location, UIController uiController, int type) {
		super(root, location, uiController, type);
	}
	
	public CButton(Composite root, Point location, UIController uiController) {
		super(root, location, uiController, UIElementType.BUTTON);
	}

	@Override
	public String getDefaultNamePattern() {
		// TODO Auto-generated method stub
		return Parameter.DEFAULT_BUTTON_NAME_PATTERN;
	}

	@Override
	public Point getDefaultSize() {
		return Parameter.DEFAULT_BUTTON_SIZE;
	}
	
	@Override
	public Point getMinSize(){
		return Parameter.BUTTON_MIN_SIZE;
	}

	@Override
	public void createControls() {
	}

	@Override
	public void displayBound(boolean display) {
		// TODO Auto-generated method stub
		setSelected(display);
	}
	
	@Override
	public void onMouseUp() {
		up = true;
		if (getStyle().up != null){
			redraw();
		}
	}

	@Override
	public void onMouseDown() {
		up = false;
		if (getStyle().down != null){
			redraw();
		}
	}

	@Override
	public void onMouseHover() {
		over = true;
		if (getStyle().over != null){
			redraw();
		}
	}

	@Override
	public void onMouseMove() {
		
	}
	
	@Override
	public void onMouseDoubleClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawContent(PaintEvent e) {
		Image drawable = null;
		
		if (disabled){
			drawable = getStyle().disabled;
		} else if (checked){
			drawable = over ? getStyle().checkedOver : getStyle().checked;
		} else {
			if (up){
				drawable = over ? getStyle().over : getStyle().up;
			} else {
				drawable = getStyle().down;
			}
		}
		
		Rectangle bound = drawable.getBounds();
		e.gc.drawImage(drawable, 0, 0, bound.width, bound.height, 0, 0, getSize().x, getSize().y);
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public ButtonStyle getStyle() {
		if (style == null){
			style = new ButtonStyle();
		}
		
		return style;
	}

	public void setStyle(ButtonStyle style) {
		this.style = style;
	}



	static class ButtonStyle {
		public Image checked;
		public Image checkedOver;
		public Image disabled;
		public Image up;
		public Image down;
		public Image over;
		public float pressedOffsetX;
		public float pressedOffsetY;
		public float unpressedOffsetX;
		public float unpressedOffsetY;
		
		public ButtonStyle(){
			try {
				up = new Image(null, new FileInputStream(Utility.getFile("datas/default/ButtonStyle/up.png")));
				down = new Image(null, new FileInputStream(Utility.getFile("datas/default/ButtonStyle/down.png")));
				over = up;
				checked = up;
				checkedOver = up;
				disabled = up;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
