package tn.com.smartsoft.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;

public interface DbSequenceSupport {
	public abstract Serializable getSequenceValue(Object bean, String property, Map<String, Object> context) throws DaoFunctionalException;
	
	public abstract Serializable getSequenceValue(Object bean, String property) throws DaoFunctionalException;
	
	public void setSequenceValues(Object bean, Map<String, Object> context) throws DaoFunctionalException;
	
	public void setSequenceValues(Object bean) throws DaoFunctionalException;
	
	public List<Serializable> getSequenceValue(@SuppressWarnings("rawtypes") List beans, String property, Map<String, Object> context) throws DaoFunctionalException;
	
	public List<Serializable> getSequenceValue(@SuppressWarnings("rawtypes") List beans, String property) throws DaoFunctionalException;
}
