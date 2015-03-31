package libgdxpluginv01.models.uielements;

import java.util.ArrayList;
import java.util.List;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.swt.custom.CustomComposite;
import libgdxpluginv01.views.properties.Error;
import libgdxpluginv01.views.properties.UIElementPropertyType;

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
import org.eclipse.swt.widgets.Display;

public abstract class UIElement {
	public static int i = 0;
	private static float ACTION_UPDATE_TIME = 0.013f;
	
	private Rectangle bound;
	private CustomComposite container;
	private String name;
	private Point size;
	private Point scale;
	private float rotation;
	private Color color;
	private boolean visible = true;
	private boolean debug;
	private boolean selected = true;
	private UIController uiController;
	private int type;
	
	private Runnable animationThread;
	private List<CAction> actions;
	
	private Point distanceWithClickedPoint;

	public UIElement(Composite root, Point location, UIController uiController, int type) {
		this.uiController = uiController;
		this.type = type;
		this.visible = true;
		
		name = getDefaultNamePattern() + i++;
		
		container = new CustomComposite(root, SWT.NO_TRIM, location);
		container.setLayout(new FillLayout());

		createControls();

		Point defaultSize = getDefaultSize();
		
		setBound(new Rectangle(location.x, location.y, defaultSize.x, defaultSize.y));
		container.setSize(defaultSize);
		
		setSize(defaultSize);

		addPaintListener();
		addMouseListener();
		
		Display.getCurrent().timerExec((int)(ACTION_UPDATE_TIME * 1000), getAnimationThread());
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
		Display.getCurrent().timerExec(-1, getAnimationThread());
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
		bound.width = size.x;
		bound.height = size.y;
		setBound(bound);
		
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
		this.container.setVisible(visible);
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
	
	public int getType(){
		return type;
	}

	public void redraw(){
		container.redraw();
	}
	
	public void refresh(){
		container.refresh();
	}

	public Runnable getAnimationThread() {
		if (animationThread == null){
			animationThread = new Runnable() {
				@Override
				public void run() {
					animate(ACTION_UPDATE_TIME);
					Display.getCurrent().timerExec((int)(ACTION_UPDATE_TIME * 1000), this);
				}
			};
		}
		
		return animationThread;
	}
	
	private void animate(float time){
		for (CAction action : getActions()) {
			action.animate(time);
		}
	}

	public void setAnimationThread(Runnable animationThread) {
		this.animationThread = animationThread;
	}

	public List<CAction> getActions() {
		if (actions == null){
			actions = new ArrayList<CAction>();
		}
		return actions;
	}

	public void setActions(List<CAction> actions) {
		this.actions = actions;
	}
	
	public void addAction(CAction action){
		getActions().add(action);
	}

	public UIController getUiController() {
		return uiController;
	}
	
	private Error isValidLocationX(String xValue){
		if (!Utility.isFloat(xValue))
			return Error.LOCATION_X_IS_STRING;
		
		float x = Float.parseFloat(xValue);
		if (x > getXRange().y | x < getXRange().x)
			return Error.LOCATION_X_OUT_RANGE;
		
		return Error.VALID;
	}
	
	private Error isValidLocationY(String yValue){
		if (!Utility.isFloat(yValue))
			return Error.LOCATION_Y_IS_STRING;
		
		float y = Float.parseFloat(yValue);
		if (y > getYRange().y | y < getYRange().x)
			return Error.LOCATION_X_OUT_RANGE;
		
		return Error.VALID;
	}

	public Error isValidProperty(UIElementPropertyType type, String value){
		switch (type) {
		case LOCATION_X:
			return isValidLocationX(value);
		case LOCATION_Y:
			return isValidLocationY(value);
		default:
			break;
		}
		
		return Error.VALID;
	}
	
	private Point getXRange(){
		return Parameter.LOCATION_RANGE_X;
	}
	
	private Point getYRange(){
		return Parameter.LOCATION_RANGE_Y;
	}
	
	public void setPropertyValue(UIElementPropertyType type, Object value){
		switch (type) {
		case LOCATION_X:
			setX(Float.parseFloat((String)value));
			break;
		case LOCATION_Y:
			setY(Float.parseFloat((String)value));
			break;
		case SIZE_WIDTH:
			setWidth(Float.parseFloat((String)value));
			break;
		case SIZE_HEIGHT:
			setHeight(Float.parseFloat((String)value));
			break;
		case VISIBLE:
			setVisible((boolean)value);
			break;
		default:
			break;
		}
	}
	
	public Object getPropertyValue(UIElementPropertyType type){
		switch (type) {
		case LOCATION_X:
			return bound.x * 1.0f;
		case LOCATION_Y:
			return bound.y * 1.0f;
		case SIZE_WIDTH:
			return bound.width * 1.0f;
		case SIZE_HEIGHT:
			return bound.height * 1.0f;
		case VISIBLE:
			return visible;
		default:
			break;
		}
		
		return null;
	}
	
	public Point getRange(UIElementPropertyType type){
		switch (type) {
		case LOCATION_X:
			return Parameter.LOCATION_RANGE_X;
		case LOCATION_Y:
			return Parameter.LOCATION_RANGE_Y;
		case SIZE_WIDTH:
			return Parameter.LOCATION_RANGE_X;
		case SIZE_HEIGHT:
			return Parameter.LOCATION_RANGE_Y;
		default:
			break;
		}
		
		return null;
	}
	
	private void setX(float xValue){
		bound.x = (int)xValue;
		setBound(bound);
	}
	
	private void setY(float yValue){
		bound.y = (int)yValue;
		setBound(bound);
	}
	
	private void setWidth(float widthValue){
		bound.width = (int)widthValue;
		setBound(bound);
	}
	
	private void setHeight(float heightValue){
		bound.height = (int)heightValue;
		setBound(bound);
	}
}
