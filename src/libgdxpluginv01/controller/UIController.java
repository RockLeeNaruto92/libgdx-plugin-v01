package libgdxpluginv01.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import libgdxpluginv01.constant.MobileResolution;
import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Resources;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.editors.EditorInterface;
import libgdxpluginv01.models.uielements.CAnimation;
import libgdxpluginv01.models.uielements.CButton;
import libgdxpluginv01.models.uielements.CCheckbox;
import libgdxpluginv01.models.uielements.CImage;
import libgdxpluginv01.models.uielements.CLabel;
import libgdxpluginv01.models.uielements.CSlider;
import libgdxpluginv01.models.uielements.CSprite;
import libgdxpluginv01.models.uielements.UIElement;
import libgdxpluginv01.models.uielements.UIElementType;
import libgdxpluginv01.views.PropertyView;
import libgdxpluginv01.views.properties.AnimationProperty;
import libgdxpluginv01.views.properties.ButtonProperty;
import libgdxpluginv01.views.properties.ButtonStyleProperty;
import libgdxpluginv01.views.properties.CheckboxProperty;
import libgdxpluginv01.views.properties.CheckboxStyleProperty;
import libgdxpluginv01.views.properties.EmptyProperty;
import libgdxpluginv01.views.properties.ImageProperty;
import libgdxpluginv01.views.properties.LabelProperty;
import libgdxpluginv01.views.properties.LabelStyleProperty;
import libgdxpluginv01.views.properties.Property;
import libgdxpluginv01.views.properties.SliderProperty;
import libgdxpluginv01.views.properties.SliderStyleProperty;
import libgdxpluginv01.views.properties.SpriteProperty;
import libgdxpluginv01.views.properties.StyleProperty;
import libgdxpluginv01.views.properties.UIElementProperty;
import libgdxpluginv01.views.properties.UIElementPropertyType;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UIController {
	private int currentMouseStyle;
	private Point mouseDownPoint;
	
	private boolean mouseDown = false;
	private boolean selecting = false;
	
	private Rectangle selectingRectangle = new Rectangle(0, 0, 0, 0);
	
	private static UIController _instance;
	private List<UIElement> uiElements;
	private List<UIElement> selectedUIElements;
	private Canvas dragComposite;
	
	private PropertyView propertyView;
	
	public UIController(){
		uiElements = new ArrayList<UIElement>();
		selectedUIElements = new ArrayList<UIElement>();
	}
	
	public static UIController getInstance(){
		if (_instance == null){
			System.out.println("create new instance of ui controlller");
			_instance = new UIController();
		}
		
		return _instance;
	}
	
	public void setDragComposite(Canvas composite){
		dragComposite = composite;
	}
	
	public void setPropertyView(PropertyView view){
		propertyView = view;
	}
	
	public void setPropertyView(UIElement uielement){
		Property view = null;
		
		if (propertyView == null)
			setPropertyView(PropertyView.getInstance());
		
		if (propertyView == null) System.out.println("Property view is null");
		
		switch (uielement.getType()) {
		case UIElementType.CHECKBOX:
			view = CheckboxProperty.getInstance(propertyView.getParent());
			break;
			
		case UIElementType.BUTTON:
			view = ButtonProperty.getInstance(propertyView.getParent());
			break;
		
		case UIElementType.LABEL:
			view = LabelProperty.getInstance(propertyView.getParent());
			break;
			
		case UIElementType.SLIDER: 
			view = SliderProperty.getInstance(propertyView.getParent());
			break;
			
		case UIElementType.SPRITE:
			view = SpriteProperty.getInstance(propertyView.getParent());
			break;
			
		case UIElementType.IMAGE:
			view = ImageProperty.getInstance(propertyView.getParent());
			break;
			
		case UIElementType.ANIMATION:
			view = AnimationProperty.getInstance(propertyView.getParent());
			break;
			
		default:
			view = EmptyProperty.getInstance(propertyView.getParent());
			break;
		}

		if (view != null){
			propertyView.setView(view);
			
			if (!(view instanceof EmptyProperty))
				((UIElementProperty)view).setObjectPropertiesToView(uielement);
		}
	}
	
	public void setPropertyView(UIElement uielement, boolean isStyleView){
		if (!isStyleView)
			setPropertyView(uielement);
		else {
			Property view = null;
			
			switch (uielement.getType()) {
			case UIElementType.LABEL:
				view = LabelStyleProperty.getInstance(propertyView.getParent(), ((CLabel)uielement).getStyle());
				break;
				
			case UIElementType.CHECKBOX:
				System.out.println("Chckbox");
				view = CheckboxStyleProperty.getInstance(propertyView.getParent(), ((CCheckbox)uielement).getStyle());
				break;
				
			case UIElementType.BUTTON:
				view = ButtonStyleProperty.getInstance(propertyView.getParent(), ((CButton)uielement).getStyle());
				break;
				
			case UIElementType.SLIDER:
				view = SliderStyleProperty.getInstance(propertyView.getParent(), ((CSlider)uielement).getStyle());
				break;
				
			default:
				break;
			}
			
			if (view != null){
				propertyView.setView(view);
				((StyleProperty)view).setPropertyToView(uielement, null);
			}
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
		UIElement newElement = createUIElementWhenRestore(dragComposite, type, location);
		
		if (newElement != null){
			addUIElement(newElement);
			addSelectedUIElement(newElement);
			setPropertyView(newElement);
			
			return newElement.getContainer();
		}
		
		return null;
	}
	
	public UIElement createUIElementWhenRestore(Canvas dragComposite, int type, Point location) {
		UIElement newElement = null;
		removeAllSelectedUIElements();
		
		switch (type){
		case UIElementType.BUTTON:
			newElement = new CButton(dragComposite, location, this);
			System.out.println("Create new c button");
			break;
		
		case UIElementType.LABEL:
			newElement = new CLabel(dragComposite, location, this);
			break;
			
		case UIElementType.SLIDER:
			newElement = new CSlider(dragComposite, location, this);
			break;
		
		case UIElementType.CHECKBOX:
			newElement = new CCheckbox(dragComposite, location, this);
			break;
			
		case UIElementType.SPRITE:
			newElement = new CSprite(dragComposite, location, this);
			break;
			
		case UIElementType.IMAGE:
			newElement = new CImage(dragComposite, location, this);
			break;
			
		case UIElementType.ANIMATION:
			newElement = new CAnimation(dragComposite, location, this);
			break;
			
		default :
			break;
		}
		
		return newElement;
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
		setPropertyView(PropertyView.getInstance());
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
		
		setPropertyView(PropertyView.getInstance());
		
		removeAllSelectedUIElements();
	}

	public void onMouseDown(MouseEvent e, UIElement element) {
		mouseDown = true;
		
		setPropertyView(PropertyView.getInstance());
		
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
		setPropertyView(PropertyView.getInstance());
		
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

	public void save(EditorInterface editor) {
		generateXml(getFilePath(editor));
		Resources.save();
	}
	
	private String getFilePath(EditorInterface editor){
//		if (editor == null)
//			System.out.println("Editor null");
//		else {
//			if (editor.getEditorInput() == null)
//				System.out.println("Editor input null");
//			else 
//				System.out.println("Editor input not null");
//		}
		
		IEditorInput input = editor.getEditorInput();
		return ((FileEditorInput)input).getPath().toString();
	}
	
	public Document generateXml(String fileName){
		System.out.println("file xml to save: " + fileName);
		Document doc = null;
		
		try{
			// create new Document
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			doc = docBuilder.newDocument();
			
			// create root elements
			// type:
			//		<libgdx name="Design">
			//		....
			//		</libgdx>
			org.w3c.dom.Element rootElement = doc.createElement("libgdx");
			rootElement.setAttribute("name", fileName);
			doc.appendChild(rootElement);
			
			// generate all uielements
			// each element has type:
			// 			<uielement>
			//				<infor></infor>
			//				<infor></infor>
			//				<infor></infor>
			//			</uielement>
			for (UIElement element : uiElements) {
				// each element generate xml
				element.generateXml(doc, rootElement);
			}
			
			// save to file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
			
			System.out.println("Save to " + fileName);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public void restore(EditorInterface editor){
		String fileName = getFilePath(editor);
		
		System.out.println("-----------------------------------");
		System.out.println("Restoring " + fileName);
		
		try {
			File xmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			// read root element
			Element rootElement = doc.getDocumentElement();
			System.out.println("file: " + rootElement.getAttribute("name"));
			
			NodeList nList = doc.getElementsByTagName("element");
			
			for (int i = 0; i < nList.getLength(); i++){
				Node nNode = nList.item(i);
				System.out.println("------------------------------------------");
				System.out.println("Element " + i + ": " + nNode.getNodeName());
				System.out.println("Infor: " );
				
				Element element = (Element)nNode;
				
				// read type
				int type = Integer.parseInt(readValue(element, UIElementPropertyType.TYPE));
				int defaultMobileY = Parameter.DEFAULT_MOBILE_POSITION.y + MobileResolution.IPHONE_6_PLUS.y;
				
				int x = Integer.parseInt(readValue(element, UIElementPropertyType.LOCATION_X)) + Parameter.DEFAULT_MOBILE_POSITION.x;
				int y = defaultMobileY - Integer.parseInt(readValue(element, UIElementPropertyType.LOCATION_Y));
				
				UIElement uiElement = createUIElementWhenRestore(dragComposite, type, new Point(x, y));
				uiElement.restore(element);
				
				dragComposite.layout(new Control[]{uiElement.getContainer()});
				
				addUIElement(uiElement);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
		System.out.println("Restored! " + fileName);
	}
	
	public String readValue(Element parentNode, Object tag){
		NodeList list = parentNode.getElementsByTagName(tag.toString());
		Element el = (Element)list.item(0);
		return el.getTextContent();
	}
}
