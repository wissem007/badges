package tn.com.smartsoft.framework.presentation.view.window.component.html;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.html.render.UIHtmlTableRender;

public class UIHtmlTable extends UIHtmlContainerComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIHtmlTable() {
		super("table");
	}

	public String getAlign() {
		return super.getAttribut("align");
	}

	public void setAlign(String align) {
		this.setAttribut("align", align);
	}

	public String getWidth() {
		return super.getAttribut("width");
	}

	public void setWidth(String width) {
		this.setAttribut("width", width);
	}

	public UIRender getRender() {
		return new UIHtmlTableRender(this);
	}
}
