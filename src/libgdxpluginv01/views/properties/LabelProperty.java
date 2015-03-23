package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.models.uielements.CLabel;
import libgdxpluginv01.models.uielements.UIElement;
import libgdxpluginv01.swt.custom.Align;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class LabelProperty extends UIElementProperty {
	private static LabelProperty _instance;
	private Text textText;
	private Combo comboAlign;
	private Button checkboxWrap;
	private Button checkboxEllipsis;
	private Text textFontScaleX;
	private Text textFontScaleY;
	
	public LabelProperty(Composite parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	public static LabelProperty getInstance(Composite parent){
		if (_instance == null){
			_instance = new LabelProperty(parent);
		}
		
		return _instance;
	}

	@Override
	public void createOtherProperties() {
		createTextField();
		createAlignField();
		createWrapField();
		createFontScaleXField();
		createFontScaleYField();
		createEllipsisField();
		createStyleField();
	}

	private void createStyleField() {
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
				
				CLabel obj = (CLabel)getUielement();
				UIController uiController = obj.getUiController();
				
				uiController.setPropertyView(obj, true);
			}
		});
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
				
				CLabel obj = (CLabel)getUielement();
				
				obj.setText(textText.getText());
				
				Point size = obj.getDefaultSize();
				Rectangle bound = obj.getBound();
				
				if (bound.width < size.x) bound.width = size.x;
				if (bound.height < size.y) bound.height = size.y;
				
				obj.setBound(bound);
				
				obj.refresh();
				obj.redraw();
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
				
				CLabel obj = (CLabel)getUielement();
				
				obj.setLabelAlign(Align.getAlign(comboAlign.getSelectionIndex()));
				obj.redraw();
			}
		});
	}

	private void createWrapField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_WRAP);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		checkboxWrap = new Button(getContainer(), SWT.CHECK);
		checkboxWrap.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}

	private void createFontScaleXField() {
		String[] labelNames = new String[]{Word.PROPERTY_FONT, Word.PROPERTY_X};
		textFontScaleX = Utility.createTextGridData4Columns(getContainer(), labelNames, true, Parameter.LOCATION_RANGE_X, 1, this, UIElementPropertyType.FONT_SCALE_X);
	}
	
	private void createFontScaleYField(){
		String[] labelNames = new String[]{"", Word.PROPERTY_Y};
		textFontScaleY = Utility.createTextGridData4Columns(getContainer(), labelNames, true, Parameter.LOCATION_RANGE_X, 1, this, UIElementPropertyType.FONT_SCALE_Y);
	}

	private void createEllipsisField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_ELLIPSIS);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		checkboxEllipsis = new Button(getContainer(), SWT.CHECK);
		checkboxEllipsis.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}
	
	@Override
	public void setObjectPropertiesToView(UIElement object) {
		super.setObjectPropertiesToView(object);
		
		CLabel obj = (CLabel)object;
		
		textText.setText(obj.getText());
		textFontScaleX.setText(obj.getFontScaleX() + "");
		textFontScaleY.setText(obj.getFontScaleY() + "");
		comboAlign.select(Align.getAlignIndex(obj.getLabelAlign()));
		checkboxWrap.setSelection(obj.isWrap());
		checkboxEllipsis.setSelection(obj.isEllipsis());
	}

	@Override
	public Point getDefaultSize() {
		return getContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}
}
