package libgdxpluginv01.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class LibgdxProjectCreationWizard extends Wizard implements INewWizard {
	private NewLibgdxProjectCreationPage page;
	private ISelection selection;
	
	public LibgdxProjectCreationWizard() {
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
		final String pName = page.getProjectName();
		final String pMainPackage = page.getProjectMainPackage();
		final String pMainClass = page.getProjectMainClass();
		final String pDestinationFolder = page.getProjectDestinationFolder();
		final boolean isCreateDesktopVersion = page.isCreateDesktopProject();
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(pName, pMainPackage, pMainClass,
							pDestinationFolder, isCreateDesktopVersion, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error",
					realException.getMessage());
			return false;
		}
		return true;
	}

	protected void doFinish(String pName, String pMainPackage,
			String pMainClass, String pDestinationFolder,
			boolean isCreateDesktopVersion, IProgressMonitor monitor) throws CoreException{
		NewProjectLibgdxCreation creation = new NewProjectLibgdxCreation(pName, pMainPackage, pMainClass, pDestinationFolder, isCreateDesktopVersion);
		
		String desc = creation.createCoreProject();
		importProject(desc, monitor);
		importProject(creation.createAndroidProject(), monitor);
		if (isCreateDesktopVersion)
			importProject(creation.createDesktopProject(), monitor);
	}
 
	private void importProject(String desc, IProgressMonitor monitor) throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProjectDescription description = workspace.loadProjectDescription(new Path(desc));
		IProject project = workspace.getRoot().getProject(description.getName());
		
		if (!project.exists()){
			project.create(description, monitor);
			project.open(monitor);
		}else if (!project.isOpen()) {
			project.open(monitor);
		}
	}
}
