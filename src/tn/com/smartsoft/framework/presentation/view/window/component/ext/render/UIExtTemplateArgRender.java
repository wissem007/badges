package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIGenericRendrableContainerComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtHtml;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtTemplateArg;

public class UIExtTemplateArgRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtTemplateArgRender(UIExtTemplateArg renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		StringBuffer template = new StringBuffer();
		render(context, template);
	}

	public void render(UIRenderContext context, StringBuffer sb) {
		renderChilds(context, sb);
	}

	public void renderChilds(UIRenderContext context, StringBuffer sb) {
		UIGenericRendrableContainerComponent extContainerComponent = (UIGenericRendrableContainerComponent) renderComponent;
		for (int i = 0; i < extContainerComponent.itemIds().size(); i++) {
			UIComponent child = extContainerComponent.getItem((String) extContainerComponent.itemIds().get(i));
			if (child instanceof UIExtHtml) {
				UIExtHtml rendrableComponent = (UIExtHtml) child;
				UIExtHtmlRender uiRender = (UIExtHtmlRender) rendrableComponent.getRender();
				uiRender.setParentRender(this);
				uiRender.setTabRender(getTabRender() + "  ");
				uiRender.render(context, sb);
			}
		}
	}
}
