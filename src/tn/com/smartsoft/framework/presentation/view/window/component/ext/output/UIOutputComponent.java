package tn.com.smartsoft.framework.presentation.view.window.component.ext.output;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.output.render.UIOutputComponentRender;

public class UIOutputComponent extends UIComponent implements UIRendrableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean isDisplayLabel = true;
	private boolean rendred = true;
	private String textExp;

	public String getText() {
		return getWindow().evalExpressionToString(getTextExp());
	}

	public String getTextExp() {
		return textExp;
	}

	public void setTextExp(String textExp) {
		this.textExp = textExp;
	}

	public boolean isRendred() {
		return rendred;
	}

	public void setRendred(boolean rendred) {
		this.rendred = rendred;
	}

	public UIRender getRender() {
		return null;
	}

	public UIRender getLabelRender() {
		return new UIOutputComponentRender(this);
	}

}
