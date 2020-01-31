package tn.com.smartsoft.configGenerale.autresParametrage.dataEntitesSimples.beans;

import java.io.Serializable;
import java.sql.Date;

import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class DataEntitesSimpleBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String dataEntitesSimplesId;
	private String libelle;
	private String abreviation;
	private String matPersCre;
	private Date dateCreation;
	private String matPersMod;
	private Date dateModification;
	private String entitesSimplesId;
	private Long ordre;

	public DataEntitesSimpleBean(String dataEntitesSimplesId, String libelle) {
		super();
		this.dataEntitesSimplesId = dataEntitesSimplesId;
		this.libelle = libelle;
	}

	public Long getOrdre() {
		return ordre;
	}

	public void setOrdre(Long ordre) {
		this.ordre = ordre;
	}

	public DataEntitesSimpleBean(String entitesSimplesId) {
		this.entitesSimplesId = entitesSimplesId;
	}

	public DataEntitesSimpleBean() {
		super();
	}

	public Serializable getId() {
		return new DataEntitesSimpleBeanId(this);
	}

	public void setId(DataEntitesSimpleBeanId id) {
		id.copyValue(this);
	}

	public String getDataEntitesSimplesId() {
		return this.dataEntitesSimplesId;
	}

	public void setDataEntitesSimplesId(String dataEntitesSimplesId) {
		this.dataEntitesSimplesId = dataEntitesSimplesId;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getAbreviation() {
		return abreviation;
	}

	public void setAbreviation(String abreviation) {
		this.abreviation = abreviation;
	}

	public String getMatPersCre() {
		return this.matPersCre;
	}

	public void setMatPersCre(String matPersCre) {
		this.matPersCre = matPersCre;
	}

	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getMatPersMod() {
		return this.matPersMod;
	}

	public void setMatPersMod(String matPersMod) {
		this.matPersMod = matPersMod;
	}

	public Date getDateModification() {
		return this.dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	public String getEntitesSimplesId() {
		return this.entitesSimplesId;
	}

	public void setEntitesSimplesId(String entitesSimplesId) {
		this.entitesSimplesId = entitesSimplesId;
	}
}