package tn.com.smartsoft.iap.dictionary.graphique.message.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.subModule.beans.SubModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBean;
import tn.com.smartsoft.iap.dictionary.graphique.messageType.beans.MessageTypeBean;

public class MessageBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String messagesId;
	private Long messagesTypeId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	private String libelle;
	private Boolean isAlert;
	private MessageTypeBean messageType;
	private SujetBean parentSujet;
	private SubModuleBean parentSubModule;
	private ModuleBean parentModule;


	public SubModuleBean getParentSubModule() {
		return parentSubModule;
	}

	public void setParentSubModule(SubModuleBean parentSubModule) {
		this.parentSubModule = parentSubModule;
	}

	public ModuleBean getParentModule() {
		return parentModule;
	}

	public void setParentModule(ModuleBean parentModule) {
		this.parentModule = parentModule;
	}

	public MessageBean() {
		super();
	}

	public Serializable getId() {
		return this.getMessagesId();
	}

	public void setId(String id) {
		this.setMessagesId(id);
	}

	public String getMessagesId() {
		return this.messagesId;
	}

	public void setMessagesId(String messagesId) {
		this.messagesId = messagesId;
	}

	public Long getMessagesTypeId() {
		return this.messagesTypeId;
	}

	public void setMessagesTypeId(Long messagesTypeId) {
		this.messagesTypeId = messagesTypeId;
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

	public Boolean getIsAlert() {
		return isAlert;
	}

	public void setIsAlert(Boolean isAlert) {
		this.isAlert = isAlert;
	}

	public MessageTypeBean getMessageType() {
		return this.messageType;
	}

	public void setMessageType(MessageTypeBean messageType) {
		this.messageType = messageType;
	}

	public SujetBean getParentSujet() {
		return this.parentSujet;
	}

	public void setParentSujet(SujetBean parentSujet) {
		this.parentSujet = parentSujet;
	}
}