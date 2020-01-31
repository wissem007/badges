package tn.com.smartsoft.iap.system.business;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.beans.FonctionUtilProfilBean;
import tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.beans.FonctionUtilisationBean;
import tn.com.smartsoft.configGenerale.administration.organismeProprietaires.beans.OrganismeProprietairesBean;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.context.impl.OrganismeContextImpl;
import tn.com.smartsoft.framework.context.impl.SocieteContextImpl;
import tn.com.smartsoft.framework.context.impl.UserContextBeanImpl;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.iap.administration.securite.profile.beans.PermissionBean;
import tn.com.smartsoft.iap.administration.securite.profile.beans.ProfileBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserMenuBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserPreferenceBean;
import tn.com.smartsoft.iap.system.dao.UserDao;

public class UserBusiness extends GenericEntiteBusiness {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	protected PasswordHashing	passwordHashing;
	protected UserDao			userDao;
	
	public UserBean getUser(UserBean user) throws FunctionalException {
		user.setPasseWord(passwordHashing.encryptPassword(user.getPasseWord()));
		UserBean userBean = userDao.getUserBean(daoSession, user);
		return userBean;
	}
	
	public UserBean getDisplayUser(UserBean user) throws FunctionalException {
		user.setPasseWord(passwordHashing.encryptPassword(user.getPasseWord()));
		UserBean userBean = userDao.getDisplayUser(daoSession, user);
		return userBean;
	}
	
	public UserContext authenticate(UserBean user) throws FunctionalException {
		UserBean userBean = userDao.getUserBean(daoSession, user);
		DbPersistantSupport persistantSupport = daoSession.persistantSupport(null);
		OrganismeBean organisme = userDao.getOrganisation(daoSession, userBean);
		int orgUserCount = 1;
		if (userBean == null)
			throw new FunctionalException("0120001");
		String encryptPassword = StringUtils.isBlank(user.getPasseWord()) ? "" : passwordHashing.encryptPassword(user.getPasseWord());
		String encryptPasswordDb = StringUtils.isBlank(userBean.getPasseWord()) ? "" : userBean.getPasseWord();
		if (!StringUtils.equals(encryptPasswordDb, encryptPassword)) {
			throw new FunctionalException("0120001");
		}
		UserContextBeanImpl userContextBean = new UserContextBeanImpl(userBean);
		if (!userBean.isSysAdmin()) {
			FonctionUtilisationBean fonctionUtilisation = userBean.getFonctionUtilisation();
			OrganismeProprietairesBean organismeBean = new OrganismeProprietairesBean(organisme);
			OrganismeContextImpl organismeContextBean = new OrganismeContextImpl(organismeBean, userContextBean);
			List<FonctionUtilProfilBean> profils = fonctionUtilisation.getProfils();
			for (int i = 0; i < profils.size(); i++) {
				FonctionUtilProfilBean fonctionUtilProfil = profils.get(i);
				userContextBean.addSecurityModule(fonctionUtilProfil.getModuleId(), fonctionUtilProfil.getProfileId());
				ProfileBean profil = fonctionUtilProfil.getProfil();
				profil = (ProfileBean) persistantSupport.get(ProfileBean.class, profil.getId());
				Map<String, PermissionBean> permissionBeans = profil.getPermissions();
				for (Iterator<String> iterator = permissionBeans.keySet().iterator(); iterator.hasNext();) {
					String permission = (String) iterator.next();
					PermissionBean permissionBean = permissionBeans.get(permission);
					userContextBean.addPermission(permissionBean);
					orgUserCount++;
				}
				SocieteContextImpl societeContext = (SocieteContextImpl) userContextBean.getUserSociete(organismeBean.getSocieteId());
				if (societeContext == null) {
					societeContext = new SocieteContextImpl(organismeBean.getOrganisme().getParentSociete(), userContextBean);
					userContextBean.addUserSociete(societeContext);
				}
				societeContext.addUserOrganisme(organismeContextBean);
			}
			if (orgUserCount == 0) {
				throw new FunctionalException("0120020");
			}
		} else {
			OrganismeProprietairesBean organismeBean = new OrganismeProprietairesBean(organisme);
			OrganismeContextImpl organismeContextBean = new OrganismeContextImpl(organismeBean, userContextBean);
			SocieteContextImpl societeContext = (SocieteContextImpl) userContextBean.getUserSociete(organismeBean.getSocieteId());
			if (societeContext == null) {
				societeContext = new SocieteContextImpl(organismeBean.getOrganisme().getParentSociete(), userContextBean);
				userContextBean.addUserSociete(societeContext);
			}
			Collection<String> modulBeans = ApplicationConfiguration.applicationManager().applicationDefinition().getModulesDefs().keySet();
			for (Iterator<String> iterator = modulBeans.iterator(); iterator.hasNext();) {
				String moduleId = iterator.next();
				userContextBean.addSecurityModule(moduleId, new Long(1));
			}
			societeContext.addUserOrganisme(organismeContextBean);
		}
		Map<String, UserPreferenceBean> userPreferences = userBean.getPreferences();
		for (Iterator<String> iterator = userPreferences.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			UserPreferenceBean userPreference = userPreferences.get(key);
			userContextBean.addUserPreference(key, userPreference.getValue());
		}
		List<UserMenuBean> menuPreferences = userBean.getMenuPreferences();
		for (int i = 0; i < menuPreferences.size(); i++) {
			userContextBean.addUserMenu(menuPreferences.get(i));
		}
		userContextBean.init();
		return userContextBean;
	}
	
	public void changePassWord(UserContext userContext, UserBean user) throws FunctionalException {
		try {
			String encryptPassword = StringUtils.isBlank(user.getOldPasseWord()) ? "" : passwordHashing.encryptPassword(user.getOldPasseWord());
			String encryptPasswordDb = StringUtils.isBlank(userContext.getUser().getOldEditPasseWord()) ? "" : userContext.getUser().getOldEditPasseWord();
			if (!StringUtils.equals(encryptPasswordDb, encryptPassword)) {
				throw new FunctionalException("0130030");
			}
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();
			UserBean userSave = (UserBean) persistantSupport.get(UserBean.class, user.getId());
			user.setPasseWord(passwordHashing.encryptPassword(user.getPasseWord()));
			userSave.setPasseWord(user.getPasseWord());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	
	public void savePreferance(UserContext userContext, UserBean user) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();
			Map<String, UserPreferenceBean> preferences = user.getPreferences();
			Iterator<String> iterator = preferences.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				UserPreferenceBean value = (UserPreferenceBean) preferences.get(key);
				persistantSupport.saveOrUpdate(value);
			}
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setPasswordHashing(PasswordHashing passwordHashing) {
		this.passwordHashing = passwordHashing;
	}
}
