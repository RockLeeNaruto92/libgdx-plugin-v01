package libgdxpluginv01.views.properties;

import libgdxpluginv01.models.uielements.UIElement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public abstract class UIElementProperty {
	private ScrolledComposite root;
	private Composite container;
	private UIElement uielement;
	Text nameText;

	public UIElementProperty(Composite parent) {
		createContainer(parent);
		createContents();
		setScrolledComposite();
		
	}
	
	private void setScrolledComposite(){
		root.setContent(container);
		root.setExpandHorizontal(true);
		root.setAlwaysShowScrollBars(true);
		
		Point size = getDefaultSize();
		container.setSize(size);
		root.setMinSize(size);
		root.getParent().setRedraw(true);
	} 

	private void createContainer(Composite parent) {
		root = new ScrolledComposite(parent, SWT.BORDER | SWT.V_SCROLL);
		
		container = new Composite(root, SWT.NONE);
		container.setLayout(new GridLayout(2, true));
	}

	public abstract void createContents();
	public abstract Point getDefaultSize();
	
	public Composite getContainer() {
		return container;
	}

	public void setContainer(ScrolledComposite container) {
		this.container = container;
	}

	public UIElement getUielement() {
		return uielement;
	}

	public void setUielement(UIElement uielement) {
		this.uielement = uielement;
	}

	public void createNameField(){
		System.out.println("Create name field");
		GridData data = new GridData(GridData.FILL);
		Label name = new Label(container, SWT.NONE);
		
		name.setText("Name");
		name.setLayoutData(data);
		
		data = new GridData(GridData.FILL);
		nameText = new Text(container, SWT.NONE);
		nameText.setText("");
		
		
		nameText.setLayoutData(data);
	}
}
