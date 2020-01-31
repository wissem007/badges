package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.UIExtFormPanel;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtPanelRender;

public class UIExtFormPanelRender extends UIExtPanelRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtFormPanelRender(UIExtFormPanel renderComponent) {
		super(renderComponent, "Ext.form.FormPanel");
	}

	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtFormPanel extRenderComponent = (UIExtFormPanel) renderComponent;
		extComponentJs().addStringValue("monitorValid", extRenderComponent.getMonitorValid());
		extComponentJs().addBooleanValue("standardSubmit", extRenderComponent.isStandardSubmit());
	}
}
