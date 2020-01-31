package tn.com.smartsoft.commons.aop.advice;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;

public interface BeforeAdvice extends Advice, Serializable {
	void before(Method method, Object[] arguments, Object target) throws Throwable;
}