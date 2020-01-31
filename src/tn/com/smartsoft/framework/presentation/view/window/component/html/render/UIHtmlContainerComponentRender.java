package tn.com.smartsoft.framework.presentation.view.window.component.html.render;

import tn.com.smartsoft.commons.web.markup.ConcreteElement;
import tn.com.smartsoft.commons.web.markup.util.HtmlComponentUtils;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.html.UIHtmlContainerComponent;
import tn.com.smartsoft.framework.presentation.view.window.render.UIHandlableComponentRender;

public class UIHtmlContainerComponentRender extends UIHandlableComponentRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConcreteElement markupElement;

	public UIHtmlContainerComponentRender(UIHtmlContainerComponent htmlMultiPartComponent) {
		super(htmlMultiPartComponent);
	}

	public void renderStart(UIRenderContext context) {
		UIHtmlContainerComponent uiHtmlComponent = (UIHtmlContainerComponent) renderComponent;
		markupElement = HtmlComponentUtils.createElement(uiHtmlComponent.getHtmlTag());
		if (markupElement == null) {
			markupElement = new ConcreteElement();
			markupElement.setElementType(uiHtmlComponent.getHtmlTag());
		}
		String[] atts = uiHtmlComponent.geAttributesName();
		for (int i = 0; i < atts.length; i++) {
			markupElement.addAttribute(atts[i], uiHtmlComponent.getAttribut(atts[i]));
		}
		context.renderln(markupElement.createStartTag(), getTabRender());
	}

	public void renderEnd(UIRenderContext context) {
		context.render(markupElement.createEndTag(), getTabRender());
	}
}
