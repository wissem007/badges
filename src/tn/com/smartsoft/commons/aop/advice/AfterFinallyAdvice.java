package tn.com.smartsoft.commons.aop.advice;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;

public interface AfterFinallyAdvice extends Advice, Serializable {
	void afterFinally(Method method, Object[] arguments, Object target);
}
