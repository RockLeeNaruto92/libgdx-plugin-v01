package libgdxpluginv01.models.uielements;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.swt.custom.CustomComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public abstract class UIElement {
	private static int i = 0;

	private Rectangle bound;
	private CustomComposite container;
	private String name;
	private Point size;
	private boolean clicked;

	private PaintListener paintListener;

	public UIElement(Composite root, Point location) {
		name = getDefaultNamePattern() + (i++);
		container = new CustomComposite(root, SWT.NO_TRIM, location);
		container.setLayout(new FillLayout());

		createControls();

		Point defaultSize = getDefaultSize();
		container.setSize(defaultSize);

		setBound(new Rectangle(location.x, location.y, defaultSize.x,
				defaultSize.y));
		// displayBound();
		displayBound(true);

		addMouseListener();
	}

	public abstract String getDefaultNamePattern();

	public abstract Point getDefaultSize();

	public abstract void createControls();

	public abstract void displayBound(boolean display);

	public abstract void addMouseListener();

	public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}

	public CustomComposite getContainer() {
		return container;
	}

	public void setContainer(CustomComposite container) {
		this.container = container;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getSize() {
		return size;
	}

	public void setSize(Point size) {
		this.size = size;
	}

	public PaintListener getPaintListener() {
		if (paintListener == null) {
			paintListener = new PaintListener() {

				@Override
				public void paintControl(PaintEvent e) {
					// TODO Auto-generated method stub
					if (clicked) {
						Point size = getSize();

						e.gc.setForeground(getContainer().getDisplay()
								.getSystemColor(SWT.COLOR_BLACK));
						e.gc.drawRectangle(Parameter.R, Parameter.R, size.x - 1
								- 2 * Parameter.R, size.y - 1 - 2 * Parameter.R);
						System.out.println("data: " + size.x + "-" + size.y);
						// draw circle
						e.gc.drawOval(0, 0, 2 * Parameter.R, 2 * Parameter.R);
						e.gc.drawOval(0, size.y - 1 - 2 * Parameter.R,
								2 * Parameter.R, 2 * Parameter.R);
						e.gc.drawOval(size.x - 1 - 2 * Parameter.R, 0,
								2 * Parameter.R, 2 * Parameter.R);
						e.gc.drawOval(size.x - 1 - 2 * Parameter.R, size.y - 1
								- 2 * Parameter.R, 2 * Parameter.R,
								2 * Parameter.R);

						Point centerPoint = new Point(size.x / 2, size.y / 2);
						e.gc.drawOval(0, centerPoint.y - 2 * Parameter.R,
								2 * Parameter.R, 2 * Parameter.R);
						e.gc.drawOval(centerPoint.x - 2 * Parameter.R, 0,
								2 * Parameter.R, 2 * Parameter.R);
						e.gc.drawOval(size.x - 1 - 2 * Parameter.R,
								centerPoint.y - 2 * Parameter.R,
								2 * Parameter.R, 2 * Parameter.R);
						e.gc.drawOval(centerPoint.x - 2 * Parameter.R, size.y
								- 1 - 2 * Parameter.R, 2 * Parameter.R,
								2 * Parameter.R);
						e.gc.drawOval(centerPoint.x - 2 * Parameter.R,
								centerPoint.y - 2 * Parameter.R,
								2 * Parameter.R, 2 * Parameter.R);
					}
				}
			};
		}

		return paintListener;
	}

	public void setPaintListener(PaintListener paintListener) {
		this.paintListener = paintListener;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
}
