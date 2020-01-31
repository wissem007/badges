package tn.com.smartsoft.framework.presentation.formater;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;

public class NumericFormater extends Formater {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object getAsObject(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return null;
		if (targetClass.equals(Long.class))
			return new Long(StringUtils.remove(target.toString(), " "));
		else if (targetClass.equals(Integer.class))
			return new Integer(StringUtils.remove(target.toString(), " "));
		else if (targetClass.equals(BigInteger.class))
			return new BigInteger(StringUtils.remove(target.toString(), " "));
		return target;
	}

	public String getAsString(Object target) {
		if (target == null || ((Number) target).longValue() == 0)
			return "";
		return target.toString();
	}

	public boolean validate(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return true;
		boolean isNumeric = StringUtils.isNumeric(target.toString());
		if (isNumeric)
			return true;
		return false;
	}
}
