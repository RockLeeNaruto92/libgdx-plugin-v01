package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class CCheckbox extends UIElement{
	public static int i = 0;
	private Button checkbox;

	public CCheckbox(Composite root, Point location) {
		super(root, location);
	}

	@Override
	public String getDefaultNamePattern() {
		return Parameter.DEFAULT_CHECKBOX_NAME_PATTERN + i;
	}

	@Override
	public Point getDefaultSize() {
		return checkbox.computeSize(Parameter.DEFAULT_CHECKBOX_SIZE.x, Parameter.DEFAULT_CHECKBOX_SIZE.y);
	}

	@Override
	public void createControls() {
		checkbox = new Button(getContainer(), SWT.CHECK);
		checkbox.setText(getName());
	}

	@Override
	public void addMouseListener() {
		checkbox.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				getContainer().onMouseUp(arg0);
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				getContainer().onMouseDown(arg0);
				setClicked(true);
				checkbox.redraw();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		checkbox.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent arg0) {
				// TODO Auto-generated method stub
				getContainer().onMouseMove(arg0);
			}
		});
		
		checkbox.addMouseTrackListener(new MouseTrackListener() {
			
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
		checkbox.addPaintListener(getPaintListener());
	}

	@Override
	public void displayBound(boolean display) {
		setClicked(display);
		checkbox.redraw();
	}

}
