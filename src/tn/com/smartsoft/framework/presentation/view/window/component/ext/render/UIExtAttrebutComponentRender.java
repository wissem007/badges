package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;

public abstract class UIExtAttrebutComponentRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtAttrebutComponentRender(UIRendrableComponent renderComponent) {
		super(renderComponent);
	}

	public abstract void render(UIRenderContext context, UIExtComponentJs componentJs);

}