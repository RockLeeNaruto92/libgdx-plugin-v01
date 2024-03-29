package libgdxpluginv01.constant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import libgdxpluginv01.swt.custom.BitmapFont;
import libgdxpluginv01.wizards.Constant;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Resources {
	private IProject project;
	private ArrayList<String> fontsPath = new ArrayList<String>();
	private ArrayList<String> imagesPath = new ArrayList<>();
	private ArrayList<BitmapFont> fonts = new ArrayList<>();
	private ArrayList<Image> images = new ArrayList<>();
	private ArrayList<String> imagesFileName = new ArrayList<>();
	private ArrayList<String> fontsFileName = new ArrayList<>();
	
	private static IProject currentProject;
	private static ArrayList<Resources> pluginResources = new ArrayList<>();
	
	public Resources(IProject project){
		this.project = project;
	}
	
	public static void setCurrentProject(IProject project){
		currentProject = project;
	}
	
	public static Resources getResources(IProject project){
		for (Resources res : pluginResources) {
			if (res.project == project){
				System.out.println("Found: " + res.project.getName());
				return res;
			}
		}
		
		System.out.println("Not found");
		return null;
	}
	
	private static Resources addNewResources(IProject project){
		Resources res = new Resources(project);
		pluginResources.add(res);
		
		System.out.println("Add new resources: " + project.getName());
		return res;
	}
	
	public static void addFont(IProject project, String fontPath){
		Resources res = getResources(project);
		
		if (res == null)
			res = addNewResources(project);
		
		if (isExist(project, fontPath, "font")) return;
		// Neu fontPath chua co trong fontsFilePath va chua co trong fontsFileName cua resources
		
		String newFontPath = getAndroidProjectPath(project) + "/assets/fonts/" + getName(fontPath);
		
		boolean isExistedInAndroid = new File(newFontPath).exists();
		System.out.println("Exised in android? : " + isExistedInAndroid);
		
		if (res.fontsFileName.contains(newFontPath) && !isExistedInAndroid){
			// rename of choosed font
			// newFont.fnt ==> newFont1.fnt
			newFontPath = newFontPath.substring(0, newFontPath.lastIndexOf(".")) + 1 + newFontPath.substring(newFontPath.lastIndexOf('.'));
		}
		
		System.out.println("Font name: " + newFontPath );
		
		// copy fontPath --> newFontPath
		try {
			if (!isExistedInAndroid){
				FileUtils.copyFile(new File(fontPath), new File(newFontPath));
			
				String imgFontPath = fontPath.substring(0, fontPath.lastIndexOf('.')) + ".png";
				String newImgFontPath = newFontPath.substring(0, newFontPath.lastIndexOf('.')) + ".png";
			
				FileUtils.copyFile(new File(imgFontPath), new File(newImgFontPath));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		res.fontsFileName.add(newFontPath);
		res.fontsPath.add(fontPath);
		res.fonts.add(new BitmapFont(Display.getCurrent(), newFontPath));
		
		System.out.println("Add new font: " + fontPath);
	}
	
	private static String getName(String fullPath){
		String name = fullPath.substring(fullPath.lastIndexOf('/') + 1);
		
		if (name == fullPath)
			name = fullPath.substring(fullPath.lastIndexOf('\\') + 1);
		return name;
	}
	
	public static void addFont(String fontPath){
		// get current project
		addFont(getCurrentProject(), fontPath);
	}
	
	public static void removeFont(IProject project, String fontPath){
		Resources res = getResources(project);
		
		if (res == null) return;
		res.fonts.remove(res.fontsPath.indexOf(fontPath));
		res.fontsPath.remove(fontPath);
		
		System.out.println("Remove font: " + fontPath);
	}
	
	public static void removeFont(String fontPath){
		removeFont(getCurrentProject(), fontPath);
	}
	
	public static void addImage(IProject project, String imgPath){
		Resources res = getResources(project);
		
		if (res == null)
			res = addNewResources(project);
		
		if (isExist(project, imgPath, "image")) return;
		// NEu image path cua co trong imgFilePath va imgsFileNAme cua resources
		
		String newImagePath = getAndroidProjectPath(project) + "/assets/imgs/" + getName(imgPath);
		System.out.println("newImagePath: " + newImagePath);
		boolean isExistedInAndroid = new File(newImagePath).exists();
		
		if (res.imagesFileName.contains(newImagePath) && !isExistedInAndroid){
			// rename of choosed image
			// newImage.png ---> newImage1.png
			newImagePath = newImagePath.substring(0, newImagePath.lastIndexOf(".")) + 1 + newImagePath.substring(newImagePath.lastIndexOf('.'));
		}
		
		try {
			if (!isExistedInAndroid)
				FileUtils.copyFile(new File(imgPath), new File(newImagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		res.imagesFileName.add(newImagePath);
		res.imagesPath.add(imgPath);
		res.images.add(new Image(Display.getCurrent(), newImagePath));
		
		System.out.println("Add new image: " + imgPath);
	}

	public static String getAndroidProjectPath(IProject project){
		return project.getLocation().toString() + Constant.EXTENSION_ANDROID;
	}

	public static void addImage(String imgPath){
		addImage(getCurrentProject(), imgPath);
	}
	
	public static void removeImage(IProject project, String imgPath){
		Resources res = getResources(project);
		
		if (res == null) return;
		res.images.remove(res.imagesPath.indexOf(imgPath));
		res.imagesPath.remove(imgPath);
		System.out.println("Remove img: " + imgPath);
	}
	
	public static void removeImage(String imgPath){
		removeImage(getCurrentProject(), imgPath);
	}
	
	public static Image getImageByPath(IProject project, String imgPath){
		Resources res = getResources(project);
		
		if (res == null) return null;
		if (res.imagesPath.contains(imgPath)){
			System.out.println("on get image bypath: " + imgPath);
			System.out.println(res.imagesPath.indexOf(imgPath));
			return res.images.get(res.imagesPath.indexOf(imgPath));
		}
		return null;
	}
	
	public static Image getImageByPath(String imgPath){
		return getImageByPath(getCurrentProject(), imgPath);
	}
	
	public static BitmapFont getFontByPath(IProject project, String fontPath){
		Resources res = getResources(project);
		
		if (res == null) return null;
		if (res.fontsPath.contains(fontPath))
			return res.fonts.get(res.fontsPath.indexOf(fontPath));
		return null;
	}
	
	public static BitmapFont getFontByPath(String fontPath){
		return getFontByPath(getCurrentProject(), fontPath);
	}
	
	public static String getPathOfImage(IProject project, Image image){
		Resources res = getResources(project);
		
		if (res == null) return null;
		if (res.images.contains(image))
			return res.imagesFileName.get(res.images.indexOf(image));
		return null;
	}
	
	public static String getPathOfImage(Image image){
		return getPathOfImage(getCurrentProject(), image);
	}
	
	public static String getPathOfFont(IProject project, BitmapFont font){
		Resources res = getResources(project);
		
		if (res == null) return null;
		if (res.fonts.contains(font))
			return res.fontsFileName.get(res.fonts.indexOf(font));
		return null;
	}
	
	public static String getPathOfFont(BitmapFont font){
		return getPathOfFont(getCurrentProject(), font);
	}
	
	public static void save(IProject project){
		System.out.println("----------------------");
		System.out.println("Begin saving res.xml!");
		
		IPath path = project.getLocation();
		String fileName = path.toString() + "/res.xml";
		System.out.println("path: " + fileName);
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			// create root element
			Element rootEl = doc.createElement("resources");
			rootEl.setAttribute("name", fileName);
			doc.appendChild(rootEl);
			
			// save fonts and images
			Resources res = getResources(project);
			
			if (res == null)
				res = addNewResources(project);
			
			// save fonts
			Element fontsEl = doc.createElement("fonts");
			
			for (int i = 0; i < res.fontsPath.size(); i++){
				Element font = doc.createElement("font");
				
				font.setAttribute("path", res.fontsFileName.get(i));
				
				fontsEl.appendChild(font);
				System.out.println("Generate font: " + res.fontsFileName.get(i));
			}
			
			rootEl.appendChild(fontsEl);
			
			// save images
			Element imagesEl = doc.createElement("images");
			
			for (int i = 0; i < res.imagesPath.size(); i++){
				Element image = doc.createElement("img");
				
				image.setAttribute("path", res.imagesPath.get(i));
				image.setAttribute("name", res.imagesFileName.get(i));
				imagesEl.appendChild(image);
				
				System.out.println("Generate image: " + res.imagesPath.get(i));
			}
			
			rootEl.appendChild(imagesEl);
			// write to file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName));
	 
			transformer.transform(source, result);
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		System.out.println("End saving res.xml");
		System.out.println("----------------------");
	}
	
	public static void save(){
		save(getCurrentProject());
	}
	
	public static void restore(IProject project){
		System.out.println("----------------------");
		System.out.println("Begin restore res.xml");
		
		String fileName = project.getLocation().toString() + "/res.xml";
		System.out.println("File: " + fileName);
		
		try{
			File xmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			
			Element rootEl = doc.getDocumentElement();
			rootEl.normalize();
			System.out.println("file: " + rootEl.getAttribute("name"));
			
			// get fonts
			NodeList nList = doc.getElementsByTagName("fonts");
			Element els = (Element)(nList.item(0));
			
			nList = els.getElementsByTagName("font");
			for (int i = 0; i < nList.getLength(); i++){
				Element el = (Element)nList.item(i);
				
				// read font path
				addFont(project, el.getAttribute("path"));
			}
			
			// get Image
			nList = doc.getElementsByTagName("images");
			els = (Element)(nList.item(0));
			nList = els.getElementsByTagName("img");
			System.out.println("images size: " + nList.getLength());
			
			for (int i = 0; i < nList.getLength(); i++){
				Element el = (Element)nList.item(i);
				
				// read image path	
				addImage(project, el.getAttribute("path"));
			}
			
			System.out.println("Restored res.xml");
			System.out.println("--------------------------------");
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void restore(){
		IProject project = getCurrentProject();
		
		if (getResources(project) == null)
			restore(project);
	}
	
	public static IProject getCurrentProject(){
		return currentProject;
	}
	
	private static boolean isExist(IProject project, String path, String compareTag){
		switch (compareTag) {
		case "font":{
			
			if (Resources.getFontByPath(project, path) != null) return true;
			
			Resources res = getResources(project);
			if (res.fontsFileName.contains(path)) return true;
			
			break;
		}
		case "image":{
			if (Resources.getImageByPath(project, path) != null) return true;
			
			Resources res = getResources(project);
			if (res.imagesFileName.contains(path)) return true;
			
			break;
		}
		}
		return false;
	}
}
