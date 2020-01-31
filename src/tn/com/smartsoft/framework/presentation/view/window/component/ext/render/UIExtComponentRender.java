package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtAttrebutComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;
import tn.com.smartsoft.framework.presentation.view.window.render.UIWindowRootRender;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;

public abstract class UIExtComponentRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected UIExtComponentJs extComponentJs;

	public UIExtComponentRender(UIExtComponent renderComponent, String jsObjectName) {
		super(renderComponent);
		this.extComponentJs = new UIExtComponentJs(jsObjectName, renderComponent.getId());
	}

	public void afterRender(UIRenderContext context) {

	}

	public void beforRender(UIRenderContext context) {

	}

	public void renderStart(UIRenderContext context) {
		this.afterRender(context);
		this.loadConfigs(context);
	}

	public abstract void renderChilds(UIRenderContext context);

	public void renderEnd(UIRenderContext context) {
		UIExtComponent extComponent = (UIExtComponent) renderComponent;
		UIEventRenderUtils.renderEvents(context, extComponent, getParentRender(), extComponentJs);
		context.addComponentsToBufferJs(extComponentJs.newInstance());
		boolean isContainerParent = parentRender instanceof UIWindowRootRender || parentRender instanceof UIExtDialogRender;
		if (isContainerParent && extComponent.isAddedToParent() && renderToComponentBufferJs) {
			context.renderJsAddToContainer(extComponentJs.getInstanceName());
		} else if (isContainerParent && extComponent.isAddedToParent() && !renderToComponentBufferJs) {
			context.renderJsAddToolItem(extComponentJs.getInstanceName());
		}
		beforRender(context);
	}

	public void render(UIRenderContext context) {
		UIExtComponent extComponent = (UIExtComponent) renderComponent;
		if (!extComponent.isRendred())
			return;
		renderStart(context);
		renderChilds(context);
		renderEnd(context);
	}

	public void loadConfigs(UIRenderContext context) {
		UIExtComponent extRenderComponent = (UIExtComponent) renderComponent;
		extComponentJs().addStringValue("id", extRenderComponent.getId());
		extComponentJs().addStringValue("cls", extRenderComponent.getCls());
		extComponentJs().addStringValue("ctCls", extRenderComponent.getCtCls());
		if (extRenderComponent.isHidden())
			extComponentJs().addBooleanValue("hidden", extRenderComponent.isHidden());
		extComponentJs().addValue("anchor", extRenderComponent.getAnchor());
		extComponentJs().addObjectValue("desktop", context.getDesktopJs().getInsatanceName());
		extComponentJs().addValues(extRenderComponent);
		for (int i = 0; i < extRenderComponent.attrebutComponentSize(); i++) {
			UIExtAttrebutComponent child = extRenderComponent.getAttrebutComponent((String) extRenderComponent.attrebutComponentIds().get(i));
			UIRendrableComponent rendrableComponent = (UIRendrableComponent) child;
			UIExtAttrebutComponentRender uiRender = (UIExtAttrebutComponentRender) rendrableComponent.getRender();
			uiRender.setParentRender(this);
			uiRender.render(context, extComponentJs());
		}
	}

	public UIExtComponentJs extComponentJs() {
		return extComponentJs;
	}

}
