package tn.com.smartsoft.framework.presentation.view.window.component.html;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.html.render.UIHtmlRowRender;

public class UIHtmlRow extends UIHtmlContainerComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIHtmlRow() {
		super("tr");
	}

	public String getWidth() {
		return super.getAttribut("width");
	}

	public void setWidth(String width) {
		this.setAttribut("width", width);
	}

	public UIRender getRender() {
		return new UIHtmlRowRender(this);
	}
}
