package tn.com.smartsoft.commons.aop;

import java.io.Serializable;

public class TrueClassFilter implements ClassFilter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final TrueClassFilter INSTANCE = new TrueClassFilter();

	private TrueClassFilter() {
	}

	public boolean matches(Class clazz) {
		return true;
	}

	private Object readResolve() {
		return INSTANCE;
	}

	public String toString() {
		return "ClassFilter.TRUE";
	}

}
