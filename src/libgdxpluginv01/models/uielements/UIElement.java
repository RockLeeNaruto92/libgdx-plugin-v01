package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.swt.custom.CustomComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;

public abstract class UIElement {
	private static int i = 0;

	private Rectangle bound;
	private CustomComposite container;
	private String name;
	private Point size;
	private Point scale;
	private float rotation;
	private Color color;
	private boolean visible;
	private boolean debug;
	private boolean clicked = true;
	private boolean resize;
	
	private Point distanceClick;

	private PaintListener paintListener;

	public UIElement(Composite root, Point location) {
		name = getDefaultNamePattern();
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
		container.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				UIController.clicked = false;
				resize = false;
				onMouseUp();
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				UIController.clicked = true;
				
				Point cursorLocation = container.getDisplay().getCursorLocation();
				distanceClick = container.toControl(cursorLocation);
				
				if (getMouseStyle(distanceClick) != SWT.CURSOR_SIZEALL){
					resize = true;
				}
				
				onMouseDown();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				onMouseDoubleClick();
			}
		});
		
		container.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent e) {
				if (UIController.clicked){
					Point cursorLocation = container.getDisplay().getCursorLocation();
					Point location = container.getParent().toControl(cursorLocation);
					int style = getMouseStyle(location);
					FormData data = (FormData)(container.getLayoutData());
					Composite parent = container.getParent();
					
					if (resize){
						switch (style){
						case SWT.CURSOR_SIZENW:
							System.out.println("top left");
							data.width += data.left.offset - location.x;
							data.height += data.top.offset - location.y;
							container.setLocation(location);
							break;
						case SWT.CURSOR_SIZESW:
							data.height += location.y - data.top.offset - data.height;
							data.width += data.left.offset - location.x;
							data.left = new FormAttachment(parent, location.x);
							break;
						case SWT.CURSOR_SIZEN:
							data.height += data.top.offset - location.y;
							data.top = new FormAttachment(parent, location.y);
							break;
						case SWT.CURSOR_SIZENE:
							data.width += location.x - data.left.offset - data.width;
							data.height += data.top.offset - location.y;
							data.top = new FormAttachment(parent, location.y);
							break;
						case SWT.CURSOR_SIZESE:
							data.width += location.x - data.left.offset - data.width;
							data.height += location.y - data.top.offset - data.height;
							break;
						case SWT.CURSOR_SIZEE:
							data.width += location.x - data.left.offset - data.width;
							break;
						case SWT.CURSOR_SIZEW:
							data.width += data.left.offset - location.x;
							data.left = new FormAttachment(parent, location.x);
							break;
						case SWT.CURSOR_SIZES:
							System.out.println("bottom");
							data.height += location.y - data.top.offset - data.height;
							break;
						default: 
							container.setLocation(location.x - distanceClick.x, location.y - distanceClick.y);
							break;
						}
					}
					container.refresh();
					redraw();
					
					bound.x = data.left.offset;
					bound.y = data.top.offset;
					bound.width = data.width;
					bound.height = data.height;
					
					onMouseMove();
				}
			}
		});

		container.addMouseTrackListener(new MouseTrackListener() {
			
			@Override
			public void mouseHover(MouseEvent e) {
				UIController.cursor = new Cursor(container.getDisplay(), SWT.CURSOR_SIZEALL);
				container.setCursor(UIController.cursor);
				
				onMouseHover();
			}
			
			@Override
			public void mouseExit(MouseEvent e) {
				UIController.cursor = new Cursor(container.getDisplay(), SWT.CURSOR_ARROW);
				container.setCursor(UIController.cursor);
			}
			
			@Override
			public void mouseEnter(MouseEvent e) {
				Point cursorLocation = container.toControl(container.getDisplay().getCursorLocation());
				int mouseStyle = getMouseStyle(cursorLocation);
				
				UIController.cursor = new Cursor(container.getDisplay(), mouseStyle);
				container.setCursor(UIController.cursor);
			}
		});
	}
	
	private int getMouseStyle(Point location){
		if (location.x <= Parameter.PADDING)
			if (location.y <= Parameter.PADDING)
				return SWT.CURSOR_SIZENW;
			else if (location.y >= getSize().y - Parameter.PADDING)
				return SWT.CURSOR_SIZESW;
			else 
				return SWT.CURSOR_SIZEW;
		else if (location.x >= getSize().x - Parameter.PADDING)
			if (location.y <= Parameter.PADDING)
				return SWT.CURSOR_SIZENE;
			else if (location.y >= getSize().y - Parameter.PADDING)
				return SWT.CURSOR_SIZESE;
			else 
				return SWT.CURSOR_SIZEE;
		else 
			if (location.y <= Parameter.PADDING)
				return SWT.CURSOR_SIZEN;
			else if (location.y >= getSize().y - Parameter.PADDING)
				return SWT.CURSOR_SIZES;
			else 
				return SWT.CURSOR_SIZEALL;
	}

	public abstract String getDefaultNamePattern();

	public abstract Point getDefaultSize();

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

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public PaintListener getPaintListener() {
		return paintListener;
	}

	public void setPaintListener(PaintListener paintListener) {
		this.paintListener = paintListener;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
	public void redraw(){
		container.redraw();
	}
}
