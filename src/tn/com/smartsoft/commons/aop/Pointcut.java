package tn.com.smartsoft.commons.aop;

import java.io.Serializable;

public interface Pointcut extends Serializable { 
	ClassFilter getClassFilter();

	MethodMatcher getMethodMatcher();

	Pointcut TRUE = TruePointcut.INSTANCE;

}
