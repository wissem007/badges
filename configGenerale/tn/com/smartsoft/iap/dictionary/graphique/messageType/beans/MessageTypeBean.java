package tn.com.smartsoft.iap.dictionary.graphique.messageType.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import java.io.Serializable;

public class MessageTypeBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long messageTypeId;
	private String libelle;

	public MessageTypeBean() {
		super();
	}

	public Serializable getId() {
		return this.getMessageTypeId();
	}

	public void setId(Long id) {
		this.setMessageTypeId(id);
	}

	public Long getMessageTypeId() {
		return this.messageTypeId;
	}

	public void setMessageTypeId(Long messageTypeId) {
		this.messageTypeId = messageTypeId;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}