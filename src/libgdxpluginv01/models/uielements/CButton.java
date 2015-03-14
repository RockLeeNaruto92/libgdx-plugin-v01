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
	public static int i = 0;
	
	private enum State {
		UP,
		DOWN,
		CHECKED,
		OVER,
		CHECKED_OVER,
		DISABLED
	}
	
	private State state = State.UP;
	private ButtonStyle style;
	

	public CButton(Composite root, Point location, UIController uiController) {
		super(root, location, uiController);
		
		style = new ButtonStyle();
	}

	@Override
	public String getDefaultNamePattern() {
		// TODO Auto-generated method stub
		return Parameter.DEFAULT_BUTTON_NAME_PATTERN + i;
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
		if (style.up != null){
			state = State.UP;
			redraw();
		}
	}

	@Override
	public void onMouseDown() {
		if (style.down != null){
			state = State.DOWN;
			redraw();
		}
	}

	@Override
	public void onMouseHover() {
		if (style.over != null){
			state = State.OVER;
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
		if (state == State.UP){
			drawable = style.up;
		} else if (state == State.DOWN){
			drawable = style.down;
		} else if (state == State.CHECKED){
			drawable = style.checked;
		} else if (state == State.CHECKED_OVER){
			drawable = style.checkedOver;
		} else if (state == State.DISABLED){
			drawable = style.disabled;
		}
		
		Rectangle bound = drawable.getBounds();
		e.gc.drawImage(drawable, 0, 0, bound.width, bound.height,
				0, 0, getSize().x, getSize().y);
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
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
