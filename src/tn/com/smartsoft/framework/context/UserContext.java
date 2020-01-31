package tn.com.smartsoft.framework.context;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBeanId;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.framework.security.Permission;
import tn.com.smartsoft.iap.administration.securite.profile.beans.PermissionBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserMenuBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;

public interface UserContext{
	
	public List<UserMenuBean> getUserMenu(String moduleId);
	public Map<String, String> getUserPreferences();
	public void addUserPreference(String key, String value);
	public abstract String getUserPreference(String key);
	public abstract int getIntUserPreference(String key, int defaultValue);
	public abstract Long getLongUserPreference(String key, Long defaultValue);
	public abstract List<OrganismeBean> getListOrganismes();
	public List<SocieteContext> getUserSocietes();
	public abstract SocieteContext getUserSociete(Long societeId);
	public abstract SocieteContext switchUserSociete(Long societeId);
	public SocieteContext switchDefaultUserSociete(Long societeId);
	public abstract OrganismeContext switchUserOrganisme(OrganismeBeanId organismeBeanId);
	public OrganismeContext switchDefaultUserOrganisme(OrganismeBeanId organismeBeanId);
	public abstract OrganismeContext getCurrentUserOrganisme();
	public abstract UserBean getUser();
	public abstract List<OrganismeContext> getUserOrganismes();
	public OrganismeContext getUserOrganisme(OrganismeBeanId organismeBeanId);
	public OrganismeContext getUserOrganisme(Long organismeId);
	public abstract boolean isGranted(String idModule, String roleId, ActionMode actionMode);
	public abstract boolean isGranted(ActionBeanId actionId);
	public abstract void setPropertysContext(DataBusinessBean dataBusinessBean, ActionMode mode, Timestamp curentDate);
	public abstract SocieteContext getCurrentUserSociete();
	public abstract Long getCurrentUserOrganismeId();
	public abstract Long getCurrentUserSocieteId();
	public abstract String userId();
	public void addPermission(String idModule, String idRole, Permission permission);
	public void addPermission(PermissionBean permissionBean);
}