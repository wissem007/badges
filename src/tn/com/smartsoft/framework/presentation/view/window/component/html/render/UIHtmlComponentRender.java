package tn.com.smartsoft.framework.presentation.view.window.component.html.render;

import tn.com.smartsoft.commons.web.markup.ConcreteElement;
import tn.com.smartsoft.commons.web.markup.util.HtmlComponentUtils;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.html.UIHtmlComponent;

public class UIHtmlComponentRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIHtmlComponentRender(UIHtmlComponent htmlSinglePartComponent) {
		super(htmlSinglePartComponent);
	}

	public void render(UIRenderContext context) {
		UIHtmlComponent uiHtmlComponent = (UIHtmlComponent) renderComponent;
		ConcreteElement markupElement = HtmlComponentUtils.createElement(uiHtmlComponent.getHtmlTag());
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
}
