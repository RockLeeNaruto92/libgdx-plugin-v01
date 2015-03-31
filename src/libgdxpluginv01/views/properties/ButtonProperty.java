package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.models.uielements.CButton;
import libgdxpluginv01.models.uielements.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

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
	
	protected void createCheckField(){
		String[] labelNames = new String[]{Word.PROPERTY_CHECK};
		checkboxCheck = Utility.createCheckboxGridData4Columns(getContainer(), labelNames, this, UIElementPropertyType.CHECK);
	}
	
	protected void createDisableField(){
		String[] labelNames = new String[]{Word.PROPERTY_DISABLE};
		checkboxDisable = Utility.createCheckboxGridData4Columns(getContainer(), labelNames, this, UIElementPropertyType.DISABLE);
	}
	
	protected void createStyleField() {
		Utility.createSetStyleButton(getContainer(), this);
	}

	@Override
	public void setObjectPropertiesToView(UIElement object) {
		super.setObjectPropertiesToView(object);
		
		CButton obj = (CButton)object;
		
		checkboxCheck.setSelection(obj.isChecked());
		checkboxDisable.setSelection(obj.isDisabled());
	}
}