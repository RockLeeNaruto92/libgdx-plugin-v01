package libgdxpluginv01.views.properties;

import java.util.ArrayList;
import java.util.List;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CAnimation;
import libgdxpluginv01.swt.custom.PlayMode;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;

public class AnimationProperty extends UIElementProperty {
	private static AnimationProperty _instance;
	private Text textOriginX, textOriginY;
	private Text textRotation;
	private Text textFrameDuration;
	private Text textCount;
	private Combo comboPlayMode;
	private List<Composite> textFrames;

	public AnimationProperty(Composite parent) {
		super(parent);
	}
	
	public static AnimationProperty getInstance(Composite parent){
		if (_instance == null){
			_instance = new AnimationProperty(parent);
		}
		
		return _instance;
	}

	@Override
	public void createOtherProperties() {
		createOriginXField();
		createOriginYField();
		createRotationField();
		createFrameDurationField();
		createPlayModeField();
		createCountField();
		createFramesField();
	}

	private void createFramesField() {
		textFrames = new ArrayList<Composite>();
		
		CAnimation obj = (CAnimation)getUielement();
		
		if (obj == null){
			System.out.println("obj null");
			return;
		}
		
		for (int i = 0; i < obj.getCount(); i++) {
			textFrames.add(createAFrameField());
		}
	}
	
	private Composite createAFrameField(){
		Composite composite = new Composite(getContainer(), SWT.NONE);
		composite.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH + Parameter.PROPERTY_COLUMN_2_WIDTH + Parameter.PROPERTY_COLUMN_3_WIDTH + Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 3));
		composite.setLayout(new GridLayout(Parameter.PROPERTY_COLUMN_NUM, false));
		
		Label label = new Label(composite, SWT.NONE);
		
		label.setText("");
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(composite, SWT.NONE);
		label.setText(Word.PROPERTY_FRAME);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		final Text text = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		text.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		
		Button button = new Button(composite, SWT.PUSH);
		button.setText(Word.PROPERTY_SET);
		button.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
		
		return composite;
	}

	private void createCountField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_KEY_FRAMES);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(getContainer(), SWT.NONE);
		label.setText(Word.PROPERTY_COUNT);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		textCount = new Text(getContainer(), SWT.BORDER);
		textCount.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 2));
		textCount.setText("0");
		
		textCount.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (getUielement() == null) return;
				
				CAnimation obj = (CAnimation)getUielement();
				if (isValidCount(textCount.getText())){
					// create or remove frame text
					int value = Integer.parseInt(textCount.getText());
					
					if (value > obj.getCount()){
						for (int i = 0; i < value - obj.getCount(); i++){
							Composite frame = createAFrameField();
							textFrames.add(frame);
						}
					} else {
						for (int i = 0; i < obj.getCount() - value; i++){
							Composite frame = textFrames.remove(textFrames.size() - 1);
							frame.dispose();
						}
					}
					
					getContainer().layout();
					getContainer().setSize(getDefaultSize());
					
					// set obj count
					obj.setCount(value);
					
				} else {
					MessageDialog.openError(obj.getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_COUNT);
					textCount.setText(obj.getCount() + "");
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void createPlayModeField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_PLAY_MODE);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		comboPlayMode = new Combo(getContainer(), SWT.READ_ONLY);
		comboPlayMode.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
		comboPlayMode.setItems(PlayMode.getStrings());
		comboPlayMode.select(0);
	}

	private void createFrameDurationField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_FRAME_DURATION);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		textFrameDuration = new Text(getContainer(), SWT.BORDER);
		textFrameDuration.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 2));
		
		textFrameDuration.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (isValidFrameDuration(textFrameDuration.getText())){
					
				} else {
					MessageDialog.openError(getUielement().getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_Y);
					textFrameDuration.setText(getUielement().getBound().x + "");
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				
			}
		});
		
		Slider slider = new Slider(getContainer(), SWT.HORIZONTAL);
		slider.setMinimum(Parameter.LOCATION_RANGE_X.x);
		slider.setMaximum(Parameter.LOCATION_RANGE_X.y);
		slider.setIncrement(Parameter.SLIDER_STEP);
		slider.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
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

	private void createOriginYField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText("");
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(getContainer(), SWT.NONE);
		label.setText(Word.PROPERTY_Y);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textOriginY = new Text(getContainer(), SWT.BORDER);
		textOriginY.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		textOriginY.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (getUielement() == null) return;
				
				if (isValidOriginY(textOriginY.getText())){
				} else {
					MessageDialog.openError(getUielement().getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_Y);
					textOriginY.setText(((CAnimation)getUielement()).getOrigin().y + "");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		
		Slider slider = new Slider(getContainer(), SWT.HORIZONTAL);
		slider.setMinimum(Parameter.LOCATION_RANGE_X.x);
		slider.setMaximum(Parameter.LOCATION_RANGE_X.y);
		slider.setIncrement(Parameter.SLIDER_STEP);
		slider.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
		
	}

	private void createOriginXField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_ORIGIN);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(getContainer(), SWT.NONE);
		label.setText(Word.PROPERTY_X);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		textOriginX = new Text(getContainer(), SWT.BORDER);
		textOriginX.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, 1));
		textOriginX.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (getUielement() == null) return;
				
				
				if (isValidOriginX(textOriginX.getText())){
				} else {
					MessageDialog.openError(getUielement().getContainer().getShell(), Word.ERROR, Word.ERROR_INVALID_X);
					textOriginX.setText(((CAnimation)getUielement()).getOrigin().x + "");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		
		Slider slider = new Slider(getContainer(), SWT.HORIZONTAL);
		slider.setMinimum(Parameter.LOCATION_RANGE_X.x);
		slider.setMaximum(Parameter.LOCATION_RANGE_X.y);
		slider.setIncrement(Parameter.SLIDER_STEP);
		slider.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
	}

	@Override
	public Point getDefaultSize() {
		// TODO Auto-generated method stub
		return getContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}
	
	private boolean isValidOriginX(String x){
		return isIntegerNum(x);
	}
	
	private boolean isValidOriginY(String y){
		return isIntegerNum(y);
	}
	
	private boolean isValidFrameDuration(String time){
		return isFloatNum(time);
	}
	
	private boolean isValidCount(String count){
		return isIntegerNum(count);
	}
}
