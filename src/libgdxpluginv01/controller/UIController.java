package libgdxpluginv01.controller;

import java.util.ArrayList;
import java.util.List;

import libgdxpluginv01.models.uielements.CLabel;
import libgdxpluginv01.models.uielements.UIElement;
import libgdxpluginv01.models.uielements.UIElementType;

import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Control;

public class UIController {
	public static boolean clicked = false;
	public static Point clickedPoint;
	public static Cursor cursor;
	
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
		if (type == UIElementType.LABEL){
			CLabel label = new CLabel(dragComposite, location);
			addUIElement(label);
			
			return label.getContainer();
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
	
	public void setPropertyView(UIElement uielement){
		
	}
	
	public void removeAll(){
		System.out.println(uiElements.size());
		while (uiElements.size() != 0){
			removeUIElement(uiElements.get(0));
		}
	}
}
