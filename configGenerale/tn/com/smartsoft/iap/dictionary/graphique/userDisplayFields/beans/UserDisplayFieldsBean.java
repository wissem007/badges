package tn.com.smartsoft.iap.dictionary.graphique.userDisplayFields.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.graphique.userType.beans.UserTypeBean;

public class UserDisplayFieldsBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String userDisplayFieldId;
	private String userTypeId;
	private String libelle;
	private String tagValue;
	private UserTypeBean userType;

	public UserDisplayFieldsBean() {
		super();
	}

	public Serializable getId() {
		return this.getUserDisplayFieldId();
	}

	public void setId(String id) {
		this.setUserDisplayFieldId(id);
	}

	public String getUserDisplayFieldId() {
		return this.userDisplayFieldId;
	}

	public void setUserDisplayFieldId(String userDisplayFieldId) {
		this.userDisplayFieldId = userDisplayFieldId;
	}

	public String getUserTypeId() {
		return this.userTypeId;
	}

	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getTagValue() {
		return this.tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public void setUserType(UserTypeBean userType) {
		this.userType = userType;
	}

	public UserTypeBean getUserType() {
		return userType;
	}

}