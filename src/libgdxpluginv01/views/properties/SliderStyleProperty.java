package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CLabel;
import libgdxpluginv01.models.uielements.CSlider;
import libgdxpluginv01.models.uielements.CLabel.LabelStyle;
import libgdxpluginv01.models.uielements.CSlider.SliderStyle;
import libgdxpluginv01.winzards.Utility;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class SliderStyleProperty extends StyleProperty {
	private static SliderStyleProperty _instance;
	private Composite background, disableBackground, knob, disabledKnob;

	public SliderStyleProperty(Composite parent, Object object) {
		super(parent, object);
	}
	
	public static SliderStyleProperty getInstance(Composite parent, Object object){
		if (_instance == null){
			_instance = new SliderStyleProperty(parent, object);
		}
		
		return _instance;
	}

	@Override
	public void createContents() {
		background = createImageField(Word.PROPERTY_BACKGROUND, null, 0);
		disableBackground = createImageField(Word.PROPERTY_DISABLED_BACKGROUND, null, 1);
		knob = createImageField(Word.PROPERTY_KNOB, null, 2);
		disabledKnob = createImageField(Word.PROPERTY_DISABLED_KNOB, null, 3);
	}

	@Override
	public void setPropertyToView(Object object, Object[] datas) {
		CSlider obj = (CSlider)object;
		
		setObject(object);
		
		SliderStyle style = obj.getStyle();

		System.out.println("On slider  property: " + datas);
		if (datas == null){
			background.setBackgroundImage(style.background);
			if (style.disabledBackground != null) disableBackground.setBackgroundImage(style.disabledBackground);
			knob.setBackgroundImage(style.knob);
			if (style.disabledKnob != null) disabledKnob.setBackgroundImage(style.disabledKnob);
			return;
		}
		
		switch ((int)datas[1]) {
		case 0:
			style.background = (Image)datas[0];
			break;
		case 1:
			style.disabledBackground = (Image)datas[0];
			break;
		case 2:
			style.knob = (Image)datas[0];
			break;
		case 3:
			style.disabledKnob = (Image)datas[0];
			break;
		default:
			break;
		}
		
		background.setBackgroundImage(style.background);
		if (style.disabledBackground != null) disableBackground.setBackgroundImage(style.disabledBackground);
		knob.setBackgroundImage(style.knob);
		if (style.disabledKnob != null) disabledKnob.setBackgroundImage(style.disabledKnob);
		
		obj.redraw();
	}

	@Override
	public Image getImageFromIndex(int index) {
		return null;
	}
}
