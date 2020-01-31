package tn.com.smartsoft.framework.dao;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;

public interface DbQuerySupport {
	public abstract int executeUpdate(String queryString, String[] paramNames, Object[] paramValues) throws DaoFunctionalException;

	public abstract int executeUpdate(String queryString, String[] paramNames, Object[] paramValues, DbQueryAccessor accessor) throws DaoFunctionalException;

	@SuppressWarnings("rawtypes")
	public abstract List execute(String queryString, String[] paramNames, Object[] paramValues, Class classBeanResult) throws DaoFunctionalException;

	@SuppressWarnings("rawtypes")
	public abstract List execute(String queryString, String[] paramNames, Object[] paramValues, Class classBeanResult, DbQueryAccessor accessor) throws DaoFunctionalException;

	@SuppressWarnings("rawtypes")
	public abstract Object executeUniqueResult(String queryString, String[] paramNames, Object[] paramValues, Class classBeanResult) throws DaoFunctionalException;

	@SuppressWarnings("rawtypes")
	public abstract Object executeUniqueResult(String queryString, String[] paramNames, Object[] paramValues, Class classBeanResult, DbQueryAccessor accessor) throws DaoFunctionalException;
}