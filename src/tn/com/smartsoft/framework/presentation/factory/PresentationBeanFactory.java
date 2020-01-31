package tn.com.smartsoft.framework.presentation.factory;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.configuration.ApplicationCacheDictionaryManager;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.context.ResponseView;
import tn.com.smartsoft.framework.presentation.definition.UserActionDefinition;
import tn.com.smartsoft.framework.presentation.view.action.UserAction;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIModuleExplorer;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;

public interface PresentationBeanFactory {
	public UIModuleExplorer loadModuleExplorers(UserDesktop userDesktop);

	public ResponseView createWindowResponseView();

	public ResponseView createResponseView(String name);

	public ApplicationCacheDictionaryManager getCacheDictionaryManager();

	public abstract Object createActionControleur(ComponentId actionId, UserContext userContext) throws FunctionalException;

	public abstract UserAction createUserAction(ComponentId userActionId, UserContext userContext);

	public boolean isExistUserAction(ComponentId userActionId);

	public UserDesktop createUserDesktop();

	public UIWindow createWindow(String id, ComponentId userActionId, UserActionDefinition userActionDefinition);
}