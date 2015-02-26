package libgdxpluginv01.views;

import java.util.List;

import libgdxpluginv01.constant.Utility;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class PaletteTreeContentProvider implements ITreeContentProvider{

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getChildren(Object element) {
		Object[] childrens = null;
		
		if (element instanceof List){
			childrens = ((List)element).toArray();
			childrens = Utility.removeArrayElement(childrens, 0);
		} else {
			childrens = new Object[1];
			childrens[0] = element;
		}
		
		return childrens;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		List list = (List) inputElement;
		return list.toArray();
	}

	@Override
	public Object getParent(Object element) {
		
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

}
