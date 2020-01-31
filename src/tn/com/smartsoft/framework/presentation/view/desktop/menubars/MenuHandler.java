package tn.com.smartsoft.framework.presentation.view.desktop.menubars;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.commons.utils.BeanComparator;
import tn.com.smartsoft.commons.utils.SorterType;

public class MenuHandler implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UIMenuNode> menus = new ArrayList<UIMenuNode>();
	private Map<String, UIMenuNode> menusMap = new HashMap<String, UIMenuNode>();
	private UIMenuNode rootMenu;
	private BeanComparator menuComparator = new BeanComparator("rang", SorterType.ASC, false);

	public MenuHandler(UIMenuNode rootMenu) {
		super();
		this.rootMenu = rootMenu;
	}

	public void addMenu(UIMenuNode menu) {
		menu.setParent(rootMenu);
		menusMap.put(menu.getId(), menu);
		menus.add(menu);
	}

	public UIMenuNode getMenu(String id) {
		return (UIMenuNode) menusMap.get(id);
	}

	public UIMenuNode getMenu(int id) {
		return (UIMenuNode) menus.get(id);
	}

	public UIGenericMenu findMenu(String id) {
		if (getMenu(id) != null)
			return getMenu(id);
		for (int i = 0; i < menus.size(); i++) {
			UIMenuNode menuNode = (UIMenuNode) menus.get(i);
			UIGenericMenu genericMenu = menuNode.findMenu(id);
			if (genericMenu != null)
				return genericMenu;
		}
		return null;
	}

	public boolean isEmpty() {
		return menus.isEmpty();
	}

	public int size() {
		return menus.size();
	}

	public void sort() {
		menuComparator.sort(menus);
	}

	public List<UIMenuNode> getMenus() {
		return menus;
	}
}
