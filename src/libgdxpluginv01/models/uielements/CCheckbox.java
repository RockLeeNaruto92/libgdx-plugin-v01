package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.swt.custom.Align;
import libgdxpluginv01.swt.custom.BitmapFont;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class CCheckbox extends CButton {
	private CheckboxStyle style;
	private String text;
	private float scaleX = 1, scaleY = 1;
	private int align = Align.left;
	private int padLeft, padRight, padTop, padBottom;
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
		// TODO Auto-generated method stub
		return Parameter.DEFAULT_CHECKBOX_NAME_PATTERN;
	}

	@Override
	public Point getDefaultSize() {
		// TODO Auto-generated method stub
		Point defaultSize = getStyle().font.getActualSize(getText(), 1, 1);
		Rectangle bound = getStyle().checkboxOn.getBounds();
		
		defaultSize.x += bound.width;
		defaultSize.y = (defaultSize.y > bound.height) ? defaultSize.y : bound.height;
		
		return defaultSize;
	}

	@Override
	public Point getMinSize() {
		// TODO Auto-generated method stub
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
		super.drawContent(e);
		
		int x = 0, y = 0;
		
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
		
		Rectangle bound = drawable.getBounds();
		
		e.gc.drawImage(drawable, 0, 0, bound.width, bound.height, padLeft, padTop, (int)(bound.width * scaleX), (int)(bound.height * scaleY));
		
		int nextX = (int)((2 * padLeft + bound.width) * scaleX);
		// draw String
		Point size = getSize();
		Point defaultSize = getDefaultSize();
		
		switch (align) {
		case Align.left:
			x = nextX;
			break;
		case Align.right:
			x = size.x - defaultSize.x - padRight;
			break;
		case Align.top:
			y = padTop;
			break;
		case Align.bottom:
			y = size.y - padBottom - defaultSize.y;
			break;
		case Align.topLeft:
			x = nextX;
			y = padTop;
			break;
		case Align.topRight:
			x = size.x - defaultSize.x - padRight;
			y = padTop;
			break;
		case Align.bottomLeft:
			x = nextX;
			y = size.y - padBottom - defaultSize.y;
			break;
		case Align.bottomRight:
			x = size.x - defaultSize.x - padRight;
			y = size.y - defaultSize.y - padBottom;
			break;
		default:
			x = nextX;
			y = padTop;
			break;
		}
		
		getStyle().font.drawString(e.gc, getText(), x, y, scaleX, scaleY);
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


	static class CheckboxStyle extends ButtonStyle{
		public BitmapFont font;
		public Color fontColor, downFontColor, overFontColor, checkedFontColor, checkedOverFontColor, disabledFontColor;
		public Image checkboxOn, checkboxOff;
		public Image checkboxOver, checkboxOnDisabled, checkboxOffDisabled;
		
		public CheckboxStyle(){
			super();
			
			font = new BitmapFont(Display.getCurrent(), Utility.getFile("datas/default/Font/default.fnt").toString());
			checkboxOn = new Image(Display.getCurrent(), Utility.getFile("datas/default/Checkbox/on.png").toString());
			checkboxOff = new Image(Display.getCurrent(), Utility.getFile("datas/default/Checkbox/off.png").toString());
			
			checkboxOver = checkboxOn;
			checkboxOnDisabled = checkboxOn;
			checkboxOffDisabled = checkboxOff;
		}
	}
}
