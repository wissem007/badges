package tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIOnExportFileEvent;

public class UIOnExportFileEventRender extends UIPartialRequestEventRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIOnExportFileEventRender(UIOnExportFileEvent renderComponent) {
		super(renderComponent, "exportFile");
	}

	public JsMap getActionConfig(UIRenderContext context) {
		final JsMap action = super.getActionConfig(context);
		return action;
	}

	public void render(UIRenderContext context) {

	}
}
