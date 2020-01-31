package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtAttrebutExpressionRender;

public class UIExtAttrebutExpression extends UIExtAttrebutComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String expression;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public UIRender getRender() {
		return new UIExtAttrebutExpressionRender(this);
	}

}
