package tn.com.smartsoft.iap.dictionary.decoupage.typeEntites.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import java.io.Serializable;

public class TypeEntitesBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long typeEntiteId;
	private String libelle;

	public TypeEntitesBean() {
		super();
	}

	public Serializable getId() {
		return this.getTypeEntiteId();
	}

	public void setId(Long id) {
		this.setTypeEntiteId(id);
	}

	public Long getTypeEntiteId() {
		return this.typeEntiteId;
	}

	public void setTypeEntiteId(Long typeEntiteId) {
		this.typeEntiteId = typeEntiteId;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}