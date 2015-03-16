package libgdxpluginv01.views;

import java.util.ArrayList;
import java.util.List;

import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.views.properties.ButtonProperty;
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
	private List<Property> properties;
	private Property view;
	private UIController uiController;
	private int current = 0;
	
	private Composite parent;
	
	public PropertyView() {
		uiController = UIController.getInstance();
		uiController.setPropertyView(this);
	}
	
	private List<Property> getPropertyInstances(){
		properties = new ArrayList<Property>();
		
		properties.add(EmptyProperty.getInstance(parent));
		properties.add(ButtonProperty.getInstance(parent));
		
		ButtonProperty.getInstance(parent).getRoot().setVisible(false);
		
		return properties;
	}
	
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FormLayout());
		view = EmptyProperty.getInstance(parent);
		
		setParent(parent);
		properties = getPropertyInstances();
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
		
		view.getRoot().setVisible(false);
		propertyView.getRoot().moveAbove(view.getRoot());
		view = propertyView;
		view.getRoot().setVisible(true);
		
		System.out.println("set view");
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}
}
