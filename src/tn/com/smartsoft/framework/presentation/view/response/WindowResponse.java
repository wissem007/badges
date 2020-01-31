package tn.com.smartsoft.framework.presentation.view.response;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.presentation.context.ResponseView;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;

public class WindowResponse extends ResponseView {

	public void response(Object model) {
		UIRenderContext renderContext = new UIRenderContext(context);
		populateHeaders();
		populateContentType();
		try {
			UIWindow uiWindow = context.userDesktop().curentUserAction().currentWindow();
			uiWindow.getRender().render(renderContext);
		} catch (Exception e) {
			new TechnicalException(e).printLogTrace(Logger.getLogger(getClass()));
			context.userDesktop().messagesHandler().addMessage(new TechnicalException(e));
			context.userDesktop().curentUserModule().goToDefaultUserAction(context);
		}
	}
}
