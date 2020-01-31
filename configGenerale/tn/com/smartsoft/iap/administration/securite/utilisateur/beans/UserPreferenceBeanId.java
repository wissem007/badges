package tn.com.smartsoft.iap.administration.securite.utilisateur.beans;

import java.io.Serializable;

import tn.com.smartsoft.commons.utils.ValueUtils;

public class UserPreferenceBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userPreferenceId;
	private String userId;

	public UserPreferenceBeanId() {
		super();
	}

	public UserPreferenceBeanId(UserPreferenceBean userPreferenceBean) {
		super();
		this.setUserId(userPreferenceBean.getUserId());
		this.setUserPreferenceId(userPreferenceBean.getUserPreferenceId());
	}

	public void copyValue(UserPreferenceBean objectValue) {
		objectValue.setUserId(this.getUserId());
		objectValue.setUserPreferenceId(this.getUserPreferenceId());
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPreferenceId() {
		return userPreferenceId;
	}

	public void setUserPreferenceId(String userPreferenceId) {
		this.userPreferenceId = userPreferenceId;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result + (this.getUserPreferenceId() == null ? 0 : this.getUserPreferenceId().hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserPreferenceBeanId))
			return false;
		UserPreferenceBeanId castOther = (UserPreferenceBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getUserId(), castOther.getUserId());
		result = result && ValueUtils.equals(this.getUserPreferenceId(), castOther.getUserPreferenceId());
		return result;
	}
}