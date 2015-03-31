package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.controller.UIController;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class CSprite extends UIElement {
	private Image image;
	private boolean flipX, flipY;
	
	public CSprite(Composite root, Point location, UIController uiController, int type) {
		super(root, location, uiController, type);
	}
	
	public CSprite(Composite root, Point location, UIController uiController) {
		super(root, location, uiController, UIElementType.SPRITE);
	}

	@Override
	public String getDefaultNamePattern() {
		return Parameter.DEFAULT_SPRITE_NAME_PATTERN;
	}

	@Override
	public Point getDefaultSize() {
		return Parameter.DEFAULT_SPRITE_SIZE;
	}

	@Override
	public Point getMinSize() {
		// TODO Auto-generated method stub
		return Parameter.SPRITE_MIN_SIZE;
	}

	@Override
	public void drawContent(PaintEvent e) {
		Rectangle bound = getImage().getBounds();
		
		e.gc.drawImage(image, 0, 0, bound.width, bound.height, 0, 0, getSize().x, getSize().y);
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

	public Image getImage() {
		if (image == null){
			image = new Image(Display.getCurrent(), Utility.getFile("datas/default/Sprite/libgdx.png").toString());
		}
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isFlipX() {
		return flipX;
	}

	public void setFlipX(boolean flipX) {
		this.flipX = flipX;
	}

	public boolean isFlipY() {
		return flipY;
	}

	public void setFlipY(boolean flipY) {
		this.flipY = flipY;
	}
}
