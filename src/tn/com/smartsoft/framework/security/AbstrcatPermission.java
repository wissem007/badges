package tn.com.smartsoft.framework.security;

import tn.com.smartsoft.iap.dictionary.securite.role.beans.ItemRoleBean;

public abstract class AbstrcatPermission implements Permission{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	protected ItemRoleBean		role;
	protected Long				permission;
	
	public AbstrcatPermission(ItemRoleBean role, Long permission) {
		super();
		this.role = role;
		this.permission = permission;
	}
}
