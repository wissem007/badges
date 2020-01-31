package tn.com.smartsoft.framework.presentation.view.window.render;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIComponentHandler;

public abstract class UIHandlableComponentRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIHandlableComponentRender(UIComponentHandler<UIComponent> renderComponent) {
		super((UIRendrableComponent) renderComponent);
	}

	public abstract void renderStart(UIRenderContext context);

	public abstract void renderEnd(UIRenderContext context);

	public void render(UIRenderContext context) {
		renderStart(context);
		renderChilds(context);
		renderEnd(context);
	}

	@SuppressWarnings("unchecked")
	protected void renderChilds(UIRenderContext context) {
		UIComponentHandler<UIComponent> treeComponentHandler = (UIComponentHandler<UIComponent>) renderComponent;
		for (int i = 0; i < treeComponentHandler.itemIds().size(); i++) {
			UIComponent child = treeComponentHandler.getItem((String) treeComponentHandler.itemIds().get(i));
			if (child instanceof UIRendrableComponent && ((UIRendrableComponent) child).isRendred()) {
				UIRender uiRender = ((UIRendrableComponent) child).getRender();
				uiRender.setParentRender(this);
				uiRender.setTabRender(getTabRender() + "  ");
				uiRender.render(context);
			}
		}
	}
}
