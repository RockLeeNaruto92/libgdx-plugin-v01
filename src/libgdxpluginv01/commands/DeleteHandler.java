package libgdxpluginv01.commands;

import libgdxpluginv01.controller.UIController;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class DeleteHandler extends AbstractHandler {

	public DeleteHandler(){
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
//		System.out.println("Handing delete");
		System.out.println("Hello commnad");
		UIController uiController = UIController.getInstance();
		
		uiController.removeAll();
		return null;
	}

}
