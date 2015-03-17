package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.swt.custom.Align;
import libgdxpluginv01.swt.custom.Scaling;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

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
	public void createControls() {
	}

	@Override
	public void displayBound(boolean display) {

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

		default:
			break;
		}
		
		switch (scaling) {
		case Scaling.fit:{
			float targetRatio = size.x * 1f / size.y;
			float sourceRatio = bound.height * 1f / bound.width;
			float scale = targetRatio > sourceRatio ? size.x * 1f / bound.width : size.y * 1f/ bound.height;
			bound.width = (int)(bound.width * scale);
			bound.height = (int)(bound.height * scale);
			break;
		}
		case Scaling.fill:{
			float targetRatio = size.x * 1f / size.y;
			float sourceRatio = bound.height * 1f / bound.width;
			float scale = targetRatio < sourceRatio ? size.x * 1f / bound.width : size.y * 1f/ bound.height;
			bound.width = (int)(bound.width * scale);
			bound.height = (int)(bound.height * scale);
			break;
		}
		case Scaling.fillX:{
			float scale = size.x * 1f / bound.width;
			bound.width = (int)(bound.width * scale);
			bound.height = (int)(bound.height * scale);
			break;
		}
		case Scaling.fillY:{
			float scale = size.y * 1f / bound.height;
			bound.width = (int)(bound.width * scale);
			bound.height = (int)(bound.height * scale);
			break;
		}
		case Scaling.stretch:
			bound.width = size.x;
			bound.height = size.y;
			break;
		case Scaling.stretchX:
			bound.width = size.x;
			break;
		case Scaling.stretchY:
			bound.height = size.y;
			break;
		case Scaling.none:
			break;

		default:
			break;
		}
		
		e.gc.drawImage(image, 0, 0, bound.width, bound.height, x, y, bound.width, bound.height);
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

	public Image getImage() {
		if (image == null){
			image = new Image(Display.getCurrent(), Utility.getFile("datas/default/Sprite/libgdx.png").toString());
		}
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
