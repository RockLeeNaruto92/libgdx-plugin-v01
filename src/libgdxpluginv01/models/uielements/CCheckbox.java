package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Resources;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.swt.custom.Align;
import libgdxpluginv01.swt.custom.BitmapFont;
import libgdxpluginv01.views.properties.UIElementPropertyType;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CCheckbox extends CButton {
	private CheckboxStyle style;
	private String text;
	private int align = Align.center;
	private int padLeft, padRight, padTop, padBottom;
	private float fontScaleX = 1, fontScaleY = 1;
	private boolean on;
	
	public CCheckbox(Composite root, Point location, UIController uiController,
			int type) {
		super(root, location, uiController, type);
	}
	
	public CCheckbox(Composite root, Point location, UIController uiController) {
		super(root, location, uiController, UIElementType.CHECKBOX);
	}

	@Override
	public String getDefaultNamePattern() {
		return Parameter.DEFAULT_CHECKBOX_NAME_PATTERN;
	}

	@Override
	public Point getDefaultSize() {
		Point defaultSize = getStyle().font.getActualSize(getText(), 1, 1);
		Rectangle bound = getStyle().checkboxOn.getBounds();
		
		defaultSize.x += bound.width;
		defaultSize.y = (defaultSize.y > bound.height) ? defaultSize.y : bound.height;
		
		Point textSize = getStyle().font.getActualSize(getText(), fontScaleX, fontScaleY);
		
		if (textSize.x > defaultSize.x)
			defaultSize.x = textSize.x;
		if (textSize.y > defaultSize.y)
			defaultSize.y = textSize.y;
		
		return defaultSize;
	}

	@Override
	public Point getMinSize() {
		return getDefaultSize();
	}

	@Override
	public void drawContent(PaintEvent e) {
		super.drawContent(e);
		
		int x = 0, y = 0;
		int imgX = padLeft, imgY = 0;
		Image drawable;
		
		// draw checkbox
		if (isDisabled()){
			drawable = isChecked() ? getStyle().checkboxOnDisabled : getStyle().checkboxOffDisabled;
		} else {
			if (isOver()){
				drawable = getStyle().checkboxOver;
			} else {
				drawable = isChecked() ? getStyle().checkboxOn : getStyle().checkboxOff;
			}
		}
		
		// draw String
		Point size = getSize();
		Point defaultSize = getDefaultSize();
		Point textSize = getStyle().font.getActualSize(getText(), fontScaleX, fontScaleY);
		Rectangle imgBound = drawable.getBounds();
		int imgWidth = 0;
		
		switch (align) {
		case Align.left:
			imgX = padLeft;
			imgWidth = (size.x > defaultSize.x) ? imgBound.width : size.x - textSize.x;
			x = imgX + imgWidth;
			y = (size.y - textSize.y) / 2;
			imgY = (size.y - imgBound.height) / 2;
			break;
		case Align.right:
			imgWidth = (size.x > defaultSize.x) ? imgBound.width : size.x - textSize.x;
			x = (size.x > textSize.x) ? size.x - padRight - defaultSize.x : 0;
			imgX = x - imgWidth;
			y = (size.y - textSize.y) / 2;
			imgY = (size.y - imgBound.height) / 2;
			break;
		case Align.top:
			if (size.x > defaultSize.x){
				imgX = (size.x - textSize.x - imgBound.width) / 2;
				x = imgX + imgBound.width;
				imgWidth = imgBound.width;
			}else{
				imgX = padLeft;
				imgWidth = size.x - textSize.x;
				x = imgWidth + imgX;
			}
			y = padTop;
			imgY = padTop;
			break;
		case Align.bottom:
			if (size.x > defaultSize.x){
				imgX = (size.x - textSize.x - imgBound.width) / 2;
				x = imgX + imgBound.width;
				imgWidth = imgBound.width;
			}else{
				imgX = padLeft;
				imgWidth = size.x - textSize.x;
				x = imgWidth + imgX;
			}
			y = size.y - padBottom - defaultSize.y;
			imgY = y;
			break;
		case Align.topLeft:
			imgX = padLeft;
			imgWidth = (size.x > defaultSize.x) ? imgBound.width : size.x - textSize.x;
			x = imgX + imgWidth;
			y = padTop;
			imgY = padTop;
			break;
		case Align.topRight:
			imgWidth = (size.x > defaultSize.x) ? imgBound.width : size.x - textSize.x;
			x = (size.x > textSize.x) ? size.x - padRight - defaultSize.x : 0;
			imgX = x - imgWidth;
			y = padTop;
			imgY = padTop;
			break;
		case Align.bottomLeft:
			imgX = padLeft;
			imgWidth = (size.x > defaultSize.x) ? imgBound.width : size.x - textSize.x;
			x = imgX + imgWidth;
			y = size.y - padBottom - defaultSize.y;
			imgY = y;
			break;
		case Align.bottomRight:
			imgWidth = (size.x > defaultSize.x) ? imgBound.width : size.x - textSize.x;
			x = (size.x > textSize.x) ? size.x - padRight - defaultSize.x : 0;
			imgX = x - imgWidth;
			y = size.y - defaultSize.y - padBottom;
			imgY = y;
			break;
		case Align.center:
			if (size.x > defaultSize.x){
				imgX = (size.x - textSize.x - imgBound.width) / 2;
				x = imgX + imgBound.width;
				imgWidth = imgBound.width;
			}else{
				imgX = padLeft;
				imgWidth = size.x - textSize.x;
				x = imgWidth + imgX;
			}
			y = (size.y - textSize.y) / 2;
			imgY = (size.y - imgBound.height) / 2;
			break;
		default:
			break;
		}
		
		getStyle().font.drawString(e.gc, getText(), x, y, fontScaleX, fontScaleY);
		
		e.gc.drawImage(drawable, 0, 0, imgBound.width, imgBound.height, imgX, imgY, imgWidth, imgBound.height);
	}

	@Override
	public void onMouseUp() {
		super.onMouseUp();
	}

	@Override
	public void onMouseDown() {
		super.onMouseDown();
	}

	@Override
	public void onMouseDoubleClick() {
		super.onMouseDoubleClick();
	}

	@Override
	public void onMouseHover() {
		super.onMouseHover();
	}

	@Override
	public void onMouseMove() {
		super.onMouseMove();
	}

	@Override
	public void setPropertyValue(UIElementPropertyType type, Object value) {
		super.setPropertyValue(type, value);
		
		switch (type) {
		case TEXT:
			setText((String)value);
			break;
		case FONT_SCALE_X:
			setFontScaleX(Float.parseFloat((String)value));
			break;
		case FONT_SCALE_Y:
			setFontScaleY(Float.parseFloat((String)value));
			break;
		case ALIGN:
			setAlign(Align.getAlign((int)value));
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
		case FONT_SCALE_X:
			return fontScaleX;
		case FONT_SCALE_Y:
			return fontScaleY;
		case ALIGN:
			return align;
		default:
			break;
		}
		
		return null;
	}

	public CheckboxStyle getStyle() {
		if (style == null){
			style = new CheckboxStyle();
		}
		
		return style;
	}

	public void setStyle(CheckboxStyle style) {
		this.style = style;
	}

	public String getText() {
		if (text == null)
			text = getName();
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getAlign() {
		return align;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	public int getPadLeft() {
		return padLeft;
	}

	public void setPadLeft(int padLeft) {
		this.padLeft = padLeft;
	}

	public int getPadRight() {
		return padRight;
	}

	public void setPadRight(int padRight) {
		this.padRight = padRight;
	}

	public int getPadTop() {
		return padTop;
	}

	public void setPadTop(int padTop) {
		this.padTop = padTop;
	}

	public int getPadBottom() {
		return padBottom;
	}

	public void setPadBottom(int padBottom) {
		this.padBottom = padBottom;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public float getFontScaleX() {
		return fontScaleX;
	}

	public void setFontScaleX(float fontScaleX) {
		this.fontScaleX = fontScaleX;
	}

	public float getFontScaleY() {
		return fontScaleY;
	}

	public void setFontScaleY(float fontScaleY) {
		this.fontScaleY = fontScaleY;
	}

	public static class CheckboxStyle extends ButtonStyle{
		public BitmapFont font;
		public Color fontColor, downFontColor, overFontColor, checkedFontColor, checkedOverFontColor, disabledFontColor;
		public Image checkboxOn, checkboxOff;
		public Image checkboxOver, checkboxOnDisabled, checkboxOffDisabled;
		
		public CheckboxStyle(){
			super();
			
			String checkboxOnPath = Resources.getAndroidProjectPath(Resources.getCurrentProject()) + "/assets/imgs/on.png";
			String checkboxOffPath = Resources.getAndroidProjectPath(Resources.getCurrentProject()) + "/assets/imgs/off.png";
			String defaultFontPath = Resources.getAndroidProjectPath(Resources.getCurrentProject()) + "/assets/fonts/default.fnt";
			
			Resources.addImage(checkboxOffPath);
			Resources.addImage(checkboxOnPath);
			Resources.addFont(defaultFontPath);
			
			font = Resources.getFontByPath(defaultFontPath);
			checkboxOn = Resources.getImageByPath(checkboxOnPath);
			checkboxOff = Resources.getImageByPath(checkboxOffPath);
			
			checkboxOver = checkboxOn;
			checkboxOnDisabled = checkboxOn;
			checkboxOffDisabled = checkboxOff;
		}
		
		public CheckboxStyle(Image up, Image on, Image off, BitmapFont font){
			super(up);
			
			this.font = font;
			this.checkboxOn = on;
			this.checkboxOff = off;
		}
	}

	@Override
	public Element generateXml(Document doc, Element parentNode) {
		Element el = super.generateXml(doc, parentNode);
		
		genenerateAttrXml(doc, el, UIElementPropertyType.ALIGN, align);
		genenerateAttrXml(doc, el, UIElementPropertyType.FONT_SCALE_X, fontScaleX);
		genenerateAttrXml(doc, el, UIElementPropertyType.FONT_SCALE_Y, fontScaleY);
		genenerateAttrXml(doc, el, UIElementPropertyType.FONT_SCALE_Y, fontScaleY);
		genenerateAttrXml(doc, el, UIElementPropertyType.TEXT, text);
		
		// generate style xml
		
		el.appendChild(generateStyleEl(doc, el));
		
		return el;
	}

	private Element generateStyleEl(Document doc, Element owner) {
		Element styleEl = doc.createElement("style");
		
		generateStyleXml(doc, styleEl, "font", Resources.getPathOfFont(style.font));
		generateStyleXml(doc, styleEl, "up", Resources.getPathOfImage(style.up));
		generateStyleXml(doc, styleEl, "down", Resources.getPathOfImage(style.down));
		generateStyleXml(doc, styleEl, "over", Resources.getPathOfImage(style.over));
		generateStyleXml(doc, styleEl, "checked", Resources.getPathOfImage(style.checked));
		generateStyleXml(doc, styleEl, "checkedOver", Resources.getPathOfImage(style.checkedOver));
		generateStyleXml(doc, styleEl, "disabled", Resources.getPathOfImage(style.disabled));
		
		generateStyleXml(doc, styleEl, "checkboxOn", Resources.getPathOfImage(style.checkboxOn));
		generateStyleXml(doc, styleEl, "checkboxOff", Resources.getPathOfImage(style.checkboxOff));
		generateStyleXml(doc, styleEl, "checkboxOnDisabled", Resources.getPathOfImage(style.checkboxOnDisabled));
		generateStyleXml(doc, styleEl, "checkboxOffDisabled", Resources.getPathOfImage(style.checkboxOffDisabled));
		generateStyleXml(doc, styleEl, "checkboxOver", Resources.getPathOfImage(style.checkboxOver));
		
		return styleEl;
	}

	@Override
	public void restore(Element element) {
		// TODO Auto-generated method stub
		setChecked((readValue(element, UIElementPropertyType.CHECK) == null) ? false : true);
		setDisabled((readValue(element, UIElementPropertyType.DISABLE) == null) ? false : true);
		
		text = readValue(element, UIElementPropertyType.TEXT);
		align = Integer.parseInt(readValue(element, UIElementPropertyType.ALIGN));
		fontScaleX = Float.parseFloat(readValue(element, UIElementPropertyType.FONT_SCALE_X));
		fontScaleY = Float.parseFloat(readValue(element, UIElementPropertyType.FONT_SCALE_Y));
		
		readStyle(element);
	}

	@Override
	public void readStyle(Element element) {
		NodeList nList = element.getElementsByTagName("style");
		Element styleEl = (Element)(nList.item(0));
		nList = styleEl.getElementsByTagName("font");
		Element fontEl = (Element)(nList.item(0));
		
		if (style == null) style = new CheckboxStyle();
		
		style.font = Resources.getFontByPath(fontEl.getAttribute("path"));
		
		// read checkbox on
		style.checkboxOn = readStyleEl(styleEl, "checkboxOn");
		style.checkboxOff = readStyleEl(styleEl, "checkboxOff");
		style.checkboxOnDisabled = readStyleEl(styleEl, "checkboxOnDisabled");
		style.checkboxOffDisabled = readStyleEl(styleEl, "checkboxOffDisabled");
		style.checkboxOver = readStyleEl(styleEl, "checkboxOver");
		
		style.up = readStyleEl(styleEl, "up");
		style.down = readStyleEl(styleEl, "down");
		style.checked = readStyleEl(styleEl, "checked");
		style.checkedOver = readStyleEl(styleEl, "checkedOver");
		style.disabled = readStyleEl(styleEl, "disabled");
		style.over = readStyleEl(styleEl, "over");
	}
}
