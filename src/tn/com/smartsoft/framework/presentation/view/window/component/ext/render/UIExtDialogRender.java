package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import tn.com.smartsoft.commons.web.js.JsFunction;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderValue;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtDialog;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UILinkedEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render.UIEventRender;
import tn.com.smartsoft.framework.presentation.view.window.component.js.UIScriptJs;
import tn.com.smartsoft.framework.presentation.view.window.component.js.render.UIScriptJsRender;

public class UIExtDialogRender extends UIExtPanelRender {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UIExtDialogRender(UIExtDialog renderComponent) {
		super(renderComponent, "Ext.sss.Dialog");
	}
	
	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtDialog extDialog = (UIExtDialog) renderComponent;
		extComponentJs().addBooleanValue("minimizable", extDialog.getMinimizable());
		extComponentJs().addBooleanValue("maximizable", extDialog.getMaximizable());
		extComponentJs().addBooleanValue("dynamiqueOnOpen", extDialog.getDynamiqueOnOpen());
		renderCallbackOnOpen(context);
		UILinkedEvent uiEvent = extDialog.getActionLoad();
		UIEventRender eventRender = (UIEventRender) uiEvent.getRender();
		eventRender.setParentRender(parentRender);
		eventRender.render(context);
		JsMap actionConfig = eventRender.getActionConfig(context);
		actionConfig.addObjectValue("args", null);
		actionConfig.addStringValue("path", context.webContext().getContextPath().getChildPath(extDialog.getWindow().getId()).getPath());
		extComponentJs.addObjectValue("actionLoad", context.createServerAction(actionConfig).getScript());
	}
	
	private void renderCallbackOnOpen(UIRenderContext context) {
		UIExtDialog extDialog = (UIExtDialog) renderComponent;
		UIScriptJs scriptJs = extDialog.getCallbackOnOpenJs();
		if (scriptJs != null) {
			final UIScriptJsRender scriptJsRender = (UIScriptJsRender) scriptJs.getRender();
			scriptJsRender.setParentRender(this);
			scriptJsRender.setWriteStartScript(false);
			final String handelName = extDialog.getId() + "CallbackOnOpen";
			context.addFunctionJs(new UIRenderValue() {
				public void render(UIRenderContext context) {
					JsFunction jsFunction = new JsFunction(handelName, new String[] { "el", "success", "response", "options" });
					context.render(jsFunction.toStartJs());
					scriptJsRender.render(context);
					context.render(jsFunction.toEndJs());
				}
			});
			extComponentJs().addObjectValue("callbackFunction", handelName);
			return;
		}
		extComponentJs().addObjectValue("callbackFunction", extDialog.getCallbackOnOpenHandeler());
	}
	
	public void render(UIRenderContext context) {
		renderStart(context);
		renderEnd(context);
	}
}
