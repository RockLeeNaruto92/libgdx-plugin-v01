package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Word;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class EmptyProperty extends Property{
	private static EmptyProperty _instance;
	private Label label;
	
	public EmptyProperty(Composite parent){
		super(parent);
		label = new Label(getRootContainer(), SWT.NONE);
//		label.setLayoutData(new FormData());
		
		label.setText(Word.PROPERTY_EMPTY);
	}
	
	public static EmptyProperty getInstance(Composite parent){
		if (_instance == null){
			_instance = new EmptyProperty(parent);
		}
		
		return _instance;
	}
}
