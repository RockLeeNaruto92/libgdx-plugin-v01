package libgdxpluginv01.winzards;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class LibgdxProjectCreation extends Wizard implements INewWizard {
	private NewLibgdxProjectCreationPage page;
	private ISelection selection;
	
	public LibgdxProjectCreation() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	@Override
	public void addPages() {
		page = new NewLibgdxProjectCreationPage(selection);
		addPage(page);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	@Override
	public boolean performFinish() {
		return false;
	}
}
