package tn.com.smartsoft.commons.aop;

class TruePointcut implements Pointcut {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final TruePointcut INSTANCE = new TruePointcut();

	private TruePointcut() {
	}

	public ClassFilter getClassFilter() {
		return ClassFilter.TRUE;
	}

	public MethodMatcher getMethodMatcher() {
		return MethodMatcher.TRUE;
	}

	private Object readResolve() {
		return INSTANCE;
	}

	public String toString() {
		return "Pointcut.TRUE";
	}

}
