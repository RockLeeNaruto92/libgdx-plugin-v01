package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.swt.custom.CustomComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;

public abstract class UIElement {
	public static int i = 0;
	
	private Rectangle bound;
	private CustomComposite container;
	private String name;
	private Point size;
	private Point scale;
	private float rotation;
	private Color color;
	private boolean visible;
	private boolean debug;
	private boolean selected = true;
	private UIController uiController;
	
	private Point distanceWithClickedPoint;

	public UIElement(Composite root, Point location, UIController uiController) {
		this.uiController = uiController;
		
		name = getDefaultNamePattern() + i++;
		
		container = new CustomComposite(root, SWT.NO_TRIM, location);
		container.setLayout(new FillLayout());

		createControls();

		Point defaultSize = getDefaultSize();
		container.setSize(defaultSize);
		setSize(defaultSize);

		setBound(new Rectangle(location.x, location.y, defaultSize.x,
				defaultSize.y));
		
		addPaintListener();
		addMouseListener();
	}
	
	public void addPaintListener(){
		container.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				drawContent(e);
				
				if (!selected) return;
				
				FormData data = (FormData)(container.getLayoutData());
				
				e.gc.setForeground(container.getDisplay().getSystemColor(SWT.COLOR_BLACK));
				e.gc.drawRectangle(Parameter.R, Parameter.R, data.width - 1 - 2 * Parameter.R, data.height - 1 - 2 * Parameter.R);
				// draw circle
				e.gc.drawOval(0, 0, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(0, data.height - 1 - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(data.width - 1 - 2 * Parameter.R, 0, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(data.width - 1 - 2 * Parameter.R, data.height - 1 - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				
				Point centerPoint = new Point(data.width / 2, data.height / 2);
				e.gc.drawOval(0, centerPoint.y - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(centerPoint.x - 2 * Parameter.R, 0, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(data.width - 1 - 2 * Parameter.R, centerPoint.y - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(centerPoint.x - 2 * Parameter.R, data.height - 1 - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(centerPoint.x - 2 * Parameter.R, centerPoint.y - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
			}
		});
	}
	
	private void addMouseListener(){
		final UIElement element = this;
		container.addMouseListener(new MouseListener() {
			public void mouseUp(MouseEvent e) {
				uiController.onMouseUp(null);
				onMouseUp();
			}
			
			public void mouseDown(MouseEvent e) {
				uiController.onMouseDown(e, element);
				onMouseDown();
			}
			
			public void mouseDoubleClick(MouseEvent e) {
				onMouseDoubleClick();
			}
		});
		
		container.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				uiController.onMouseMove(e);
				onMouseMove();
			}
		});

	}
	
	public abstract String getDefaultNamePattern();

	public abstract Point getDefaultSize();
	
	public abstract Point getMinSize();

	public abstract void createControls();
	
	public abstract void displayBound(boolean display);
	
	public abstract void drawContent(PaintEvent e);
	
	public abstract void onMouseUp();
	
	public abstract void onMouseDown();
	
	public abstract void onMouseDoubleClick();
	
	public abstract void onMouseHover();
	
	public abstract void onMouseMove();
	
	public void remove(){
		container.dispose();
	}

	public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		container.setLocation(bound.x, bound.y);
		container.setSize(bound.width, bound.height);
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
		size.x = bound.width;
		size.y = bound.height;
		return size;
	}

	public void setSize(Point size) {
		this.size = size;
	}
	
	public Point getScale() {
		return scale;
	}

	public void setScale(Point scale) {
		this.scale = scale;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isDebug() {
		return debug;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public Point getDistanceWithClickedPoint() {
		return distanceWithClickedPoint;
	}

	public void setDistanceWithClickedPoint(Point distanceWithClickedPoint) {
		this.distanceWithClickedPoint = distanceWithClickedPoint;
	}

	public void redraw(){
		container.redraw();
	}
}
