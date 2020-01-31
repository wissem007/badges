package tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer;

import java.util.HashMap;
import java.util.Map;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.desktop.DefaultUserDesktop;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuBar;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuItem;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.window.UIGenericComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UILinkedEvent;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;

public class UIUserModule extends UIGenericComponent implements UIObject{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	protected UIMenuBar			menuBar				= null;
	private ModuleBean			moduleBean;
	private DefaultUserDesktop	userDesktop;
	private ComponentId			defaultUserActionId;
	private Map<String, Object>	preferences			= new HashMap<String, Object>();
	
	public UIUserModule(ModuleBean moduleBean, UIMenuBar menuBar, DefaultUserDesktop userDesktop) {
		super();
		this.moduleBean = moduleBean;
		this.userDesktop = userDesktop;
		this.menuBar = menuBar;
		addEvent(new UILinkedEvent(ClientEvent.ON_CLICK, this, "onClikListener"));
	}
	public void onClikListener(ListenerContext context) throws FunctionalException {
		WebContext webContext = context.webContext();
		context.userDesktop().userDesktopNavigation().goToModule(this.getId(), webContext);
		menuBar.setDisplayActionTitle(null);
		menuBar.setMenuActions((UIMenuItem) null);
		menuBar.setDisplayMenuTitle(null);
	}
	public void addPreference(String id, Object value) {
		preferences.put(id, value);
	}
	public Object getPreference(String id) {
		return preferences.get(id);
	}
	public boolean containsPreference(String id) {
		return preferences.containsKey(id);
	}
	public ComponentId getDefaultUserActionId() {
		if (defaultUserActionId != null) {
			ActionBeanId ActionBeanId = new ActionBeanId(defaultUserActionId.getId(), defaultUserActionId.getSujetId().getSujetId(), defaultUserActionId.getSujetId().getSubModuleId(), defaultUserActionId.getSujetId().getModuleId());
			if (!userDesktop.userContext().isGranted(ActionBeanId)) {
				defaultUserActionId = userDesktop.getDefaultUserActionId();
			}
		}
		if (defaultUserActionId == null) defaultUserActionId = userDesktop.getDefaultUserActionId();
		return defaultUserActionId;
	}
	public void setDefaultUserActionId(ComponentId defaultUserActionId) {
		this.defaultUserActionId = defaultUserActionId;
	}
	public void goToDefaultUserAction(WebContext context) {
		userDesktop.userDesktopNavigation().goToUserAction(getDefaultUserActionId(), context);
	}
	public void goToModule(WebContext context) {
		goToDefaultUserAction(context);
	}
	public ModuleBean getModuleBean() {
		return moduleBean;
	}
	public UIMenuBar getMenuBar() {
		return menuBar;
	}
	public String getHelp() {
		return moduleBean.getHelp();
	}
	public String getIconUrl() {
		return moduleBean.getIconUrl();
	}
	public String getId() {
		return moduleBean.getModuleId();
	}
	public String getLibelle() {
		return moduleBean.getLibelle();
	}
	public Long getRang() {
		return moduleBean.getRang();
	}
}
