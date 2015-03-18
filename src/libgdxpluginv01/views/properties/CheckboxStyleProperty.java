package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CCheckbox;
import libgdxpluginv01.models.uielements.CCheckbox.CheckboxStyle;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
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
		
		font = createFontField(null, Word.PROPERTY_FONT, 6);
		fontColor = createColorField(Word.PROPERTY_FONT_COLOR, null, 7);
		downFontColor = createColorField(Word.PROPERTY_DOWN_FONT_COLOR, null, 8);
		overFontColor = createColorField(Word.PROPERTY_OVER, null, 9);
		disabledFontColor = createColorField(Word.PROPERTY_DISABLED_FONT_COLOR, null, 10);
		checkedFontColor = createColorField(Word.PROPERTY_CHECKED_FONT_COLOR, null, 11);
		checkedOverFontColor = createColorField(Word.PROPERTY_CHECKED_OVER_FONT_COLOR, null, 12);
	}

	@Override
	public void setPropertyToView(Object object, Object[] datas) {
		super.setPropertyToView(object, datas);
		
		CCheckbox obj = (CCheckbox)object;
		CheckboxStyle style = obj.getStyle();
		
		if (datas != null){
			switch ((Integer)datas[1]) {
			case 6:{
				style.font.setFont((String)datas[0]);
				break;
			}
			case 7:{
				style.fontColor.dispose();
				style.fontColor = (Color)datas[0];
				break;
			}
			case 8:{
				style.downFontColor.dispose();
				style.downFontColor = (Color)datas[0];
				break;
			}
			case 9:{
				style.overFontColor.dispose();
				style.overFontColor = (Color)datas[0];
				break;
			}
			case 10: {
				style.disabledFontColor.dispose();
				style.disabledFontColor = (Color)datas[0];
				break;
			}
			case 11:{
				style.checkedFontColor.dispose();
				style.checkedFontColor = (Color)datas[0];
				break;
			}
			case 12:{
				style.checkedOverFontColor.dispose();
				style.checkedOverFontColor = (Color)datas[0];
				break;
			}
			default:
				break;
			}
		}
		
		font.setText(style.font.getName());
		fontColor.setBackground(style.fontColor);
		downFontColor.setBackground(style.downFontColor);
		overFontColor.setBackground(style.overFontColor);
		checkedFontColor.setBackground(style.checkedFontColor);
		checkedOverFontColor.setBackground(style.checkedOverFontColor);
		disabledFontColor.setBackground(style.disabledFontColor);
		
		obj.redraw();
	}
	
}
