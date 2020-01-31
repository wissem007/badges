package tn.com.smartsoft.framework.presentation.view.response;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.presentation.context.ResponseView;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtDialog;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtDialogRender;

public class DialogResponse extends ResponseView {
	
	public void response(Object model) {
		UIExtDialog extDialog = (UIExtDialog) model;
		UIRenderContext renderContext = new UIRenderContext(context);
		renderContext.getDesktopJs().setInsatanceName(extDialog.getId());
		renderContext.setLodedDialog(true);
		populateHeaders();
		populateContentType();
		try {
			UIExtDialogRender dialogRender = (UIExtDialogRender) extDialog.getRender();
			dialogRender.renderChilds(renderContext);
			renderContext.renderScript();
		} catch (Exception e) {
			new TechnicalException(e).printLogTrace(Logger.getLogger(getClass()));
			context.userDesktop().messagesHandler().addMessage(new TechnicalException(e));
			context.userDesktop().curentUserModule().goToDefaultUserAction(context);
		}
		
	}
	
}
