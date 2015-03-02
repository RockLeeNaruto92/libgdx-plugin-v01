package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.swt.custom.CustomComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;

public abstract class UIElement {
	private static int i = 0;
	
	private Rectangle bound;
	private CustomComposite container;
	private String name;
	
	public UIElement(Composite root, Point location){
		Point size = getDefaultSize();
		
		name = getDefaultNamePattern() + (i++);
		container = new CustomComposite(root, SWT.NONE, location, size);
		container.setLayout(new FillLayout());
		
		setBound(new Rectangle(location.x, location.y, size.x, size.y));
	}
	
	public abstract String getDefaultNamePattern();
	public abstract Point getDefaultSize();
	
	public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}

	public CustomComposite getContainer() {
		return container;
	}

	public void setContainer(CustomComposite container) {
		this.container = container;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
