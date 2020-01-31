package tn.com.smartsoft.framework.presentation.view.window.component.ext.toolbar.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtGenericContainerRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.toolbar.UIExtToolbar;

public class UIExtToolbarRender extends UIExtGenericContainerRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtToolbarRender(UIExtToolbar renderComponent) {
		super(renderComponent, "Ext.Toolbar");
	}

	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtToolbar extRenderComponent = (UIExtToolbar) renderComponent;
		extComponentJs().addStringValue("buttonAlign", extRenderComponent.getButtonAlign());
	}
}
