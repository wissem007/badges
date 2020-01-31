package tn.com.smartsoft.commons.aop;

import java.lang.reflect.Method;

public class PatternMethodMatcher implements MethodMatcher {
	public static String GETTER_SETTER_PATTERN = "get\\w+|set\\w+";
	private final String pattern;

	public boolean matches(Method method, Class targetClass) {
		return method.getName().matches(pattern);
	}

	public static PatternMethodMatcher getterSetterFilter() {
		return new PatternMethodMatcher(GETTER_SETTER_PATTERN);
	}

	public PatternMethodMatcher(String pattern) {
		this.pattern = pattern;
	}

	public boolean matches(Method method, Class targetClass, Object[] args) {
		return matches(method, targetClass);
	}

}
