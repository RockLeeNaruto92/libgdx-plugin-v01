package libgdxpluginv01.views.properties;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;

public abstract class Property {
	public abstract Control getRoot();
	
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