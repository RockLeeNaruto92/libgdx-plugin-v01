package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CLabel extends UIElement{
	Label label;
	
	public CLabel(Composite root, Point location) {
		super(root, location);
		
		label = new Label(getContainer(), SWT.BORDER);
		label.setText(getName());
	}

	@Override
	public Point getDefaultSize() {
		// TODO Auto-generated method stub
		return Parameter.DEFAULT_LABEL_SIZE;
	}

	@Override
	public String getDefaultNamePattern() {
		// TODO Auto-generated method stub
		return Parameter.DEFAULT_LABEL_NAME_PATTERN;
	}
}
