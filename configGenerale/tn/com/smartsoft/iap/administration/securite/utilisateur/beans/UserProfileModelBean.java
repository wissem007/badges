package tn.com.smartsoft.iap.administration.securite.utilisateur.beans;

import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.framework.beans.BeanObject;
import tn.com.smartsoft.iap.administration.securite.profile.beans.ProfileBean;

public class UserProfileModelBean implements BeanObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OrganismeBean organisme = new OrganismeBean();
	private Map<String, ProfileBean> profiles = new HashMap<String, ProfileBean>();
	private String listActive;
	
	public UserProfileModelBean(OrganismeBean organisme) {
		super();
		this.organisme = organisme;
	}
	
	public String getListActive() {
		return listActive;
	}
	
	public void setListActive(String listActive) {
		this.listActive = listActive;
	}
	
	public OrganismeBean getOrganisme() {
		return organisme;
	}
	
	public void setOrganisme(OrganismeBean organisme) {
		this.organisme = organisme;
	}
	
	public Map<String, ProfileBean> getProfiles() {
		return profiles;
	}
	
	public void addProfile(String moduleId, ProfileBean profile) {
		this.profiles.put(moduleId, profile);
	}
	
	public void setProfiles(Map<String, ProfileBean> profiles) {
		this.profiles = profiles;
	}
	
}
