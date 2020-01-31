package tn.com.smartsoft.commons.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;

import tn.com.smartsoft.commons.aop.Aspect;
import tn.com.smartsoft.commons.aop.InvocationAspect;
import tn.com.smartsoft.commons.aop.Pointcut;
import tn.com.smartsoft.commons.aop.advice.AfteThrowingAdvice;
import tn.com.smartsoft.commons.aop.advice.AfterFinallyAdvice;
import tn.com.smartsoft.commons.aop.advice.AfterReturningAdvice;
import tn.com.smartsoft.commons.aop.advice.BeforeAdvice;
import tn.com.smartsoft.commons.aop.advice.InvocationAdvice;

public class AspectsInvocationHandler implements InvocationHandler {
	private Aspect[] aspects;
	private Object[] arguments;
	private Method method;
	private InvocationAspect[] invocationAspect;

	public AspectsInvocationHandler(Aspect[] aspects, InvocationAspect[] invocationAspect) {
		super();
		this.aspects = aspects;
		this.invocationAspect = invocationAspect;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		this.arguments = args;
		this.method = method;
		try {
			for (int i = 0; i < aspects.length; i++) {
				Advice advice = aspects[i].getAdvice();
				if (testPointcut(i) && advice instanceof BeforeAdvice) {
					((BeforeAdvice) advice).before(method, arguments, proxy);
				}
			}
			Object returnValue = null;
			for (int i = 0; i < invocationAspect.length; i++) {
				InvocationAdvice advice = invocationAspect[i].getInvocationAdvice();
				if (testInvocationPointcut(i)) {
					returnValue = advice.invoke(method, arguments, proxy);
				}
			}

			for (int i = 0; i < aspects.length; i++) {
				Advice advice = aspects[i].getAdvice();
				if (testPointcut(i) && advice instanceof AfterReturningAdvice) {
					((AfterReturningAdvice) advice).afterReturning(returnValue, method, arguments, proxy);
				}
			}
			return returnValue;
		} catch (Exception e) {
			boolean isAfteThrowingAdvice = false;
			for (int i = 0; i < aspects.length; i++) {
				Advice advice = aspects[i].getAdvice();
				if (testPointcut(i) && advice instanceof AfteThrowingAdvice) {
					isAfteThrowingAdvice = true;
					((AfteThrowingAdvice) advice).afterThrowing(e, method, arguments, proxy);
				}
			}
			if (!isAfteThrowingAdvice)
				throw e;
			return null;
		} finally {
			for (int i = 0; i < aspects.length; i++) {
				Advice advice = aspects[i].getAdvice();
				if (testPointcut(i) && advice instanceof AfterFinallyAdvice) {
					((AfterFinallyAdvice) advice).afterFinally(method, arguments, proxy);
				}
			}
		}
	}

	protected boolean testInvocationPointcut(int index) {
		Pointcut pointcut = invocationAspect[index].getPointcut();
		boolean testPointcut = pointcut.getClassFilter().matches(method.getDeclaringClass());
		testPointcut = testPointcut && pointcut.getMethodMatcher().matches(method, method.getDeclaringClass(), arguments);
		return testPointcut;
	}

	protected boolean testPointcut(int index) {
		Pointcut pointcut = aspects[index].getPointcut();
		boolean testPointcut = pointcut.getClassFilter().matches(method.getDeclaringClass());
		testPointcut = testPointcut && pointcut.getMethodMatcher().matches(method, method.getDeclaringClass(), arguments);
		return testPointcut;
	}

}
