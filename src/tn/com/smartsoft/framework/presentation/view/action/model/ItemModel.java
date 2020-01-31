package tn.com.smartsoft.framework.presentation.view.action.model;

import java.io.Serializable;

import tn.com.smartsoft.framework.presentation.utils.ActionMode;

public interface ItemModel extends Serializable {
	public String getName();

	public Class<?> getJavaType();

	public String getLibelle();

	public ActionMode getMode();

	public void setMode(ActionMode mode);
}
