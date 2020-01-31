package tn.com.smartsoft.framework.presentation.view.desktop.menubars;

import java.util.List;

import tn.com.smartsoft.framework.presentation.view.desktop.menubars.render.UIMenuRender;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.utils.MenuUtils;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;

public class UIMenu extends UIMenuNode implements UIListMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected MenuHandler menuHandler = new MenuHandler(this);

	public UIMenu() {
		super();
	}

	public UIMenu(String id, String libelle, String help, Long rang, String iconUrl) {
		super(id, libelle, help, rang, iconUrl);
	}

	public void addMenu(UIMenuNode menu) {
		menuHandler.addMenu(menu);
	}

	public UIGenericMenu findMenu(String id) {
		return menuHandler.findMenu(id);
	}

	public UIMenuNode getMenu(String id) {
		return menuHandler.getMenu(id);
	}

	public List<String> getTitle() {
		return MenuUtils.getTitle(this);
	}

	public List<UIMenuNode> getMenus() {
		return menuHandler.getMenus();
	}

	public MenuHandler getMenuHandler() {
		return menuHandler;
	}

	public UIRender getRender() {
		return new UIMenuRender(this);
	}
}
