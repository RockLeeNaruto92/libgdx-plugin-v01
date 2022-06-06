package libgdxpluginv01.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.models.Element;
import libgdxpluginv01.models.Group;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class PaletteTreeLabelProvider implements ILabelProvider{
	private List listeners;
	private Image icon;
	
	public PaletteTreeLabelProvider(){
		listeners = new ArrayList();
		
		try {
			icon = new Image(null, new FileInputStream(Utility.getFile("icons/sample.gif")));
		} catch (FileNotFoundException e) {
			System.out.println("File not found: icons/sample.gif" );
		}
	}
	
	@Override
	public void addListener(ILabelProviderListener arg0) {
		listeners.add(arg0);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		if (icon != null)
			icon.dispose();
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		listeners.remove(arg0);
	}

	@Override
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return icon;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof Group)
			return ((Group)element).getName();
		else if (element instanceof Element)
			return ((Element)element).getName();
		
		return null;
	}

}
