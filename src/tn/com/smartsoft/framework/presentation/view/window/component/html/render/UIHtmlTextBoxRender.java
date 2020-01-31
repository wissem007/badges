package tn.com.smartsoft.framework.presentation.view.window.component.html.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.html.UIHtmlDisplayLabel;
import tn.com.smartsoft.framework.presentation.view.window.component.html.UIHtmlTextBox;

public class UIHtmlTextBoxRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIHtmlTextBoxRender(UIHtmlTextBox htmlTextBox) {
		super(htmlTextBox);
	}

	public void render(UIRenderContext context) {
		UIHtmlTextBox uiHtmlTextBox = (UIHtmlTextBox) renderComponent;
		context.render(getTabRender());
		for (int i = 0; i < uiHtmlTextBox.getRepeate(); i++) {
			UIHtmlDisplayLabel uiHtmlLibelle = (UIHtmlDisplayLabel) renderComponent;
			String libelle = uiHtmlLibelle.getLibelle();
			if (libelle == null || libelle.equals("null"))
				libelle = "invalide expression libelle :" + uiHtmlLibelle.getExpression();
			context.render(libelle);
		}
		context.renderln();
	}
}
