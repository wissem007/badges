package tn.com.smartsoft.framework.presentation.view.desktop.menubars;

import tn.com.smartsoft.framework.presentation.view.desktop.menubars.render.UISplitButtonRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;

public class UISplitButton extends UIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String menuAlign;

	public UISplitButton(String id, String libelle, String help, Long rang, String iconUrl) {
		super(id, libelle, help, rang, iconUrl);
	}

	public UISplitButton(String id, UIMenuItem menuItem) {
		super(id, "<b class='label-Action'>" + menuItem.getLibelle() + "</b>", null, new Long(0), menuItem.getIconUrl());
		this.menuAlign = "tl-br?";
		this.menuHandler = menuItem.getMenuActions();
	} 

	public UISplitButton() {
		super();
	}

	public String getMenuAlign() {
		return menuAlign;
	}

	public void setMenuAlign(String menuAlign) {
		this.menuAlign = menuAlign;
	}

	public UIRender getRender() {
		return new UISplitButtonRender(this);
	}
}
