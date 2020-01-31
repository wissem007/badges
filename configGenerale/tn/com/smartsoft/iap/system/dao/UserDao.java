package tn.com.smartsoft.iap.system.dao;

import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserBean;

public interface UserDao {
	public UserBean getUserBean(DbSession daoSession, UserBean userBean);
	
	public UserBean getDisplayUser(DbSession daoSession, UserBean userBean);
	
	public OrganismeBean getOrganisation(DbSession daoSession, UserBean userBean);
	
}
