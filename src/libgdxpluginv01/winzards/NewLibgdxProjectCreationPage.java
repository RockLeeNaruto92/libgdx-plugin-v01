package libgdxpluginv01.winzards;

import javax.swing.JFileChooser;

import libgdxpluginv01.constant.Word;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.badlogic.gdx.setup.GdxSetup;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class NewLibgdxProjectCreationPage extends WizardPage {
	
	private static int NUMBER_OF_INPUT_FIELD = 6;
	
	private boolean createDesktopProject = false;
	private Text textProjectNameContainer;
	private Text textProjectMainPackageContainer;
	private Text textProjectMainClassContainer;
	private Text textProjectDestinationFolder;
	private Text textAndroidSdkPath;
	private Button checkboxDesktopVersion;

	private ISelection selection;

	/**
	 * Constructor for NewLibgdxProjectCreationWizardPage.
	 * 
	 * @param pageName
	 */
	
	public NewLibgdxProjectCreationPage(ISelection selection) {
		super(Word.WIZARD_PAGE_NAME);
		setTitle(Word.WIZARD_PAGE_TITLE);
		setDescription(Word.WIZARD_PAGE_DESCRIPTION);
		
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		GridLayout layout = new GridLayout(NUMBER_OF_INPUT_FIELD, false);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		container.setLayoutData(new GridData(SWT.FILL));
		container.setLayout(layout);
		
		createNameInputText(container);
		createMainPackageInputText(container);
		createMainClassInputText(container);
		createDestinationFolderInputText(container);
		createAndroidSdkPathInputText(container);
		createDesktopVersionCheckbox(container);

		initialize();
		dialogChanged();
		setControl(container);
	}
	
	private void createAndroidSdkPathInputText(Composite container){
		Label label = new Label(container, SWT.NULL);
		label.setText(Word.LABEL_PROJECT_ANDROID_SDK_PATH);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		textAndroidSdkPath = new Text(container, SWT.BORDER | SWT.SINGLE);
		textAndroidSdkPath.setLayoutData(gd);
		textAndroidSdkPath.setText(Word.DEFAULT_PROJECT_DESTIONATION_FOLDER);
		textAndroidSdkPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		Button button = new Button(container, SWT.PUSH);
		button.setText(Word.LABEL_BUTTON_BROWSE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse(textAndroidSdkPath);
			}
		});
	}
	
	/***
	 * Create name input container include project name label and project name's inputText element
	 * @param container
	 */
	private void createNameInputText(Composite container){
		Label label = new Label(container, SWT.NULL);
		label.setText(Word.LABEL_PROJECT_NAME);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		textProjectNameContainer = new Text(container, SWT.BORDER | SWT.SINGLE);
		textProjectNameContainer.setLayoutData(gd);
		textProjectNameContainer.setText(Word.DEFAULT_PROJECT_NAME);
		textProjectNameContainer.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		label = new Label(container, SWT.NULL);
	}
	
	/**
	 * Create main package name input container include package name label and project main package name's inputText element
	 * @param container
	 */
	private void createMainPackageInputText(Composite container){
		Label label = new Label(container, SWT.NULL);
		label.setText(Word.LABEL_PROJECT_MAIN_PACKAGE);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		textProjectMainPackageContainer = new Text(container, SWT.BORDER | SWT.SINGLE);
		textProjectMainPackageContainer.setLayoutData(gd);
		textProjectMainPackageContainer.setText(Word.DEFAULT_PROJECT_MAIN_PACKAGE);
		textProjectMainPackageContainer.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		label = new Label(container, SWT.NULL);
	}
	
	
	/**
	 * 
	 * @param container
	 */
	private void createMainClassInputText(Composite container){
		Label label = new Label(container, SWT.NULL);
		label.setText(Word.LABEL_PROJECT_MAIN_CLASS);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		textProjectMainClassContainer = new Text(container, SWT.BORDER | SWT.SINGLE);
		textProjectMainClassContainer.setLayoutData(gd);
		textProjectMainClassContainer.setText(Word.DEFAULT_PROJECT_MAIN_CLASS);
		textProjectMainClassContainer.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		label = new Label(container, SWT.NULL);
	}
	
	/**
	 * 
	 * @param container
	 */
	private void createDestinationFolderInputText(Composite container){
		Label label = new Label(container, SWT.NULL);
		label.setText(Word.LABEL_PROJECT_DESTINATION_FOLDER);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		textProjectDestinationFolder = new Text(container, SWT.BORDER | SWT.SINGLE);
		textProjectDestinationFolder.setLayoutData(gd);
		textProjectDestinationFolder.setText(Word.DEFAULT_PROJECT_DESTIONATION_FOLDER);
		textProjectDestinationFolder.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		Button button = new Button(container, SWT.PUSH);
		button.setText(Word.LABEL_BUTTON_BROWSE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse(textProjectDestinationFolder);
			}
		});
	}
	
	/**
	 * 
	 * @param container
	 */
	private void createDesktopVersionCheckbox(Composite container){
		Label label = new Label(container, SWT.NULL);
		label.setText(Word.LABEL_DESKTOP_PROJECT);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		checkboxDesktopVersion = new Button(container, SWT.CHECK);
		checkboxDesktopVersion.setLayoutData(gd);
		checkboxDesktopVersion.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				createDesktopProject = checkboxDesktopVersion.getSelection();
			}
		});
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
			}
		}
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse(Text text) {
		JFileChooser dialog = new JFileChooser();
		dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			text.setText(dialog.getSelectedFile().getPath());
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		if (isValidProjectName() && isValidProjectMainPackage() && isValidProjectMainClass() 
				&& isValidProjectDestinationFolder() && isValidProjectAndroidSdkPatg())
			updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	/**
	 * Check inputed project name
	 */
	private boolean isValidProjectName(){
		String projectName = getProjectName();
		
		if (projectName.length() == 0){
			updateStatus(Message.PROJECT_NAME_IS_NULL);
			return false;
		}
		
		if (projectName.contains("'.,:?;")){
			updateStatus(Message.PROJECT_NAME_CONTAINS_INVALID_CHARACTER);
			return false;
		}
		
		return true;
	}
	
	private boolean isValidProjectMainPackage(){
		String projectMainPackage = getProjectMainPackage();
		
		if  (projectMainPackage.length() == 0){
			updateStatus(Message.PROJECT_MAIN_PACKAGE_IS_NULL);
			return false;
		}
		
		if (projectMainPackage.contains(",?:;")){
			updateStatus(Message.PROJECT_MAIN_PACKAGE_CONTAINS_INVALID_CHARACTER);
			return false;
		}
		
		if (projectMainPackage.endsWith(".")){
			updateStatus(Message.PROJECT_MAIN_PACKAGE_ENDS_BY_DOT_CHACRACTER);
			return false;
		}
		return true;
	}
	
	private boolean isValidProjectMainClass(){
		String projectMainClass = getProjectMainClass();
		
		if (projectMainClass.length() == 0){
			updateStatus(Message.PROJECT_MAIN_CLASS_IS_NULL);
			return false;
		}
		
		if (projectMainClass.contains("?:.,")){
			updateStatus(Message.PROJECT_MAIN_CLASS_CONTAINS_INVALID_CHARACTER);
			return false;
		}
		
		if (!Character.isUpperCase(projectMainClass.charAt(0))){
			updateStatus(Message.PROJECT_MAIN_CLASS_BEGINS_WITH_LOWERCASE_CHARACTER);
			return false;
		}
		return true;
	}
	
	private boolean isValidProjectDestinationFolder(){
		String projectDestinationFolder = getProjectDestinationFolder();
		
		if (projectDestinationFolder.length() == 0){
			updateStatus(Message.PROJECT_DESTINATION_FOLDER_IS_NULL);
			return false;
		}
		
		// Check destination folder exist
		if (!GdxSetup.isEmptyDirectory(projectDestinationFolder)){
			updateStatus(Message.PROJECT_DESTINATION_FOLDER_IS_NOT_EMPTY);
			return false;
		}
		
		return true;
	}
	
	private boolean isValidProjectAndroidSdkPatg(){
		String androidSDKPath = getProjectAndroidSdkPath();
		
		if (androidSDKPath.length() == 0){
			updateStatus(Message.PROJECT_ANDROID_SDK_IS_NULL);
			return false;
		}
		
		if (!GdxSetup.isSdkLocationValid(androidSDKPath)){
			updateStatus(Message.PROJECT_ANDROID_SDK_IS_INVALID);
			return false;
		}
		
		return true;
	}
	
	/**
	 * GET method
	 */
	public String getProjectName(){
		return textProjectNameContainer.getText();
	}
	
	public String getProjectMainPackage(){
		return textProjectMainPackageContainer.getText();
	}
	
	public String getProjectMainClass(){
		return textProjectMainClassContainer.getText();
	}
	
	public String getProjectDestinationFolder(){
		return textProjectDestinationFolder.getText();
	}
	
	public boolean isCreateDesktopProject(){
		return createDesktopProject;
	}

	public String getProjectAndroidSdkPath() {
		return textAndroidSdkPath.getText();
	}
}