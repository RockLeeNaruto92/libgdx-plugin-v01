package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Word;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ButtonProperty extends UIElementProperty{
	private static ButtonProperty _instance;
	
	private Button checkboxCheck;
	private Button checkboxDisable;

	public ButtonProperty(Composite parent) {
		super(parent);
	}
	
	public static ButtonProperty getInstance(Composite parent){
		if (_instance == null){
			_instance = new ButtonProperty(parent);
		}
		
		return _instance;
	}
	
	@Override
	public void createOtherProperties() {
		createCheckField();
		createDisableField();
		createStyleField();
	}

	@Override
	public Point getDefaultSize() {
		return getContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}
	
	private void createCheckField(){
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_DISABLE);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		checkboxCheck = new Button(getContainer(), SWT.CHECK);
		checkboxCheck.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}
	
	private void createDisableField(){
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_CHECK);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		checkboxDisable = new Button(getContainer(), SWT.CHECK);
		checkboxDisable.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}
	
	private void createStyleField(){
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_STYLE);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		Button button = new Button(getContainer(), SWT.PUSH);
		button.setText(Word.PROPERTY_SET_STYLE);
		button.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}

}
