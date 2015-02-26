package libgdxpluginv01.constant;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
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
}
