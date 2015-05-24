package libgdxpluginv01.constant;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import libgdxpluginv01.swt.custom.BitmapFont;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Resources {
	private IProject project;
	private ArrayList<String> fontsPath = new ArrayList<String>();
	private ArrayList<String> imagesPath = new ArrayList<>();
	private ArrayList<BitmapFont> fonts = new ArrayList<>();
	private ArrayList<Image> images = new ArrayList<>();
	
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
		
		if (res.fontsPath.contains(fontPath)) return;
		
		res.fontsPath.add(fontPath);
		res.fonts.add(new BitmapFont(Display.getCurrent(), fontPath));
		
		System.out.println("Add new font: " + fontPath);
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
		
		if (res.imagesPath.contains(imgPath)) return;
		
		res.imagesPath.add(imgPath);
		res.images.add(new Image(Display.getCurrent(), imgPath));
		System.out.println("Add new image: " + imgPath);
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
		if (res.imagesPath.contains(imgPath))
			return res.images.get(res.imagesPath.indexOf(imgPath));
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
			return res.imagesPath.get(res.images.indexOf(image));
		return null;
	}
	
	public static String getPathOfImage(Image image){
		return getPathOfImage(getCurrentProject(), image);
	}
	
	public static String getPathOfFont(IProject project, BitmapFont font){
		Resources res = getResources(project);
		
		if (res == null) return null;
		if (res.fonts.contains(font))
			return res.fontsPath.get(res.fonts.indexOf(font));
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
				
				font.setAttribute("path", res.fonts.get(i).getPath());
				
				fontsEl.appendChild(font);
				System.out.println("Generate font: " + res.fonts.get(i).getPath());
			}
			
			rootEl.appendChild(fontsEl);
			
			// save images
			Element imagesEl = doc.createElement("images");
			
			for (int i = 0; i < res.imagesPath.size(); i++){
				Element image = doc.createElement("img");
				
				image.setAttribute("path", res.imagesPath.get(i));
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
	
	private static IProject getCurrentProject(){
//		IWorkbench wb = PlatformUI.getWorkbench();
//		IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
//		IWorkbenchPage page = window.getActivePage();
//		IEditorPart editor = page.getActiveEditor();
//		IEditorInput input = editor.getEditorInput();
//		
//		return ((FileEditorInput)input).getFile().getProject();
		return currentProject;
	}
}
