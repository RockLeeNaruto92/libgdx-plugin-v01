package libgdxpluginv01.custom.listeners;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Text;

public class CustomKeyListener implements KeyListener {
	private Text text;
	
	public CustomKeyListener(Text text){
		this.text = text;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
