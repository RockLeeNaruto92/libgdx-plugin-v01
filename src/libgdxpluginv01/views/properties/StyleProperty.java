package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.swt.custom.BitmapFont;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
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
		
		getRoot().setVisible(false);
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
//		FormData data = new FormData();
//		
//		root.setLayoutData(data);
		
		container = new Composite(root, SWT.NONE);
		container.setLayout(new GridLayout(Parameter.PROPERTY_COLUMN_NUM, false));
		
		root.setContent(container);
		root.setMinSize(getDefaultSize());
	}
	
	public abstract void createContents();
	public abstract Image getImageFromIndex(int index);
	
	private Point getDefaultSize(){
		return container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}

	protected Composite createImageField(String text, final Image background, final int index) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		final Composite imgContainer = new Composite(container, SWT.BORDER);
		imgContainer.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH + Parameter.PROPERTY_COLUMN_3_WIDTH, Parameter.PROPERTY_COLUMN_1_WIDTH, 2));
		imgContainer.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				Image background = getImageFromIndex(index);
				
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
		button.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				String imageFile = Utility.openSelectFileDialog(container, new String[]{"*.jpg", "*.png"});
				
				if (imageFile == null) return;
				
				Image image = new Image(Display.getCurrent(), imageFile);
				setPropertyToView(getObject(), new Object[]{image, new Integer(index)});
			}
		});
		
		return imgContainer;
	}
	
	protected Text createFontField(final BitmapFont font, String text) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		final Text fontText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		fontText.setText(font == null ? "null" : font.getName());
		fontText.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 2));
		
		Button button = new Button(container, SWT.PUSH);
		button.setText(Word.PROPERTY_SET);
		button.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
		button.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				String fontFile = Utility.openSelectFileDialog(container.getShell(), new String[]{"*.fnt"});
				
				if (fontFile == null) return;
				
				setPropertyToView(getObject(), new Object[]{fontFile});
			}
		});
		
		return fontText;
	}
	
	protected Text createColorField(String text, Color color) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		final Text colorText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		if (color == null)
			color = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
		colorText.setBackground(color);
		colorText.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 2));
		
		Button button = new Button(container, SWT.PUSH);
		button.setText(Word.PROPERTY_SET);
		button.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
		button.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				RGB selectColor = Utility.openChooseColorDialog(container, colorText.getBackground().getRGB());
				
				if (selectColor == null) return;
				
				setPropertyToView(getObject(), new Object[]{selectColor});
				colorText.setBackground(new Color(Display.getCurrent(), selectColor));
			}
		});
		
		return colorText;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
	
	public Object getObject(){
		return this.object;
	}
	
	public abstract void setPropertyToView(Object object, Object[] datas);
}
