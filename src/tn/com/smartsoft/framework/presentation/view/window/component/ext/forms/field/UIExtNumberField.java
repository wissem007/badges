package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtNumberFieldRender;

public class UIExtNumberField extends UIExtComparableDateField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean allowDecimals = true;
	private String decimalSeparator = ",";
	private int decimalPrecision = -1;
	private boolean allowNegative = false;
	
	public boolean isAllowDecimals() {
		return allowDecimals;
	}
	
	public void setAllowDecimals(boolean allowDecimals) {
		this.allowDecimals = allowDecimals;
	}
	
	public String getDecimalSeparator() {
		return decimalSeparator;
	}
	
	public void setDecimalSeparator(String decimalSeparator) {
		this.decimalSeparator = decimalSeparator;
	}
	
	public int getDecimalPrecision() {
		return decimalPrecision;
	}
	
	public void setDecimalPrecision(int decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
	}
	
	public boolean isAllowNegative() {
		return allowNegative;
	}
	
	public void setAllowNegative(boolean allowNegative) {
		this.allowNegative = allowNegative;
	}
	
	public UIRender getRender() {
		return new UIExtNumberFieldRender(this);
	}
}
