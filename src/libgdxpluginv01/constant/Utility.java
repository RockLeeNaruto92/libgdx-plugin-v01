package libgdxpluginv01.constant;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import libgdxpluginv01.models.uielements.UIElement;
import libgdxpluginv01.views.properties.Error;
import libgdxpluginv01.views.properties.UIElementProperty;
import libgdxpluginv01.views.properties.UIElementPropertyType;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
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
	
	public static Text createTextGridData4Columns(Composite parent, String[] labelNames, boolean hasSlider, Point range, int step, UIElementProperty property, final UIElementPropertyType type){
		Label label = new Label(parent, SWT.NONE);
		label.setText(labelNames[0]);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_1_WIDTH, 0, 1));
		
		label = new Label(parent, SWT.NONE);
		label.setText(labelNames[1]);
		label.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_2_WIDTH, 0, 1));
		
		final Text text = new Text(parent, SWT.BORDER);
		text.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_3_WIDTH, 0, hasSlider ? 1 : 2));
		
		if (hasSlider){
			// create slider
			final Slider slider = new Slider(parent, SWT.HORIZONTAL);
			slider.setLayoutData(createLayoutData(Parameter.PROPERTY_COLUMN_4_WIDTH, 0, 1));
			
			slider.setMaximum(range.x);
			slider.setMinimum(range.y);
			slider.setIncrement(step);
			
			// add listener for slider
			slider.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event arg0) {
					text.setText(slider.getSelection() + "");
				}
			});
			
			addListenerToText(text, slider, property, type);
			
		} else 
			addListenerToText(text, null, property, type);
		
		return text;
	}
	
	private static Text addListenerToText(final Text text, final Slider slider, final UIElementProperty property, final UIElementPropertyType type){
		text.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				UIElement object = property.getUielement();
				Error error = object.isValidProperty(type, text.getText());
				if (error == Error.VALID){
					object.setPropertyValue(type, text.getText());
					if (slider != null)
						slider.setSelection((int)(object.getPropertyValue(type)));
					
					object.refresh();
				}else {
					displayErrorMessage(error);
					text.setText(object.getPropertyValue(type) + "");
				}
			}
		});
		
		return text;
	}
	
	private static void displayErrorMessage(Error error){
		
	}
	
	public static boolean isInteger(String value){
		try {
			Integer.parseInt(value);
		} catch (Exception e){
			return false;
		}
		
		return true;
	}
}
