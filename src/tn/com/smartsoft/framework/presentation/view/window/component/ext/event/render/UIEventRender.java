package tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.utils.UIEventRenderUtils;

public abstract class UIEventRender extends UIRender implements UIInterfaceEventRender {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String eType;
	
	public UIEventRender(UIEvent renderComponent, String eType) {
		super(renderComponent);
		this.eType = eType;
	}
	
	public String createHandelServerAction(UIRenderContext context) {
		return UIEventRenderUtils.createHandelServerAction(context, (UIEvent) renderComponent, getActionConfig(context));
	}
	
	public JsMap getActionConfig(UIRenderContext context) {
		return UIEventRenderUtils.getActionConfig(context, (UIEvent) renderComponent, this, eType);
	}
	
}