package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CLabel extends UIElement{
	Label label;
	
	public CLabel(Composite root, Point location) {
		super(root, location);
		
		setSize(getDefaultSize());
		
		displayBounds();
	}
	
	public void displayBounds(){
		label.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				Point size = getSize();
				
				e.gc.setForeground(getContainer().getDisplay().getSystemColor(SWT.COLOR_BLACK));
				e.gc.drawRectangle(Parameter.R, Parameter.R, size.x - 1 - 2 * Parameter.R, size.y - 1 - 2 * Parameter.R);
				System.out.println("data: " + size.x + "-" + size.y);
				// draw circle
				e.gc.drawOval(0, 0, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(0, size.y - 1 - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(size.x - 1 - 2 * Parameter.R, 0, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(size.x - 1 - 2 * Parameter.R, size.y - 1 - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				
				Point centerPoint = new Point(size.x / 2, size.y / 2);
				e.gc.drawOval(0, centerPoint.y - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(centerPoint.x - 2 * Parameter.R, 0, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(size.x - 1 - 2 * Parameter.R, centerPoint.y - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(centerPoint.x - 2 * Parameter.R, size.y - 1 - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
				e.gc.drawOval(centerPoint.x - 2 * Parameter.R, centerPoint.y - 2 * Parameter.R, 2 * Parameter.R, 2 * Parameter.R);
			}
		});
	}
	
	@Override
	public Point getDefaultSize() {
		// TODO Auto-generated method stub
		return label.computeSize(Parameter.DEFAULT_LABEL_SIZE.x, Parameter.DEFAULT_LABEL_SIZE.y);
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

	@Override
	public void createControls() {
		// TODO Auto-generated method stub
		label = new Label(getContainer(), SWT.NONE);
		label.setText(getName());
	}
	
}
