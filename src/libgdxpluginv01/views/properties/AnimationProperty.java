package libgdxpluginv01.views.properties;

import java.util.ArrayList;
import java.util.List;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CAnimation;
import libgdxpluginv01.models.uielements.UIElement;
import libgdxpluginv01.swt.custom.PlayMode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
			System.out.println("Get AnimationProperty Instance");
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
		
		for (int i = 0; i < 4; i++){
			textFrames.add(createAFrameField(i));
		}
	}
	
	private Composite createAFrameField(int index){
		Composite composite = new Composite(getContainer(), SWT.NONE);
		composite.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH + Parameter.PROPERTY_COLUMN_2_WIDTH + Parameter.PROPERTY_COLUMN_3_WIDTH + Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 4));
		composite.setLayout(new GridLayout(Parameter.PROPERTY_COLUMN_NUM, false));
		
		String[] labelNames = {"", Word.PROPERTY_FRAME + " " + index};
		Utility.createImageGridData(composite, labelNames, this, UIElementPropertyType.KEY_FRAMES, index);
		textFrames.add(composite);
		
		return composite;
	}

	private void createCountField() {
		String[] labelNames = new String[]{Word.PROPERTY_FRAME, Word.PROPERTY_COUNT};
		textCount = Utility.createTextGridData4Columns(getContainer(), labelNames, false, null, 0, this, UIElementPropertyType.COUNT);
	}

	private void createPlayModeField() {
		String[] labelNames = new String[]{Word.PROPERTY_PLAY_MODE};
		comboPlayMode = Utility.createComboGridData2Columns(getContainer(), labelNames, PlayMode.getStrings(), this, UIElementPropertyType.PLAY_MODE);
	}

	private void createFrameDurationField() {
		String[] labelNames = new String[]{Word.PROPERTY_FRAME_DURATION};
		textFrameDuration = Utility.createTextGridData4Columns(getContainer(), labelNames, false, null, 0, this, UIElementPropertyType.FRAME_DURATION);
	}

	private void createRotationField() {
		String[] labelNames = new String[]{Word.PROPERTY_ROTATION};
		textRotation = Utility.createTextGridData4Columns(getContainer(), labelNames, false, null, 0, this, UIElementPropertyType.ROTATION);
	}

	private void createOriginYField() {
		String[] labelNames = new String[]{"", Word.PROPERTY_Y};
		textOriginY = Utility.createTextGridData4Columns(getContainer(), labelNames, false, null, 1, this, UIElementPropertyType.ORIGIN_Y);
	}

	private void createOriginXField() {
		String[] labelNames = new String[]{Word.PROPERTY_ORIGIN, Word.PROPERTY_X};
		textOriginX = Utility.createTextGridData4Columns(getContainer(), labelNames, false, null, 1, this, UIElementPropertyType.ORIGIN_X);
	}

	@Override
	public Point getDefaultSize() {
		return getContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}

	@Override
	public void setObjectPropertiesToView(UIElement object) {
		super.setObjectPropertiesToView(object);
		
		CAnimation anim = (CAnimation)object;
		
		textOriginX.setText(anim.getOrigin().x + "");
		textOriginY.setText(anim.getOrigin().y + "");
		textRotation.setText(anim.getRotation() + "");
		textFrameDuration.setText(anim.getFrameDuration() + "");
		comboPlayMode.select(PlayMode.getPlayModeIndex(anim.getPlayMode()));
		textCount.setText(anim.getCount() + "");
		
		// update frames
		// 1. remove all old frames
		System.out.println("Remove frames " + textFrames.size());
		while (!textFrames.isEmpty()) textFrames.remove(0).dispose();
		// 2. add new frames
		List<Image> frames = anim.getKeyFrames();
		for (int i = 0; i < frames.size(); i++){
//			Control[] changed = new Control[]{createAFrameField(i)};
//			textFrames.add((Composite)changed[0]);
//			System.out.println("Create new frame!");
//			getContainer().layout(changed);
		}
		System.out.println("Add frames " + textFrames.size() );
	}
}
