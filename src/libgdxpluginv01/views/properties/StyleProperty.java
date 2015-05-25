package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Resources;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.constant.Word;
import libgdxpluginv01.swt.custom.BitmapFont;

import org.eclipse.swt.SWT;
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
		getRoot().setContent(container);
		getRoot().setAlwaysShowScrollBars(true);
		
		Point size = getDefaultSize();
		container.setSize(size);
		getRoot().setMinSize(size);
	}
	
	public void createContainer(Composite parent){
		container = new Composite(getRoot(), SWT.NONE);
		container.setLayout(new GridLayout(Parameter.PROPERTY_COLUMN_NUM, false));
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
				
				Image image = Resources.getImageByPath(imageFile);
				
				if (image == null){
					Resources.addImage(imageFile);
					image = Resources.getImageByPath(imageFile);
				}
				
				setPropertyToView(getObject(), new Object[]{image, new Integer(index)});
			}
		});
		
		return imgContainer;
	}
	
	protected Text createFontField(final BitmapFont font, String text, final int index) {
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
				
				BitmapFont font = Resources.getFontByPath(fontFile);
				
				if (font == null){
					Resources.addFont(fontFile);
					font = Resources.getFontByPath(fontFile);
				}
				
				setPropertyToView(getObject(), new Object[]{fontFile, new Integer(index)});
			}
		});
		
		return fontText;
	}
	
	protected Text createColorField(String text, Color color, final int index) {
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
				
				Color color = new Color(Display.getCurrent(), selectColor);
				setPropertyToView(getObject(), new Object[]{color, new Integer(index)});
				colorText.setBackground(color);
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
