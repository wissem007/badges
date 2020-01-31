package tn.com.smartsoft.commons.aop;

import java.io.Serializable;

import tn.com.smartsoft.commons.aop.advice.InvocationAdvice;

public class InvocationAspect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InvocationAdvice advice;
	private Pointcut pointcut;

	public InvocationAspect(InvocationAdvice advice, Pointcut pointcut) {
		super();
		this.advice = advice;
		this.pointcut = pointcut;
	}

	public InvocationAspect(InvocationAdvice advice) {
		this(advice, Pointcut.TRUE);
	}

	public InvocationAdvice getInvocationAdvice() {
		return advice;
	}

	public Pointcut getPointcut() {
		return pointcut;
	}
}
