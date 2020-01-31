/**
 * 
 */
package tn.com.smartsoft.framework.business.factory;

import java.lang.reflect.Method;

import tn.com.smartsoft.commons.aop.advice.BeforeAdvice;
import tn.com.smartsoft.commons.log.Logger;

public class BusinessBeforeAspect implements BeforeAdvice {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long startTime = 0;

	public void before(Method method, Object[] arguments, Object target) throws Throwable {
		Logger log = Logger.getLogger(target.getClass());
		startTime = System.currentTimeMillis();
		log.debug("\n----------------------->start :[" + method.getName() + "]");
	}

	public long getStartTime() {
		return startTime;
	}

}