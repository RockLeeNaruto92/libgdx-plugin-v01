package libgdxpluginv01.editors;

import libgdxpluginv01.constant.MobileResolution;
import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Resources;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.dnd.UIElementDropAdapter;
import libgdxpluginv01.models.Element;
import libgdxpluginv01.models.uielements.UIElementType;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

public class EditorInterface extends EditorPart{
	private ScrolledComposite root;
	private Canvas dragComposite;
	private LocalSelectionTransfer transfer;
	private UIController uiController;
	private IEditorInput input;
	private IEditorSite site;

	public EditorInterface(Composite parent) {
		transfer = LocalSelectionTransfer.getTransfer();
	}
	
	private void createDragComposite(){
		dragComposite = new Canvas(root, SWT.NONE);
		dragComposite.setLayout(new FormLayout());
		
		dragComposite.getShell().setMinimumSize(Parameter.DEFAULT_DESIGN_PART_MIN_SIZE);
		dragComposite.setSize(Parameter.DEFAULT_DESIGN_PART_MIN_SIZE);
		
		root.setContent(dragComposite);
		
		addMouseListener(dragComposite);
		addDropListener(dragComposite);
		addPaintListener(dragComposite);
	}

	private void createScrolledLayout(Composite parent) {
		int style = SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL;
		root = new ScrolledComposite(parent, style);
		root.setLayout(new FillLayout());
	}
	
	private void addMouseListener(final Composite dragComposite){
		dragComposite.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
				uiController.onMouseUp(dragComposite);
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				uiController.onMouseDown(dragComposite);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		
		dragComposite.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				uiController.onEditorMouseMove(dragComposite);
			}
		});
	}
	
	private void addDropListener(Composite dragComposite){
		final DropTarget dropTarget = new DropTarget(dragComposite, DND.DROP_MOVE | DND.DROP_COPY);
		dropTarget.setTransfer(new Transfer[] {LocalSelectionTransfer.getTransfer()});
		dropTarget.addDropListener(new UIElementDropAdapter(dragComposite, this));
	}
	
	private void addPaintListener(final Composite composite){
		composite.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				// draw mobile rectangle
				e.gc.setLineWidth(Parameter.DEFAULT_MOBILE_WIDTH);
				e.gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN));
				e.gc.fillRectangle(Parameter.DEFAULT_MOBILE_POSITION.x, Parameter.DEFAULT_MOBILE_POSITION.y, MobileResolution.IPHONE_6_PLUS.x / 2, MobileResolution.IPHONE_6_PLUS.y / 2);
				e.gc.drawRectangle(Parameter.DEFAULT_MOBILE_POSITION.x, Parameter.DEFAULT_MOBILE_POSITION.y, MobileResolution.IPHONE_6_PLUS.x / 2, MobileResolution.IPHONE_6_PLUS.y / 2);
				
				// draw rectangle selection
				if (uiController.isSelecting()) {
					e.gc.setLineWidth(Parameter.DEFAULT_SELECT_WIDTH);
					e.gc.drawRectangle(uiController.getSelectingRectangle());
				}
			}
		});
	}
	
	public void drop(DropTargetEvent e){
		int type = identifyType();
		Control[] changed = null;
		
		Point location = dragComposite.toControl(e.x, e.y);
		
		Control container = uiController.createUIElement(dragComposite, type, location);
		changed = new Control[] {container};
		
		dragComposite.layout(changed);
	}
	
	private int identifyType(){
		final Element droppedObj = (Element)((StructuredSelection)transfer.getSelection()).getFirstElement();
		String name = droppedObj.getName();
		
		if (name.equals("Label"))
			return UIElementType.LABEL;
		
		if (name.equals("Checkbox"))
			return UIElementType.CHECKBOX;
		
		if (name.equals("Slider"))
			return UIElementType.SLIDER;
		
		if (name.equals("Button"))
			return UIElementType.BUTTON;
		
		if (name.equals("Sprite"))
			return UIElementType.SPRITE;
		
		if (name.equals("Image"))
			return UIElementType.IMAGE;
		
		if (name.equals("Animation"))
			return UIElementType.ANIMATION;
		// TO DO
		return -1;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		uiController.save(this);
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		this.site = site;
		this.input = input;
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		return true;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		createScrolledLayout(parent);
		createDragComposite();
		restore();
	}
	
	private void restore(){
		IProject project = ((FileEditorInput)input).getFile().getProject();
		Resources.setCurrentProject(project);
		
		if (Resources.getResources(project) == null)
			Resources.restore(project);
		
		if (uiController == null){
			uiController = new UIController();
			uiController.setDragComposite(dragComposite);
		}
		
		uiController.restore(this);
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void addPropertyListener(IPropertyListener arg0) {
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public IWorkbenchPartSite getSite() {
		return site;
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public Image getTitleImage() {
		return null;
	}

	@Override
	public String getTitleToolTip() {
		return null;
	}

	@Override
	public void removePropertyListener(IPropertyListener arg0) {
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSaveOnCloseNeeded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IEditorInput getEditorInput() {
		// TODO Auto-generated method stub
		return input;
	}

	@Override
	public IEditorSite getEditorSite() {
		// TODO Auto-generated method stub
		return site;
	}
}
