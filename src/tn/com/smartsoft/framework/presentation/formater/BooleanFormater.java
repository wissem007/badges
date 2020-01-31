package tn.com.smartsoft.framework.presentation.formater;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class BooleanFormater extends Formater {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final List<String> TRUE_VALUES = Arrays.asList(new String[] { "yes", "true", "on", "1", "enabled" });

	static final List<String> FALSE_VALUES = Arrays.asList(new String[] { "no", "false", "off", "0", "disabled" });

	public Object getAsObject(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return null;
		String stringValue = target.toString().trim().toLowerCase();
		if (TRUE_VALUES.contains(stringValue)) {
			return Boolean.TRUE;
		}
		if (FALSE_VALUES.contains(stringValue)) {
			return Boolean.FALSE;
		}
		return null;
	}

	public String getAsString(Object target) {
		if (target == null)
			return "false";
		String stringValue = target.toString().toLowerCase();
		if (TRUE_VALUES.contains(stringValue))
			return "true";
		return "false";
	}

	public boolean validate(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return true;
		String stringValue = target.toString().trim().toLowerCase();
		boolean isBoolean = TRUE_VALUES.contains(stringValue) || FALSE_VALUES.contains(stringValue);
		if (isBoolean)
			return true;
		return false;
	}

}
