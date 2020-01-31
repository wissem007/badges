package tn.com.smartsoft.framework.presentation.formater;

import org.apache.commons.lang.StringUtils;

public class AlphaNumericFormater extends Formater {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getAsString(Object target) {
		if (target == null)
			return "";
		return target.toString();
	}

	public Object getAsObject(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return null;
		return target;
	}

	public boolean validate(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return true;
		boolean isAlphanumeric = StringUtils.isAlphanumericSpace(target.toString());
		if (isAlphanumeric)
			return true;
		return false;
	}
}
