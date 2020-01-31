package tn.com.smartsoft.iap.system.presentation.model;

import java.io.Serializable;

import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserBean;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBeanId;

public class LoginModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserBean userBean = new UserBean();
	private OrganismeBeanId organismeId = new OrganismeBeanId();

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public OrganismeBeanId getOrganismeId() {
		return organismeId;
	}

	public void setOrganismeId(OrganismeBeanId organismeId) {
		this.organismeId = organismeId;
	}
}
