package tn.com.smartsoft.framework.presentation.view.window.component.ext.event.utils;

import java.util.ArrayList;

import tn.com.smartsoft.commons.web.js.JsFunction;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderValue;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.js.UIScriptJs;
import tn.com.smartsoft.framework.presentation.view.window.component.js.render.UIScriptJsRender;

public class UIEventRenderUtils {
	public static String createHandelServerAction(UIRenderContext context, UIEvent event, JsMap actionConf) {
		ArrayList<String> handleParams = event.getHandleParams();
		if (handleParams.isEmpty()) {
			handleParams.add("el");
			handleParams.add("e");
		}
		JsFunction jsFunction = new JsFunction(null, handleParams, context.createHandelServerAction(actionConf).getScript() + "();");
		jsFunction.setAttrebute(true);
		return jsFunction.toJs();
	}

	public static String createServerAction(UIRenderContext context, UIEvent event, JsMap actionConf) {
		ArrayList<String> handleParams = event.getHandleParams();
		if (handleParams.isEmpty()) {
			handleParams.add("el");
			handleParams.add("e");
		}
		JsFunction jsFunction = new JsFunction(null, handleParams, context.createServerAction(actionConf).getScript());
		jsFunction.setAttrebute(true);
		return jsFunction.toJs();
	}

	public static JsMap getActionConfig(UIRenderContext context, final UIEvent event, UIRender eventRender, String eType) {
		final JsMap action = new JsMap();
		action.addStringValue("componentId", event.getComponentId());
		action.addStringValue("eventName", event.getEventName());
		action.addStringValue("actionPattern", event.getActionPattern());
		action.addBooleanValue("isWait", event.isWait());
		if (!event.getRequestParams().isEmpty())
			action.addValue("params", event.getRequestParams().toJs(), false);
		action.addBooleanValue("clientValidation", event.isClientValidation());
		action.addStringValue("waitMsg", event.getWaitMessage());
		action.addStringValue("msgConfirm", event.getMsgConfirm());
		action.addStringValue("msgConfirmTitle", event.getMsgConfirmTitle());
		action.addStringValue("container", event.getMsgContainer());
		action.addBooleanValue("isConfirmMsg", event.isConfirmMsg());
		action.addStringValue("xtype", eType);
		JsTable args = new JsTable();
		for (int i = 0; i < event.getHandleParams().size(); i++) {
			args.addObjectValue(event.getHandleParams().get(i));
		}
		action.addObjectValue("args", args.toJs());
		UIScriptJs scriptJs = event.getCondition();
		if (scriptJs != null) {
			final UIScriptJsRender scriptJsRender = (UIScriptJsRender) scriptJs.getRender();
			scriptJsRender.setParentRender(eventRender);
			scriptJsRender.setWriteStartScript(false);
			final String handelName = event.getParent().getId() + event.getId() + "Condition" + "Handel";
			context.addFunctionJs(new UIRenderValue() {
				public void render(UIRenderContext context) {
					JsFunction jsFunction = new JsFunction(handelName, event.getHandleParams());
					context.render(jsFunction.toStartJs());
					scriptJsRender.render(context);
					context.render(jsFunction.toEndJs());
				}
			});
			action.addObjectValue("conditionHandler", handelName);
		} else
			action.addObjectValue("conditionHandler", event.getConditionHandler());
		return action;
	}
}
