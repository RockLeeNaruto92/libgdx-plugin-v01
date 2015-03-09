package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.ole.win32.DISPPARAMS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Slider;

public class CSlider extends UIElement {
	public static int i = 0;
	private Slider slider;

	public CSlider(Composite root, Point location) {
		super(root, location);
	}

	@Override
	public String getDefaultNamePattern() {
		// TODO Auto-generated method stub
		return Parameter.DEFAULT_SLIDER_NAME_PATTERN + i;
	}

	@Override
	public Point getDefaultSize() {
		return slider.computeSize(Parameter.DEFAULT_SLIDER_SIZE.x, Parameter.DEFAULT_SLIDER_SIZE.y);
	}

	@Override
	public void createControls() {
		slider = new Slider(getContainer(), SWT.HORIZONTAL);
		slider.setMinimum(0);
		slider.setMaximum(100);
		slider.setIncrement(1);
		slider.setSelection(50);
	}

	@Override
	public void addMouseListener() {
		slider.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				getContainer().onMouseUp(arg0);
				
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				getContainer().onMouseDown(arg0);
				
				displayBound(true);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		slider.addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent arg0) {
				// TODO Auto-generated method stub
				getContainer().onMouseMove(arg0);
			}
		});
		
		slider.addMouseTrackListener(new MouseTrackListener() {
			
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
		slider.addPaintListener(getPaintListener());
	}

	@Override
	public void displayBound(boolean display) {
		setClicked(true);
		slider.redraw();
	}

	@Override
	public void drawContent() {
		// TODO Auto-generated method stub
		
	}


}
