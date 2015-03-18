package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CCheckbox;
import libgdxpluginv01.models.uielements.UIElement;
import libgdxpluginv01.swt.custom.Align;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
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
		textPadBottom.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (getUielement() == null) return;
				
				CCheckbox uielement = (CCheckbox)getUielement();
				
				if (isValidTextPadBottom(textPadBottom.getText())){
					uielement.setPadBottom(Integer.parseInt(textPadBottom.getText()));
					uielement.redraw();
				} else {
					MessageDialog.openError(uielement.getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_PAD_BOTTOM);
					textPadBottom.setText(uielement.getPadBottom() + "");
				}
			}
		});
		
		createSlider(getContainer(), textPadBottom, Parameter.LOCATION_RANGE_X, 1, Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1);
	}
	
	private boolean isValidTextPadBottom(String text){
		return isIntegerNum(text);
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
		textPadTop.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (getUielement() == null) return;
				
				CCheckbox uielement = (CCheckbox)getUielement();
				
				if (isValidTextPadTop(textPadTop.getText())){
					uielement.setPadTop(Integer.parseInt(textPadTop.getText()));
					uielement.redraw();
				} else {
					MessageDialog.openError(uielement.getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_PAD_TOP);
					textPadTop.setText(uielement.getPadTop() + "");
				}
			}
		});
		
		createSlider(getContainer(), textPadTop, Parameter.LOCATION_RANGE_X, 1, Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1);
	}

	private boolean isValidTextPadTop(String text) {
		return isIntegerNum(text);
	}

	private void createPadRightField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText("");
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(getContainer(), SWT.NONE);
		label.setText(Word.PROPERTY_PAD_RIGHT);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textPadRight = new Text(getContainer(), SWT.BORDER);
		textPadRight.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		textPadRight.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (getUielement() == null) return;
				
				CCheckbox uielement = (CCheckbox)getUielement();
				
				if (isValidTextPadRight(textPadRight.getText())){
					uielement.setPadRight(Integer.parseInt(textPadRight.getText()));
					uielement.redraw();
				} else {
					MessageDialog.openError(uielement.getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_PAD_RIGHT);
					textPadRight.setText(uielement.getPadRight() + "");
				}
			}
		});
		
		createSlider(getContainer(), textPadRight, Parameter.LOCATION_RANGE_X, 1, Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1);
	}
	
	private boolean isValidTextPadRight(String text) {
		return isFloatNum(text);
	}

	private void createPadLeftField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_PAD);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(getContainer(), SWT.NONE);
		label.setText(Word.PROPERTY_PAD_LEFT);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textPadLeft = new Text(getContainer(), SWT.BORDER);
		textPadLeft.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		textPadLeft.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (getUielement() == null) return;
				
				CCheckbox uielement = (CCheckbox)getUielement();
				
				if (isValidTextPadLeft(textPadLeft.getText())){
					uielement.setPadLeft(Integer.parseInt(textPadLeft.getText()));
					uielement.redraw();
				} else {
					MessageDialog.openError(uielement.getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_PAD_LEFT);
					textPadLeft.setText(uielement.getPadLeft() + "");
				}
			}
		});
		
		createSlider(getContainer(), textPadLeft, Parameter.LOCATION_RANGE_X, 1, Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1);
	}
	
	private boolean isValidTextPadLeft(String text){
		return isIntegerNum(text);
	}

	private void createTextField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_TEXT);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		textText = new Text(getContainer(), SWT.BORDER);
		textText.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
		textText.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (getUielement() == null) return;
				CCheckbox uielement = (CCheckbox)getUielement();
				
				uielement.setText(textText.getText());
				uielement.redraw();
			}
		});
	}
	
	private void createAlignField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_ALIGN);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		comboAlign = new Combo(getContainer(), SWT.READ_ONLY);
		comboAlign.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
		comboAlign.setItems(Align.getStrings());
		comboAlign.select(0);
		comboAlign.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (getUielement() == null) return;
				CCheckbox obj = (CCheckbox)getUielement();
				
				obj.setAlign(Align.getAlignIndex(comboAlign.getSelectionIndex()));
				obj.redraw();
			}
		});
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
