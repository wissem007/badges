package tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class StatuDemandes extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	public static final Long STATUS_EN_COURS = 1L;
	public static final Long STATUS_VERS_IMPRESION = 2L;
	public static final Long STATUS_REJETE = 3L;
	public static final Long STATUS_IMPRIME = 4L;

	private String libelle;
	private Long statuDemandesId;

	public StatuDemandes() {
		super();
	}

	public Serializable getId() {
		return this.getStatuDemandesId();
	}

	public void setId(Long id) {
		this.setStatuDemandesId(id);
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Long getStatuDemandesId() {
		return statuDemandesId;
	}

	public void setStatuDemandesId(Long statuDemandesId) {
		this.statuDemandesId = statuDemandesId;
	}

}