package libgdxpluginv01.views;

import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.views.properties.EmptyProperty;
import libgdxpluginv01.views.properties.Property;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.part.ViewPart;

public class PropertyView extends ViewPart {
	private Property view;
	private UIController uiController;
	
	private Composite parent;
	
	public PropertyView() {
		uiController = UIController.getInstance();
		uiController.setPropertyView(this);
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FormLayout());
		view = EmptyProperty.getInstance(parent);
		
		setParent(parent);
	}
	
	public void setParent(Composite parent){
		this.parent = parent;
	}
	
	public Composite getParent(){
		return parent;
	}
	
	public void setView(Property propertyView){
		if (view == propertyView)
			return;
		
		System.out.println("hide");
		
		view.hide(true);
		view = propertyView;
		parent.layout();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}
}
