package tn.com.smartsoft.framework.presentation.view.desktop.menubars;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopPartType;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.render.UIMenuItemRender;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.utils.MenuUtils;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEventHandler;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UILinkedEvent;

public class UIMenuItem extends UIMenuNode implements UIEventHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MenuHandler menuActions = new MenuHandler(this);
	private UILinkedEvent eventHandeler = new UILinkedEvent(ClientEvent.ON_CLICK, this, "onClikListener");
	private ComponentId userActionId;

	public UIMenuItem() {
		super();
		addEvent(eventHandeler);
		eventHandeler.setActionPattern(DesktopPartType.MENUBAR_PART.toString());
	}

	public UIMenuItem(String id, String libelle, String help, Long rang, String iconUrl) {
		super(id, libelle, help, rang, iconUrl);
		addEvent(new UILinkedEvent(ClientEvent.ON_CLICK, this, "onClikListener"));
	}

	public void onClikListener(ListenerContext context) throws FunctionalException {
		WebContext webContext = context.webContext();
		UIMenuBar menuBar = context.userDesktop().menuBar();
		int menuActionSize = this.menuActions.getMenus().size();
		if (menuActionSize == 1) {
			UIMenuAction menuAction = (UIMenuAction) this.menuActions.getMenus().get(0);
			menuAction.fireActionEvent(webContext);
			return;
		}
		menuBar.setDisplayActionTitle(null);
		menuBar.setDisplayMenuTitle(MenuUtils.renderTitle(context, (UIMenuNode) this.getParent()));
		menuBar.setMenuActions(this);
		context.userDesktop().curentUserModule().goToDefaultUserAction(webContext);
	}

	public void onClikActionListener(ListenerContext context) throws FunctionalException {
		context.userDesktop().userDesktopNavigation().goToUserAction(this.getUserActionId(), context.webContext());
	}

	public ComponentId getUserActionId() {
		return userActionId;
	}

	public void setUserActionId(ComponentId userActionId) {
		this.userActionId = userActionId;
	}

	public void addRequestParam(String name, String value, Boolean expected) {
		this.eventHandeler.addRequestParam(name, value, expected.booleanValue());
	}

	public void addMenuAction(UIMenuAction menu) {
		menuActions.addMenu(menu);
	}

	public MenuHandler getMenuActions() {
		return menuActions;
	}

	public void setMenuActions(MenuHandler menuActions) {
		this.menuActions = menuActions;
	}

	public UIGenericMenu getMenuAction(String id) {
		return (UIGenericMenu) menuActions.getMenu(id);
	}

	public UIGenericMenu findMenu(String id) {
		return getMenuAction(id);
	}

	public List<String> getTitle() {
		return MenuUtils.getTitle(this);
	}

	public UILinkedEvent getEventHandeler() {
		return eventHandeler;
	}

	public UIRender getRender() {
		return new UIMenuItemRender(this);
	}
}
