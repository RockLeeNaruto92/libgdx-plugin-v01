package libgdxpluginv01.models.uielements;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Utility;
import libgdxpluginv01.swt.custom.Align;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CLabel extends UIElement{
	private static int i = 0;
	private LabelStyle style;
	private Align align;
	private boolean wrap;
	private boolean ellipsis;
	private Point fontScale;
	private Rectangle textBounds;
	
	public CLabel(Composite root, Point location) {
		super(root, location);
		style = new LabelStyle();
	}
	
	@Override
	public Point getDefaultSize() {
		// TODO Auto-generated method stub
		return Parameter.DEFAULT_LABEL_SIZE;
	}

	@Override
	public String getDefaultNamePattern() {
		// TODO Auto-generated method stub
		return Parameter.DEFAULT_LABEL_NAME_PATTERN + i;
	}

	@Override
	public void createControls() {
		// TODO Auto-generated method stub
//		label = new Label(getContainer(), SWT.NONE);
//		label.setText(getName());
	}
	
	@Override
	public void addMouseListener() {
	}

	@Override
	public void addPaintListener() {
		getContainer().addPaintListener(getPaintListener());
	}
	
	@Override
	public void displayBound(boolean display){
		setClicked(display);
		getContainer().redraw();
	}

	@Override
	public void drawContent(PaintEvent e) {
		
		// draw background
		if (style.background != null){
			Rectangle bound = style.background.getBounds();
			e.gc.drawImage(style.background, 0, 0, bound.width, bound.height, 
					0, 0, getSize().x, getSize().y);
		}
		
		// draw string
		// To do
		
	}
	
	
	static class LabelStyle{
		public String font;
		public Image background;
		public Color color;
		
		public LabelStyle(){
			try {
				background = new Image(null, new FileInputStream(Utility.getFile("icons/sample.gif")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
