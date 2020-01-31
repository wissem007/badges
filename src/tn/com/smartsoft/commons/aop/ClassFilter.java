package tn.com.smartsoft.commons.aop;

public interface ClassFilter {
	boolean matches(Class clazz);

	ClassFilter TRUE = TrueClassFilter.INSTANCE;
}
