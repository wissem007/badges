package tn.com.smartsoft.framework.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.iap.administration.securite.profile.beans.PermissionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;

public class SecurityContext implements Serializable{
	
	/**
	 * 
	 */
	private static final long			serialVersionUID	= 1L;
	private Map<String, SecurityModule>	permissionModules	= new HashMap<String, SecurityModule>();
	private UserContext					userContext;
	
	public SecurityContext(UserContext userContext) {
		super();
		this.userContext = userContext;
	}
	public void addSecurityModule(SecurityModule securityModule) {
		permissionModules.put(securityModule.getIdModule(), securityModule);
	}
	public SecurityModule addSecurityModule(String idModule, Long profil) {
		SecurityModule value = new SecurityModule(idModule, profil, userContext);
		permissionModules.put(idModule, value);
		return value;
	}
	public SecurityModule getSecurityModule(String idModule) {
		return permissionModules.get(idModule);
	}
	public void addPermission(String idModule, String idRole, Permission permission) {
		getSecurityModule(idModule).addPermission(idRole, permission);
	}
	public void addPermission(PermissionBean permissionBean) {
		getSecurityModule(permissionBean.getModuleId()).addPermission(permissionBean);
	}
	public boolean isGranted(ActionBeanId actionId) {
		if (!permissionModules.containsKey(actionId.getModuleId())) return false;
		ActionBean actionBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getActionBean(actionId);
		if(actionBean==null)
			return false;
		return isGranted(actionBean.getModuleId(), actionBean.getRoleId(), new ActionMode(actionBean.getActionTypeId().intValue()));
	}
	public boolean isGranted(String idModule, String idRole, ActionMode actionMode) {
		if (!permissionModules.containsKey(idModule)) return false;
		return permissionModules.get(idModule).isGranted(idRole, actionMode);
	}
}
