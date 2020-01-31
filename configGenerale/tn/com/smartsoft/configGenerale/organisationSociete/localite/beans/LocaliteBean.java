package tn.com.smartsoft.configGenerale.organisationSociete.localite.beans;

import tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.beans.GovernoratBean;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import java.io.Serializable;

public class LocaliteBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long localiteId;
	private String libelle;
	private String libelleArabe;
	private Long codePostal;
	private Long governoratId;
	private GovernoratBean governorat;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LocaliteBean() {
		super();
	}

	public Serializable getId() {
		return this.getLocaliteId();
	}

	public void setId(Long id) {
		this.setLocaliteId(id);
	}

	public Long getLocaliteId() {
		return this.localiteId;
	}

	public void setLocaliteId(Long localiteId) {
		this.localiteId = localiteId;
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

	public Long getGovernoratId() {
		return this.governoratId;
	}

	public void setGovernoratId(Long governoratId) {
		this.governoratId = governoratId;
	}

	public GovernoratBean getGovernorat() {
		return this.governorat;
	}

	public void setGovernorat(GovernoratBean gouvernorat) {
		this.governorat = gouvernorat;
	}

	public Long getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(Long codePostal) {
		this.codePostal = codePostal;
	}

}