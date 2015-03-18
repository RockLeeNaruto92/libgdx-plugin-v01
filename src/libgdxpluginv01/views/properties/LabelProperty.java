package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CLabel;
import libgdxpluginv01.models.uielements.UIElement;
import libgdxpluginv01.swt.custom.Align;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;

public class LabelProperty extends UIElementProperty {
	private static LabelProperty _instance;
	private Text textText;
	private Combo comboAlign;
	private Button checkboxWrap;
	private Button checkboxEllipsis;
	private Text textFontScaleX;
	private Text textFontScaleY;
	
	public LabelProperty(Composite parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	public static LabelProperty getInstance(Composite parent){
		if (_instance == null){
			_instance = new LabelProperty(parent);
		}
		
		return _instance;
	}

	@Override
	public void createOtherProperties() {
		createTextField();
		createAlignField();
		createWrapField();
		createFontScaleXField();
		createFontScaleYField();
		createEllipsisField();
		createStyleField();
	}

	private void createStyleField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_STYLE);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		Button button = new Button(getContainer(), SWT.PUSH);
		button.setText(Word.PROPERTY_SET_STYLE);
		button.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}

	private void createTextField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_TEXT);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		textText = new Text(getContainer(), SWT.BORDER);
		textText.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
		textText.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (getUielement() == null) return;
				
				CLabel obj = (CLabel)getUielement();
				
				obj.setText(textText.getText());
				
				Point size = obj.getDefaultSize();
				System.out.println(size);
				Rectangle bound = obj.getBound();
				
				bound.width = size.x;
				bound.height = size.y;
				
				obj.setBound(bound);
				
				obj.refresh();
				obj.redraw();
			}
		});
	}

	private void createAlignField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_ALIGN);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		comboAlign = new Combo(getContainer(), SWT.READ_ONLY);
		comboAlign.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
		comboAlign.setItems(Align.getStrings());
		comboAlign.select(0);
	}

	private void createWrapField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_WRAP);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		checkboxWrap = new Button(getContainer(), SWT.CHECK);
		checkboxWrap.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}

	private void createFontScaleXField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_FONT_SCALE);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(getContainer(), SWT.NONE);
		label.setText(Word.PROPERTY_X);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textFontScaleX = new Text(getContainer(), SWT.BORDER);
		textFontScaleX.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		
		Slider sliderX = new Slider(getContainer(), SWT.HORIZONTAL);
		sliderX.setMinimum(Parameter.FONT_SCALE_RANGE_X.x);
		sliderX.setMaximum(Parameter.FONT_SCALE_RANGE_X.y);
		sliderX.setIncrement(Parameter.SLIDER_STEP);
		sliderX.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
	}
	
	private void createFontScaleYField(){
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText("");
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(getContainer(), SWT.NONE);
		label.setText(Word.PROPERTY_Y);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textFontScaleY = new Text(getContainer(), SWT.BORDER);
		textFontScaleY.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		
		Slider sliderY = new Slider(getContainer(), SWT.HORIZONTAL);
		sliderY.setMinimum(Parameter.FONT_SCALE_RANGE_X.x);
		sliderY.setMaximum(Parameter.FONT_SCALE_RANGE_X.y);
		sliderY.setIncrement(Parameter.SLIDER_STEP);
		sliderY.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
	}
	

	private void createEllipsisField() {
		// TODO Auto-generated method stub
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_ELLIPSIS);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		checkboxEllipsis = new Button(getContainer(), SWT.CHECK);
		checkboxEllipsis.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
	}
	
	@Override
	public void setObjectPropertiesToView(UIElement object) {
		super.setObjectPropertiesToView(object);
		
		CLabel obj = (CLabel)object;
		
		textText.setText(obj.getText());
		textFontScaleX.setText(obj.getFontScaleX() + "");
		textFontScaleY.setText(obj.getFontScaleY() + "");
		comboAlign.select(Align.getAlignIndex(obj.getLabelAlign()));
		checkboxWrap.setSelection(obj.isWrap());
		checkboxEllipsis.setSelection(obj.isEllipsis());
	}

	@Override
	public Point getDefaultSize() {
		return getContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}

}
