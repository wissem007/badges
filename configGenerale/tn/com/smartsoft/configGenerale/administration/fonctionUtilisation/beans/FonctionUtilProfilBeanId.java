package tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.beans;

import java.io.Serializable;
import tn.com.smartsoft.commons.utils.ValueUtils;

public class FonctionUtilProfilBeanId implements Serializable{
	
	private static final long	serialVersionUID	= 1L;
	private Long				fonctionUtilisationId;
	private Long				profileId;
	private String				moduleId;
	
	public FonctionUtilProfilBeanId() {
		super();
	}
	public FonctionUtilProfilBeanId(FonctionUtilProfilBean fonctionUtilProfilBean) {
		super();
		this.setFonctionUtilisationId(fonctionUtilProfilBean.getFonctionUtilisationId());
		this.setProfileId(fonctionUtilProfilBean.getProfileId());
		this.setModuleId(fonctionUtilProfilBean.getModuleId());
	}
	public void copyValue(FonctionUtilProfilBean objectValue) {
		objectValue.setFonctionUtilisationId(this.getFonctionUtilisationId());
		objectValue.setProfileId(this.getProfileId());
		objectValue.setModuleId(this.getModuleId());
	}
	public Long getFonctionUtilisationId() {
		return this.fonctionUtilisationId;
	}
	public void setFonctionUtilisationId(Long fonctionUtilisationId) {
		this.fonctionUtilisationId = fonctionUtilisationId;
	}
	public Long getProfileId() {
		return this.profileId;
	}
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	public String getModuleId() {
		return this.moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getFonctionUtilisationId() == null ? 0 : this.getFonctionUtilisationId().hashCode());
		result = 37 * result + (this.getProfileId() == null ? 0 : this.getProfileId().hashCode());
		result = 37 * result + (this.getModuleId() == null ? 0 : this.getModuleId().hashCode());
		return result;
	}
	public boolean equals(Object other) {
		if ((this == other)) return true;
		if ((other == null)) return false;
		if (!(other instanceof FonctionUtilProfilBeanId)) return false;
		FonctionUtilProfilBeanId castOther = (FonctionUtilProfilBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getFonctionUtilisationId(), castOther.getFonctionUtilisationId());
		result = result && ValueUtils.equals(this.getProfileId(), castOther.getProfileId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		return result;
	}
}