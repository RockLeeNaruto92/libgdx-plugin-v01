package libgdxpluginv01.views;

import java.util.ArrayList;
import java.util.List;

import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.views.properties.AnimationProperty;
import libgdxpluginv01.views.properties.ButtonProperty;
import libgdxpluginv01.views.properties.ButtonStyleProperty;
import libgdxpluginv01.views.properties.CheckboxProperty;
import libgdxpluginv01.views.properties.CheckboxStyleProperty;
import libgdxpluginv01.views.properties.EmptyProperty;
import libgdxpluginv01.views.properties.ImageProperty;
import libgdxpluginv01.views.properties.LabelProperty;
import libgdxpluginv01.views.properties.LabelStyleProperty;
import libgdxpluginv01.views.properties.Property;
import libgdxpluginv01.views.properties.SliderProperty;
import libgdxpluginv01.views.properties.SliderStyleProperty;
import libgdxpluginv01.views.properties.SpriteProperty;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class PropertyView extends ViewPart {
	private List<Property> properties;
	private Property view;
	private UIController uiController;
	
	private Composite parent;
	
	public PropertyView() {
		uiController = UIController.getInstance();
		uiController.setPropertyView(this);
	}
	
	private List<Property> getPropertyInstances(){
		properties = new ArrayList<Property>();
		
		properties.add(EmptyProperty.getInstance(parent));
		properties.add(ButtonProperty.getInstance(parent));
		properties.add(LabelProperty.getInstance(parent));
		properties.add(SliderProperty.getInstance(parent));
		properties.add(CheckboxProperty.getInstance(parent));
		properties.add(SpriteProperty.getInstance(parent));
		properties.add(ImageProperty.getInstance(parent));
		properties.add(AnimationProperty.getInstance(parent));
		properties.add(LabelStyleProperty.getInstance(parent, null));
		properties.add(ButtonStyleProperty.getInstance(parent, null));
		properties.add(CheckboxStyleProperty.getInstance(parent, null));
		properties.add(SliderStyleProperty.getInstance(parent, null));
		
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
	}

	@Override
	public void setFocus() {
	}
}
