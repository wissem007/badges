package tn.com.smartsoft.iap.administration.securite.utilisateur.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class UserPreferenceBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String userPreferenceId;
	private String userId;
	private String value;

	public UserPreferenceBean(String userPreferenceId, String userId, String value) {
		super();
		this.userPreferenceId = userPreferenceId;
		this.userId = userId;
		this.value = value;
	}

	public UserPreferenceBean() {
		super();
	}

	public Serializable getId() {
		return new UserPreferenceBeanId(this);
	}

	public void setId(UserPreferenceBeanId id) {
		id.copyValue(this);
	}

	public String getUserPreferenceId() {
		return this.userPreferenceId;
	}

	public void setUserPreferenceId(String userPreferenceId) {
		this.userPreferenceId = userPreferenceId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}