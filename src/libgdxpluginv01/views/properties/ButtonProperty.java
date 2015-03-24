package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.models.uielements.CButton;
import libgdxpluginv01.models.uielements.CLabel;
import libgdxpluginv01.models.uielements.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
	
	protected void createStyleField(){
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_STYLE);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		Button button = new Button(getContainer(), SWT.PUSH);
		button.setText(Word.PROPERTY_SET_STYLE);
		button.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
		button.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (getUielement() == null) return;
				
				UIElement obj = getUielement();
				UIController uiController = obj.getUiController();
				
				uiController.setPropertyView(obj, true);
			}
		});
	}

	@Override
	public void setObjectPropertiesToView(UIElement object) {
		super.setObjectPropertiesToView(object);
		
		CButton obj = (CButton)object;
		
		checkboxCheck.setSelection(obj.isChecked());
		checkboxDisable.setSelection(obj.isDisabled());
	}
}
