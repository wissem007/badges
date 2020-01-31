package tn.com.smartsoft.iap.administration.securite.profile.beans;

import java.io.Serializable;
import tn.com.smartsoft.commons.utils.ValueUtils;

public class ProfileBeanId implements Serializable{

	private static final long	serialVersionUID	= 1L;
	private Long				profileId;
	protected String			moduleId;

	public ProfileBeanId() {
		super();
	}
	public ProfileBeanId(Long profileId, String moduleId) {
		super();
		this.profileId = profileId;
		this.moduleId = moduleId;
	}
	public ProfileBeanId(ProfileBean profileBean) {
		super();
		this.setProfileId(profileBean.getProfileId());
		this.setModuleId(profileBean.getModuleId());
	}
	public void copyValue(ProfileBean objectValue) {
		objectValue.setProfileId(this.getProfileId());
		objectValue.setModuleId(this.getModuleId());
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public Long getProfileId() {
		return this.profileId;
	}
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getProfileId() == null ? 0 : this.getProfileId().hashCode());
		result = 37 * result + (this.getModuleId() == null ? 0 : this.getModuleId().hashCode());
		return result;
	}
	public boolean equals(Object other) {
		if ((this == other)) return true;
		if ((other == null)) return false;
		if (!(other instanceof ProfileBeanId)) return false;
		ProfileBeanId castOther = (ProfileBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getProfileId(), castOther.getProfileId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		return result;
	}
}