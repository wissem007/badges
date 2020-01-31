package tn.com.smartsoft.framework.presentation.view.window.component.ext.grid;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;

public class UIExtGridHeaderColumn extends UIComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rowspan = 1;
	private int colspan = 1;
	private String header;
	private String align;
	private boolean isPrint = true;

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public String getHeader() {
		return header;
	}

	public String getLibelle() {
		return getWindow().evalExpressionToString(getHeader());
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public boolean isPrint() {
		return isPrint;
	}

	public void setPrint(boolean isPrint) {
		this.isPrint = isPrint;
	}

}
