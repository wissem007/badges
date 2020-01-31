package tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIOnOpenMediaEvent;

public class UIOnOpenMediaEventRender extends UIPartialRequestEventRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIOnOpenMediaEventRender(UIOnOpenMediaEvent renderComponent) {
		super(renderComponent, "media");
	}

	public JsMap getActionConfig(UIRenderContext context) {
		final UIOnOpenMediaEvent onOpenMediaEvent = (UIOnOpenMediaEvent) renderComponent;
		final JsMap action = super.getActionConfig(context);
		action.addStringValue("extention", onOpenMediaEvent.getExtention());
		return action;
	}

	public void render(UIRenderContext context) {
		// final UIOnOpenMediaEvent onOpenMediaEvent = (UIOnOpenMediaEvent) renderComponent;
	}
}
