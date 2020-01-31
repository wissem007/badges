package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtTimeFieldRender;

public class UIExtTimeField extends UIExtComparableDateField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int increment = 15;
	private String format = "H:i";

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public UIRender getRender() {
		return new UIExtTimeFieldRender(this);
	}
}
