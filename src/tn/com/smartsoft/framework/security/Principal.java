package tn.com.smartsoft.framework.security;

import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;

public interface Principal {

	public String userId();

	public abstract boolean granted(String roleId, ActionMode ActionMode);

	public abstract boolean granted(ActionBeanId actionId);
}
