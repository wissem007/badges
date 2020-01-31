package tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIPartialRequestEvent;

public abstract class UIPartialRequestEventRender extends UIEventRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIPartialRequestEventRender(UIPartialRequestEvent renderComponent, String eType) {
		super(renderComponent, eType);
	}

	public JsMap getActionConfig(UIRenderContext context) {
		final UIPartialRequestEvent partialRequestEvent = (UIPartialRequestEvent) renderComponent;
		final JsMap action = super.getActionConfig(context);
		if (!partialRequestEvent.getRequestFields().isEmpty())
			action.addObjectValue("paramsField", partialRequestEvent.getRequestFields().toJs());
		action.addBooleanValue("allField", partialRequestEvent.isAllField());
		return action;
	}

}
