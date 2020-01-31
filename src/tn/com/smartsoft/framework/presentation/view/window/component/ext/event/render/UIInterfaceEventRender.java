package tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;

public interface UIInterfaceEventRender {
	public abstract void render(UIRenderContext context);

	public abstract String createHandelServerAction(UIRenderContext context);

	public JsMap getActionConfig(UIRenderContext context);
}
