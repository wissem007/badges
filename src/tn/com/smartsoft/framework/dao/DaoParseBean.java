package tn.com.smartsoft.framework.dao;

import java.util.List;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;

public interface DaoParseBean {
	public Object parse(Object o) throws DaoFunctionalException;

	public List<?> parse(List<?> entitys) throws DaoFunctionalException;

	public Map<?, ?> parseMap(List<?> entitys) throws DaoFunctionalException;

	public Map<?, ?> parseMap(List<?> entitys, String keyPropertyName) throws DaoFunctionalException;
}
