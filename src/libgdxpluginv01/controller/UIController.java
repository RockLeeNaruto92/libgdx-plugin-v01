package libgdxpluginv01.controller;

import java.util.ArrayList;
import java.util.List;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.models.uielements.CButton;
import libgdxpluginv01.models.uielements.CLabel;
import libgdxpluginv01.models.uielements.CSlider;
import libgdxpluginv01.models.uielements.UIElement;
import libgdxpluginv01.models.uielements.UIElementType;
import libgdxpluginv01.views.PropertyView;
import libgdxpluginv01.views.properties.ButtonProperty;
import libgdxpluginv01.views.properties.EmptyProperty;
import libgdxpluginv01.views.properties.LabelProperty;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class UIController {
	private int currentMouseStyle;
	private Point mouseDownPoint;
	
	private boolean mouseDown = false;
	private boolean selecting = false;
	
	private Rectangle selectingRectangle = new Rectangle(0, 0, 0, 0);
	
	private static UIController _instance;
	private List<UIElement> uiElements;
	private List<UIElement> selectedUIElements;
	
	private PropertyView propertyView;
	
	public UIController(){
		uiElements = new ArrayList<UIElement>();
		selectedUIElements = new ArrayList<UIElement>();
	}
	
	public static UIController getInstance(){
		if (_instance == null){
			_instance = new UIController();
		}
		
		return _instance;
	}
	
	public void setPropertyView(PropertyView view){
		propertyView = view;
	}
	
	public void setPropertyView(UIElement uielement){
		switch (uielement.getType()) {
		case UIElementType.BUTTON:
			propertyView.setView(ButtonProperty.getInstance(propertyView.getParent()));
			break;
		
		case UIElementType.LABEL:
			propertyView.setView(LabelProperty.getInstance(propertyView.getParent()));
			break;
			
		default:
			break;
		}
	}
	
	public void emptyPropertyView(){
		propertyView.setView(EmptyProperty.getInstance(propertyView.getParent()));
	}
	
	public boolean isSelecting(){
		return selecting;
	}

	public Rectangle getSelectingRectangle() {
		return selectingRectangle;
	}

	public Control createUIElement(Canvas dragComposite, int type, Point location) {
		UIElement newElement = null;
		removeAllSelectedUIElements();
		
		switch (type){
		case UIElementType.BUTTON:
			newElement = new CButton(dragComposite, location, this);
			break;
		
		case UIElementType.LABEL:
			newElement = new CLabel(dragComposite, location, this);
			break;
			
		case UIElementType.SLIDER:
			newElement = new CSlider(dragComposite, location, this);
			
		default :
			break;
		}
		
		if (newElement != null){
			addUIElement(newElement);
			addSelectedUIElement(newElement);
			return newElement.getContainer();
		}
		
		return null;
	}
	
	public void addUIElement(UIElement uielement){
		uiElements.add(uielement);
	}
	
	public void removeUIElement(UIElement uielement){
		uielement.remove();
		uiElements.remove(uielement);
	}

	public void removeAllSelectedUIElements(){
		while (selectedUIElements.size() > 0){
			UIElement element = selectedUIElements.get(0);
			removeSelectedUIElement(element);
			element.setSelected(false);
			element.redraw();
		}
	}
	
	public void addSelectedUIElement(UIElement uielement){
		selectedUIElements.add(uielement);
	}
	
	public void removeSelectedUIElement(UIElement uielement){
		selectedUIElements.remove(uielement);
	}
	
	public void removeAll(){
		while (uiElements.size() != 0){
			removeUIElement(uiElements.get(0));
		}
	}
	
	private boolean isSelected(UIElement uiElement){
		for (UIElement element : selectedUIElements) {
			if (uiElement == element)
				return true;
		}
		
		return false;
	}

	public void onMouseUp(Composite composite) {
		mouseDown = false;
		
		if (selecting){
			selecting = false;
			selectingRectangle = Utility.validateRectangle(selectingRectangle);
			addSelectedUiElementsInRectangle(selectingRectangle);
		}
		
		if (selectedUIElements.size() == 1)
			setPropertyView(selectedUIElements.get(0));
		else
			emptyPropertyView();
		
		if (composite != null)
			composite.redraw();
	}
	
	public void onMouseDown(Composite composite){
		mouseDown = true;
		mouseDownPoint = Display.getCurrent().getCursorLocation();
		
		removeAllSelectedUIElements();
	}

	public void onMouseDown(MouseEvent e, UIElement element) {
		mouseDown = true;
		
		removeAllSelectedUIElements();
		addSelectedUIElement(element);
		
		element.setSelected(true);
		element.redraw();
		
		mouseDownPoint = Display.getCurrent().getCursorLocation();
		setDistanceOfClickedPointForSelectedUiElements(mouseDownPoint);
		
		if (!isSelected(element)){
			addSelectedUIElement(element);
			element.setSelected(true);
			element.redraw();
		}
		
		currentMouseStyle = getMouseStyle(element.getSize(), element.getContainer().toControl(Display.getCurrent().getCursorLocation()));
	}

	public void onMouseMove(MouseEvent e) {
		if (selectedUIElements.size() == 1)
			onUIElementMouseMove(selectedUIElements.get(0));
		else if (selectedUIElements.size() > 1)
			onEditorMouseMove(null);
	}

	public void onEditorMouseMove(Composite composite) {
		if (!mouseDown) return;
		if (selectedUIElements.isEmpty()){
			// draw rectangle selection
			selecting = true;
			
			Point location = composite.toControl(mouseDownPoint);
			
			selectingRectangle.x = location.x;
			selectingRectangle.y = location.y;
			selectingRectangle.width = Display.getCurrent().getCursorLocation().x - mouseDownPoint.x;
			selectingRectangle.height = Display.getCurrent().getCursorLocation().y - mouseDownPoint.y;
			composite.redraw();
		} else {
			// move all selected elements
		}
	}

	private void onUIElementMouseMove(UIElement uiElement) {
		int tempWidth, tempHeight;
		Point cursorLocation = Display.getCurrent().getCursorLocation();
		Point cursorLocationOnEditor = uiElement.getContainer().getParent().toControl(cursorLocation);
		int mouseStyle = getMouseStyle(uiElement.getSize(), uiElement.getContainer().toControl(cursorLocation));
		Rectangle bound = uiElement.getBound();
		
		if (!mouseDown && mouseStyle != currentMouseStyle)
			uiElement.getContainer().setCursor(new Cursor(null, mouseStyle));
		
		if (mouseDown){
			switch (currentMouseStyle) {
			case SWT.CURSOR_SIZENW: // top left
				tempWidth = bound.width + bound.x - cursorLocationOnEditor.x;
				tempHeight = bound.height + bound.y - cursorLocationOnEditor.y;
				
				if (tempWidth >= uiElement.getMinSize().x) {
					bound.x = cursorLocationOnEditor.x;
					bound.width = tempWidth;
				}
				
				if (tempHeight >= uiElement.getMinSize().y){
					bound.y = cursorLocationOnEditor.y;
					bound.height = tempHeight;
				}
				break;
				
			case SWT.CURSOR_SIZESW: // bottom left
				tempWidth = bound.width + bound.x - cursorLocationOnEditor.x;
				tempHeight = cursorLocationOnEditor.y - bound.y + (cursorLocationOnEditor.y >= bound.y ? 0 : bound.height);

				if (tempWidth >= uiElement.getMinSize().x) {
					bound.x = cursorLocationOnEditor.x;
					bound.width = tempWidth;
				}
				
				if (tempHeight >= uiElement.getMinSize().y){
					bound.height = tempHeight;
				}
				break;
				
			case SWT.CURSOR_SIZENE: // top right
				tempWidth = cursorLocationOnEditor.x - bound.x + (cursorLocationOnEditor.x >= bound.x ? 0 : bound.width);
				tempHeight = bound.height + bound.y - cursorLocationOnEditor.y;
				
				if (tempWidth >= uiElement.getMinSize().x) {
					bound.width = tempWidth;
				}
				
				if (tempHeight >= uiElement.getMinSize().y){
					bound.y = cursorLocationOnEditor.y;
					bound.height = tempHeight;
				}
				break;
				
			case SWT.CURSOR_SIZESE: // bottom right
				tempWidth = cursorLocationOnEditor.x - bound.x + (cursorLocationOnEditor.x >= bound.x ? 0 : bound.width);
				tempHeight = cursorLocationOnEditor.y - bound.y + (cursorLocationOnEditor.y >= bound.y ? 0 : bound.height);
				
				if (tempWidth >= uiElement.getMinSize().x) {
					bound.width = tempWidth;
				}
				
				if (tempHeight >= uiElement.getMinSize().y){
					bound.height = tempHeight;
				}
				
				break;
				
			case SWT.CURSOR_SIZEW: // left
				tempWidth = bound.width + bound.x - cursorLocationOnEditor.x;
				
				if (tempWidth >= uiElement.getMinSize().x) {
					bound.x = cursorLocationOnEditor.x;
					bound.width = tempWidth;
				}
				break;
				
			case SWT.CURSOR_SIZEE: // right
				tempWidth = cursorLocationOnEditor.x - bound.x + (cursorLocationOnEditor.x >= bound.x ? 0 : bound.width);
				
				if (tempWidth >= uiElement.getMinSize().x) {
					bound.width = tempWidth;
				}
				break;
				
			case SWT.CURSOR_SIZEN: // top
				tempHeight = bound.height + bound.y - cursorLocationOnEditor.y;
				
				if (tempHeight >= uiElement.getMinSize().y){
					bound.y = cursorLocationOnEditor.y;
					bound.height = tempHeight;
				}
				break;
				
			case SWT.CURSOR_SIZES: // bottom
				tempHeight = cursorLocationOnEditor.y - bound.y + (cursorLocationOnEditor.y >= bound.y ? 0 : bound.height);
				
				if (tempHeight >= uiElement.getMinSize().y){
					bound.height = tempHeight;
				}
				
				break;
				
			case SWT.CURSOR_SIZEALL: // move
				Point distance = uiElement.getDistanceWithClickedPoint();
				
				bound.x = cursorLocationOnEditor.x - distance.x;
				bound.y = cursorLocationOnEditor.y - distance.y;
				break;
				
			default:
				break;
			}
			
			uiElement.setBound(bound);
			uiElement.getContainer().refresh();
			uiElement.redraw();
		}
		
	}
	
	private int getMouseStyle(Point size, Point location){
		if (location.x <= Parameter.PADDING)
			if (location.y <= Parameter.PADDING)
				return SWT.CURSOR_SIZENW;
			else if (location.y >= size.y - Parameter.PADDING)
				return SWT.CURSOR_SIZESW;
			else 
				return SWT.CURSOR_SIZEW;
		else if (location.x >= size.x - Parameter.PADDING)
			if (location.y <= Parameter.PADDING)
				return SWT.CURSOR_SIZENE;
			else if (location.y >= size.y - Parameter.PADDING)
				return SWT.CURSOR_SIZESE;
			else 
				return SWT.CURSOR_SIZEE;
		else 
			if (location.y <= Parameter.PADDING)
				return SWT.CURSOR_SIZEN;
			else if (location.y >= size.y - Parameter.PADDING)
				return SWT.CURSOR_SIZES;
			else 
				return SWT.CURSOR_SIZEALL;
	}
	
	private void setDistanceOfClickedPointForSelectedUiElements(Point clickedPoint) {
		for (UIElement uielement : selectedUIElements) {
			Point distance = uielement.getContainer().toControl(clickedPoint);
			uielement.setDistanceWithClickedPoint(distance);
		}
	}
	
	private void addSelectedUiElementsInRectangle(Rectangle rect){
		for (UIElement element : uiElements) {
			Rectangle bound = element.getBound();
			if (rect.contains(bound.x, bound.y) && rect.contains(bound.x + bound.width, bound.y + bound.height)){
				addSelectedUIElement(element);
				
				element.setSelected(true);
				element.redraw();
			}
		}
	}
}
