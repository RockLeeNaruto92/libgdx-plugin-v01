package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CButton.ButtonStyle;

import org.eclipse.swt.widgets.Composite;

public class ButtonStyleProperty extends StyleProperty {
	private static ButtonStyleProperty _instance;
	private ButtonStyle style;
	private Composite up, down, checked, over, disabled, checkover;
	
	public ButtonStyleProperty(Composite parent, Object style){
		super(parent, style);
	}
	
	public static ButtonStyleProperty getInstance(Composite parent, Object style){
		if (_instance == null){
			_instance = new ButtonStyleProperty(parent, style);
		}
		
		return _instance;
	}

	@Override
	public void createContents() {
		up = createImageField(Word.PROPERTY_UP, ((ButtonStyle)getObject()).up);
		down = createImageField(Word.PROPERTY_DOWN, ((ButtonStyle)getObject()).down);
		checked = createImageField(Word.PROPERTY_CHECKED, ((ButtonStyle)getObject()).checked);
		disabled = createImageField(Word.PROPERTY_DISABLED, ((ButtonStyle)getObject()).disabled);
		over = createImageField(Word.PROPERTY_OVER, ((ButtonStyle)getObject()).over);
		checkover = createImageField(Word.PROPERTY_CHECKED_OVER, ((ButtonStyle)getObject()).checkedOver);
	}

	@Override
	public void setPropertyToView(Object object, Object[] datas) {
		
	}
}
