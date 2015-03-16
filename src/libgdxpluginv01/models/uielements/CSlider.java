package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.swt.custom.BitmapFont;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class CSlider extends UIElement {
	private boolean disabled;
	private float min, max, stepSize;
	private float value;
	private boolean vertical;
	
	private SliderStyle style;
	
	public CSlider(Composite root, Point location, UIController uiController,
			int type) {
		super(root, location, uiController, type);
	}
	
	public CSlider(Composite root, Point location, UIController uiController) {
		super(root, location, uiController, UIElementType.SLIDER);
	}
	
	@Override
	public String getDefaultNamePattern() {
		return Parameter.DEFAULT_SLIDER_NAME_PATTERN;
	}

	@Override
	public Point getDefaultSize() {
		// TODO Auto-generated method stub
		return Parameter.DEFAULT_SLIDER_SIZE;
	}

	@Override
	public Point getMinSize() {
		// TODO Auto-generated method stub
		return Parameter.SLIDER_MIN_SIZE;
	}

	@Override
	public void createControls() {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayBound(boolean display) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawContent(PaintEvent e) {
		// draw background and knob
		Image image;
		image = disabled ? getStyle().disabledBackground : getStyle().background;
		
		Rectangle bBound = image.getBounds();
		Point size = getSize();
		
		e.gc.drawImage(image, 0, 0, bBound.width, bBound.height, 0, 0, size.x, size.y);
		
		image = disabled ? style.disabledKnob : style.knob;
		Rectangle kBound = image.getBounds();
		
		e.gc.drawImage(image, 0, 0, kBound.width, kBound.height, 0, 0, (int)(size.x * (kBound.width * 1f / bBound.width)), size.y);
	}

	@Override
	public void onMouseUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseDoubleClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseHover() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseMove() {
		// TODO Auto-generated method stub

	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getStepSize() {
		return stepSize;
	}

	public void setStepSize(float stepSize) {
		this.stepSize = stepSize;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	public SliderStyle getStyle() {
		if (style == null){
			style = new SliderStyle();
		}
		return style;
	}

	public void setStyle(SliderStyle style) {
		this.style = style;
	}

	static public class SliderStyle{
		public Image background;
		public Image disabledBackground;
		public Image knob, disabledKnob;
		public Image knobBefore, knobAfter, disabledKnobBefore, disabledKnobAfter;

		public SliderStyle(){
			background = new Image(Display.getCurrent(), Utility.getFile("datas/default/Slider/background.png").toString());
			knob = new Image(Display.getCurrent(), Utility.getFile("datas/default/Slider/knob.png").toString());
			
			disabledBackground = background;
			disabledKnob = knob;
		}
		
		public SliderStyle(Image background, Image knob) {
			super();
			this.background = background;
			this.knob = knob;
		}
		
	}
	
}
