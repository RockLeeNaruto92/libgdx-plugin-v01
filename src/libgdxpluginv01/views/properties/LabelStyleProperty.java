package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CLabel;
import libgdxpluginv01.models.uielements.CLabel.LabelStyle;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class LabelStyleProperty extends StyleProperty {
	private static LabelStyleProperty _instance;
	private Text textFont;

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
		createColorField(Word.PROPERTY_COLOR, null);
		createImageField(Word.PROPERTY_BACKGROUND, null);
	}

	@Override
	public void setPropertyToView(Object object, Object[] datas) {
		CLabel obj = (CLabel)object;
		
		setObject(object);
		
		LabelStyle style = obj.getStyle();
		
		if (datas != null){
			style.font.setFont((String)datas[0]);
		}
		
		textFont.setText(style.font.getName());
		
		// redraw
		obj.redraw();
	}

}
