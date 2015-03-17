package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Word;

import org.eclipse.swt.widgets.Composite;

public class LabelStyleProperty extends StyleProperty {
	private static LabelStyleProperty _instance;

	public LabelStyleProperty(Composite parent, Object object) {
		super(parent, object);
		// TODO Auto-generated constructor stub
	}
	
	public static LabelStyleProperty getInstance(Composite parent, Object object){
		if (_instance == null){
			_instance = new LabelStyleProperty(parent, object);
		}
		
		return _instance;
	}

	@Override
	public void createContents() {
		createFontField(Word.PROPERTY_FONT, "fontname.fnt");
		createColorField(Word.PROPERTY_COLOR, null);
		createImageField(Word.PROPERTY_BACKGROUND, null);
	}

}
