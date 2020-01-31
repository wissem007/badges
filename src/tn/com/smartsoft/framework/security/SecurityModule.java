package tn.com.smartsoft.framework.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.iap.administration.securite.profile.beans.PermissionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;

public class SecurityModule implements Serializable{
	
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	private String					idModule;
	private Map<String, Permission>	permissionModules	= new HashMap<String, Permission>();
	private UserContext				userContext;
	private Long					profil;
	
	public SecurityModule(String idModule, Long profil, UserContext userContext) {
		super();
		this.idModule = idModule;
		this.profil = profil;
		this.userContext = userContext;
	}
	public void addPermission(String idRole, Permission permission) {
		permissionModules.put(idRole, permission);
	}
	public void addPermission(PermissionBean permissionBean) {
		Permission permission = ApplicationConfiguration.applicationManager().permissionFactory().create(permissionBean);
		this.addPermission(permissionBean.getRoleId(), permission);
	}
	public boolean isGranted(ActionBeanId actionId) {
		ActionBean actionBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getActionBean(actionId);
		return isGranted(actionBean.getRoleId(), new ActionMode(actionBean.getActionTypeId().intValue()));
	}
	public boolean isGranted(String idRole, ActionMode actionMode) {
		return getPermission(idRole).isGranted(actionMode);
	}
	public String getIdModule() {
		return idModule;
	}
	private Permission getPermission(String idRole) {
		if (StringUtils.isBlank(idModule)) { return StaticPermission.NONE; }
		ModuleBean moduleBean = getModuleBean();
		if (userContext.getUser().isSysAdmin()) {
			if (moduleBean.getType().intValue() > 1) { return StaticPermission.GRANTED; }
			return StaticPermission.NONE;
		}
		if (moduleBean.getType().intValue() == 3 || moduleBean.getType().intValue() == 0) { return StaticPermission.NONE; }
		if (profil == null) { return StaticPermission.NONE; }
		if (StringUtils.isBlank(idRole)) { return StaticPermission.GRANTED; }
		Permission permission = permissionModules.get(idRole);
		if (permission == null) { return StaticPermission.NONE; }
		return permission;
	}
	private ModuleBean getModuleBean() {
		ModuleBean moduleBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getModuleBean(idModule);
		return moduleBean;
	}
}
