package libgdxpluginv01.views.properties;

import libgdxpluginv01.constant.Word;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class EmptyProperty extends Property{
	private static EmptyProperty _instance;
	
	public EmptyProperty(Composite parent){
		Label label = new Label(parent, SWT.NONE);
		
		label.setText(Word.PROPERTY_EMPTY);
	}
	
	public static EmptyProperty getInstance(Composite parent){
		if (_instance == null){
			_instance = new EmptyProperty(parent);
		}
		
		return _instance;
	}
	
}
