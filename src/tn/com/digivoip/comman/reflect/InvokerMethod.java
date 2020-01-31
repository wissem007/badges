package tn.com.digivoip.comman.reflect;

import java.io.Serializable;
import org.apache.commons.beanutils.MethodUtils;
import tn.com.digivoip.framework.exception.TechnicalException;

public class InvokerMethod implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private Object				controler;
	private String				methodName;

	public InvokerMethod(Object controler, String methodName) {
		super();
		this.controler = controler;
		this.methodName = methodName;
	}
	public Object run(Object[] params, Class<?>[] paramsType) throws TechnicalException {
		try {
			return MethodUtils.invokeMethod(controler, methodName, params, paramsType);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	public Object run(Object param1, Object param2, Object param3, Object param4, Object param5, Class<?> paramType1, Class<?> paramType2, Class<?> paramType3, Class<?> paramType4,
			Class<?> paramType5) throws TechnicalException {
		return this.run(new Object[] { param1, param2, param3, param4, param5 }, new Class<?>[] { paramType1, paramType2, paramType3, paramType4, paramType5 });
	}
	public Object run(Object param1, Object param2, Object param3, Object param4, Class<?> paramType1, Class<?> paramType2, Class<?> paramType3, Class<?> paramType4)
			throws TechnicalException {
		return this.run(new Object[] { param1, param2, param3, param4 }, new Class<?>[] { paramType1, paramType2, paramType3, paramType4 });
	}
	public Object run(Object param1, Object param2, Object param3, Class<?> paramType1, Class<?> paramType2, Class<?> paramType3) throws TechnicalException {
		return this.run(new Object[] { param1, param2, param3 }, new Class<?>[] { paramType1, paramType2, paramType3 });
	}
	public Object run(Object param1, Object param2, Class<?> paramType1, Class<?> paramType2) throws TechnicalException {
		return this.run(new Object[] { param1, param2 }, new Class<?>[] { paramType1, paramType2 });
	}
	public Object run(Object param1, Class<?> paramType1) throws TechnicalException {
		return this.run(new Object[] { param1 }, new Class<?>[] { paramType1 });
	}
	public String getMethodName() {
		return methodName;
	}
}
