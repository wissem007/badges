package tn.com.smartsoft.configGenerale.autresParametrage.mois.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class MoisBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long moisId;
	private String libelle;

	public MoisBean() {
		super();
	}

	public MoisBean(Long moisId, String libelle) {
		super();
		this.moisId = moisId;
		this.libelle = libelle;
	}

	public Serializable getId() {
		return this.getMoisId();
	}

	public Long getMoisId() {
		return this.moisId;
	}

	public void setMoisId(Long moisId) {
		this.moisId = moisId;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}