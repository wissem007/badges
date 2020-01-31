package tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.beans;

import java.io.Serializable;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.administration.securite.profile.beans.ProfileBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;

public class FonctionUtilProfilBean extends DataBusinessBean{
	
	private static final long	serialVersionUID	= 1L;
	private Long				fonctionUtilisationId;
	private Long				profileId;
	private String				moduleId;
	private ProfileBean			profil;
	private ModuleBean			moduleBean;
	
	public FonctionUtilProfilBean() {
		super();
	}
	public Serializable getId() {
		return new FonctionUtilProfilBeanId(this);
	}
	public void setId(FonctionUtilProfilBeanId id) {
		id.copyValue(this);
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
	public ProfileBean getProfil() {
		return this.profil;
	}
	public void setProfil(ProfileBean profil) {
		this.profil = profil;
	}
	public ModuleBean getModuleBean() {
		return moduleBean;
	}
	public void setModuleBean(ModuleBean moduleBean) {
		this.moduleBean = moduleBean;
	}
}