package tn.com.smartsoft.commons.utils;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;

public interface InvokerMethod {
	
	public abstract Object run(Object[] params, Class<?>[] paramsType) throws FunctionalException, TechnicalException;
	
	public abstract Object run(Object param1, Object param2, Object param3, Class<?> paramType1, Class<?> paramType2, Class<?> paramType3) throws FunctionalException, TechnicalException;
	
	public abstract Object run(Object param1, Object param2, Class<?> paramType1, Class<?> paramType2) throws FunctionalException, TechnicalException;
	
	public abstract Object run(Object param1, Class<?> paramType1) throws FunctionalException, TechnicalException;
	
}