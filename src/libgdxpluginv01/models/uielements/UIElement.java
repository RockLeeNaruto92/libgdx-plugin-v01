package libgdxpluginv01.models.uielements;

import libgdxpluginv01.swt.custom.CustomComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public abstract class UIElement {
	private static int i = 0;
	
	private Rectangle bound;
	private CustomComposite container;
	private String name;
	private Point size;
	
	public UIElement(Composite root, Point location){
		name = getDefaultNamePattern() + (i++);
		container = new CustomComposite(root, SWT.NO_TRIM, location);
		container.setLayout(new FillLayout());
		
		createControls();
		
		Point defaultSize = getDefaultSize();
		container.setSize(defaultSize);
		
		setBound(new Rectangle(location.x, location.y, defaultSize.x, defaultSize.y));
		
		addMouseListener();
	}
	
	public abstract String getDefaultNamePattern();
	public abstract Point getDefaultSize();
	public abstract void createControls();
	public abstract void addMouseListener();
	
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

	public Point getSize() {
		return size;
	}

	public void setSize(Point size) {
		this.size = size;
	}
}
