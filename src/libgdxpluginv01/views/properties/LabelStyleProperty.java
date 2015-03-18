package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CLabel;
import libgdxpluginv01.models.uielements.CLabel.LabelStyle;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class LabelStyleProperty extends StyleProperty {
	private static LabelStyleProperty _instance;
	private Text textFont, textFontColor;
	private Composite image;

	public LabelStyleProperty(Composite parent, Object object) {
		super(parent, object);
	}
	
	public static LabelStyleProperty getInstance(Composite parent, Object object){
		if (_instance == null){
			_instance = new LabelStyleProperty(parent, object);
		}
		
		return _instance;
	}

	@Override
	public void createContents() {
		textFont = createFontField(null, Word.PROPERTY_FONT);
		textFontColor = createColorField(Word.PROPERTY_COLOR, null);
		image = createImageField(Word.PROPERTY_BACKGROUND, null, 0);
	}

	@Override
	public void setPropertyToView(Object object, Object[] datas) {
		CLabel obj = (CLabel)object;
		
		setObject(object);
		
		LabelStyle style = obj.getStyle();
		
		if (datas != null){
			if (datas[0] instanceof String)
				style.font.setFont((String)datas[0]);
			else if (datas[0] instanceof RGB){
				style.fontColor.dispose();
				style.fontColor = new Color(Display.getCurrent(), (RGB)datas[0]);
			} else if (datas[0] instanceof Image){
				if (style.background != null)
					style.background.dispose();
				style.background = (Image)datas[0];
			}
				
		}
		
		textFont.setText(style.font.getName());
		textFontColor.setBackground(style.fontColor);
		if (style.background != null)
			image.setBackgroundImage(style.background);
		
		// redraw
		obj.redraw();
	}

	@Override
	public Image getImageFromIndex(int index) {
		// TODO Auto-generated method stub
		return null;
	}

}
