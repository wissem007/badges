package tn.com.smartsoft.framework.presentation.view.window.component.ext.menu;

import java.util.List;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.menu.render.UIExtMenuRender;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public class UIExtMenu extends UIExtMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIDefaultComponentHandler<UIExtMenuItem> menuItems = new UIDefaultComponentHandler<UIExtMenuItem>(this);

	public UIExtMenu() {
		super();
		setAddedToParent(false);
	}

	public UIExtMenu(String libelle, String help, String iconUrl) {
		super(libelle, help, iconUrl);

	}

	public void addMenuItem(String contenu) {
		menuItems.addItem(contenu);
	}

	public void addMenuItem(UIExtMenuItem component) {
		menuItems.addItem(component);
	}

	public UIExtMenuItem findMenuItem(String id) {
		return menuItems.findItem(id);
	}

	public UIExtMenuItem getMenuItem(String id) {
		return menuItems.getItem(id);
	}

	public List<UIExtMenuItem> getMenuItems() {
		return menuItems.getItems();
	}

	public boolean hasMenuItem(String id) {
		return menuItems.hasItem(id);
	}

	public List<String> menuItemIds() {
		return menuItems.itemIds();
	}

	public int menuItemSize() {
		return menuItems.itemSize();
	}

	public void removeMenuItem(int start, int end) {
		menuItems.removeItem(start, end);
	}

	public void removeMenuItem(int start) {
		menuItems.removeItem(start);
	}

	public void removeMenuItem(String id) {
		menuItems.removeItem(id);
	}

	public void removeMenuItem(String[] ids) {
		menuItems.removeItem(ids);
	}

	public UIRender getRender() {
		return new UIExtMenuRender(this);
	}
}
