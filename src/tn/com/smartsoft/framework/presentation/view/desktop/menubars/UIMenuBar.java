package tn.com.smartsoft.framework.presentation.view.desktop.menubars;

import java.util.List;

import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.context.RequestEventInfo;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.utils.ResolveEventPathUtils;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopElement;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopPartType;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.render.UIMenuBarRender;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEventHandler;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserMenuBean;

public class UIMenuBar extends UIComponent implements UIRendrableComponent, UIListMenu, DesktopElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MenuHandler menuHandler = new MenuHandler(null);
	private boolean isDisplayed = true;
	private String displayMenuTitle;
	private String displayActionTitle;
	private UISplitButton menuActions;
	private UISplitButton userActions;
	private UISplitButton societeActions;
	private boolean rendred = true;
	private String moduleId;
	private UserContext userContext;

	public UIMenuBar(String moduleId, UserContext userContext) {
		super();
		this.moduleId = moduleId;
		this.userContext = userContext;
	}

	public List<UserMenuBean> getUserMenus() {
		return userContext.getUserMenu(moduleId);
	}

	public UISplitButton getSocieteActions() {
		return societeActions;
	}

	public void setSocieteActions(UISplitButton societeActions) {
		this.societeActions = societeActions;
	}

	public UISplitButton getUserActions() {
		return userActions;
	}

	public void setUserActions(UISplitButton userActions) {
		this.userActions = userActions;
	}

	public void addMenu(UIMenuNode menu) {
		menuHandler.addMenu(menu);
	}

	public UIGenericMenu findMenu(String id) {
		if (userActions != null) {
			UIGenericMenu menu = userActions.findMenu(id);
			if (menu != null)
				return menu;
		}
		if (societeActions != null) {
			UIGenericMenu menu = societeActions.findMenu(id);
			if (menu != null)
				return menu;
		}
		return menuHandler.findMenu(id);
	}

	public UIMenuNode getMenu(String id) {
		return menuHandler.getMenu(id);
	}

	public List<UIMenuNode> getMenus() {
		return menuHandler.getMenus();
	}

	public MenuHandler getMenuHandler() {
		return menuHandler;
	}

	public boolean isDisplayed() {
		if (!isDisplayed)
			return isDisplayed;
		return menuHandler.getMenus() != null && !menuHandler.getMenus().isEmpty();
	}

	public void setDisplayed(boolean isDisplayed) {
		this.isDisplayed = isDisplayed;
	}

	public UISplitButton getMenuActions() {
		return menuActions;
	}

	public void setMenuActions(UISplitButton menuActions) {
		this.menuActions = menuActions;
	}

	public void setMenuActions(UIMenuItem menuItem) {
		if (menuItem != null)
			this.setMenuActions(new UISplitButton("sssMenuActions", menuItem));
		else
			this.setMenuActions((UISplitButton) null);
	}

	public UIRender getRender() {
		return new UIMenuBarRender(this);
	}

	public void fireActionEvent(WebContext context) {
		RequestEventInfo requestEventInfo = context.request().requestEventInfo();

		UIGenericMenu menu = findMenu(requestEventInfo.componentSourceEvent());
		if (menu instanceof UIEventHandler) {
			((UIEventHandler) menu).fireActionEvent(context);
		}
	}

	public DesktopPartType desktopPartType() {
		return DesktopPartType.MENUBAR_PART;
	}

	public String resolvePath(WebContext context, String page, String componentId, String event) {
		return ResolveEventPathUtils.resolveEventPath(this, context, page, componentId, event);
	}

	public String getDisplayMenuTitle() {
		return displayMenuTitle;
	}

	public void setDisplayMenuTitle(String displayMenuTitle) {
		this.displayMenuTitle = displayMenuTitle;
	}

	public String getDisplayActionTitle() {
		return displayActionTitle;
	}

	public void setDisplayActionTitle(String displayActionTitle) {
		this.displayActionTitle = displayActionTitle;
	}

	public boolean isRendred() {
		return rendred;
	}

	public void setRendred(boolean rendred) {
		this.rendred = rendred;
	}
}
