package tn.com.smartsoft.framework.presentation.view.desktop.menubars;

import tn.com.smartsoft.framework.presentation.view.desktop.menubars.render.UISocieteMenuRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;

public class UISocieteMenu extends UISplitButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UISocieteMenu() {
		super();
	}

	public UISocieteMenu(String id, String libelle, String help, Long rang, String iconUrl) {
		super(id, libelle, help, rang, iconUrl);
	}

	public UISocieteMenu(String id, UIMenuItem menuItem) {
		super(id, menuItem);
	}

	public UIRender getRender() {
		return new UISocieteMenuRender(this);
	}
}
