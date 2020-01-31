package tn.com.smartsoft.iap.dictionary.decoupage.niveauApplicatif.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import java.io.Serializable;

public class NiveauApplicatifBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long niveauApplicatifId;
	private String libelle;
	private String abreviation;

	public NiveauApplicatifBean() {
		super();
	}

	public Serializable getId() {
		return this.getNiveauApplicatifId();
	}

	public void setId(Long id) {
		this.setNiveauApplicatifId(id);
	}

	public Long getNiveauApplicatifId() {
		return this.niveauApplicatifId;
	}

	public void setNiveauApplicatifId(Long niveauApplicatifId) {
		this.niveauApplicatifId = niveauApplicatifId;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getAbreviation() {
		return this.abreviation;
	}

	public void setAbreviation(String abreviation) {
		this.abreviation = abreviation;
	}
}