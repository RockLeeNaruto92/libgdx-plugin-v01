package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
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
		String[] labelNames = new String[]{Word.PROPERTY_MAX};
		textMax = Utility.createTextGridData4Columns(getContainer(), labelNames, false, null, 0, this, UIElementPropertyType.MAX);
	}

	private void createMinField() {
		String[] labelNames = new String[]{Word.PROPERTY_MIN};
		textMin = Utility.createTextGridData4Columns(getContainer(), labelNames, false, null, 0, this, UIElementPropertyType.MIN);
	}

	private void createStepField() {
		String[] labelNames = new String[]{Word.PROPERTY_STEP};
		textStep = Utility.createTextGridData4Columns(getContainer(), labelNames, false, null, 0, this, UIElementPropertyType.STEP_SIZE);
	}

	private void createValueField() {
		String[] labelNames = new String[]{Word.PROPERTY_VALUE};
		textValue = Utility.createTextGridData4Columns(getContainer(), labelNames, false, null, 0, this, UIElementPropertyType.VALUE);
	}
	
	private void createStyleField() {
		Utility.createSetStyleButton(getContainer(), this);
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
