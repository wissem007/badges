package tn.com.smartsoft.commons.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {

	public boolean matches(Method method, Class targetClass);

	public boolean matches(Method method, Class targetClass, Object[] args);

	public MethodMatcher TRUE = TrueMethodMatcher.INSTANCE;
}
