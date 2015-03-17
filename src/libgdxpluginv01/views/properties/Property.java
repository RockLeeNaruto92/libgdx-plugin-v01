package libgdxpluginv01.views.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class Property {
	private Composite rootContainer;
	
	public Property(Composite parent){
		rootContainer = new Composite(parent, SWT.NONE);
		rootContainer.setLayout(new FillLayout());
		rootContainer.setLayoutData(new FormData());
	}
	
	public Control getRoot() {
		return getRootContainer();
	};
	
	public Composite getRootContainer() {
		return rootContainer;
	}

	public void setRootContainer(Composite rootContainer) {
		this.rootContainer = rootContainer;
	}

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