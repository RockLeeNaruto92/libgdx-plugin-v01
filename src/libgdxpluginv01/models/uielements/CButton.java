package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Resources;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.views.properties.UIElementPropertyType;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CButton extends UIElement {
	private boolean checked, disabled, over, up = true;
	
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
	public void onMouseUp() {
		up = true;
		redraw();
	}

	@Override
	public void onMouseDown() {
		up = false;
		redraw();
	}

	@Override
	public void onMouseHover() {
		over = true;
		redraw();
	}

	@Override
	public void onMouseMove() {
		if (up){
			over = true;
			redraw();
		}
	}
	
	@Override
	public void onMouseDoubleClick() {
	}

	@Override
	public void onMouseExit() {
		over = false;
		redraw();
	}

	@Override
	public void onMouseEnter() {
	}

	@Override
	public void drawContent(PaintEvent e) {
		Image drawable = null;
		ButtonStyle style = getStyle();
		
		if (disabled & style.disabled != null)
			drawable = style.disabled;
		else if (!up && style.down != null)
			drawable = style.down;
		else if (checked && style.checked != null)
			drawable = (style.checkedOver != null && over) ? style.checkedOver : style.checked;
		else if (over & style.over != null)
			drawable = style.over;
		else if (style.up != null)
			drawable = style.up;
		
		Rectangle bound = drawable.getBounds();
		e.gc.drawImage(drawable, 0, 0, bound.width, bound.height, 0, 0, getSize().x, getSize().y);
	}
	
	@Override
	public void setPropertyValue(UIElementPropertyType type, Object value) {
		super.setPropertyValue(type, value);
		
		switch (type) {
		case CHECK:
			setChecked((boolean)value);
			break;
		case DISABLE:
			setDisabled((boolean)value);
			break;
		default:
			break;
		}
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

	public static class ButtonStyle {
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
			/*String upPath = Utility.getFile("datas/default/ButtonStyle/up.png").toString();
			String downPath = Utility.getFile("datas/default/ButtonStyle/down.png").toString();*/
			String upPath = Resources.getAndroidProjectPath(Resources.getCurrentProject()) + "/assets/imgs/up.png";
			String downPath = Resources.getAndroidProjectPath(Resources.getCurrentProject()) + "/assets/imgs/down.png";
			Resources.addImage(upPath);
			Resources.addImage(downPath);
			up = Resources.getImageByPath(upPath);
			down = Resources.getImageByPath(downPath);
			over = up;
			checked = up;
			checkedOver = up;
			disabled = up;
		}
		
		public ButtonStyle(Image up){
			this.up = up;
			this.down = up;
			this.checked = up;
			this.checkedOver = up;
			this.over = up;
			this.disabled = up;
		}
	}

	@Override
	public Element generateXml(Document doc, Element parentNode) {
		Element el = super.generateXml(doc, parentNode);
		
		if (disabled)
			genenerateAttrXml(doc, el, UIElementPropertyType.DISABLE, disabled);
		if (checked)
			genenerateAttrXml(doc, el, UIElementPropertyType.CHECK, checked);
		
		// generate style xml
		
		if (style != null) el.appendChild(generateStyleEl(doc, el, style));
		
		return el;
	}
	
	private Element generateStyleEl(Document doc, Element owner, ButtonStyle style) {
		Element styleEl = doc.createElement("style");
		
		generateStyleXml(doc, styleEl, "up", Resources.getPathOfImage(style.up));
		generateStyleXml(doc, styleEl, "down", Resources.getPathOfImage(style.down));
		generateStyleXml(doc, styleEl, "over", Resources.getPathOfImage(style.over));
		generateStyleXml(doc, styleEl, "checked", Resources.getPathOfImage(style.checked));
		generateStyleXml(doc, styleEl, "checkedOver", Resources.getPathOfImage(style.checkedOver));
		generateStyleXml(doc, styleEl, "disabled", Resources.getPathOfImage(style.disabled));
		
		return styleEl;
	}
	
	@Override
	public void restore(Element element) {
		super.restore(element);
		
		checked = (readValue(element, UIElementPropertyType.CHECK) == null) ? false : true;
		disabled = (readValue(element, UIElementPropertyType.DISABLE) == null) ? false : true;
		
		// restore style
		readStyle(element);
	}
	
	public void readStyle(Element element){
		NodeList nList = element.getElementsByTagName("style");
		Element styleEl = (Element)(nList.item(0));
		
		if (style == null) style = new ButtonStyle();
		
		style.up = readStyleEl(styleEl, "up");
		style.down = readStyleEl(styleEl, "down");
		style.checked = readStyleEl(styleEl, "checked");
		style.checkedOver = readStyleEl(styleEl, "checkedOver");
		style.disabled = readStyleEl(styleEl, "disabled");
		style.over = readStyleEl(styleEl, "over");
	}
	
	protected Image readStyleEl(Element styleEl, String tag){
		NodeList nList = styleEl.getElementsByTagName(tag);
		Element tagEl = (Element)(nList.item(0));
		
		return Resources.getImageByPath(tagEl.getAttribute("path"));
	}
}