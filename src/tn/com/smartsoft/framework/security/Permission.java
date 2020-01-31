package tn.com.smartsoft.framework.security;

import java.io.Serializable;

import tn.com.smartsoft.framework.presentation.utils.ActionMode;

public interface Permission extends Serializable{
	public abstract boolean isGranted(ActionMode mode);
}
