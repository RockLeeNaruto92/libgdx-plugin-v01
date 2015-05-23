package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.swt.custom.Align;
import libgdxpluginv01.swt.custom.Scaling;
import libgdxpluginv01.views.properties.UIElementPropertyType;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CImage extends UIElement {
	private Image image;
	private int align = Align.left;
	private int scaling = Scaling.fit;
	
	public CImage(Composite root, Point location, UIController uiController,
			int type) {
		super(root, location, uiController, type);
	}
	
	public CImage(Composite root, Point location, UIController uiController) {
		super(root, location, uiController, UIElementType.IMAGE);
	}

	@Override
	public String getDefaultNamePattern() {
		return Parameter.DEFAULT_IMAGE_NAME_PATTERN;
	}

	@Override
	public Point getDefaultSize() {
		return new Point(getImage().getBounds().width, getImage().getBounds().height);
	}

	@Override
	public Point getMinSize() {
		return getDefaultSize();
	}

	@Override
	public void drawContent(PaintEvent e) {
		Rectangle bound = getImage().getBounds();
		int x = 0, y = 0;
		Point size = getSize();
		
		switch (align) {
		case Align.left:
			x = 0;
			break;
		case Align.right:
			x = size.x - bound.width;
			break;
		case Align.top:
			y = 0;
			break;
		case Align.bottom:
			y = size.y - bound.height;
			break;
		case Align.topLeft:
			x = 0;
			y = 0;
			break;
		case Align.topRight:
			x = size.x - bound.width;
			y = 0;
			break;
		case Align.bottomLeft:
			x = 0;
			y = size.y - bound.height;
			break;
		case Align.bottomRight:
			x = size.x - bound.width;
			y = size.y - bound.height;
			break;
		case Align.center:
			x = (size.x - bound.width) / 2;
			y = (size.y - bound.height) / 2;
			break;

		default:
			break;
		}
		
		int width = 0, height = 0;
		
		switch (scaling) {
		case Scaling.fit:{
			float targetRatio = size.x * 1f / size.y;
			float sourceRatio = bound.height * 1f / bound.width;
			float scale = targetRatio > sourceRatio ? size.x * 1f / bound.width : size.y * 1f/ bound.height;
			width = (int)(bound.width * scale);
			height = (int)(bound.height * scale);
			break;
		}
		case Scaling.fill:{
			float targetRatio = size.x * 1f / size.y;
			float sourceRatio = bound.height * 1f / bound.width;
			float scale = targetRatio < sourceRatio ? size.x * 1f / bound.width : size.y * 1f/ bound.height;
			width = (int)(bound.width * scale);
			height = (int)(bound.height * scale);
			break;
		}
		case Scaling.fillX:{
			float scale = size.x * 1f / bound.width;
			width = (int)(bound.width * scale);
			height = (int)(bound.height * scale);
			break;
		}
		case Scaling.fillY:{
			float scale = size.y * 1f / bound.height;
			width = (int)(bound.width * scale);
			height = (int)(bound.height * scale);
			break;
		}
		case Scaling.stretch:
			width = size.x;
			height = size.y;
			break;
		case Scaling.stretchX:
			width = size.x;
			break;
		case Scaling.stretchY:
			height = size.y;
			break;
		case Scaling.none:
			break;

		default:
			break;
		}
		
		e.gc.drawImage(image, 0, 0, bound.width, bound.height, x, y, width, height);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseEnter() {
		// TODO Auto-generated method stub
		
	}

	public Image getImage() {
		if (image == null){
			image = new Image(Display.getCurrent(), Utility.getFile("datas/default/Sprite/libgdx.png").toString());
		}
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getAlign() {
		return align;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	public int getScaling() {
		return scaling;
	}

	public void setScaling(int scaling) {
		this.scaling = scaling;
	}

	@Override
	public Element generateXml(Document doc, Element parentNode) {
		Element el = super.generateXml(doc, parentNode);
		
		if (align != Align.left)
			genenerateAttrXml(doc, el, UIElementPropertyType.ALIGN, align);
		if (scaling != Scaling.fit)
			genenerateAttrXml(doc, el, UIElementPropertyType.SCALING, scaling);
		
		// generate image path
		
		return el;
	}
}
