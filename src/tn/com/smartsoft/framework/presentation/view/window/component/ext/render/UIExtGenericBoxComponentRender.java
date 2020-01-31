package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtBoxComponent;

public abstract class UIExtGenericBoxComponentRender extends UIExtComponentRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtGenericBoxComponentRender(UIExtBoxComponent renderComponent, String jsObjectName) {
		super(renderComponent, jsObjectName);
	}

	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtBoxComponent extRenderComponent = (UIExtBoxComponent) renderComponent;
		extComponentJs().addValue("width", extRenderComponent.getWidth());
		extComponentJs().addValue("height", extRenderComponent.getHeight());
		extComponentJs().addObjectValue("autoHeight", extRenderComponent.isAutoHeight());
		extComponentJs().addObjectValue("autoWidth", extRenderComponent.isAutoWidth());
	}

	public void renderChilds(UIRenderContext context) {
	}
}
