package tn.com.smartsoft.commons.aop;

import java.io.Serializable;

import org.aopalliance.aop.Advice;

public class Aspect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Advice advice;
	private Pointcut pointcut;

	public Aspect(Advice advice, Pointcut pointcut) {
		super();
		this.advice = advice;
		this.pointcut = pointcut;
	}

	public Aspect(Advice advice) {
		this(advice, Pointcut.TRUE);
	}

	public Advice getAdvice() {
		return advice;
	}

	public Pointcut getPointcut() {
		return pointcut;
	}
}
