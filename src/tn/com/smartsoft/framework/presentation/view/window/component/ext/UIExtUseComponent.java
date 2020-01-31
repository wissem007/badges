package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtUseComponentRender;

public class UIExtUseComponent extends UIExtContainer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String xtype;
	
	public UIRender getRender() {
		return new UIExtUseComponentRender(this);
	}
	
	public String getXtype() {
		return xtype;
	}
	
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}
}
