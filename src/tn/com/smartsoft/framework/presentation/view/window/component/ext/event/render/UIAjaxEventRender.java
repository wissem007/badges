package tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render;

import tn.com.smartsoft.commons.web.js.JsFunction;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderValue;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIAjaxEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.js.UIScriptJs;
import tn.com.smartsoft.framework.presentation.view.window.component.js.render.UIScriptJsRender;

public class UIAjaxEventRender extends UIPartialRequestEventRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIAjaxEventRender(UIAjaxEvent renderComponent) {
		super(renderComponent, "ajax");
	}

	public JsMap getActionConfig(UIRenderContext context) {
		final UIAjaxEvent ajaxEvent = (UIAjaxEvent) renderComponent;
		final JsMap action = super.getActionConfig(context);
		if (!ajaxEvent.getHeaders().isEmpty())
			action.addValue("headers", ajaxEvent.getHeaders().toJs(), false);
		action.addStringValue("scope", ajaxEvent.getScope());
		renderHandler(context, action, true);
		renderHandler(context, action, false);
		return action;
	}

	public void render(UIRenderContext context) {

	}

	private void renderHandler(UIRenderContext context, JsMap action, boolean isSuccess) {
		final UIAjaxEvent ajaxEvent = (UIAjaxEvent) renderComponent;
		UIScriptJs scriptJs = isSuccess ? ajaxEvent.getSuccess() : ajaxEvent.getFailure();
		if (scriptJs != null) {
			final UIScriptJsRender scriptJsRender = (UIScriptJsRender) scriptJs.getRender();
			scriptJsRender.setParentRender(this);
			scriptJsRender.setWriteStartScript(false);
			final String handelName = ajaxEvent.getParent().getId() + ajaxEvent.getId() + (isSuccess ? "Success" : "Failure") + "Handel";
			context.addFunctionJs(new UIRenderValue() {
				public void render(UIRenderContext context) {
					JsFunction jsFunction = new JsFunction(handelName, new String[] { "response", "options" });
					context.render(jsFunction.toStartJs());
					scriptJsRender.render(context);
					context.render(jsFunction.toEndJs());
				}
			});
			action.addObjectValue(isSuccess ? "success" : "failure", handelName);
			return;
		}
		String handlerJs = isSuccess ? ajaxEvent.getSuccessHandle() : ajaxEvent.getFailureHandle();
		action.addObjectValue(isSuccess ? "success" : "failure", handlerJs);
	}
}
