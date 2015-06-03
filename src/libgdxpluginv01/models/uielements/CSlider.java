package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Resources;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.views.properties.Error;
import libgdxpluginv01.views.properties.UIElementPropertyType;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
	public void drawContent(PaintEvent e) {
		// draw background and knob
		Image image;
		image = disabled ? getStyle().disabledBackground : getStyle().background;
		
		Rectangle bBound = image.getBounds();
		Point size = getSize();
		
		e.gc.drawImage(image, 0, 0, bBound.width, bBound.height, 0, 0, size.x, size.y);
		
		image = disabled ? style.disabledKnob : style.knob;
		Rectangle kBound = image.getBounds();
		
		int x = (int)(((value - min) / (max - min)) * size.x);
		
		e.gc.drawImage(image, 0, 0, kBound.width, kBound.height, x, 0, (int)(size.x * (kBound.width * 1f / bBound.width)), size.y);
	}

	@Override
	public void onMouseUp() {
	}

	@Override
	public void onMouseDown() {
	}

	@Override
	public void onMouseDoubleClick() {
	}

	@Override
	public void onMouseHover() {
	}

	@Override
	public void onMouseMove() {
	}
	
	@Override
	public void onMouseExit() {
	}

	@Override
	public void onMouseEnter() {
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

	@Override
	public Error isValidProperty(UIElementPropertyType type, String value) {
		Error error = super.isValidProperty(type, value);
		
		if (error != Error.VALID) return error;
		switch (type) {
		case MAX:
		case MIN:
		case STEP_SIZE:
		case VALUE:
			return isFloatNum((String)value);
		default:
			break;
		}
		
		return Error.VALID;
	}
	
	private Error isFloatNum(String value){
		try {
			Float.parseFloat(value);
		}catch (Exception e){
			return Error.MAX_MIN_STEP_IS_NOT_FLOAT_NUM;
		}
		
		return Error.VALID;
	}

	@Override
	public void setPropertyValue(UIElementPropertyType type, Object value) {
		super.setPropertyValue(type, value);
		
		System.out.println("set property value: " + type + ":" + value);
		switch (type) {
		case VALUE:
			this.value = Float.parseFloat((String)value);
			break;
		case MAX:
			max = Float.parseFloat((String)value);
			break;
		case MIN:
			min = Float.parseFloat((String)value);
			break;
		case STEP_SIZE:
			stepSize = Float.parseFloat((String)value);
			break;
		case DISABLE:
			disabled = (boolean)value;
			break;
		case VERTICAL:
			vertical = (boolean)value;
			break;

		default:
			break;
		}
	}

	@Override
	public Object getPropertyValue(UIElementPropertyType type) {
		Object result = super.getPropertyValue(type);
		
		if (result != null) return result;
		switch (type) {
		case VALUE:
			return getValue();
		case MIN:
			return getMin();
		case MAX:
			return getMax();
		case STEP_SIZE:
			return getStepSize();
		case DISABLE:
			return isDisabled();
		case VERTICAL:
			return isVertical();
		default:
			break;
		}
		
		return null;
	}

	static public class SliderStyle{
		public Image background;
		public Image disabledBackground;
		public Image knob, disabledKnob;
		public Image knobBefore, knobAfter, disabledKnobBefore, disabledKnobAfter;

		public SliderStyle(){
			String background = Resources.getAndroidProjectPath(Resources.getCurrentProject()) + "/assets/imgs/slider-background.png";
			String knob = Resources.getAndroidProjectPath(Resources.getCurrentProject()) + "/assets/imgs/knob.png";
			
			Resources.addImage(background);
			Resources.addImage(knob);
			
			this.background = Resources.getImageByPath(background);
			this.knob = Resources.getImageByPath(knob);
			
			disabledBackground = this.background;
			disabledKnob = this.knob;
		}
		
		public SliderStyle(Image background, Image knob) {
			super();
			this.background = background;
			this.knob = knob;
		}
	}

	@Override
	public Element generateXml(Document doc, Element parentNode) {
		Element el = super.generateXml(doc, parentNode);
		
		genenerateAttrXml(doc, el, UIElementPropertyType.MAX, max);
		genenerateAttrXml(doc, el, UIElementPropertyType.MIN, min);
		genenerateAttrXml(doc, el, UIElementPropertyType.STEP_SIZE, stepSize);
		genenerateAttrXml(doc, el, UIElementPropertyType.VALUE, value);
		genenerateAttrXml(doc, el, UIElementPropertyType.VERTICAL, vertical);
		
		Element styleEl = doc.createElement("style");
		generateStyleXml(doc, styleEl, "background", Resources.getPathOfImage(style.background));
		generateStyleXml(doc, styleEl, "knob", Resources.getPathOfImage(style.knob));
		generateStyleXml(doc, styleEl, "disabledBackground", Resources.getPathOfImage(style.disabledBackground));
		generateStyleXml(doc, styleEl, "disabledKnob", Resources.getPathOfImage(style.disabledKnob));
		
		el.appendChild(styleEl);
		
		return el;
	}

	@Override
	public void restore(Element element) {
		super.restore(element);
		
		max = Float.parseFloat(readValue(element, UIElementPropertyType.MAX));
		min = Float.parseFloat(readValue(element, UIElementPropertyType.MIN));
		stepSize = Float.parseFloat(readValue(element, UIElementPropertyType.STEP_SIZE));
		value = Float.parseFloat(readValue(element, UIElementPropertyType.VALUE));
		vertical = readValue(element, UIElementPropertyType.VERTICAL) == "true" ? true : false;
		
		readStyle(element);
	}
	
	public void readStyle(Element element){
		NodeList nList = element.getElementsByTagName("style");
		Element styleEl = (Element)(nList.item(0));
		
		if (style == null) style = new SliderStyle();
		
		style.background = readStyleEl(styleEl, "background");
		style.knob = readStyleEl(styleEl, "knob");
		style.disabledBackground = readStyleEl(styleEl, "disabledBackground");
		style.disabledKnob = readStyleEl(styleEl, "disabledKnob");
	}
	
	protected Image readStyleEl(Element styleEl, String tag){
		NodeList nList = styleEl.getElementsByTagName(tag);
		Element tagEl = (Element)(nList.item(0));
		
		return Resources.getImageByPath(tagEl.getAttribute("path"));
	}

	@Override
	public Image getImageByIndex(int imgIndex) {
		return null;
	}
}
