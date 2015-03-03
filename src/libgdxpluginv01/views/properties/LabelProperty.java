package libgdxpluginv01.views.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class LabelProperty extends UIElementProperty{
	
	public LabelProperty(Composite parent) {
		super(parent);
	}

	@Override
	public void createContents() {
		createNameField();
	}

	@Override
	public Point getDefaultSize() {
		return getContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}

}
