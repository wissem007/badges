package tn.com.smartsoft.iap.dictionary.decoupage.actionType.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import java.io.Serializable;

public class ActionTypeBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long actionTypeId;
	private String libelle;

	public ActionTypeBean() {
		super();
	}

	public Serializable getId() {
		return this.getActionTypeId();
	}

	public void setId(Long id) {
		this.setActionTypeId(id);
	}

	public Long getActionTypeId() {
		return this.actionTypeId;
	}

	public void setActionTypeId(Long actionTypeId) {
		this.actionTypeId = actionTypeId;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}