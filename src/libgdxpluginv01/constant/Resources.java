package libgdxpluginv01.constant;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import libgdxpluginv01.swt.custom.BitmapFont;

public class Resources {
	private static ArrayList<String> fontsPath = new ArrayList<String>();
	private static ArrayList<String> imagesPath = new ArrayList<>();
	private static ArrayList<BitmapFont> fonts = new ArrayList<>();
	private static ArrayList<Image> images = new ArrayList<>();
	
	public static void addFont(String fontPath){
		if (fontsPath.contains(fontPath)) return;
		fontsPath.add(fontPath);
		fonts.add(new BitmapFont(Display.getCurrent(), fontPath));
	}
	
	public static void removeFont(String fontPath){
		fonts.remove(fontsPath.indexOf(fontPath));
		fontsPath.remove(fontPath);
	}

	public static void addImage(String imgPath){
		if (imagesPath.contains(imgPath)) return;
		imagesPath.add(imgPath);
		images.add(new Image(Display.getCurrent(), imgPath));
	}
	
	public static void removeImage(String imgPath){
		images.remove(imagesPath.indexOf(imgPath));
		imagesPath.remove(imgPath);
	}
	
	public static Image getImageByPath(String imgPath){
		if (imagesPath.indexOf(imgPath) == -1) return null;
		
		return images.get(imagesPath.indexOf(imgPath));
	}
	
	public static BitmapFont getFontByPath(String fontPath){
		if (fontsPath.indexOf(fontPath) == -1) return null;
		
		return fonts.get(fonts.indexOf(fontPath));
	}
	
	public static String getPathOfImage(Image image){
		if (!images.contains(image)) return null;
		
		return imagesPath.get(images.indexOf(image));
	}
	
	public static String getPathOfFont(BitmapFont font){
		if (!fonts.contains(font)) return null;
		
		return fontsPath.get(fonts.indexOf(font));
	}
}
