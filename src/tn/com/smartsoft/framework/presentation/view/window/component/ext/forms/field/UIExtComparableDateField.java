package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;

public class UIExtComparableDateField extends UIExtTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String maxValue;
	private String minValue;
	private String messageMaxValueKey;
	private String messageMinValueKey;
	
	public Object getMaxValue() {
		if (StringUtils.isBlank(maxValue))
			return null;
		return ConvertUtils.convert(maxValue, getJavaType());
	}
	
	public Object getMinValue() {
		if (StringUtils.isBlank(minValue))
			return null;
		return ConvertUtils.convert(minValue, getJavaType());
	}
	
	public void setMaxValue(Object maxValue) {
		if (maxValue != null)
			this.maxValue = maxValue.toString();
		else
			this.maxValue = null;
	}
	
	public void setMinValue(Object minValue) {
		if (minValue != null)
			this.minValue = minValue.toString();
		else
			this.minValue = null;
	}
	
	public void setMaxStringValue(String maxValue) {
		this.maxValue = maxValue;
	}
	
	public void setMinStringValue(String minValue) {
		this.minValue = minValue;
	}
	
	public String getMessageMaxValueKey() {
		return messageMaxValueKey;
	}
	
	public void setMessageMaxValueKey(String messageMaxValueKey) {
		this.messageMaxValueKey = messageMaxValueKey;
	}
	
	public String getMessageMinValueKey() {
		return messageMinValueKey;
	}
	
	public void setMessageMinValueKey(String messageMinValueKey) {
		this.messageMinValueKey = messageMinValueKey;
	}
}
