package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CLabel extends UIElement{
	Label label;
	
	public CLabel(Composite root, Point location) {
		super(root, location);
		
		label = new Label(getContainer(), SWT.BORDER);
		label.setText(getName());
		
		addMouseListener();
	}

	@Override
	public Point getDefaultSize() {
		// TODO Auto-generated method stub
		return Parameter.DEFAULT_LABEL_SIZE;
	}

	@Override
	public String getDefaultNamePattern() {
		// TODO Auto-generated method stub
		return Parameter.DEFAULT_LABEL_NAME_PATTERN;
	}

	public void addMouseListener() {
		// TODO Auto-generated method stub
		label.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				getContainer().onMouseUp(arg0);
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				getContainer().onMouseDown(arg0);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		label.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent arg0) {
				// TODO Auto-generated method stub
				getContainer().onMouseMove(arg0);
			}
		});
		
		label.addMouseTrackListener(new MouseTrackListener() {
			
			@Override
			public void mouseHover(MouseEvent arg0) {
				// TODO Auto-generated method stub
				getContainer().onMouseHover(arg0);
			}
			
			@Override
			public void mouseExit(MouseEvent arg0) {
				// TODO Auto-generated method stub
				getContainer().onMouseExit(arg0);
			}
			
			@Override
			public void mouseEnter(MouseEvent arg0) {
				// TODO Auto-generated method stub
				getContainer().onMouseEnter(arg0);
			}
		});
	}
	
}
