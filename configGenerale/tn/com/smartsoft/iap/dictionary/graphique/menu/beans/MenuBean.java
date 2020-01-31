package tn.com.smartsoft.iap.dictionary.graphique.menu.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import java.io.Serializable;

public class MenuBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long menuId;
	private String moduleId;
	private String libelle;
	private String help;
	private Long rang;
	private ModuleBean parentModule;
	private String iconUrl;

	public MenuBean() {
		super();
	}

	public Serializable getId() {
		return new MenuBeanId(this);
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public void setId(MenuBeanId id) {
		id.copyValue(this);
	}

	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getHelp() {
		return this.help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public Long getRang() {
		return this.rang;
	}

	public void setRang(Long rang) {
		this.rang = rang;
	}

	public ModuleBean getParentModule() {
		return this.parentModule;
	}

	public void setParentModule(ModuleBean parentModule) {
		this.parentModule = parentModule;
	}
}