package libgdxpluginv01.controller;

import java.util.ArrayList;
import java.util.List;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.models.uielements.CButton;
import libgdxpluginv01.models.uielements.UIElement;
import libgdxpluginv01.models.uielements.UIElementType;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class UIController {
	public static boolean clicked = false;
	public Point clickedPoint;
	public Cursor cursor;
	
	private boolean mouseDown = false;
	
	private static UIController _instance;
	private List<UIElement> uiElements;
	private List<UIElement> selectedUIElements;
	
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

	public Control createUIElement(Canvas dragComposite, UIElementType type, Point location) {
//		if (type == UIElementType.LABEL){
//			CLabel label = new CLabel(dragComposite, location);
//			addUIElement(label);
//			
//			return label.getContainer();
//		}
		
//		if (type == UIElementType.CHECKBOX){
//			CCheckbox checkbox = new CCheckbox(dragComposite, location);
//			addUIElement(checkbox);
//			
//			return checkbox.getContainer();
//		}
		
		removeAllSelectedUIElements();
		
		if (type == UIElementType.BUTTON){
			CButton button = new CButton(dragComposite, location, this);
			addUIElement(button);
			addSelectedUIElement(button);
			
			return button.getContainer();
		}
		
//		if (type == UIElementType.SLIDER){
//			CSlider slider = new CSlider(dragComposite, location);
//			addUIElement(slider);
//			
//			return slider.getContainer();
//		}
		
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
			removeSelectedUIElement(selectedUIElements.get(0));
		}
	}
	
	public void addSelectedUIElement(UIElement uielement){
		selectedUIElements.add(uielement);
	}
	
	public void removeSelectedUIElement(UIElement uielement){
		selectedUIElements.remove(uielement);
	}
	
	public void setPropertyView(UIElement uielement){
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

	public void onMouseUp(MouseEvent e, UIElement element) {
		mouseDown = false;
	}

	public void onMouseDown(MouseEvent e, UIElement element) {
		mouseDown = true;
		clickedPoint = Display.getCurrent().getCursorLocation();
		setDistanceOfClickedPointForSelectedUiElements(clickedPoint);
		
		if (!isSelected(element))
			addSelectedUIElement(element);
	}

	public void onMouseDoubleClick(MouseEvent e) {
	}

	public void onMouseMove(MouseEvent e) {
		if (selectedUIElements.size() == 1)
			onUIElementMouseMove(e, selectedUIElements.get(0));
		else if (selectedUIElements.size() > 1)
			onEditorMouseMove(e);
	}

	private void onEditorMouseMove(MouseEvent e) {
		
	}

	private void onUIElementMouseMove(MouseEvent e, UIElement uiElement) {
		Point cursorLocation = Display.getCurrent().getCursorLocation();
		Point cursorLocationOnEditor = uiElement.getContainer().getParent().toControl(cursorLocation);
		int mouseStyle = getMouseStyle(uiElement.getSize(), uiElement.getContainer().toControl(cursorLocation));
		Rectangle bound = uiElement.getBound();
		
		uiElement.getContainer().setCursor(new Cursor(null, mouseStyle));
		
		if (mouseDown){
			switch (mouseStyle) {
			case SWT.CURSOR_SIZENW: // top left
				bound.width += bound.x - cursorLocationOnEditor.x;
				bound.height += bound.y - cursorLocationOnEditor.y;
				bound.x = cursorLocationOnEditor.x;
				bound.y = cursorLocationOnEditor.y;
				break;
			case SWT.CURSOR_SIZESW: // bottom left
				break;
			case SWT.CURSOR_SIZEW: // left
				break;
			case SWT.CURSOR_SIZENE: // top right
				break;
			case SWT.CURSOR_SIZESE: // bottom right
				break;
			case SWT.CURSOR_SIZEE: // right
				break;
			case SWT.CURSOR_SIZEN: // top
				break;
			case SWT.CURSOR_SIZES: // bottom
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
}
