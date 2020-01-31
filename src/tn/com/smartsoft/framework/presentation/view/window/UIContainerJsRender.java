package tn.com.smartsoft.framework.presentation.view.window;

import tn.com.smartsoft.framework.presentation.view.window.handler.UIComponentHandler;

public abstract class UIContainerJsRender extends UIJsRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIContainerJsRender(UIRendrableComponent renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		context.setCurentComponentsJsBuffer(bufferRender());
		renderStart(context);
		renderChilds(context);
		renderEnd(context);
		context.setCurentComponentsJsBuffer(null);
	}

	public abstract void renderStart(UIRenderContext context);

	public abstract void renderEnd(UIRenderContext context);

	protected void renderChilds(UIRenderContext context) {
		UIComponentHandler<?> treeComponentHandler = (UIComponentHandler<?>) renderComponent;
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
