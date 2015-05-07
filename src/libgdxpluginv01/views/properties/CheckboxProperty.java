package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CCheckbox;
import libgdxpluginv01.models.uielements.UIElement;
import libgdxpluginv01.swt.custom.Align;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class CheckboxProperty extends ButtonProperty {
	private static CheckboxProperty _instance;
	
	private Text textText;
	private Text textFontScaleX;
	private Text textFontScaleY;
	private Combo comboAlign;

	public CheckboxProperty(Composite parent) {
		super(parent);
	}
	
	public static CheckboxProperty getInstance(Composite parent){
		if (_instance == null)
			_instance = new CheckboxProperty(parent);
		
		return _instance;
	}

	@Override
	public void createOtherProperties() {
		createCheckField();
		createDisableField();
		createTextField();
		createAlignField();
		createFontScaleXField();
		createFontScaleYField();
		createStyleField();
	}
	
	private void createAlignField() {
		String[] labelNames = new String[]{Word.PROPERTY_ALIGN};
		comboAlign = Utility.createComboGridData2Columns(getContainer(), labelNames, Align.getStrings(), this, UIElementPropertyType.ALIGN);		
	}

	private void createTextField() {
		String[] labelNames = new String[]{Word.PROPERTY_TEXT};
		textText = Utility.createTextGridData4Columns(getContainer(), labelNames, false, null, 0, this, UIElementPropertyType.TEXT);
	}
	
	private void createFontScaleXField(){
		String[] labelNames = new String[]{Word.PROPERTY_FONT, Word.PROPERTY_X};
		textFontScaleX = Utility.createTextGridData4Columns(getContainer(), labelNames, true, Parameter.FONT_SCALE_RANGE_X, 0.02f, this, UIElementPropertyType.FONT_SCALE_X);
	}
	
	private void createFontScaleYField(){
		String[] labelNames = new String[]{"", Word.PROPERTY_Y};
		textFontScaleY = Utility.createTextGridData4Columns(getContainer(), labelNames, true, Parameter.LOCATION_RANGE_X, 0.02f, this, UIElementPropertyType.FONT_SCALE_Y);
	}

	@Override
	public Point getDefaultSize() {
		return super.getDefaultSize();
	}

	@Override
	public void setObjectPropertiesToView(UIElement object) {
		super.setObjectPropertiesToView(object);
		
		CCheckbox obj = (CCheckbox)object;
		
		textText.setText(obj.getText());
		textFontScaleX.setText(obj.getFontScaleX() + "");
		textFontScaleY.setText(obj.getFontScaleY() + "");
		comboAlign.select(Align.getAlignIndex(obj.getAlign()));
	}
}
