package libgdxpluginv01.constant;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.graphics.Rectangle;
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
}
