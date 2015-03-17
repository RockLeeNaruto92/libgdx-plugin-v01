package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CSlider;
import libgdxpluginv01.models.uielements.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SliderProperty extends UIElementProperty {
	private static SliderProperty _instance;
	
	private Button checkboxDisable;
	private Text textMax;
	private Text textMin;
	private Text textValue;
	private Text textStep;
	
	public SliderProperty(Composite parent) {
		super(parent);
	}
	
	public static SliderProperty getInstance(Composite parent){
		if (_instance == null){
			_instance = new SliderProperty(parent);
		}
		
		return _instance;
	}

	@Override
	public void createOtherProperties() {
		createDisabledField();
		createMaxField();
		createMinField();
		createStepField();
		createValueField();
		createStyleField();
	}

	private void createDisabledField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_CHECK);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		checkboxDisable = new Button(getContainer(), SWT.CHECK);
		checkboxDisable.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}

	private void createMaxField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_MAX);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		textMax = new Text(getContainer(), SWT.BORDER);
		textMax.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}

	private void createMinField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_MIN);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		textMin = new Text(getContainer(), SWT.BORDER);
		textMin.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}

	private void createStepField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_STEP);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		textStep = new Text(getContainer(), SWT.BORDER);
		textStep.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}

	private void createValueField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_VALUE);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		textValue = new Text(getContainer(), SWT.BORDER);
		textValue.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}
	
	private void createStyleField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_STYLE);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		Button button = new Button(getContainer(), SWT.PUSH);
		button.setText(Word.PROPERTY_SET_STYLE);
		button.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}
	

	@Override
	public void setObjectPropertiesToView(UIElement object) {
		super.setObjectPropertiesToView(object);
		
		CSlider obj = (CSlider)object;
		checkboxDisable.setSelection(obj.isDisabled());
		textMax.setText(obj.getMax() + "");
		textMin.setText(obj.getMin() + "");
		textStep.setText(obj.getStepSize() + "");
		textValue.setText(obj.getValue() + "");
	}

	@Override
	public Point getDefaultSize() {
		// TODO Auto-generated method stub
		return getContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}

}
