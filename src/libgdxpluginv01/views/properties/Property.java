package libgdxpluginv01.views.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class Property {
	private ScrolledComposite root;
	
	public Property(Composite parent){
		root = new ScrolledComposite(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		
		FormData data = new FormData();
		
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.right = new FormAttachment(100, 0);
		
		root.setLayoutData(data);
	}
	
	public ScrolledComposite getRoot() {
		return root;
	};
	
	public GridData createLayoutData(int width, int height, int horizontalSpan){
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.CENTER;
		data.widthHint = width;
		if (height != 0)
			data.heightHint = height;
		data.horizontalSpan = horizontalSpan;
		
		return data;
	}
}