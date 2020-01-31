package tn.com.smartsoft.framework.presentation.view.window.component.html;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.html.render.UIHtmlColumnRender;

public class UIHtmlColumn extends UIHtmlContainerComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIHtmlColumn() {
		super("td");
	}

	public int getRowSpan() {
		return Integer.parseInt(super.getAttribut("rowSpan"));
	}

	public void setRowSpan(int rowSpan) {
		this.setAttribut("rowSpan", String.valueOf(rowSpan));
	}

	public int getColSpan() {
		return Integer.parseInt(super.getAttribut("colSpan"));
	}

	public void setColSpan(int colSpan) {
		this.setAttribut("colSpan", String.valueOf(colSpan));
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
		return new UIHtmlColumnRender(this);
	}

}
