package tn.com.smartsoft.framework.presentation.view.window.component.js.render;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsFunction;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIContainerJsRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderValue;
import tn.com.smartsoft.framework.presentation.view.window.component.js.UIFunctionJs;

public class UIFunctionJsRender extends UIContainerJsRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIComponent containerComponent;
	private String eventComponent;

	public UIFunctionJsRender(UIFunctionJs renderComponent) {
		super(renderComponent);
	}

	public String getEventComponent() {
		return eventComponent;
	}

	public void setEventComponent(String eventComponent) {
		this.eventComponent = eventComponent;
	}

	public void renderStart(UIRenderContext context) {
	}

	public UIComponent getContainerComponent() {
		return containerComponent;
	}

	public void setContainerComponent(UIComponent containerComponent) {
		this.containerComponent = containerComponent;
	}

	public void renderEnd(UIRenderContext context) {
		final UIFunctionJs functionJs = (UIFunctionJs) renderComponent;
		if (StringUtils.isNotBlank(functionJs.getScript()))
			bufferRender().addBufferRender(JsFunction.formatScript(functionJs.getScript()));
		UIRenderValue render = new UIRenderValue() {
			public void render(UIRenderContext context) {
				JsFunction jsFunction = new JsFunction(functionJs.getId(), functionJs.getParams());
				context.render(jsFunction.toStartJs());
				bufferRender().render(context);
				context.render(jsFunction.toEndJs());
			}
		};
		context.addFunctionJs(render);
	}
}
