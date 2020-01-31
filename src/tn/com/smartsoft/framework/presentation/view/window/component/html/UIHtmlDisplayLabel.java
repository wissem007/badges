package tn.com.smartsoft.framework.presentation.view.window.component.html;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.html.render.UIHtmlLibelleRender;

public class UIHtmlDisplayLabel extends UIComponent implements UIRendrableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String expression;
	private boolean rendred = true;

	public boolean isRendred() {
		return rendred;
	}

	public void setRendred(boolean rendred) {
		this.rendred = rendred;
	}

	public String getLibelle() {
		return getWindow().evalExpressionToString(getExpression());
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public UIRender getRender() {
		return new UIHtmlLibelleRender(this);
	}

}
