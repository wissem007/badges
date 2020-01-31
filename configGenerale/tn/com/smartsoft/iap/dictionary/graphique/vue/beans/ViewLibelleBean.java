package tn.com.smartsoft.iap.dictionary.graphique.vue.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class ViewLibelleBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String viewLibellesId;
	private String viewId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	private String libelle;
	private String help;

	public ViewLibelleBean() {
		super();
	}

	public Serializable getId() {
		return new ViewLibelleBeanId(this);
	}

	public void setId(ViewLibelleBeanId id) {
		id.copyValue(this);
	}

	public String getViewLibellesId() {
		return this.viewLibellesId;
	}

	public void setViewLibellesId(String viewLibellesId) {
		this.viewLibellesId = viewLibellesId;
	}

	public String getViewId() {
		return this.viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public String getSujetId() {
		return this.sujetId;
	}

	public void setSujetId(String sujetId) {
		this.sujetId = sujetId;
	}

	public String getSubModuleId() {
		return this.subModuleId;
	}

	public void setSubModuleId(String subModuleId) {
		this.subModuleId = subModuleId;
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
}