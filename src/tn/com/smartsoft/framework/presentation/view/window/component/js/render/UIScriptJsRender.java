package tn.com.smartsoft.framework.presentation.view.window.component.js.render;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsFunction;
import tn.com.smartsoft.commons.web.markup.html.Script;
import tn.com.smartsoft.framework.presentation.view.window.UIAppenderJsRender;
import tn.com.smartsoft.framework.presentation.view.window.UIContainerJsRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.js.UIScriptJs;

public class UIScriptJsRender extends UIContainerJsRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isWriteStartScript = true;

	public UIScriptJsRender(UIScriptJs renderComponent) {
		super(renderComponent);
	}

	public void renderStart(UIRenderContext context) {

	}

	public boolean isWriteStartScript() {
		return isWriteStartScript;
	}

	public void setWriteStartScript(boolean isWriteStartScript) {
		this.isWriteStartScript = isWriteStartScript;
	}

	public void renderEnd(UIRenderContext context) {
		UIScriptJs scriptJs = (UIScriptJs) renderComponent;
		if (StringUtils.isNotBlank(scriptJs.getScript()))
			bufferRender().addBufferRender(JsFunction.formatScript(scriptJs.getScript()));
		if (parentRender instanceof UIAppenderJsRender) {
			((UIAppenderJsRender) parentRender).bufferRender().addBufferRender(bufferRender());
		} else if (isWriteStartScript) {
			context.renderln(new Script().createStartTag());
			bufferRender().render(context);
			context.renderln(new Script().createEndTag());
		} else {
			bufferRender().render(context);
		}
	}

}
