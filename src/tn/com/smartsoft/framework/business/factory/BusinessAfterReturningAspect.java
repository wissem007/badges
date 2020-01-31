/**
 * 
 */
package tn.com.smartsoft.framework.business.factory;

import java.lang.reflect.Method;

import tn.com.smartsoft.commons.aop.advice.AfterReturningAdvice;
import tn.com.smartsoft.commons.log.Logger;

public class BusinessAfterReturningAspect implements AfterReturningAdvice {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BusinessBeforeAspect businessBeforeAspect;

	public BusinessAfterReturningAspect(BusinessBeforeAspect businessBeforeAspect) {
		super();
		this.businessBeforeAspect = businessBeforeAspect;
	}

	public void afterReturning(Object returnValue, Method method, Object[] arguments, Object target) throws Throwable {
		Logger log = Logger.getLogger(target.getClass());
		log.debug("\n----------------------->end   :[" + method.getName() + "] Time running " + (System.currentTimeMillis() - businessBeforeAspect.getStartTime())
				+ "(ms)\n");
	}
}