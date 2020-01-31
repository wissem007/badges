package tn.com.smartsoft.framework.presentation.formater;

import org.apache.commons.lang.StringUtils;

public class AlphaFormater extends Formater {

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
			return "";
		return target.toString();
	}

	public boolean validate(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return true;
		boolean isAlpha = StringUtils.isAlphaSpace(target.toString());
		if (isAlpha)
			return true;
		return false;
	}
}
