package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
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
		createContainer(parent);
		createContents();
		setScrolledComposite();
	}
	
	private void setScrolledComposite(){
		root.setContent(container);
		root.setExpandHorizontal(true);
		root.setAlwaysShowScrollBars(true);
		
		Point size = getDefaultSize();
		container.setSize(size);
		root.getParent().setRedraw(true);
	} 

	private void createContainer(Composite parent) {
		root = new ScrolledComposite(parent, SWT.BORDER | SWT.V_SCROLL);
		
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

	public void setContainer(ScrolledComposite container) {
		this.container = container;
	}

	public UIElement getUielement() {
		return uielement;
	}

	public void setUielement(UIElement uielement) {
		this.uielement = uielement;
	}
	
	public GridData createLayoutData(int width, int height, int horizontalSpan){
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.CENTER;
		data.widthHint = width;
		data.horizontalSpan = horizontalSpan;
		
		return data;
	}

	private void createNameField(){
		Label label = new Label(container, SWT.NONE);
		
		label.setText(Word.PROPERTY_NAME);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		textName = new Text(container, SWT.BORDER);
		textName.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
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
		label.setText(Word.PROPERTY_WIDTH);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textSizeHeight = new Text(container, SWT.BORDER);
		textSizeHeight.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		
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
	}
}
