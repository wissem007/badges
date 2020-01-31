package tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UILinkedEvent;

public class UILinkedEventRender extends UIPartialRequestEventRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UILinkedEventRender(UILinkedEvent renderComponent) {
		super(renderComponent, "href");
	}

	public JsMap getActionConfig(UIRenderContext context) {
		final JsMap action = super.getActionConfig(context);
		return action;
	}

	public void render(UIRenderContext context) {
	}

}
