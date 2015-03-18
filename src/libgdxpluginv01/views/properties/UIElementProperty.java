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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
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
		textName.addListener(SWT.FocusOut, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (uielement == null) return;
				
				if (isValidName(textName.getText())){
					uielement.setName(textName.getText());
				} else {
					MessageDialog.openError(uielement.getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_NAME);
					textName.setText(uielement.getName());
				}
			}
		});
	}
	
	private void processLocationX(){
		if (uielement == null) return;
		
		if (isValidPositionX(textLocationX.getText())){
			Rectangle bound = uielement.getBound();
			
			bound.x = Integer.parseInt(textLocationX.getText());
			uielement.setBound(bound);
			uielement.refresh();
		} else {
			MessageDialog.openError(uielement.getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_X);
			textLocationY.setText(uielement.getBound().x + "");
		}
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
		textLocationX.addListener(SWT.FocusOut, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				processLocationX();
			}
		});
		textLocationX.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				processLocationX();
			}
		});
		
		createSlider(container, textLocationX, Parameter.LOCATION_RANGE_X, Parameter.SLIDER_STEP, Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1);
	}
	
	private void processLocationY(){
		System.out.println("process");
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
	
	private void createLocationYField(){
		Label label = new Label(container, SWT.NONE);
		
		label.setText("");
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(container, SWT.NONE);
		label.setText(Word.PROPERTY_Y);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textLocationY = new Text(container, SWT.BORDER);
		textLocationY.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		textLocationY.addListener(SWT.FocusOut, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				processLocationY();				
			}
		});
		textLocationY.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				processLocationY();
			}
		});
		
		createSlider(container, textLocationY, Parameter.LOCATION_RANGE_Y, Parameter.SLIDER_STEP, Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1);
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
		textSizeWidth.addListener(SWT.FocusOut, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				processSizeWidth();
			}
		});
		textSizeWidth.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				processSizeWidth();
			}
		});
		
		createSlider(container, textSizeWidth, Parameter.LOCATION_RANGE_X, Parameter.SLIDER_STEP, Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1);
		
	}
	
	private void processSizeWidth(){
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
	
	private void processSizeHeight(){
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
	
	private void createSizeHeightField(){
		Label label = new Label(container, SWT.NONE);
		label.setText("");
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(container, SWT.NONE);
		label.setText(Word.PROPERTY_HEIGHT);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textSizeHeight = new Text(container, SWT.BORDER);
		textSizeHeight.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		textSizeHeight.addListener(SWT.FocusOut, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				processSizeHeight();
			}
		});
		textSizeHeight.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				System.out.println("Modify height");
				processSizeHeight();
			}
		});
		
		createSlider(container, textSizeHeight, Parameter.LOCATION_RANGE_X, Parameter.SLIDER_STEP, Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1);
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
	
	public Slider createSlider(Composite parent, final Text target, Point range, int step, int width, int height, int horizontalSpan){
		final Slider slider = new Slider(parent, SWT.HORIZONTAL);
		
		slider.setMinimum(range.x);
		slider.setMaximum(range.y);
		slider.setIncrement(step);
		slider.setLayoutData(createLayoutData(width, height, horizontalSpan));
		slider.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				target.setText(slider.getSelection() + "");
			}
		});
		
		return slider;
	}
}
