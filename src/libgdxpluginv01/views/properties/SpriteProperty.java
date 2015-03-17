package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CSprite;
import libgdxpluginv01.models.uielements.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;
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
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_IMAGE);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		image = new Composite(getContainer(), SWT.BORDER);
		image.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, Parameter.PROPERTY_COLUMN_2_WIDTH, 2));
//		image.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
		image.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				drawBackground(e, (CSprite)getUielement());
			}
		});
		
		Button setImage = new Button(getContainer(), SWT.PUSH);
		setImage.setText(Word.PROPERTY_SET_IMAGE);
		setImage.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
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
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText("");
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_Y);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		checkboxFlipY = new Button(getContainer(), SWT.CHECK);
		checkboxFlipY.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 2));
	}

	private void createFlipXField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_FLIP);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_X);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		checkboxFlipX = new Button(getContainer(), SWT.CHECK);
		checkboxFlipX.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 2));
	}

	private void createRotationField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_ROTATION);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		textRotation = new Text(getContainer(), SWT.BORDER);
		textRotation.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 2));
		
		Slider slider = new Slider(getContainer(), SWT.HORIZONTAL);
		slider.setMinimum(Parameter.LOCATION_RANGE_X.x);
		slider.setMaximum(Parameter.LOCATION_RANGE_X.y);
		slider.setIncrement(Parameter.SLIDER_STEP);
		slider.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
	}
	

	@Override
	public void setObjectPropertiesToView(UIElement object) {
		super.setObjectPropertiesToView(object);
		
		CSprite obj = (CSprite)object;
		
		textRotation.setText(obj.getRotation() + "");
		textColor.setBackground(obj.getColor());
		checkboxFlipX.setSelection(obj.isFlipX());
		checkboxFlipY.setSelection(obj.isFlipY());
	}
	
	private void drawBackground(PaintEvent e, CSprite object){
		if (object == null){
			return;
		}
		
//		e.gc.setAdvanced(true);
//		
//		if (!e.gc.getAdvanced()){
//			e.gc.drawText("Advanced graphics not supported", 30, 30, true);
//            return;
//		}
		
//		Transform transform = new Transform(Display.getCurrent());
		Rectangle bound = object.getImage().getBounds();
		
//		transform.setElements(-1, 0, 0, 1, 0 ,0);
//		e.gc.setTransform(transform);
		
		System.out.println(bound);
		System.out.println(getDrawImageArea());
		e.gc.drawImage(object.getImage(), 0, 0, bound.width, bound.height, 0, 0, -getDrawImageArea().x, getDrawImageArea().y);
//		transform.dispose();
	}
	
	private Point getDrawImageArea(){
		return new Point(Parameter.PROPERTY_COLUMN_2_WIDTH + Parameter.PROPERTY_COLUMN_3_WIDTH, Parameter.PROPERTY_COLUMN_2_WIDTH);
	}

	@Override
	public Point getDefaultSize() {
		return getContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}

}