package tn.com.smartsoft.framework.business.factory;

import tn.com.smartsoft.commons.aop.Aspect;
import tn.com.smartsoft.commons.aop.GenericPointcut;
import tn.com.smartsoft.commons.aop.PatternMethodMatcher;
import tn.com.smartsoft.commons.aop.proxy.ProxyFactory;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.beans.factory.CreateInstanceBeanFactory;

public class BusinessCreateInstanceFactory implements CreateInstanceBeanFactory {

	private static final String BUSINESS_METHOD_PATTERN = "do\\w+";

	public Object create(Object bean) {
		try {
			ProxyFactory proxyFactory = new ProxyFactory();
			GenericPointcut pointcut = new GenericPointcut(new PatternMethodMatcher(BUSINESS_METHOD_PATTERN));
			BusinessBeforeAspect businessBeforeAspect = new BusinessBeforeAspect();
			BusinessAfterReturningAspect businessAfterReturningAspect = new BusinessAfterReturningAspect(businessBeforeAspect);
			Aspect beforeAspect = new Aspect(businessBeforeAspect, pointcut);
			Aspect afterAspect = new Aspect(businessAfterReturningAspect, pointcut);
			proxyFactory.addAspect(beforeAspect);
			proxyFactory.addAspect(afterAspect);
			proxyFactory.setTarget(bean);
			return proxyFactory.getProxy();
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
}
