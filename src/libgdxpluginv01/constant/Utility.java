package libgdxpluginv01.constant;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.models.uielements.UIElement;
import libgdxpluginv01.views.properties.Error;
import libgdxpluginv01.views.properties.UIElementProperty;
import libgdxpluginv01.views.properties.UIElementPropertyType;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;

public class Utility {
	public static File getFile(String fileName){
		Bundle bundle = Platform.getBundle(Plugin.BUNDLE_NAME);
		URL fileURL = bundle.getEntry(fileName);
		File file = null;
		
		try {
			file = new File(FileLocator.resolve(fileURL).toURI());
		} catch (URISyntaxException e) {
			System.out.println("URI Syntax Error on getFile( " + fileName + " )");
		} catch (IOException e) {
			System.out.println("File not found: " + fileName);
		}
		
		return file;
	}
	
	public static Object[] removeArrayElement(Object[] array, int index){
		System.arraycopy(array, index + 1, array, index, array.length - 1);
		
		return array;
	}
	
	public static Rectangle validateRectangle(Rectangle rect){
		if (rect.width < 0){
			rect.x = rect.x + rect.width;
			rect.width = -rect.width;
		}
		
		if (rect.height < 0){
			rect.y = rect.y + rect.height;
			rect.height = -rect.height;
		}
		
		return rect;
	}
	
	public static String openSelectFileDialog(Composite parent, String[] filters){
		FileDialog fd = new FileDialog(parent.getShell(), SWT.OPEN);

		fd.setText(Word.OPEN);
		fd.setFilterPath("C:/");
		fd.setFilterExtensions(filters);
		
		return fd.open();
	}
	
	public static RGB openChooseColorDialog(Composite parent, RGB oldColor){
		ColorDialog dlg = new ColorDialog(parent.getShell());
		
		dlg.setRGB(oldColor);
		dlg.setText(Word.CHOOSE_COLOR);
		
		return dlg.open(); 
	}
	
	public static GridData createLayoutData(int width, int height, int horizontalSpan){
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.CENTER;
		data.widthHint = width;
		if (height != 0)
			data.heightHint = height;
		data.horizontalSpan = horizontalSpan;
		
		return data;
	}
	
	public static Text createTextGridData4Columns(Composite parent, String[] labelNames, boolean hasSlider, Point range, final float step, final UIElementProperty property, final UIElementPropertyType type){
		Label label = new Label(parent, SWT.NONE);
		label.setText(labelNames[0]);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		if (labelNames.length > 1){
			label = new Label(parent, SWT.NONE);
			label.setText(labelNames[1]);
			label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		}
		
		int horizontalSpan = (hasSlider) ? 3 - labelNames.length : 4 - labelNames.length;
		final Text text = new Text(parent, SWT.BORDER);
		text.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, horizontalSpan));
		
		if (hasSlider){
			// create slider
			final Slider slider = new Slider(parent, SWT.HORIZONTAL);
			slider.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
			
			slider.setMaximum((int)(range.x / step));
			slider.setMinimum((int)(range.y / step));
			slider.setIncrement((int)step);
			
			// add listener for slider
			slider.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event arg0) {
					text.setText((slider.getSelection() * step) + "");
					property.getUielement().redraw();
				}
			});
			
			addListenerToText(text, slider, step, property, type, range);
			
		} else 
			addListenerToText(text, null, step, property, type, range);
		
		return text;
	}
	
	private static Text addListenerToText(final Text text, final Slider slider, final float step, final UIElementProperty property, final UIElementPropertyType type, final Object infor){
		text.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				UIElement object = property.getUielement();
				Error error = object.isValidProperty(type, text.getText());
				if (error == Error.VALID){
					object.setPropertyValue(type, text.getText());
					if (slider != null){
						float value = (float)(object.getPropertyValue(type));
						int selection = (int) (value / step);
						slider.setSelection(selection);
					}
					
					object.refresh();
					object.redraw();
				}else {
					displayErrorMessage(error, infor);
					text.setText(object.getPropertyValue(type) + "");
				}
			}
		});
		
		return text;
	}
	
	private static void displayErrorMessage(Error error, Object infor){
		Shell shell = Display.getCurrent().getActiveShell();
		
		switch (error) {
		case LOCATION_X_IS_STRING:
			MessageDialog.openError(shell, Word.ERROR, error.toString(null));
			break;
		case LOCATION_X_OUT_RANGE:
			MessageDialog.openError(shell, Word.ERROR, error.toString((Point)infor));
			break;

		default:
			break;
		}
	}
	
	public static Button createCheckboxGridData4Columns(Composite parent, String[] labelNames, final UIElementProperty property, final UIElementPropertyType type){
		Label label = new Label(parent, SWT.NONE);
		label.setText(labelNames[0]);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		if (labelNames.length > 1){
			label = new Label(parent, SWT.NONE);
			label.setText(labelNames[1]);
			label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		}
		
		int horizontalSpan = 4 - labelNames.length;
		final Button checkbox = new Button(parent, SWT.CHECK);
		checkbox.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, horizontalSpan));
		checkbox.setSelection(true);
		checkbox.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				UIElement obj = property.getUielement();
				
				if (obj == null) return;
				obj.setPropertyValue(type, checkbox.getSelection());
				obj.redraw();
			}
		});
		
		return checkbox;
	}
	
	public static Combo createComboGridData2Columns(Composite parent, String[] labelNames, String[] items, final UIElementProperty property, final UIElementPropertyType type){
		for (String string : labelNames) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(string);
			label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		} 
		
		final Combo combo = new Combo(parent, SWT.READ_ONLY);
		combo.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 4 - labelNames.length));
		combo.setItems(items);
		combo.select(0);
		combo.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				UIElement obj = property.getUielement();
				
				if (obj == null) return;
				obj.setPropertyValue(type, combo.getSelectionIndex());
				obj.redraw();
			}
		});
		
		return combo;
	}
	
	public static Composite createImageGridData(final Composite parent, String[] labelNames, final UIElementProperty property, final UIElementPropertyType type, final int imgIndex){
		for (String string : labelNames) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(string);
			label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		}
		
		final Composite composite = new Composite(parent, SWT.BORDER);
		final GridData data = createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, Parameter.PROPERTY_COLUMN_2_WIDTH, 1);
		composite.setLayoutData(data);
		composite.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				UIElement obj = property.getUielement();
				
				if (obj == null) return;
				Image image = obj.getImageByIndex(imgIndex);
				if (image == null) return;
				
				e.gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, data.widthHint, data.heightHint);
			}
		});
		
		Button button = new Button(parent, SWT.PUSH);
		button.setText(Word.PROPERTY_SET_IMAGE);
		button.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 2));
		
		button.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event e) {
				String imageFile = Utility.openSelectFileDialog(parent, new String[]{"*.jpg", "*.png"});
				
				if (imageFile == null) return;
				
				Image image = Resources.getImageByPath(imageFile);
				
				if (image == null){
					Resources.addImage(imageFile);
					image = Resources.getImageByPath(imageFile);
				}
				
				// set image property to ui element
				UIElement element = property.getUielement();
				if (element == null) return;
				
				element.setPropertyValue(type, new Object[]{image, imgIndex});
				element.redraw();
				composite.redraw();
			}
		});
		
		return composite;
	}
	
	public static void createSetStyleButton(Composite parent, final UIElementProperty property){
		Label label = new Label(parent, SWT.NONE);
		label.setText(Word.PROPERTY_STYLE);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		Button setStyleBtn = new Button(parent, SWT.PUSH);
		setStyleBtn.setText(Word.PROPERTY_SET_STYLE);
		setStyleBtn.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 3));
		setStyleBtn.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				UIElement obj = property.getUielement();
				
				if (obj == null) return;
				
				UIController uiController = obj.getUiController();
				uiController.setPropertyView(obj, true);
			}
		});
	}
	
	public static ImageData flip(ImageData srcData, boolean vertical) {
		int bytesPerPixel = srcData.bytesPerLine / srcData.width;
		int destBytesPerLine = srcData.width * bytesPerPixel;
		byte[] newData = new byte[srcData.data.length];
		for (int srcY = 0; srcY < srcData.height; srcY++) {
			for (int srcX = 0; srcX < srcData.width; srcX++) {
				int destX = 0, destY = 0, destIndex = 0, srcIndex = 0;
				if (vertical) {
					destX = srcX;
					destY = srcData.height - srcY - 1;
				} else {
					destX = srcData.width - srcX - 1;
					destY = srcY;
				}
				destIndex = (destY * destBytesPerLine)
						+ (destX * bytesPerPixel);
				srcIndex = (srcY * srcData.bytesPerLine)
						+ (srcX * bytesPerPixel);
				System.arraycopy(srcData.data, srcIndex, newData, destIndex,
						bytesPerPixel);
			}
		}
		// destBytesPerLine is used as scanlinePad to ensure that no padding is
		// required
		return new ImageData(srcData.width, srcData.height, srcData.depth,
				srcData.palette, srcData.scanlinePad, newData);
	}
	
	public static boolean isInteger(String value){
		try {
			Integer.parseInt(value);
		} catch (Exception e){
			return false;
		}
		
		return true;
	}
	
	public static boolean isFloat(String value){
		try {
			Float.parseFloat(value);
		} catch (Exception e){
			return false;
		}
		
		return true;
	}
}
