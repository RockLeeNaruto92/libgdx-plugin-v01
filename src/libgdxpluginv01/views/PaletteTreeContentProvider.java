package libgdxpluginv01.views;

import java.util.List;

import libgdxpluginv01.models.Element;
import libgdxpluginv01.models.Group;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class PaletteTreeContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getChildren(Object element) {
		if (element instanceof Group){
			return ((Group)element).getChildrens().toArray();
		}

		return new Object[0];
	}

	@Override
	public Object[] getElements(Object inputElement) {
		List list = (List) inputElement;
		return list.toArray();
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Element) {
			return ((Element) element).getParent();
		}
		
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Group)
			if (((Group)element).getChildrens().size() != 0)
				return true;
		return false;
	}

}
