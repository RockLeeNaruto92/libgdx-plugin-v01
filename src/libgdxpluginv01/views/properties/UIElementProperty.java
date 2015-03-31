package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public abstract class UIElementProperty extends Property{
	private Composite container;
	private UIElement uielement;
	private Text textName;
	private Text textLocationX;
	private Text textLocationY;
	private Text textSizeWidth;
	private Text textSizeHeight;
	private Button checkboxVisible;
	
	public UIElementProperty(Composite parent) {
		super(parent);
		createContainer(parent);
		createContents();
		setScrolledComposite();
		
		getRoot().setVisible(false);
	}
	
	private void setScrolledComposite(){
		getRoot().setContent(container);
		getRoot().setAlwaysShowScrollBars(true);
		
		Point size = getDefaultSize();
		container.setSize(size);
		getRoot().setMinSize(size);
		getRoot().getParent().setRedraw(true);
	}
	
	private void createContainer(Composite parent) {
		container = new Composite(getRoot(), SWT.NONE);
		container.setLayout(new GridLayout(Parameter.PROPERTY_COLUMN_NUM, false));
	}

	public abstract void createOtherProperties();
	public abstract Point getDefaultSize();
	
	private void createContents(){
		createNameField();
		createLocationXField();
		createLocationYField();
		createSizeWidthField();
		createSizeHeightField();
		createVisbleField();
		createOtherProperties();
	}
	
	public Composite getContainer() {
		return container;
	}

	public UIElement getUielement() {
		return uielement;
	}

	public void setUielement(UIElement uielement) {
		this.uielement = uielement;
	}
	
	private void createNameField(){
		String[] labelNames = new String[]{Word.PROPERTY_NAME};
		textName = Utility.createTextGridData4Columns(container, labelNames, false, Parameter.LOCATION_RANGE_X, 1, this, UIElementPropertyType.NAME);
	}
	
	private void createLocationXField(){
		String[] labelNames = new String[]{Word.PROPERTY_LOCATION, Word.PROPERTY_X};
		textLocationX = Utility.createTextGridData4Columns(container, labelNames, true, Parameter.LOCATION_RANGE_X, 1, this, UIElementPropertyType.LOCATION_X);
	}
	
	private void createLocationYField(){
		String[] labelNames = new String[]{"", Word.PROPERTY_Y};
		textLocationY= Utility.createTextGridData4Columns(container, labelNames, true, Parameter.LOCATION_RANGE_X, 1, this, UIElementPropertyType.LOCATION_Y);
	}
	
	private void createSizeWidthField(){
		String[] labelNames = new String[]{Word.PROPERTY_SIZE, Word.PROPERTY_WIDTH};
		textSizeWidth = Utility.createTextGridData4Columns(container, labelNames, true, Parameter.LOCATION_RANGE_X, 1, this, UIElementPropertyType.SIZE_WIDTH);
	}
	
	private void createSizeHeightField(){
		String[] labelNames = new String[]{"", Word.PROPERTY_HEIGHT};
		textSizeHeight = Utility.createTextGridData4Columns(container, labelNames, true, Parameter.LOCATION_RANGE_X, 1, this, UIElementPropertyType.SIZE_HEIGHT);
	}
	
	private void createVisbleField(){
		String[] labelNames = new String[]{Word.PROPERTY_VISIBLE};
		checkboxVisible = Utility.createCheckboxGridData4Columns(container, labelNames, this, UIElementPropertyType.VISIBLE);
	}
	
	public void setObjectPropertiesToView(UIElement object){
		setUielement(object);
		
		textName.setText(object.getName());
		textLocationX.setText(object.getBound().x + "");
		textLocationY.setText(object.getBound().y + "");
		textSizeWidth.setText(object.getBound().width + "");
		textSizeHeight.setText(object.getBound().height + "");
		checkboxVisible.setSelection(object.isVisible());
	}
	
	private boolean isValidName(String name){
		if (name == null) 
			return false;
		if (name.length() == 0)
			return false;
		if (name.charAt(0) <= '9' && name.charAt(0) >= '0')
			return false;
		if (name.contains(Parameter.SPECIAL_CHARACTER))
			return false;
		
		return true;
	}
	
	public boolean isIntegerNum(String text){
		for (int i = 0; i < text.length(); i++){
			if (!Character.isDigit(text.charAt(i)))
				return false;
		}
		
		return true;
	}
	
	public boolean isFloatNum(String text){
		try {
			Float.parseFloat(text);
		} catch (Exception e){
			return false;
		}
		
		return true;
	}
}
