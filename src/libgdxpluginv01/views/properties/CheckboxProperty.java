package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CCheckbox;
import libgdxpluginv01.models.uielements.UIElement;
import libgdxpluginv01.swt.custom.Align;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;

public class CheckboxProperty extends ButtonProperty{
	private static CheckboxProperty _instance;
	private Text textText;
	private Combo comboAlign;
	private Text textPadLeft, textPadRight, textPadTop, textPadBottom;
	
	public CheckboxProperty(Composite parent) {
		super(parent);
	}
	
	public static CheckboxProperty getInstance(Composite parent){
		if (_instance == null){
			_instance = new CheckboxProperty(parent);
		}
		
		return _instance;
	}
	
	@Override
	public void createOtherProperties() {
		createPadLeftField();
		createPadRightField();
		createPadTopField();
		createPadBottomField();
		createCheckField();
		createDisableField();
		createTextField();
		createAlignField();
		createStyleField();
	}

	private void createPadBottomField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText("");
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(getContainer(), SWT.NONE);
		label.setText(Word.PROPERTY_PAD_BOTTOM);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textPadBottom = new Text(getContainer(), SWT.BORDER);
		textPadBottom.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		
		Slider slider = new Slider(getContainer(), SWT.HORIZONTAL);
		slider.setMinimum(Parameter.LOCATION_RANGE_X.x);
		slider.setMaximum(Parameter.LOCATION_RANGE_X.y);
		slider.setIncrement(Parameter.SLIDER_STEP);
		slider.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
	}

	private void createPadTopField() {
		// TODO Auto-generated method stub
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText("");
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(getContainer(), SWT.NONE);
		label.setText(Word.PROPERTY_PAD_TOP);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textPadTop = new Text(getContainer(), SWT.BORDER);
		textPadTop.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		
		Slider slider = new Slider(getContainer(), SWT.HORIZONTAL);
		slider.setMinimum(Parameter.LOCATION_RANGE_X.x);
		slider.setMaximum(Parameter.LOCATION_RANGE_X.y);
		slider.setIncrement(Parameter.SLIDER_STEP);
		slider.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
	}

	private void createPadRightField() {
		// TODO Auto-generated method stub
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText("");
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(getContainer(), SWT.NONE);
		label.setText(Word.PROPERTY_PAD_RIGHT);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textPadRight = new Text(getContainer(), SWT.BORDER);
		textPadRight.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		
		Slider slider = new Slider(getContainer(), SWT.HORIZONTAL);
		slider.setMinimum(Parameter.LOCATION_RANGE_X.x);
		slider.setMaximum(Parameter.LOCATION_RANGE_X.y);
		slider.setIncrement(Parameter.SLIDER_STEP);
		slider.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
	}

	private void createPadLeftField() {
		// TODO Auto-generated method stub
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_PAD);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(getContainer(), SWT.NONE);
		label.setText(Word.PROPERTY_PAD_LEFT);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textPadLeft = new Text(getContainer(), SWT.BORDER);
		textPadLeft.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		
		Slider slider = new Slider(getContainer(), SWT.HORIZONTAL);
		slider.setMinimum(Parameter.LOCATION_RANGE_X.x);
		slider.setMaximum(Parameter.LOCATION_RANGE_X.y);
		slider.setIncrement(Parameter.SLIDER_STEP);
		slider.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
	}

	private void createTextField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_TEXT);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		textText = new Text(getContainer(), SWT.BORDER);
		textText.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}
	
	private void createAlignField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_ALIGN);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		comboAlign = new Combo(getContainer(), SWT.READ_ONLY);
		comboAlign.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
		comboAlign.setItems(Align.getStrings());
		comboAlign.select(0);
	}
	
	@Override
	public void setObjectPropertiesToView(UIElement object) {
		super.setObjectPropertiesToView(object);
		
		CCheckbox obj = (CCheckbox)object;
		
		textText.setText(obj.getText());
		comboAlign.select(Align.getAlignIndex(obj.getAlign()));
		textPadLeft.setText(obj.getPadLeft() + "");
		textPadRight.setText(obj.getPadRight() + "");
		textPadTop.setText(obj.getPadTop() + "");
		textPadBottom.setText(obj.getPadBottom() + "");
	}

	@Override
	public Point getDefaultSize() {
		return getContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}
}
