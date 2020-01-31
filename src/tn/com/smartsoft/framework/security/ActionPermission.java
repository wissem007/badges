package tn.com.smartsoft.framework.security;

import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.ActionRoleBean;

public class ActionPermission extends AbstrcatPermission {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public ActionPermission(ActionRoleBean role, Long permission) {
		super(role, permission);
	}

	public boolean isGranted(ActionMode mode) {
		return permission.intValue() > 0;
	}
}
