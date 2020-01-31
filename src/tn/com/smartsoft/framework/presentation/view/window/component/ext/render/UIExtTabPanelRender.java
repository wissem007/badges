package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtTabPanel;

public class UIExtTabPanelRender extends UIExtPanelRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtTabPanelRender(UIExtTabPanel renderComponent) {
		super(renderComponent, "Ext.TabPanel");
	}

	public void loadConfigs(UIRenderContext context) {
		UIExtTabPanel extRenderComponent = (UIExtTabPanel) renderComponent;
		super.loadConfigs(context);
		extComponentJs().addObjectValue("activeTab", extRenderComponent.getActiveTab());
	}

}
