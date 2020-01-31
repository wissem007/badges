package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import tn.com.smartsoft.commons.web.js.JsClass;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtTemplate;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtTemplateArg;

public class UIExtTemplateRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtTemplateRender(UIExtTemplate renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		UIExtTemplate extTemplate = (UIExtTemplate) renderComponent;
		JsClass jsObject = new JsClass("Ext.XTemplate");
		String[] rags = new String[extTemplate.getArgumentds().size()];
		boolean[] expecteds = new boolean[extTemplate.getArgumentds().size()];
		for (int i = 0; i < extTemplate.getArgumentds().size(); i++) {
			StringBuffer tp = new StringBuffer();
			UIExtTemplateArg child = extTemplate.getArgumentds().get(i);
			UIExtTemplateArgRender uiRender = (UIExtTemplateArgRender) child.getRender();
			uiRender.setParentRender(this);
			uiRender.render(context, tp);
			rags[i] = tp.toString();
			expecteds[i] = true;
		}
		context.addComponentsToBufferJs(jsObject.invokeNewInstance(extTemplate.getId(), true, rags, expecteds));
	}

}
