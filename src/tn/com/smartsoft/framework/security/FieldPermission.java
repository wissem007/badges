package tn.com.smartsoft.framework.security;

import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.FieldRoleBean;

public class FieldPermission extends AbstrcatPermission {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public FieldPermission(FieldRoleBean role, Long permission) {
		super(role, permission);
	}

	public boolean isGranted(ActionMode mode) {
		int permissionValue = GrantedAuthorityUtils.getFieldsPermission(mode, permission);
		return permissionValue > 0;
	}
}
