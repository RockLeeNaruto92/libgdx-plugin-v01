package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CCheckbox;
import libgdxpluginv01.models.uielements.CCheckbox.CheckboxStyle;
import libgdxpluginv01.swt.custom.BitmapFont;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class CheckboxStyleProperty extends ButtonStyleProperty{
	private static CheckboxStyleProperty _instance;
	
	private Text font;
	private Text fontColor, downFontColor, overFontColor;
	private Text disabledFontColor, checkedFontColor, checkedOverFontColor;
	private Composite on, off, disabledOn, disabledOff;
	
	public CheckboxStyleProperty(Composite parent, Object style) {
		super(parent, style);
	}
	
	public static CheckboxStyleProperty getInstance(Composite parent, Object object){
		if (_instance == null){
			_instance = new CheckboxStyleProperty(parent, object);
			System.out.println("Get CheckboxStyleProperty Instance");
		}
		
		return _instance;
	}

	@Override
	public void createContents() {
		super.createContents();
		
		on = createImageField(Word.PROPERTY_ON, null, 13);
		disabledOn = createImageField(Word.PROPERTY_ON_DISABLED, null, 14);
		off = createImageField(Word.PROPERTY_OFF, null, 15);
		disabledOff = createImageField(Word.PROPERTY_OFF_DISABLED, null, 16);
		font = createFontField(null, Word.PROPERTY_FONT, 6);
		fontColor = createColorField(Word.PROPERTY_FONT_COLOR, null, 7);
		downFontColor = createColorField(Word.PROPERTY_DOWN_FONT_COLOR, null, 8);
		overFontColor = createColorField(Word.PROPERTY_OVER_FONT_COLOR, null, 9);
		disabledFontColor = createColorField(Word.PROPERTY_DISABLED_FONT_COLOR, null, 10);
		checkedFontColor = createColorField(Word.PROPERTY_CHECKED_FONT_COLOR, null, 11);
		checkedOverFontColor = createColorField(Word.PROPERTY_CHECKED_OVER_FONT_COLOR, null, 12);
	}

	@Override
	public void setPropertyToView(Object object, Object[] datas) {
		super.setPropertyToView(object, datas);
		
		CCheckbox obj = (CCheckbox)object;
		setObject(obj);
		CheckboxStyle style = obj.getStyle();
		
		if (datas != null){
			switch ((Integer)datas[1]) {
			case 6:{
				style.font.dispose();
				style.font = (BitmapFont)datas[0];
				break;
			}
			case 7:{
				style.fontColor.dispose();
				style.fontColor = (Color)datas[0];
				break;
			}
			case 8:{
				if (style.downFontColor != null) style.downFontColor.dispose();
				style.downFontColor = (Color)datas[0];
				break;
			}
			case 9:{
				if (style.overFontColor != null) style.overFontColor.dispose();
				style.overFontColor = (Color)datas[0];
				break;
			}
			case 10: {
				if (style.disabledFontColor != null) style.disabledFontColor.dispose();
				style.disabledFontColor = (Color)datas[0];
				break;
			}
			case 11:{
				if (style.checkedFontColor != null) style.checkedFontColor.dispose();
				style.checkedFontColor = (Color)datas[0];
				break;
			}
			case 12:{
				if (style.checkedOverFontColor != null) style.checkedOverFontColor.dispose();
				style.checkedOverFontColor = (Color)datas[0];
				break;
			}
			case 13:{
				style.checkboxOn = (Image)datas[0];
				break;
			}
			case 14:{
				style.checkboxOnDisabled = (Image)datas[0];
				break;
			}
			case 15:{
				style.checkboxOff = (Image)datas[0];
				break;
			}
			case 16:{
				style.checkboxOffDisabled = (Image)datas[0];
				break;
			}
			default:
				break;
			}
		}
		
		if (style == null){
			System.out.println("Style null");
		} else {
			if (font == null){
				System.out.println("CHeckbox style not null");
			}
		}
		font.setText(style.font.getName());
		fontColor.setBackground(style.fontColor);
		downFontColor.setBackground(style.downFontColor);
		overFontColor.setBackground(style.overFontColor);
		checkedFontColor.setBackground(style.checkedFontColor);
		checkedOverFontColor.setBackground(style.checkedOverFontColor);
		disabledFontColor.setBackground(style.disabledFontColor);
		on.setBackgroundImage(style.checkboxOn);
		disabledOn.setBackgroundImage(style.checkboxOnDisabled);
		off.setBackgroundImage(style.checkboxOff);
		disabledOff.setBackgroundImage(style.checkboxOffDisabled);
		
		obj.redraw();
	}
	
}
