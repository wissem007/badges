package tn.com.smartsoft.framework.security;

import tn.com.smartsoft.framework.presentation.utils.ActionMode;

public class StaticPermission implements Permission{
	
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	public static final Permission	NONE				= new StaticPermission(false);
	public static final Permission	GRANTED				= new StaticPermission(true);
	private boolean					permission;
	
	private StaticPermission(boolean permission) {
		this.permission = permission;
	}
	public boolean isGranted(ActionMode mode) {
		return permission;
	}
}
