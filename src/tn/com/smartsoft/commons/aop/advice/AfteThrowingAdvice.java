package tn.com.smartsoft.commons.aop.advice;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;

public interface AfteThrowingAdvice extends Advice, Serializable {
	void afterThrowing(Throwable throwable, Method method, Object[] arguments, Object target);
}
