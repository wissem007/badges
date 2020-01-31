package tn.com.smartsoft.framework.presentation.view.action.controleur;

import java.io.Serializable;

public interface ActionControlerConfig extends Serializable{
	public Object getContolerBean();
	
	public String getMethodName();
}
