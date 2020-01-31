package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtUseComponent;
import tn.com.smartsoft.framework.presentation.view.window.render.UIWindowRootRender;

public class UIExtUseComponentRender extends UIExtContainerRender {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UIExtUseComponentRender(UIExtUseComponent renderComponent) {
		super(renderComponent);
	}
	
	public void loadConfigs(UIRenderContext context) {
		UIExtUseComponent extComponent = (UIExtUseComponent) renderComponent;
		super.loadConfigs(context);
		extComponentJs().addObjectValue("xtype", extComponent.getXtype());
	}
	
	public void renderEnd(UIRenderContext context) {
		UIExtUseComponent extComponent = (UIExtUseComponent) renderComponent;
		UIEventRenderUtils.renderEvents(context, extComponent, getParentRender(), extComponentJs);
		context.addComponentsToBufferJs(extComponentJs.newInstance(true, false));
		if (parentRender instanceof UIWindowRootRender) {
			context.renderJsAddToContainer(extComponentJs.getInstanceName());
		}
		beforRender(context);
	}
}
