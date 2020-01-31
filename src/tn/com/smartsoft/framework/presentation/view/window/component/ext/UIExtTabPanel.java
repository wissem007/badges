package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtTabPanelRender;

public class UIExtTabPanel extends UIExtPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int activeTab;

	public int getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(int activeTab) {
		this.activeTab = activeTab;
	}

	public UIRender getRender() {
		return new UIExtTabPanelRender(this);
	}
}
