package libgdxpluginv01.views;

import libgdxpluginv01.views.properties.LabelProperty;
import libgdxpluginv01.views.properties.UIElementProperty;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class PropertyView extends ViewPart {
	UIElementProperty view;
	
	public PropertyView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		view = new LabelProperty(parent);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

}
