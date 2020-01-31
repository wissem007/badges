package tn.com.smartsoft.commons.aop.advice;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;

public interface AfterReturningAdvice extends Advice, Serializable {
	void afterReturning(Object returnValue, Method method, Object[] arguments, Object target) throws Throwable;

}
