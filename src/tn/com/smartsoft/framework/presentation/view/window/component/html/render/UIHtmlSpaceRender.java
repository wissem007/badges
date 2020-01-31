package tn.com.smartsoft.framework.presentation.view.window.component.html.render;

import tn.com.smartsoft.commons.web.utils.Htmls;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.html.UIHtmlSpace;

public class UIHtmlSpaceRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIHtmlSpaceRender(UIHtmlSpace htmlSpace) {
		super(htmlSpace);
	}

	public void render(UIRenderContext context) {
		UIHtmlSpace uiHtmlSpace = (UIHtmlSpace) renderComponent;
		context.render(getTabRender());
		for (int i = 0; i < uiHtmlSpace.getRepeate(); i++) {
			context.render(Htmls.NBSP_ENTITY);
		}
		context.renderln();
	}

}
