package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CSprite;
import libgdxpluginv01.models.uielements.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SpriteProperty extends UIElementProperty {
	private static SpriteProperty _instance;
	
	private Text textRotation;
	private Text textColor;
	private Button checkboxFlipX, checkboxFlipY;
	private Composite image;
	
	public SpriteProperty(Composite parent) {
		super(parent);
	}
	
	public static SpriteProperty getInstance(Composite parent){
		if (_instance == null){
			_instance = new SpriteProperty(parent);
			System.out.println("Get SpriteProperty Instance");
		}
		
		return _instance;
	}

	@Override
	public void createOtherProperties() {
		createRotationField();
		createFlipXField();
		createFlipYField();
		createColorField();
		createImageField();
	}

	private void createImageField() {
		String[] labelNames = new String[]{Word.PROPERTY_IMAGE};
		image = Utility.createImageGridData(getContainer(), labelNames, this, UIElementPropertyType.IMAGE, 0);
	}

	private void createColorField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_COLOR);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		textColor = new Text(getContainer(), SWT.READ_ONLY | SWT.BORDER);
		textColor.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 2));
		
		textColor.setBackground(new Color(Display.getCurrent(), 0, 0, 0));
		
		Button setColor = new Button(getContainer(), SWT.PUSH);
		setColor.setText(Word.PROPERTY_SET_COLOR);
		setColor.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
	}

	private void createFlipYField() {
		String[] labelNames = new String[]{"", Word.PROPERTY_Y};
		checkboxFlipY = Utility.createCheckboxGridData4Columns(getContainer(), labelNames, this, UIElementPropertyType.FLIP_Y);
	}

	private void createFlipXField() {
		String[] labelNames = new String[]{Word.PROPERTY_FLIP, Word.PROPERTY_X};
		checkboxFlipX = Utility.createCheckboxGridData4Columns(getContainer(), labelNames, this, UIElementPropertyType.FLIP_X);
	}

	private void createRotationField() {
		String[] labelNames = new String[]{Word.PROPERTY_ROTATION};
		textRotation = Utility.createTextGridData4Columns(getContainer(), labelNames, true, Parameter.ROTATION_RANGE, 1, this, UIElementPropertyType.ROTATION);
	}
	

	@Override
	public void setObjectPropertiesToView(UIElement object) {
		super.setObjectPropertiesToView(object);
		
		CSprite obj = (CSprite)object;
		
		textRotation.setText(obj.getRotation() + "");
		textColor.setBackground(obj.getColor());
		checkboxFlipX.setSelection(obj.isFlipX());
		checkboxFlipY.setSelection(obj.isFlipY());
		image.redraw();
	}

	@Override
	public Point getDefaultSize() {
		return getContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}
}