package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CButton;
import libgdxpluginv01.models.uielements.CButton.ButtonStyle;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class ButtonStyleProperty extends StyleProperty {
	private static ButtonStyleProperty _instance;
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
		up = createImageField(Word.PROPERTY_UP, null, 0);
		down = createImageField(Word.PROPERTY_DOWN, null, 1);
		checked = createImageField(Word.PROPERTY_CHECKED, null, 2);
		disabled = createImageField(Word.PROPERTY_DISABLED, null, 3);
		over = createImageField(Word.PROPERTY_OVER, null, 4);
		checkover = createImageField(Word.PROPERTY_CHECKED_OVER, null, 5);
	}

	@Override
	public void setPropertyToView(Object object, Object[] datas) {
		CButton obj = (CButton)object;
		
		setObject(object);
		ButtonStyle style = obj.getStyle();
		
		if (datas != null){
			switch ((Integer)datas[1]) {
			case 0:{
				Image image = (Image)datas[0];
				style.up = image;
				break;
			}
			case 1:{
				Image image = (Image)datas[0];
				style.down = image;
				break;
			}
			case 2:{
				Image image = (Image)datas[0];
				style.checked = image;
				break;
			}
			case 3:{
				Image image = (Image)datas[0];
				style.disabled = image;
				break;
			}
			case 4: {
				Image image = (Image)datas[0];
				style.over = image;
				break;
			}
			case 5:{
				Image image = (Image)datas[0];
				style.checkedOver = image;
				break;
			}
			default:
				break;
			}
		}
		
		up.redraw();
		down.redraw();
		checked.redraw();
		disabled.redraw();
		checkover.redraw();
		over.redraw();
		
		obj.redraw();
	}

	@Override
	public Image getImageFromIndex(int index) {
		if (getObject() == null) return null;
		
		ButtonStyle style = ((CButton)getObject()).getStyle();
		
		if (style == null) return null;
		
		switch (index) {
		case 0:
			return style.up;
		case 1:
			return style.down;
		case 2:
			return style.checked;
		case 3:
			return style.disabled;
		case 4: 
			return style.over;
		case 5:
			return style.checkedOver;
		default:
			break;
		}
		
		return null;
	}
}
