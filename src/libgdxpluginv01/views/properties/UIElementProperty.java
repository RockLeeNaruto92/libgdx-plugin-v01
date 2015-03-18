package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.UIElement;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;

public abstract class UIElementProperty extends Property{
	private ScrolledComposite root;
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
		root.setContent(container);
		root.setExpandHorizontal(true);
		root.setAlwaysShowScrollBars(true);
		
		Point size = getDefaultSize();
		container.setSize(size);
		root.setMinSize(size);
		getRoot().getParent().setRedraw(true);
	}
	
	private void createContainer(Composite parent) {
		root = new ScrolledComposite(getRootContainer(), SWT.BORDER | SWT.V_SCROLL);
		
//		root.setLayoutData(new FormData());
		
		container = new Composite(root, SWT.NONE);
		container.setLayout(new GridLayout(Parameter.PROPERTY_COLUMN_NUM, false));
		
		root.setContent(container);
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
		Label label = new Label(container, SWT.NONE);
		
		label.setText(Word.PROPERTY_NAME);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		textName = new Text(container, SWT.BORDER);
		textName.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
		textName.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (uielement == null) return;
				
				if (isValidName(textName.getText())){
					uielement.setName(textName.getText());
				} else {
					MessageDialog.openError(uielement.getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_NAME);
					textName.setText(uielement.getName());
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
	}
	
	private void createLocationXField(){
		Label label = new Label(container, SWT.NONE);
		
		label.setText(Word.PROPERTY_LOCATION);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(container, SWT.NONE);
		label.setText(Word.PROPERTY_X);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textLocationX = new Text(container, SWT.BORDER);
		textLocationX.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		textLocationX.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (uielement == null) return;
				
				if (isValidPositionX(textLocationX.getText())){
					Rectangle bound = uielement.getBound();
					bound.x = Integer.parseInt(textLocationX.getText());
					
					uielement.setBound(bound);
					uielement.refresh();
				} else {
					MessageDialog.openError(uielement.getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_X);
					textLocationX.setText(uielement.getBound().x + "");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		
		Slider sliderX = new Slider(container, SWT.HORIZONTAL);
		sliderX.setMinimum(Parameter.LOCATION_RANGE_X.x);
		sliderX.setMaximum(Parameter.LOCATION_RANGE_X.y);
		sliderX.setIncrement(Parameter.SLIDER_STEP);
		sliderX.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
	}
	
	private void createLocationYField(){
		Label label = new Label(container, SWT.NONE);
		
		label.setText("");
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(container, SWT.NONE);
		label.setText(Word.PROPERTY_Y);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		
		textLocationY = new Text(container, SWT.BORDER);
		textLocationY.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		textLocationY.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (uielement == null) return;
				
				if (isValidPositionY(textLocationY.getText())){
					Rectangle bound = uielement.getBound();
					bound.y = Integer.parseInt(textLocationY.getText());
					
					uielement.setBound(bound);
					uielement.refresh();
				} else {
					MessageDialog.openError(uielement.getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_Y);
					textLocationY.setText(uielement.getBound().y + "");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		
		Slider sliderY = new Slider(container, SWT.HORIZONTAL);
		sliderY.setMinimum(Parameter.LOCATION_RANGE_Y.x);
		sliderY.setMaximum(Parameter.LOCATION_RANGE_Y.y);
		sliderY.setIncrement(Parameter.SLIDER_STEP);
		sliderY.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
	}
	
	private void createSizeWidthField(){
		Label label = new Label(container, SWT.NONE);
		label.setText(Word.PROPERTY_SIZE);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(container, SWT.NONE);
		label.setText(Word.PROPERTY_WIDTH);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textSizeWidth = new Text(container, SWT.BORDER);
		textSizeWidth.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		textSizeWidth.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (uielement == null) return;
				
				if (isValidSizeWidth(textSizeWidth.getText())){
					Rectangle bound = uielement.getBound();
					bound.width = Integer.parseInt(textSizeWidth.getText());
					
					uielement.setBound(bound);
					uielement.refresh();
				} else {
					MessageDialog.openError(uielement.getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_WIDTH);
					textSizeWidth.setText(uielement.getBound().width + "");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		
		Slider sliderY = new Slider(container, SWT.HORIZONTAL);
		sliderY.setMinimum(Parameter.SIZE_RANGE_WIDTH.x);
		sliderY.setMaximum(Parameter.SIZE_RANGE_WIDTH.y);
		sliderY.setIncrement(Parameter.SLIDER_STEP);
		sliderY.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
	}
	
	private void createSizeHeightField(){
		Label label = new Label(container, SWT.NONE);
		label.setText("");
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(container, SWT.NONE);
		label.setText(Word.PROPERTY_HEIGHT);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textSizeHeight = new Text(container, SWT.BORDER);
		textSizeHeight.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		textSizeHeight.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (uielement == null) return;
				
				if (isValidSizeHeight(textSizeHeight.getText())){
					Rectangle bound = uielement.getBound();
					bound.height = Integer.parseInt(textSizeHeight.getText());
					
					uielement.setBound(bound);
					uielement.refresh();
				} else {
					MessageDialog.openError(uielement.getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_HEIGHT);
					textSizeHeight.setText(uielement.getBound().height + "");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		
		Slider sliderY = new Slider(container, SWT.HORIZONTAL);
		sliderY.setMinimum(Parameter.SIZE_RANGE_HEIGHT.x);
		sliderY.setMaximum(Parameter.SIZE_RANGE_HEIGHT.y);
		sliderY.setIncrement(Parameter.SLIDER_STEP);
		sliderY.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
	}
	
	private void createVisbleField(){
		Label label = new Label(container, SWT.NONE);
		label.setText(Word.PROPERTY_VISIBLE);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		checkboxVisible = new Button(container, SWT.CHECK);
		checkboxVisible.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
		checkboxVisible.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UIElement obj = getUielement();
				
				if (obj == null){
					return;
				}
				
				obj.setVisible(!obj.isVisible());
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
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
	
	private boolean isValidPositionX(String x){
		return isIntegerNum(x);
	}
	
	private boolean isValidPositionY(String y){
		return isIntegerNum(y);
	}
	
	private boolean isValidSizeWidth(String width){
		return isIntegerNum(width);
	}
	
	private boolean isValidSizeHeight(String height){
		return isIntegerNum(height);
	}
}
