package tn.com.smartsoft.framework.context.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBeanId;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.context.OrganismeContext;
import tn.com.smartsoft.framework.context.SocieteContext;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.framework.security.Permission;
import tn.com.smartsoft.framework.security.SecurityContext;
import tn.com.smartsoft.iap.administration.securite.profile.beans.PermissionBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserMenuBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.system.ConstantUser;

public class UserContextBeanImpl implements UserContext{
	
	/**
	 * 
	 */
	private Map<Long, SocieteContext>		societeContexts	= new HashMap<Long, SocieteContext>();
	private UserBean						userInfo;
	private OrganismeContext				currentUserOrganisme;
	private SocieteContext					currentUserSociete;
	private Map<String, String>				userPreferences	= new HashMap<String, String>();
	private Map<String, List<UserMenuBean>>	menuPreferences	= new HashMap<String, List<UserMenuBean>>();
	private SecurityContext					securityManager;
	
	public SecurityContext getSecurityManager() {
		if (securityManager == null) this.securityManager = new SecurityContext(this);
		return this.securityManager;
	}
	public void addSecurityModule(String idModule, Long profil) {
		getSecurityManager().addSecurityModule(idModule, profil);
	}
	public UserContextBeanImpl(UserBean userInfo) {
		super();
		this.userInfo = userInfo;
	}
	public Map<String, String> getUserPreferences() {
		return userPreferences;
	}
	public void addUserMenu(UserMenuBean value) {
		List<UserMenuBean> moduleMenu = menuPreferences.get(value.getModuleId());
		if (moduleMenu == null) {
			moduleMenu = new ArrayList<UserMenuBean>();
			menuPreferences.put(value.getModuleId(), moduleMenu);
		}
		moduleMenu.add(value);
	}
	public List<UserMenuBean> getUserMenu(String moduleId) {
		return menuPreferences.get(moduleId);
	}
	public void addUserPreference(String key, String value) {
		userPreferences.put(key, value);
	}
	public String getUserPreference(String key) {
		return userPreferences.get(key);
	}
	public int getIntUserPreference(String key, int defaultValue) {
		if (userPreferences.containsKey(key)) return Integer.parseInt(userPreferences.get(key));
		else return defaultValue;
	}
	public Long getLongUserPreference(String key, Long defaultValue) {
		if (userPreferences.containsKey(key)) return new Long(Integer.parseInt(userPreferences.get(key)));
		else return defaultValue;
	}
	public List<OrganismeBean> getListOrganismes() {
		return getCurrentUserSociete().getListOrganismes();
	}
	public void addUserSociete(SocieteContext societeContext) {
		societeContexts.put(societeContext.getSocieteBean().getSocieteId(), societeContext);
	}
	public SocieteContext getUserSociete(Long societeId) {
		return societeContexts.get(societeId);
	}
	public SocieteContext switchUserSociete(Long societeId) {
		currentUserSociete = getUserSociete(societeId);
		currentUserOrganisme = getCurrentUserSociete().switchDefaultUserOrganisme(new Long(0));
		return getCurrentUserSociete();
	}
	public SocieteContext switchDefaultUserSociete(Long societeId) {
		if (!societeContexts.containsKey(societeId)) societeId = societeContexts.keySet().iterator().next();
		return switchUserSociete(societeId);
	}
	public OrganismeContext switchDefaultUserOrganisme(OrganismeBeanId organismeBeanId) {
		if (societeContexts.size() == 0) return null;
		Long societeId = organismeBeanId.getSocieteId();
		if (!societeContexts.containsKey(societeId)) societeId = societeContexts.keySet().iterator().next();
		currentUserSociete = getUserSociete(societeId);
		currentUserOrganisme = getCurrentUserSociete().switchDefaultUserOrganisme(organismeBeanId.getOrganismeId());
		return currentUserOrganisme;
	}
	public OrganismeContext switchUserOrganisme(OrganismeBeanId organismeBeanId) {
		currentUserSociete = getUserSociete(organismeBeanId.getSocieteId());
		currentUserOrganisme = getCurrentUserSociete().switchUserOrganisme(organismeBeanId.getOrganismeId());
		return currentUserOrganisme;
	}
	public OrganismeContext getUserOrganisme(OrganismeBeanId organismeBeanId) {
		SocieteContext societeContext = getUserSociete(organismeBeanId.getSocieteId());
		return societeContext.getUserOrganisme(organismeBeanId.getOrganismeId());
	}
	public OrganismeContext getUserOrganisme(Long organismeId) {
		return getCurrentUserSociete().getUserOrganisme(organismeId);
	}
	public OrganismeContext getCurrentUserOrganisme() {
		return currentUserOrganisme;
	}
	public UserBean getUser() {
		return userInfo;
	}
	public List<OrganismeContext> getUserOrganismes() {
		return getCurrentUserSociete().getUserOrganismes();
	}
	public void setPropertysContext(DataBusinessBean dataBusinessBean, ActionMode mode, Timestamp curentDate) {
		dataBusinessBean.setParentOrganismeId(currentUserOrganisme.getOrganismeBean().getOrganismeId());
		dataBusinessBean.setParentSocieteId(currentUserOrganisme.getOrganismeBean().getParentSocieteId());
		if (ActionMode.isUpdateMode(mode)) {
			dataBusinessBean.setUpdatedById(userInfo.getUserId());
			dataBusinessBean.setUpdatedDate(curentDate);
		}
		if (ActionMode.isCreateMode(mode)) {
			dataBusinessBean.setCreatedById(userInfo.getUserId());
			dataBusinessBean.setCreatedDate(curentDate);
		}
	}
	public void init() {
		OrganismeBeanId organismeBeanId = new OrganismeBeanId();
		organismeBeanId.setSocieteId(this.getLongUserPreference(ConstantUser.SOCIETE_PREFERENTCE_KEY, new Long(0)));
		organismeBeanId.setOrganismeId(this.getLongUserPreference(ConstantUser.ORGANISME_PREFERENTCE_KEY, new Long(0)));
		this.switchDefaultUserOrganisme(organismeBeanId);
	}
	public SocieteContext getCurrentUserSociete() {
		return currentUserSociete;
	}
	public Long getCurrentUserOrganismeId() {
		return currentUserOrganisme.getOrganismeBean().getOrganismeId();
	}
	public Long getCurrentUserSocieteId() {
		return currentUserOrganisme.getOrganismeBean().getSocieteId();
	}
	public boolean isSysAdmin() {
		return userInfo.isSysAdmin();
	}
	public String userId() {
		return userInfo.getUserId();
	}
	public void addPermission(String idModule, String idRole, Permission permission) {
		getSecurityManager().addPermission(idModule, idRole, permission);
	}
	public void addPermission(PermissionBean permissionBean) {
		getSecurityManager().addPermission(permissionBean);
	}
	public boolean isGranted(ActionBeanId actionId) {
		return getSecurityManager().isGranted(actionId);
	}
	public boolean isGranted(String idModule, String idRole, ActionMode actionMode) {
		return getSecurityManager().isGranted(idModule, idRole, actionMode);
	}
	public List<SocieteContext> getUserSocietes() {
		return new ArrayList<SocieteContext>(societeContexts.values());
	}
}
