package tn.com.smartsoft.framework.presentation.view.window.component.ext.output.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.output.UIOutputComponent;

public class UIOutputComponentRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIOutputComponentRender(UIOutputComponent renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		UIOutputComponent outputComponent = (UIOutputComponent) renderComponent;
		context.addComponentsToBufferJs(outputComponent.getText());
	}

}
