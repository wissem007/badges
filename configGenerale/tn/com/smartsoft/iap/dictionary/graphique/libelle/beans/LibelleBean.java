package tn.com.smartsoft.iap.dictionary.graphique.libelle.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBean;
import java.io.Serializable;

public class LibelleBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String libelleId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	private String libelle;
	private SujetBean parentSujet;

	public LibelleBean() {
		super();
	}

	public Serializable getId() {
		return this.getLibelleId();
	}

	public void setId(String id) {
		this.setLibelleId(id);
	}

	public String getLibelleId() {
		return this.libelleId;
	}

	public void setLibelleId(String libelleId) {
		this.libelleId = libelleId;
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

	public SujetBean getParentSujet() {
		return this.parentSujet;
	}

	public void setParentSujet(SujetBean parentSujet) {
		this.parentSujet = parentSujet;
	}
}