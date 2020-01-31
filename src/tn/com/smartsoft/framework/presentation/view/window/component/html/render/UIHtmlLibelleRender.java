package tn.com.smartsoft.framework.presentation.view.window.component.html.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.html.UIHtmlDisplayLabel;

public class UIHtmlLibelleRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIHtmlLibelleRender(UIHtmlDisplayLabel renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		UIHtmlDisplayLabel uiHtmlLibelle = (UIHtmlDisplayLabel) renderComponent;
		String libelle = uiHtmlLibelle.getLibelle();
		if (libelle == null || libelle.equals("null"))
			libelle = "invalide expression libelle :" + uiHtmlLibelle.getExpression();
		context.renderln(libelle, getTabRender());
	}
}
