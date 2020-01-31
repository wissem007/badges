package tn.com.smartsoft.framework.presentation.view.desktop;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.factory.DefaultPresentationBeanFactory;
import tn.com.smartsoft.framework.presentation.factory.PresentationBeanFactory;
import tn.com.smartsoft.framework.presentation.view.action.DefaultUserAction;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuBar;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIUserModule;
import tn.com.smartsoft.iap.system.ConstantUser;

public class DefaultUserDesktopNavigation implements UserDesktopNavigation {
	private PresentationBeanFactory presentationBeanFactory;
	private DefaultUserDesktop userDesktop;
	protected static final Logger logger = Logger.getLogger(DefaultUserDesktopNavigation.class);
	protected DesktopControleur desktopControleur;

	public DefaultUserDesktopNavigation(PresentationBeanFactory presentationBeanFactory, DefaultUserDesktop userDesktop) {
		super();
		this.presentationBeanFactory = presentationBeanFactory;
		this.userDesktop = userDesktop;
		this.desktopControleur = new DesktopControleur(userDesktop);
	}

	public PresentationBeanFactory presentationBeanFactory() {
		return presentationBeanFactory;
	}

	public void goToModule(String moduleId, WebContext context) {
		UIUserModule curentUserModule = userDesktop.moduleExplorer().getUserModule(moduleId);
		curentUserModule.goToModule(context);
		userDesktop.getUserContext().addUserPreference(ConstantUser.MODULE_PREFERENTCE_KEY, moduleId);
		userDesktop.setCurentUserModule(curentUserModule);
	}

	public void goToDefaultModule(WebContext context) {
		String startModule = userDesktop.getUserContext().getUserPreference(ConstantUser.MODULE_PREFERENTCE_KEY);
		if (StringUtils.isBlank(startModule)) {
			startModule = userDesktop.getDefaultModule();
		}
		goToModule(startModule, context);
	}

	public void goToDefaultUserAction(WebContext context) {
		UIMenuBar menuBar = context.userDesktop().menuBar();
		if (menuBar != null) {
			menuBar.setDisplayActionTitle(null);
		}
		goToUserAction(userDesktop.getDefaultUserActionId(), context);
	}

	public void goToUserAction(ComponentId userActionId, WebContext context) {
		try {
			DefaultUserAction curentUserAction;
			if (presentationBeanFactory.isExistUserAction(userActionId)) {
				curentUserAction = (DefaultUserAction) presentationBeanFactory.createUserAction(userActionId, userDesktop.getUserContext());
				((DefaultUserAction) curentUserAction).setUserDesktop(userDesktop);

			} else {
				curentUserAction = (DefaultUserAction) presentationBeanFactory.createUserAction(userDesktop.getDefaultUserActionId(), userDesktop.getUserContext());
				((DefaultUserAction) curentUserAction).setUserDesktop(userDesktop);
				context.userDesktop().messagesHandler().addMessage("Action non encore implémenter");
			}
			userDesktop.setCurentUserAction(curentUserAction);
			userDesktop.curentUserAction().goToHomeWindow(context);
		} catch (Exception e) {
			new TechnicalException(e).printLogTrace(logger);
			userDesktop.messagesHandler().addMessage(new TechnicalException(e));
			userDesktop.curentUserModule().goToDefaultUserAction(context);
		}
	}

	public void doStartSession(WebContext context, UserContext userContextBean) {
		userDesktop.setUserContext(userContextBean);
		((DefaultPresentationBeanFactory) presentationBeanFactory).loadUserDesktop(userDesktop);
		userContextBean.getCurrentUserOrganisme().setModuleExplorer(ApplicationConfiguration.applicationManager().presentationBeanFactory().loadModuleExplorers(userDesktop));
		userDesktop.setModuleExplorer(userContextBean.getCurrentUserOrganisme().getModuleExplorer());
		goToDefaultModule(context);
		userDesktop.accesApplication().setLogin(true);
	}

	public void doEndSession(WebContext context) {
		userDesktop.setUserContext(null);
		userDesktop.setModuleExplorer(null);
	}

	public void fireActionEvent(WebContext context) {
		this.desktopControleur.fireActionEvent(context);
	}
}
