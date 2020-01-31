package tn.com.smartsoft.framework.presentation.view.window.component.ext.menu;

import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;

public abstract class UIExtMenuItem extends UIExtComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String libelle;
	private String help;
	private String iconUrl;

	public UIExtMenuItem(String libelle, String help, String iconUrl) {
		super();
		this.libelle = libelle;
		this.help = help;
		this.iconUrl = iconUrl;
	}

	public UIExtMenuItem() {
		super();
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
}
