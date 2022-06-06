package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Resources;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.swt.custom.Align;
import libgdxpluginv01.swt.custom.BitmapFont;
import libgdxpluginv01.views.properties.Error;
import libgdxpluginv01.views.properties.UIElementPropertyType;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CLabel extends UIElement {
	private int labelAlign = Align.left;
	private boolean wrap;
	private float fontScaleX = 1, fontScaleY = 1;
	private boolean ellipsis;
	private String text;
	
	private LabelStyle style;
	
	public CLabel(Composite root, Point location, UIController uiController,
			int type) {
		super(root, location, uiController, type);
		
		text = getName();
	}
	
	public CLabel(Composite root, Point location, UIController uiController) {
		super(root, location, uiController, UIElementType.LABEL);
		text = getName();
	}

	@Override
	public String getDefaultNamePattern() {
		return Parameter.DEFAULT_LABEL_NAME_PATTERN;
	}

	@Override
	public Point getDefaultSize() {
		// TODO Auto-generated method stub
		return getStyle().font.getActualSize(getText(), 1, 1);
	}

	@Override
	public Point getMinSize() {
		return getDefaultSize();
	}

	@Override
	public void drawContent(PaintEvent e) {
		int x = 0, y = 0;
		Point size = getSize();
		Point defaultSize = style.font.getActualSize(text, fontScaleX, fontScaleY);
		
		if (size.x < defaultSize.x || size.y < defaultSize.y){
			setSize(defaultSize);
			size = defaultSize;
		}
		
		// draw background
		if (style.background != null){
			Rectangle bound = style.background.getBounds();
			e.gc.drawImage(style.background, 0, 0, bound.width, bound.height, 0, 0, size.x, size.y);
			System.out.println("style background not null");
		}
		
		switch (labelAlign) {
		case Align.left:
			x = 0;
			break;
		case Align.right:
			x = size.x - defaultSize.x;
			break;
		case Align.top:
			y = 0;
			break;
		case Align.bottom:
			y = size.y - defaultSize.y;
			break;
		case Align.topLeft:
			x = 0;
			y = 0;
			break;
		case Align.topRight:
			x = size.x - defaultSize.x;
			y = 0;
			break;
		case Align.bottomLeft:
			x = 0;
			y = size.y - defaultSize.y;
			break;
		case Align.bottomRight:
			x = size.x - defaultSize.x;
			y = size.y - defaultSize.y;
			break;
		case Align.center:
			x = (size.x - defaultSize.x) / 2;
			y = (size.y - defaultSize.y) / 2;
			break;
		default:
			x = 0;
			y = 0;
			break;
		}
		style.font.drawString(e.gc, getText(), x, y, fontScaleX, fontScaleY);
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
	}

	@Override
	public void onMouseMove() {
	}
	
	@Override
	public void onMouseExit() {
	}

	@Override
	public void onMouseEnter() {
		// TODO Auto-generated method stub
		
	}

	public int getLabelAlign() {
		return labelAlign;
	}

	public void setLabelAlign(int labelAlign) {
		this.labelAlign = labelAlign;
	}

	public boolean isWrap() {
		return wrap;
	}

	public void setWrap(boolean wrap) {
		this.wrap = wrap;
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

	public boolean isEllipsis() {
		return ellipsis;
	}

	public void setEllipsis(boolean ellipsis) {
		this.ellipsis = ellipsis;
	}

	public String getText() {
		if (text == null){
			text = getName();
		}
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LabelStyle getStyle() {
		if (style == null)
			style = new LabelStyle();
		
		return style;
	}

	public void setStyle(LabelStyle style) {
		this.style = style;
	}
	
	@Override
	public Object getPropertyValue(UIElementPropertyType type) {
		// TODO Auto-generated method stub
		Object result = super.getPropertyValue(type);
		
		if (result != null) return result;
		switch (type) {
		case ALIGN:
			return labelAlign;
		case FONT_SCALE_X:
			return fontScaleX;
		case FONT_SCALE_Y:
			return fontScaleY;
		case ELLIPSIS:
			return ellipsis;
		case WRAP:
			return wrap;
		case TEXT:
			return getText();
		default:
			break;
		}
		
		return null;
	}
	
	@Override
	public void setPropertyValue(UIElementPropertyType type, Object value) {
		// TODO Auto-generated method stub
		super.setPropertyValue(type, value);
		switch (type) {
		case ALIGN:
			setLabelAlign(Align.getAlign((int)value));
			break;
		case FONT_SCALE_X:
			setFontScaleX(Float.parseFloat((String)value));
			break;
		case FONT_SCALE_Y:
			setFontScaleY(Float.parseFloat((String)value));
			break;
		case ELLIPSIS:
			setEllipsis((boolean)value);
			break;
		case WRAP:
			setWrap((boolean)value);
			break;
		case TEXT:
			setText((String)value);
			break;
		default:
			break;
		}
	}

	@Override
	public Point getRange(UIElementPropertyType type) {
		// TODO Auto-generated method stub
		Point result = super.getRange(type);
		
		if (result != null) return result;
		switch (type) {
		case FONT_SCALE_X:
			return Parameter.FONT_SCALE_RANGE_X;
		case FONT_SCALE_Y:
			return Parameter.FONT_SCALE_RANGE_Y;
		default:
			break;
		}
		return null;
	}
	
	private Error isValidText(String text){
		return Error.VALID;
	}
	
	private Error isValidFontScale(String text){
		if (text.length() == 0) return Error.FONT_SCALE_IS_EMPTY;
		
		return Error.VALID;
	}

	@Override
	public Error isValidProperty(UIElementPropertyType type, String value) {
		Error result = super.isValidProperty(type, value);
		
		if (result != Error.VALID) return result;
		switch (type) {
		case TEXT:
			return isValidText(value);
		case FONT_SCALE_X:
		case FONT_SCALE_Y:
			return isValidFontScale(value);
		default:
			break;
		}
		
		return Error.VALID;
	}

	public class LabelStyle{
		public BitmapFont font;
		public Color fontColor;
		public Image background;
		
		public LabelStyle(){
			String defaultFontPath = Resources.getAndroidProjectPath(Resources.getCurrentProject()) + "/assets/fonts/default.fnt";
			
			Resources.addFont(defaultFontPath);
			font = Resources.getFontByPath(defaultFontPath);
			fontColor = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
		}
		
		public LabelStyle(BitmapFont font, Color fontColor){
			this.font = font;
			this.fontColor = fontColor;
		}
	}

	@Override
	public Element generateXml(Document doc, Element parentNode) {
		Element el = super.generateXml(doc, parentNode);
		
		if (labelAlign != Align.left)
			genenerateAttrXml(doc, el, UIElementPropertyType.ALIGN, labelAlign);
		genenerateAttrXml(doc, el, UIElementPropertyType.FONT_SCALE_X, fontScaleX);
		genenerateAttrXml(doc, el, UIElementPropertyType.FONT_SCALE_Y, fontScaleY);
		genenerateAttrXml(doc, el, UIElementPropertyType.TEXT, text);
		genenerateAttrXml(doc, el, UIElementPropertyType.WRAP, wrap);
		genenerateAttrXml(doc, el, UIElementPropertyType.ELLIPSIS, ellipsis);
		
		// generate style xml`
		Element styleEl = doc.createElement("style");
		
		generateStyleXml(doc, styleEl, "background", Resources.getPathOfImage(style.background));
		generateStyleXml(doc, styleEl, "font", Resources.getPathOfFont(style.font));
		
		el.appendChild(styleEl);
		return el;
	}

	@Override
	public void restore(Element element) {
		// TODO Auto-generated method stub
		super.restore(element);

		// read style
		NodeList nList = element.getElementsByTagName("style");
		Element styleEl = (Element)(nList.item(0));
		
		if (style == null) style = new LabelStyle();
		
		// read font
		nList = styleEl.getElementsByTagName("font");
		Element fontEl = (Element)(nList.item(0));
		style.font = Resources.getFontByPath(fontEl.getAttribute("path"));
	}

	@Override
	public Image getImageByIndex(int imgIndex) {
		return null;
	}
}
