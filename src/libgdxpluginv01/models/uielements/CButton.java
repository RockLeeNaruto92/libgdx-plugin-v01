package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class CButton extends UIElement{
	public static int i = 0;
	Button button;

	public CButton(Composite root, Point location) {
		super(root, location);
	}

	@Override
	public String getDefaultNamePattern() {
		// TODO Auto-generated method stub
		return Parameter.DEFAULT_BUTTON_NAME_PATTERN + i;
	}

	@Override
	public Point getDefaultSize() {
		return button.computeSize(Parameter.DEFAULT_BUTTON_SIZE.x, Parameter.DEFAULT_BUTTON_SIZE.y);
	}

	@Override
	public void createControls() {
		button = new Button(getContainer(), SWT.PUSH);
		button.setBackground(getContainer().getDisplay().getSystemColor(SWT.COLOR_BLACK));
	}

	@Override
	public void addMouseListener() {
		// TODO Auto-generated method stub
		button.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				getContainer().onMouseUp(arg0);
				
				button.setBackground(getContainer().getDisplay().getSystemColor(SWT.COLOR_WHITE));
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				button.setBackground(getContainer().getDisplay().getSystemColor(SWT.COLOR_BLACK));
				getContainer().onMouseDown(arg0);
				setClicked(true);
				button.redraw();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		button.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent arg0) {
				// TODO Auto-generated method stub
				getContainer().onMouseMove(arg0);
			}
		});
		
		button.addMouseTrackListener(new MouseTrackListener() {
			
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

	@Override
	public void addPaintListener() {
		// TODO Auto-generated method stub
		button.addPaintListener(getPaintListener());
	}

	@Override
	public void displayBound(boolean display) {
		// TODO Auto-generated method stub
		setClicked(display);
		button.redraw();
	}

	@Override
	public void drawContent() {
		// TODO Auto-generated method stub
		
	}


}
