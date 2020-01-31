package tn.com.smartsoft.framework.presentation.view.window.render;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIWindowRoot;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIEventRenderUtils;

public class UIWindowRootRender extends UIHandlableComponentRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIWindowRootRender(UIWindowRoot renderComponent) {
		super(renderComponent);
	}

	public void renderEnd(UIRenderContext context) {

	}

	protected void renderChilds(UIRenderContext context) {
		super.renderChilds(context);
		UIWindowRoot extComponent = (UIWindowRoot) renderComponent;
		for (int i = 0; i < extComponent.itemToolSize(); i++) {
			UIComponent child = extComponent.getToolItem((String) extComponent.itemToolIds().get(i));
			if (child instanceof UIRendrableComponent && ((UIRendrableComponent) child).isRendred()) {
				UIRender uiRender = ((UIRendrableComponent) child).getRender();
				uiRender.setParentRender(this);
				uiRender.setRenderToComponentBufferJs(false);
				uiRender.setTabRender(getTabRender() + "  ");
				uiRender.render(context);
			}
		}
	}

	public void renderStart(UIRenderContext context) {
		UIWindowRoot extComponent = (UIWindowRoot) renderComponent;
		UIEventRenderUtils.renderWindowEvents(context, extComponent, parentRender);
	}

}
