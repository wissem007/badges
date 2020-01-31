package tn.com.smartsoft.framework.presentation.view.window.component.ext.charts;

import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtBoxComponent;

public class UIExtYFieldChart extends UIExtBoxComponent {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	private String yField;
	private String displayName;
	private String color;

	public String getyField() {
		return yField;
	}

	public void setyField(String yField) {
		this.yField = yField;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
