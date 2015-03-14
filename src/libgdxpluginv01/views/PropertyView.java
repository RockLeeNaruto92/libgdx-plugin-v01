package libgdxpluginv01.views;

import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.views.properties.EmptyProperty;
import libgdxpluginv01.views.properties.Property;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class PropertyView extends ViewPart {
	private Property view;
	private UIController uiController;
	
	public PropertyView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		view = EmptyProperty.getInstance(parent);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

}
