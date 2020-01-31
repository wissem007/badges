package tn.com.smartsoft.framework.security;

import java.io.Serializable;

import tn.com.smartsoft.iap.administration.securite.profile.beans.PermissionBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.ActionRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.FieldRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.role.beans.RoleBean;

public class PermissionFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Permission create(PermissionBean permissionBean) {
		return create(permissionBean.getRole(), permissionBean.getPermission());
	}

	public Permission create(RoleBean role, Long permission) {
		if (role instanceof FieldRoleBean) {
			return new FieldPermission((FieldRoleBean) role, permission);
		} else {
			return new ActionPermission((ActionRoleBean) role, permission);
		}
	}
}
