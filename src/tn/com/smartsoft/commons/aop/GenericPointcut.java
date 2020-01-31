package tn.com.smartsoft.commons.aop;

public class GenericPointcut implements Pointcut {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClassFilter classFilter;
	private MethodMatcher methodMatcher;

	public GenericPointcut(ClassFilter classFilter, MethodMatcher methodMatcher) {
		super();
		this.classFilter = classFilter;
		this.methodMatcher = methodMatcher;
	}

	public GenericPointcut(ClassFilter classFilter) {
		this(classFilter, null);
	}

	public GenericPointcut(MethodMatcher methodMatcher) {
		this(null, methodMatcher);
	}

	public GenericPointcut() {
		this(null, null);
	}

	public ClassFilter getClassFilter() {
		if (this.classFilter == null)
			this.classFilter = ClassFilter.TRUE;
		return classFilter;
	}

	public void setClassFilter(ClassFilter classFilter) {
		this.classFilter = classFilter;
	}

	public MethodMatcher getMethodMatcher() {
		if (this.methodMatcher == null)
			this.methodMatcher = MethodMatcher.TRUE;
		return methodMatcher;
	}

	public void setMethodMatcher(MethodMatcher methodMatcher) {
		this.methodMatcher = methodMatcher;
	}

}
