package tn.com.smartsoft.framework.presentation.factory;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.xml.utils.ParserDefinition;
import tn.com.smartsoft.framework.beans.factory.BeanFactory;
import tn.com.smartsoft.framework.beans.factory.BeanFactoryUtils;
import tn.com.smartsoft.framework.business.definition.BusinessBeanRefDefinition;
import tn.com.smartsoft.framework.business.factory.BusinessBeanFactory;
import tn.com.smartsoft.framework.business.factory.BusinessCreateInstanceFactory;
import tn.com.smartsoft.framework.configuration.ApplicationCacheDictionaryManager;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.configuration.definition.ModuleDefinition;
import tn.com.smartsoft.framework.configuration.impl.ComponentIdImpl;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.context.ResponseView;
import tn.com.smartsoft.framework.presentation.definition.ControleBeanDefinition;
import tn.com.smartsoft.framework.presentation.definition.PresentationDefinition;
import tn.com.smartsoft.framework.presentation.definition.ResponseHeaderDefinition;
import tn.com.smartsoft.framework.presentation.definition.ResponseViewDefinition;
import tn.com.smartsoft.framework.presentation.definition.UserActionDefinition;
import tn.com.smartsoft.framework.presentation.definition.WebDefinition;
import tn.com.smartsoft.framework.presentation.definition.WindowDefinition;
import tn.com.smartsoft.framework.presentation.definition.WindowRefDefinition;
import tn.com.smartsoft.framework.presentation.view.action.DefaultUserAction;
import tn.com.smartsoft.framework.presentation.view.action.UserAction;
import tn.com.smartsoft.framework.presentation.view.action.UserActionModel;
import tn.com.smartsoft.framework.presentation.view.action.controleur.DefaultUserActionControleur;
import tn.com.smartsoft.framework.presentation.view.action.model.BeanModelEntiteFactory;
import tn.com.smartsoft.framework.presentation.view.desktop.DefaultUserDesktop;
import tn.com.smartsoft.framework.presentation.view.desktop.DefaultUserDesktopNavigation;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;
import tn.com.smartsoft.framework.presentation.view.desktop.login.UIDefaultAccesApplication;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuBar;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.utils.MenuUtils;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIModuleExplorer;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIUserModule;
import tn.com.smartsoft.framework.presentation.view.tags.UIParameterTag;
import tn.com.smartsoft.framework.presentation.view.tags.UITag;
import tn.com.smartsoft.framework.presentation.view.tags.UITagManager;
import tn.com.smartsoft.framework.presentation.view.window.UIDefaultWindow;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBean;

public class DefaultPresentationBeanFactory implements Serializable,PresentationBeanFactory{

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	private PresentationDefinition	presentationDefinition;
	private BeanFactory				beanFactory;
	private BusinessBeanFactory		businessBeanFactory;

	public DefaultPresentationBeanFactory(PresentationDefinition presentationDefinition, BeanFactory beanFactory, BusinessBeanFactory businessBeanFactory) {
		super();
		this.presentationDefinition = presentationDefinition;
		this.beanFactory = beanFactory;
		this.businessBeanFactory = businessBeanFactory;
	}
	public Object createActionControleur(ComponentId actionId, UserContext userContext) throws FunctionalException {
		ControleBeanDefinition controleBeanDefinition = presentationDefinition.getControleBeanDefinition(actionId);
		Object actionControlerBean = BeanFactoryUtils.createBean(beanFactory, actionId, controleBeanDefinition, new BusinessCreateInstanceFactory());
		List<BusinessBeanRefDefinition> refBusinessBeans = controleBeanDefinition.getRefBusinessBeans();
		for (int i = 0; i < refBusinessBeans.size(); i++) {
			BusinessBeanRefDefinition businessBeanRefDefinition = refBusinessBeans.get(i);
			Object businessBean = businessBeanFactory.createBusinessBean(businessBeanRefDefinition.getComponentRefId(actionId));
			BeanFactoryUtils.setProperty(actionControlerBean, businessBeanRefDefinition.getName(), businessBean);
		}
		return actionControlerBean;
	}
	public UIWindow createWindow(String id, ComponentId userActionId, UserActionDefinition userActionDefinition) {
		try {
			ParserDefinition parserAction = userActionDefinition.getUserActionParser();
			UITagManager tagManager = presentationDefinition.getWebDefinition();
			WindowDefinition windowBeanDef = userActionDefinition.getWindowBeanDefinition(id);
			if (windowBeanDef instanceof WindowRefDefinition) {
				WindowRefDefinition windowRefDefinition = (WindowRefDefinition) windowBeanDef;
				WindowDefinition windowDefinitionRef = presentationDefinition.getWindowDefinition(new ComponentIdImpl(userActionId.getSujetId(), windowRefDefinition.getRef()));
				windowBeanDef = new WindowDefinition();
				windowDefinitionRef.copyTo(windowBeanDef);
				windowBeanDef.setId(id);
			}
			ComponentId windowView = new ComponentIdImpl(userActionId.getSujetId(), parserAction.parse(windowBeanDef.getViewId()));
			ViewBean viewBean = presentationDefinition.getApplicationCacheDictionaryManager().getViewBean(windowView.getViewBeanId());
			UIDefaultWindow uiWindow;
			uiWindow = tagManager.parse(windowBeanDef.getLocation(), windowBeanDef.getParameter(), parserAction);
			uiWindow.setViewBean(viewBean);
			uiWindow.setOnDestroy(parserAction.parse(windowBeanDef.getOnDestroy()));
			uiWindow.setOnInit(parserAction.parse(windowBeanDef.getOnInit()));
			uiWindow.setOnRender(parserAction.parse(windowBeanDef.getOnRender()));
			uiWindow.setOnSecurityCheck(parserAction.parse(windowBeanDef.getOnSecurityCheck()));
			uiWindow.setId(id);
			return uiWindow;
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	public boolean isExistUserAction(ComponentId userActionId) {
		return presentationDefinition.isExistUserAction(userActionId);
	}
	public UserAction createUserAction(ComponentId userActionId, UserContext userContext) {
		try {
			UserActionDefinition userActionDefinition = presentationDefinition.getUserActionDefinition(userActionId);
			ActionBean actionBean = presentationDefinition.getApplicationCacheDictionaryManager().getActionBean(userActionId.getActionBeanId());
			DefaultUserAction userAction = new DefaultUserAction();
			userAction.setUserActionDefinition(userActionDefinition);
			userAction.setUserActionId(userActionId);
			userAction.setPresentationBeanFactory(this);
			userAction.setActionBean(actionBean);
			UserActionModel rootBeanModel = BeanModelEntiteFactory.createBeanModel(userActionDefinition, userAction, presentationDefinition);
			userAction.setModel(rootBeanModel);
			if (StringUtils.isNotBlank(userActionDefinition.getControlerBean())) {
				ComponentId componentBeanControlerId = new ComponentIdImpl(userActionId.getSujetId(), userActionDefinition.getControlerBean());
				Object controlerBean = createActionControleur(componentBeanControlerId, userContext);
				userAction.setControleur(new DefaultUserActionControleur(controlerBean));
			}
			return userAction;
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	public UserDesktop createUserDesktop() {
		DefaultUserDesktop userDesktop = new DefaultUserDesktop();
		DefaultUserDesktopNavigation userDesktopNavigation = new DefaultUserDesktopNavigation(this, userDesktop);
		userDesktop.setUserDesktopNavigation(userDesktopNavigation);
		UIDefaultAccesApplication accesApplication = new UIDefaultAccesApplication(userDesktop, presentationDefinition.getLoginUserActionId());
		userDesktop.setAccesApplication(accesApplication);
		return userDesktop;
	}
	public void loadUserDesktop(UserDesktop userDesktop) {
		((DefaultUserDesktop) userDesktop).setDefaultModule(presentationDefinition.getApplicationCacheDictionaryManager().getApplicationBean().getModuleBaseId());
		((DefaultUserDesktop) userDesktop).setDefaultUserActionId(presentationDefinition.getDefaultUserActionId());
	}
	public ResponseView createWindowResponseView() {
		WebDefinition webDefinition = presentationDefinition.getWebDefinition();
		return createResponseView(webDefinition.getWindowResponseView());
	}
	public ResponseView createResponseView(String name) {
		WebDefinition webDefinition = presentationDefinition.getWebDefinition();
		ResponseViewDefinition responseViewDefinition = webDefinition.getResponseViewDefinition(name);
		if (responseViewDefinition == null) { throw new TechnicalException("no responseView with name " + name); }
		try {
			ResponseView responseView = (ResponseView) responseViewDefinition.getClassName().newInstance();
			responseView.setContentType(responseViewDefinition.getContentType());
			for (int i = 0; i < responseViewDefinition.getHeaders().size(); i++) {
				ResponseHeaderDefinition responseHeaderDefinition = responseViewDefinition.getHeaders().get(i);
				responseView.setHeader(responseHeaderDefinition.getName(), responseHeaderDefinition.getValue());
			}
			responseView.setParameters(responseViewDefinition.getParameters());
			return responseView;
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	public UIModuleExplorer loadModuleExplorers(UserDesktop userDesktop) {
		UIModuleExplorer objectExplorer = new UIModuleExplorer();
		UITag uiTag = presentationDefinition.getWebDefinition().getTag("module-explorer");
		if (uiTag != null) {
			for (int i = 0; i < uiTag.getParameters().size(); i++) {
				UIParameterTag parameterTag = uiTag.getParameters().get(i);
				try {
					BeanUtils.setProperty(objectExplorer, parameterTag.getProperty(), parameterTag.getValue());
				} catch (Exception e) {}
			}
		}
		Collection<String> modulBeans = presentationDefinition.getModuleDefinitions().keySet();
		Map<String, UIUserModule> itemObjectExplorers = new HashMap<String, UIUserModule>();
		objectExplorer.setUserModules(itemObjectExplorers);
		String systemModuleId = presentationDefinition.getApplicationCacheDictionaryManager().getApplicationBean().getSystemModuleId();
		for (Iterator<String> iterator = modulBeans.iterator(); iterator.hasNext();) {
			String moduleId = iterator.next();
			ModuleDefinition moduleDefinition = presentationDefinition.getModuleDefinition(moduleId);
			ModuleBean moduleBean = presentationDefinition.getApplicationCacheDictionaryManager().getModuleBean(moduleId);
			boolean isLoadModule = moduleBean != null && moduleDefinition != null && !moduleBean.getModuleId().equalsIgnoreCase(systemModuleId);
			if (isLoadModule && moduleBean.getActivate()) {
				UIMenuBar menuBar = MenuUtils.loadMenuBar(userDesktop.userContext(), moduleId, this);
				if (menuBar.getMenus().size() > 0) {
					UIUserModule value = new UIUserModule(moduleBean, menuBar, (DefaultUserDesktop) userDesktop);
					if (moduleDefinition.getDefaultUserActionId() != null && moduleDefinition.getDefaultUserActionId().getSujetId().getModuleId() == null) {
						moduleDefinition.getDefaultUserActionId().getSujetId().setModuleId(moduleId);
					}
					value.setDefaultUserActionId(moduleDefinition.getDefaultUserActionId());
					itemObjectExplorers.put(moduleBean.getModuleId(), value);
				}
			}
		}
		return objectExplorer;
	}
	public ApplicationCacheDictionaryManager getCacheDictionaryManager() {
		return presentationDefinition.getApplicationCacheDictionaryManager();
	}
}
