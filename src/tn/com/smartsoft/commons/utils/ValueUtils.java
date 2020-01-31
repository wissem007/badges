package tn.com.smartsoft.commons.utils;

public class ValueUtils {
	public static boolean equals(Object value1, Object value2) {
		if (value1 == null && value2 == null)
			return true;
		if (value1 != null && value2 == null)
			return false;
		if (value1 == null && value2 != null)
			return false;
		return value1.equals(value2);
	}
	
	public static boolean equals(Object[] value1, Object[] value2) {
		if (value1.length != value2.length)
			return false;
		for (int i = 0; i < value1.length; i++) {
			if (!equals(value1[i], value2[i]))
				return false;
		}
		return true;
	}
	
	public static boolean in(Object value1, Object[] values) {
		for (int i = 0; i < values.length; i++) {
			if (equals(value1, values[i]))
				return true;
		}
		return false;
	}
}
