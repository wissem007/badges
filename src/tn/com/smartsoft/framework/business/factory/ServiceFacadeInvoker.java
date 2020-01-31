package tn.com.smartsoft.framework.business.factory;

import java.util.Map;

import tn.com.smartsoft.commons.exceptions.FunctionalException;

public interface ServiceFacadeInvoker {
	public Object execute(Map<?, ?> prarameters) throws FunctionalException;
}
