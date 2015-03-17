package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CCheckbox.CheckboxStyle;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class CheckboxStyleProperty extends ButtonStyleProperty{
	private static CheckboxStyleProperty _instance;
	
	private Text font;
	private Text fontColor, downFontColor, overFontColor;
	private Text disabledFontColor, checkedFontColor, checkedOverFontColor;
	
	public CheckboxStyleProperty(Composite parent, Object style) {
		super(parent, style);
	}
	
	public static CheckboxStyleProperty getInstance(Composite parent, Object object){
		if (_instance == null){
			_instance = new CheckboxStyleProperty(parent, object);
		}
		
		return _instance;
	}

	@Override
	public void createContents() {
		super.createContents();
		
		CheckboxStyle style = (CheckboxStyle)getObject();
		
		if (style == null) return;
		
		font = createFontField(Word.PROPERTY_FONT, style.font.getPath());
		fontColor = createColorField(Word.PROPERTY_FONT_COLOR, style.fontColor);
		downFontColor = createColorField(Word.PROPERTY_DOWN_FONT_COLOR, style.downFontColor);
		overFontColor = createColorField(Word.PROPERTY_OVER, style.overFontColor);
		disabledFontColor = createColorField(Word.PROPERTY_DISABLED_FONT_COLOR, style.disabledFontColor);
		checkedFontColor = createColorField(Word.PROPERTY_CHECKED_FONT_COLOR, style.checkedFontColor);
		checkedOverFontColor = createColorField(Word.PROPERTY_CHECKED_OVER_FONT_COLOR, style.checkedOverFontColor);
	}
	
}
