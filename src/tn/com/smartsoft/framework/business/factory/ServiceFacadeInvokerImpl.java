package tn.com.smartsoft.framework.business.factory;

import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;

public class ServiceFacadeInvokerImpl implements ServiceFacadeInvoker {
	private Object businessBean;
	private String methodName;
	private String[] parameterNames;

	public ServiceFacadeInvokerImpl(Object businessBean, String methodName, String[] parameterNames) {
		super();
		this.businessBean = businessBean;
		this.methodName = methodName;
		this.parameterNames = parameterNames;
	}

	public Object execute(Map<?, ?> prarameters) throws FunctionalException {
		try {
			Object[] args = new Object[parameterNames.length];
			for (int i = 0; i < parameterNames.length; i++) {
				args[i] = prarameters.get(parameterNames[i]);
			}
			return MethodUtils.invokeExactMethod(businessBean, methodName, args);
		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (e.getClass().isAssignableFrom(FunctionalException.class)) {
				throw (FunctionalException) e;
			} else if (cause != null && cause.getClass().isAssignableFrom(FunctionalException.class)) {
				throw (FunctionalException) cause;
			}
			throw new TechnicalException(e);
		}
	}
}
