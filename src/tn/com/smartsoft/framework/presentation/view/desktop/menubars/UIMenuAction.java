package tn.com.smartsoft.framework.presentation.view.desktop.menubars;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopPartType;
import tn.com.smartsoft.framework.presentation.view.desktop.UIActionLink;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.render.UIMenuActionRender;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.utils.MenuUtils;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UILinkedEvent;

public class UIMenuAction extends UIMenuNode implements UIActionLink {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ComponentId userActionId;
	private UILinkedEvent eventHandeler = new UILinkedEvent(ClientEvent.ON_CLICK, this, "onClikListener");

	public UIMenuAction() {
		addEvent(eventHandeler);
		eventHandeler.setActionPattern(DesktopPartType.MENUBAR_PART.toString());
	}

	public void addRequestParam(String name, String value, Boolean expected) {
		this.eventHandeler.addRequestParam(name, value, expected.booleanValue());
	}

	public UIMenuAction(String id, String libelle, String help, Long rang, String iconUrl, ComponentId userActionId, int type) {
		super(id, libelle, help, rang, iconUrl);
		this.userActionId = userActionId;
		addEvent(new UILinkedEvent(ClientEvent.ON_CLICK, this, "onClikListener"));
	}

	public void onClikListener(ListenerContext context) throws FunctionalException {
		WebContext webContext = context.webContext();
		UIMenuBar menuBar = context.userDesktop().menuBar();
		if (this.getParent() instanceof UIMenuItem) {
			UIMenuItem menuItem = (UIMenuItem) this.getParent();
			if (menuItem.getMenuActions().size() == 1) {
				menuBar.setMenuActions((UIMenuItem) null);
				menuBar.setDisplayActionTitle(null);
				menuBar.setDisplayMenuTitle(MenuUtils.renderTitle(context, menuItem));
			} else {
				menuBar.setDisplayActionTitle(this.getLibelle());
				menuBar.setDisplayMenuTitle(MenuUtils.renderTitle(context, (UIMenuNode) menuItem.getParent()));
				menuBar.setMenuActions(menuItem);
			}
		} else {
			menuBar.setDisplayActionTitle(this.getLibelle());
			menuBar.setDisplayMenuTitle(null);
			menuBar.setMenuActions((UIMenuItem) null);
		}
		context.userDesktop().userDesktopNavigation().goToUserAction(this.getUserActionId(), webContext);

	}

	public ComponentId getUserActionId() {
		return userActionId;
	}

	public void setUserActionId(ComponentId userActionId) {
		this.userActionId = userActionId;
	}

	public List<String> getTitle() {
		return MenuUtils.getTitle(this);
	}

	public UIGenericMenu findMenu(String id) {
		return null;
	}

	public UIRender getRender() {
		return new UIMenuActionRender(this);
	}

	public UILinkedEvent getEventHandeler() {
		return eventHandeler;
	}
}
