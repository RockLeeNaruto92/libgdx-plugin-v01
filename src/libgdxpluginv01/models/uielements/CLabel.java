package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.swt.custom.Align;
import libgdxpluginv01.swt.custom.BitmapFont;
import libgdxpluginv01.views.properties.UIElementPropertyType;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class CLabel extends UIElement {
	private int labelAlign = Align.right;
	private boolean wrap;
	private float fontScaleX = 1, fontScaleY = 1;
	private boolean ellipsis;
	private String text;
	
	private LabelStyle style ;
	
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
	public void createControls() {
	}

	@Override
	public void displayBound(boolean display) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawContent(PaintEvent e) {
		int x = 0, y = 0;
		Point size = getSize();
		Point defaultSize = getDefaultSize();
		
		// draw background
		if (style.background != null){
			Rectangle bound = style.background.getBounds();
			e.gc.drawImage(style.background, 0, 0, bound.width, bound.height, 0, 0, size.x, size.y);
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


	public class LabelStyle{
		public BitmapFont font;
		public Color fontColor;
		public Image background;
		
		public LabelStyle(){
			font = new BitmapFont(Display.getCurrent(), Utility.getFile("datas/default/Font/default.fnt").toString());
//			font = new BitmapFont(Display.getCurrent(), "datas\\default.fnt");
			fontColor = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
		}
		
		public LabelStyle(BitmapFont font, Color fontColor){
			this.font = font;
			this.fontColor = fontColor;
		}
	}
}
