package tn.com.smartsoft.commons.aop;

import java.io.Serializable;
import java.lang.reflect.Method;

public class TrueMethodMatcher implements MethodMatcher, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final TrueMethodMatcher INSTANCE = new TrueMethodMatcher();

	private TrueMethodMatcher() {
	}

	public boolean matches(Method method, Class targetClass) {
		return true;
	}

	public boolean matches(Method method, Class targetClass, Object[] args) {
		return true;
	}

	private Object readResolve() {
		return INSTANCE;
	}

	public String toString() {
		return "MethodMatcher.TRUE";
	}
}
