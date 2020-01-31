package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import java.util.Date;
import java.util.List;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtDateFieldRender;

public class UIExtDateField extends UIExtComparableDateField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String format;
	private String disabledDaysMessageKey;
	private String disabledDatesMessageKey;
	private List<Integer> disabledDays;
	private List<Date> disabledDates;
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getDisabledDaysMessageKey() {
		return disabledDaysMessageKey;
	}
	
	public void setDisabledDaysMessageKey(String disabledDaysMessageKey) {
		this.disabledDaysMessageKey = disabledDaysMessageKey;
	}
	
	public String getDisabledDatesMessageKey() {
		return disabledDatesMessageKey;
	}
	
	public void setDisabledDatesMessageKey(String disabledDatesMessageKey) {
		this.disabledDatesMessageKey = disabledDatesMessageKey;
	}
	
	public List<Integer> getDisabledDays() {
		return disabledDays;
	}
	
	public void setDisabledDays(List<Integer> disabledDays) {
		this.disabledDays = disabledDays;
	}
	
	public List<Date> getDisabledDates() {
		return disabledDates;
	}
	
	public void setDisabledDates(List<Date> disabledDates) {
		this.disabledDates = disabledDates;
	}
	
	public UIRender getRender() {
		return new UIExtDateFieldRender(this);
	}
}
