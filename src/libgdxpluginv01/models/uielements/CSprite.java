package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Resources;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.views.properties.UIElementPropertyType;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
		int x = 0, y = 0;
		
		Transform transform = new Transform(Display.getCurrent());
		
		float cos = (float)Math.cos(Math.PI * getRotation() / 180);
		float sin = (float)Math.sin(Math.PI * getRotation() / 180);
		Point size = getSize();
		double length = Math.sqrt(size.x * size.x + size.y * size.y) / 2;
		
		transform.setElements(cos, sin, -sin, cos, 0, 0);
		e.gc.setTransform(transform);
		
		x = -size.x / 2 + (int)(length * Math.cos((45 - getRotation()) * Math.PI / 180));
		y = -size.y / 2 + (int)(length * Math.sin((45 - getRotation()) * Math.PI / 180));
		
		ImageData data = image.getImageData();
		
		if (flipX) data = Utility.flip(data, false);
		if (flipY) data = Utility.flip(data, true);
		
		Image tempImage = new Image(Display.getCurrent(), data);
		
		e.gc.drawImage(tempImage, 0, 0, bound.width, bound.height, x, y, size.x, size.y);
		transform.dispose();
		tempImage.dispose();
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
			String defaultImagePath = Resources.getAndroidProjectPath(Resources.getCurrentProject()) + "/assets/imgs/libgdx.png";
			Resources.addImage(defaultImagePath);
			image = Resources.getImageByPath(defaultImagePath);
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

	@Override
	public Object getPropertyValue(UIElementPropertyType type) {
		Object value = super.getPropertyValue(type);
		
		if (value != null) return value;
		
		switch (type) {
		case ROTATION:
			return getRotation();
		case FLIP_X:
			return isFlipX();
		case FLIP_Y:
			return isFlipY();
		default:
			return null;
		}
	}

	@Override
	public void setPropertyValue(UIElementPropertyType type, Object value) {
		super.setPropertyValue(type, value);
		
		switch (type) {
		case ROTATION:
			setRotation(Float.parseFloat((String)value));
			break;
		case FLIP_X:
			setFlipX((boolean)value);
			break;
		case FLIP_Y:
			setFlipY((boolean)value);
			break;
		case IMAGE:
			setImage((Image)value);
			break;
		default:
			break;
		}
	}

	@Override
	public Element generateXml(Document doc, Element parentNode) {
		Element el = super.generateXml(doc, parentNode);
		
		genenerateAttrXml(doc, el, UIElementPropertyType.IMAGE, Resources.getPathOfImage(image));
		genenerateAttrXml(doc, el, UIElementPropertyType.FLIP_X, flipX);
		genenerateAttrXml(doc, el, UIElementPropertyType.FLIP_Y, flipY);
		
		return el;
	}

	@Override
	public void restore(Element element) {
		super.restore(element);
		
		image = Resources.getImageByPath(readValue(element, UIElementPropertyType.IMAGE));
		flipX = (readValue(element, UIElementPropertyType.FLIP_X) == "true") ? true : false;
		flipY = (readValue(element, UIElementPropertyType.FLIP_Y) == "true") ? true : false;
	}
}
