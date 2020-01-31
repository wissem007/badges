package tn.com.smartsoft.framework.presentation.view.window.component.ext.menu;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.menu.render.UIExtMenuActionRender;

public class UIExtMenuAction extends UIExtMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtMenuAction() {
		super();
		setAddedToParent(false);
	}

	public UIExtMenuAction(String libelle, String help, String iconUrl) {
		super(libelle, help, iconUrl);
	}

	public UIRender getRender() {
		return new UIExtMenuActionRender(this);
	}

}
