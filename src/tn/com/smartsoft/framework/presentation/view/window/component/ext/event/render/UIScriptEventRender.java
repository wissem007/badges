package tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render;

import java.util.List;

import tn.com.smartsoft.commons.web.js.JsFunction;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderValue;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIScriptEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.utils.UIEventRenderUtils;
import tn.com.smartsoft.framework.presentation.view.window.component.js.UIScriptJs;
import tn.com.smartsoft.framework.presentation.view.window.component.js.render.UIScriptJsRender;

public class UIScriptEventRender extends UIRender implements UIInterfaceEventRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String handelName;

	public UIScriptEventRender(UIScriptEvent renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {

	}

	public void renderEnd(UIRenderContext context) {
		final UIScriptEvent scriptEvent = (UIScriptEvent) renderComponent;
		final UIScriptJs scriptJs = scriptEvent.getScriptCmp();
		if (scriptJs == null)
			return;
		handelName = scriptEvent.getParent().getId() + scriptEvent.getId() + "Handel";
		UIRenderValue render = new UIRenderValue() {
			public void render(UIRenderContext context) {
				final UIScriptJsRender scriptJsRender = (UIScriptJsRender) scriptJs.getRender();
				scriptJsRender.setParentRender(UIScriptEventRender.this);
				scriptJsRender.setWriteStartScript(false);
				String[] parmFct = new String[scriptEvent.getHandleParams().size()];
				parmFct = (String[]) scriptEvent.getHandleParams().toArray(parmFct);
				JsFunction jsFunction = new JsFunction(handelName, parmFct);
				context.render(jsFunction.toStartJs());
				scriptJsRender.render(context);
				context.render(jsFunction.toEndJs());
			}
		};
		context.addFunctionJs(render);
	}

	public String createHandelServerAction(UIRenderContext context) {
		return UIEventRenderUtils.createHandelServerAction(context, (UIEvent) renderComponent, getActionConfig(context));
	}

	public JsMap getActionConfig(UIRenderContext context) {
		final UIScriptEvent scriptEvent = (UIScriptEvent) renderComponent;
		final JsMap action = UIEventRenderUtils.getActionConfig(context, scriptEvent, this, "script");
		renderEnd(context);
		action.addObjectValue("handel", handelName);
		List<UIComponent> actions = scriptEvent.getItems();
		JsMap actionsJs = new JsMap();
		for (int i = 0; i < actions.size(); i++) {
			UIComponent component = actions.get(i);
			if (component instanceof UIEvent) {
				UIEvent uiEvent = (UIEvent) component;
				if (uiEvent.isRendred()) {
					UIRender eventRender = uiEvent.getRender();
					eventRender.setParentRender(parentRender);
					if (uiEvent.isEmptyComponentId())
						uiEvent.setComponentId(scriptEvent.getComponentId());
					if (uiEvent.isEmptyEventName())
						uiEvent.setEventName(scriptEvent.getEventName());
					eventRender.render(context);
					if (eventRender instanceof UIInterfaceEventRender) {
						actionsJs.addObjectValue(uiEvent.getName(), ((UIInterfaceEventRender) eventRender).getActionConfig(context));
					}
				}
			}
		}
		if (!actionsJs.isEmpty())
			action.addObjectValue("actions", actionsJs.toJs());
		return action;
	}

}
