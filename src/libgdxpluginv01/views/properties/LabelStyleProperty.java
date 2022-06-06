package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CLabel;
import libgdxpluginv01.models.uielements.CLabel.LabelStyle;
import libgdxpluginv01.swt.custom.BitmapFont;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
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
			System.out.println("Get LabelStyleProperty Instance");
		}
		
		return _instance;
	}

	@Override
	public void createContents() {
		textFont = createFontField(null, Word.PROPERTY_FONT, 0);
		textFontColor = createColorField(Word.PROPERTY_COLOR, null, 0);
		image = createImageField(Word.PROPERTY_BACKGROUND, null, 0);
	}

	@Override
	public void setPropertyToView(Object object, Object[] datas) {
		CLabel obj = (CLabel)object;
		
		setObject(object);
		
		LabelStyle style = obj.getStyle();
		
		if (datas != null){
			if (datas[0] instanceof BitmapFont){
				style.font.dispose();
				style.font = (BitmapFont)datas[0];
			}
			else if (datas[0] instanceof RGB){
				style.fontColor.dispose();
				style.fontColor = (Color) datas[0];
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
