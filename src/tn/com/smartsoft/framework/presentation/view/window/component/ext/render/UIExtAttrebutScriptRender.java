package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsFunction;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderValue;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtAttrebutScript;
import tn.com.smartsoft.framework.presentation.view.window.component.js.UIScriptJs;
import tn.com.smartsoft.framework.presentation.view.window.component.js.render.UIScriptJsRender;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;

public class UIExtAttrebutScriptRender extends UIExtAttrebutComponentRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String handelName;

	public UIExtAttrebutScriptRender(UIExtAttrebutScript renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		final UIExtAttrebutScript extAttrebutScript = (UIExtAttrebutScript) renderComponent;
		UIScriptJs scriptJs = extAttrebutScript.getScript();
		if (scriptJs != null) {
			final UIScriptJsRender scriptJsRender = (UIScriptJsRender) scriptJs.getRender();
			scriptJsRender.setParentRender(this);
			scriptJsRender.setWriteStartScript(false);
			handelName = StringUtils.isNotBlank(extAttrebutScript.getHandleName()) ? extAttrebutScript.getHandleName() : extAttrebutScript.getParent().getId() + extAttrebutScript.getExtAttrebut()
					+ "AttHandel";
			context.addFunctionJs(new UIRenderValue() {
				public void render(UIRenderContext context) {
					ArrayList<String> handleParams = extAttrebutScript.getHandleParams();
					JsFunction jsFunction = new JsFunction(handelName, handleParams);
					context.render(jsFunction.toStartJs());
					scriptJsRender.render(context);
					context.render(jsFunction.toEndJs());
				}
			});
		}
	}

	public void render(UIRenderContext context, UIExtComponentJs componentJs) {
		final UIExtAttrebutScript extAttrebutScript = (UIExtAttrebutScript) renderComponent;
		componentJs.addObjectValue(extAttrebutScript.getExtAttrebut(), handelName);
	}

}
