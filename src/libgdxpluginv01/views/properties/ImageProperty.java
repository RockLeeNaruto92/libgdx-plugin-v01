package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.models.uielements.CImage;
import libgdxpluginv01.models.uielements.UIElement;
import libgdxpluginv01.swt.custom.Align;
import libgdxpluginv01.swt.custom.Scaling;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

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
			System.out.println("Get ImageProperty Instance");
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
		String[] labelNames = {Word.PROPERTY_IMAGE};
		image = Utility.createImageGridData(getContainer(), labelNames, this, UIElementPropertyType.IMAGE, 0);
	}

	private void createScalingField() {
		String[] labelNames = {Word.PROPERTY_SCALING};
		comboScaling = Utility.createComboGridData2Columns(getContainer(), labelNames, Scaling.getStrings(), this, UIElementPropertyType.SCALING);
	}

	private void createAlignField() {
		String[] labelNames = {Word.PROPERTY_ALIGN};
		comboAlign = Utility.createComboGridData2Columns(getContainer(), labelNames, Align.getStrings(), this, UIElementPropertyType.ALIGN);
	}

	@Override
	public void setObjectPropertiesToView(UIElement object) {
		super.setObjectPropertiesToView(object);
		
		CImage obj = (CImage)getUielement();
		if (obj == null) return;
		
		comboAlign.select(Align.getAlignIndex(obj.getAlign()));
		comboScaling.select(Scaling.getScalingIndex(obj.getScaling()));
		image.redraw();
	}

	@Override
	public Point getDefaultSize() {
		return getContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}
}
