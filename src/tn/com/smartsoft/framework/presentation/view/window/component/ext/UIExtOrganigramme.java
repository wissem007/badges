package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtOrganigrammeRender;

public class UIExtOrganigramme extends UIExtPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UIRender getRender() {
		return new UIExtOrganigrammeRender(this);
	}
}
