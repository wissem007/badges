package tn.com.smartsoft.framework.presentation.formater;

import org.apache.commons.lang.StringUtils;

public class PhoneNumberFormater extends Formater {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int NUM_DIGITS = 8;

	public Object getAsObject(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return null;
		String digits = target.toString().replaceAll("[^0-9]", "");
		if (digits.length() != NUM_DIGITS)
			return null;
		return digits;
	}

	public String getAsString(Object target) {
		if (target == null)
			return "";
		String digits = (String) target;
		StringBuffer buf = new StringBuffer("(");
		buf.append(digits.substring(0, 3));
		buf.append(") ");
		buf.append(digits.substring(3, 6));
		buf.append("-");
		buf.append(digits.substring(6));
		return buf.toString();
	}

	public boolean validate(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return true;
		String digits = target.toString();
		if (digits.length() != NUM_DIGITS)
			return true;
		return false;
	}

}
