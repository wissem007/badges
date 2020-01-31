package tn.com.smartsoft.commons.utils;

import java.io.Serializable;

import org.apache.commons.beanutils.MethodUtils;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.exceptions.utils.ExceptionUtils;

public class DefauldInvokerMethod implements Serializable, InvokerMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object controler;
	private String methodName;

	public DefauldInvokerMethod(Object controler, String methodName) {
		super();
		this.controler = controler;
		this.methodName = methodName;
	}

	public Object run(Object[] params, Class<?>[] paramsType) throws FunctionalException, TechnicalException {
		try {
			return MethodUtils.invokeMethod(controler, methodName, params, paramsType);
		} catch (Exception e) {
			ExceptionUtils.throwException(e);
			return null;
		}
	}

	public Object run(Object param1, Object param2, Object param3, Class<?> paramType1, Class<?> paramType2, Class<?> paramType3) throws FunctionalException, TechnicalException {
		return this.run(new Object[] { param1, param2, param3 }, new Class<?>[] { paramType1, paramType2, paramType3 });
	}

	public Object run(Object param1, Object param2, Class<?> paramType1, Class<?> paramType2) throws FunctionalException, TechnicalException {
		return this.run(new Object[] { param1, param2 }, new Class<?>[] { paramType1, paramType2 });
	}

	public Object run(Object param1, Class<?> paramType1) throws FunctionalException, TechnicalException {
		return this.run(new Object[] { param1 }, new Class<?>[] { paramType1 });
	}
}
