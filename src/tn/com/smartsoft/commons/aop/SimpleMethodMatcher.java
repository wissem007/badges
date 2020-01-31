package tn.com.smartsoft.commons.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SimpleMethodMatcher implements MethodMatcher {
	private final Set methodNames;

	public SimpleMethodMatcher() {
		super();
		this.methodNames = new HashSet();
	}

	public SimpleMethodMatcher(String[] methodNames) {
		this.methodNames = new HashSet(Arrays.asList(methodNames));
	}

	public boolean matches(Method method, Class targetClass) {
		return methodNames.contains(method.getName());
	}

	public boolean matches(Method method, Class targetClass, Object[] args) {
		return matches(method, targetClass);
	}

}
