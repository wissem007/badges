package tn.com.smartsoft.commons.aop;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SimpleClassFilter implements ClassFilter {
	private final Set classNames;

	public SimpleClassFilter() {
		super();
		this.classNames = new HashSet();
	}

	public SimpleClassFilter(Class[] classNames) {
		this.classNames = new HashSet(Arrays.asList(classNames));
	}

	public boolean matches(Class clazz) {
		return classNames.contains(clazz);
	}

}
