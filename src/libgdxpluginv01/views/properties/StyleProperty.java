package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Word;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public abstract class StyleProperty extends Property{
	private ScrolledComposite root;
	private Composite container;
	private Object object;
	
	public StyleProperty(Composite parent, Object object){
		super(parent);
		setObject(object);
		createContainer(parent);
		createContents();
		setScrolledComposite();
		
		root.setVisible(true);
	}
	
	private void setScrolledComposite(){
		root.setContent(container);
		root.setAlwaysShowScrollBars(true);
		
		Point size = getDefaultSize();
		container.setSize(size);
		root.getParent().setRedraw(true);
	}
	
	public void createContainer(Composite parent){
		root = new ScrolledComposite(getRootContainer(), SWT.BORDER | SWT.V_SCROLL);
		FormData data = new FormData();
		
		root.setLayoutData(data);
		
		container = new Composite(root, SWT.NONE);
		container.setLayout(new GridLayout(Parameter.PROPERTY_COLUMN_NUM, false));
		
		root.setContent(container);
		root.setMinSize(getDefaultSize());
	}
	
	public abstract void createContents();
	
	private Point getDefaultSize(){
		return container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}

	@Override
	public Control getRoot() {
		// TODO Auto-generated method stub
		return root;
	}
	
	protected Composite createImageField(String text, final Image background) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		final Composite imgContainer = new Composite(container, SWT.BORDER);
		imgContainer.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH + Parameter.PROPERTY_COLUMN_3_WIDTH, Parameter.PROPERTY_COLUMN_1_WIDTH, 2));
		imgContainer.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				if (background == null){
					return;
				}
				
				Rectangle bound = background.getBounds();
				
				GridData data = (GridData)imgContainer.getLayoutData();
				
				e.gc.drawImage(background, 0, 0, bound.width, bound.height, 0, 0, data.widthHint, data.heightHint);
			}
		});
		
		Button button = new Button(container, SWT.PUSH);
		button.setText(Word.PROPERTY_SET);
		button.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
		
		return imgContainer;
	}
	
	protected Text createFontField(String text, String fontName) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		Text fontText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		fontText.setText(fontName);
		fontText.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 2));
		
		Button button = new Button(container, SWT.PUSH);
		button.setText(Word.PROPERTY_SET);
		button.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
		
		return fontText;
	}
	
	protected Text createColorField(String text, Color color) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		Text colorText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		if (color == null)
			color = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
		colorText.setBackground(color);
		colorText.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 2));
		
		Button button = new Button(container, SWT.PUSH);
		button.setText(Word.PROPERTY_SET);
		button.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
		
		return colorText;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
	
	public Object getObject(){
		return this.object;
	}
}
