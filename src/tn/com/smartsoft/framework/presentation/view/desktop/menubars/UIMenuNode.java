package tn.com.smartsoft.framework.presentation.view.desktop.menubars;

import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.view.window.UIGenericRendrableComponent;

public abstract class UIMenuNode extends UIGenericRendrableComponent implements UIObject, UIGenericMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String libelle;
	private String help;
	private Long rang;
	private String iconUrl;

	public UIMenuNode() {

	}

	public UIMenuNode(String id, String libelle, String help, Long rang, String iconUrl) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.help = help;
		this.rang = rang;
		this.iconUrl = iconUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Long getRang() {
		return rang;
	}

	public void setRang(Long rang) {
		this.rang = rang;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getMenuName() {
		return "menu" + getId();
	}

	public abstract UIGenericMenu findMenu(String id);

}
