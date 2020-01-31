package tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;
import java.io.Serializable;

public class GovernoratBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long governoratId;
	private String libelle;
	private String libelleArabe;
	private Long paysId;
	private PayBean pays;

	public GovernoratBean() {
		super();
	}

	public Serializable getId() {
		return this.getGovernoratId();
	}

	

	public Long getGovernoratId() {
		return governoratId;
	}

	public void setGovernoratId(Long governoratId) {
		this.governoratId = governoratId;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelleArabe() {
		return this.libelleArabe;
	}

	public void setLibelleArabe(String libelleArabe) {
		this.libelleArabe = libelleArabe;
	}

	public Long getPaysId() {
		return this.paysId;
	}

	public void setPaysId(Long paysId) {
		this.paysId = paysId;
	}

	public PayBean getPays() {
		return this.pays;
	}

	public void setPays(PayBean pays) {
		this.pays = pays;
	}
}