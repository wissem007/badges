package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtLegendsComponent;

public class UIExtLegendsComponentRender extends UIExtComponentRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtLegendsComponentRender(UIExtLegendsComponent renderComponent) {
		super(renderComponent, "sss.ext.Legends");
	}

	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtLegendsComponent extRenderComponent = (UIExtLegendsComponent) renderComponent;
		extComponentJs().addValue("labelField", extRenderComponent.getLabelField());
		extComponentJs().addValue("colorField", extRenderComponent.getColorField());
		extComponentJs().addObjectValue("store", extRenderComponent.getStoreId());
	}

	public void renderChilds(UIRenderContext context) {
	}

}
