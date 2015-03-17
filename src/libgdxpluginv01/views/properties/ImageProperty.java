package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CImage;
import libgdxpluginv01.swt.custom.Align;
import libgdxpluginv01.swt.custom.Scaling;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ImageProperty extends UIElementProperty {
	private static ImageProperty _instance;
	private Composite image;
	private Combo comboScaling, comboAlign;

	public ImageProperty(Composite parent) {
		super(parent);
	}
	
	public static ImageProperty getInstance(Composite parent){
		if (_instance == null){
			_instance = new ImageProperty(parent);
		}
		
		return _instance;
	}

	@Override
	public void createOtherProperties() {
		createAlignField();
		createScalingField();
		createImageField();
	}

	private void createImageField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_IMAGE);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		image = new Composite(getContainer(), SWT.BORDER);
		image.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, Parameter.PROPERTY_COLUMN_2_WIDTH, 1));
		image.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				CImage obj = (CImage)getUielement();
				
				if (obj == null) return;
				if (obj.getImage() == null) return;
				Rectangle bound = obj.getImage().getBounds();
				GridData data = (GridData)(image.getLayoutData());
				
				e.gc.drawImage(obj.getImage(), 0, 0, bound.width, bound.height, 0, 0, data.widthHint, data.heightHint);
			}
		});
		
		Button setImage = new Button(getContainer(), SWT.PUSH);
		setImage.setText(Word.PROPERTY_SET_IMAGE);
		setImage.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 2));
	}

	private void createScalingField() {
		Label label = new Label(getContainer(), SWT.NONE);
		
		label.setText(Word.PROPERTY_SCALING);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		comboScaling = new Combo(getContainer(), SWT.READ_ONLY);
		comboScaling.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
		comboScaling.setItems(Scaling.getStrings());
		comboScaling.select(0);
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

	@Override
	public Point getDefaultSize() {
		return getContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}
}
