package tn.com.smartsoft.framework.presentation.view.desktop.menubars;

import java.util.List;

public interface UIListMenu {
	public void addMenu(UIMenuNode menu);

	public List<UIMenuNode> getMenus();

	public UIGenericMenu findMenu(String id);
}
