package tn.com.smartsoft.framework.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.framework.dao.DbQueryAccessor;
import tn.com.smartsoft.framework.dao.DbQuerySupport;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.utils.AliasSqlToBeanResultTransformer;
import tn.com.smartsoft.framework.dao.utils.InputQuery;
import tn.com.smartsoft.framework.dao.utils.QueryUtils;

public class DbSqlQuerySupportImpl implements DbQuerySupport, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DbSession dbSession;

	public DbSqlQuerySupportImpl(DbSession dbSession) {
		super();
		this.dbSession = dbSession;
	}

	public int executeUpdate(String queryString, String[] paramNames, Object[] paramValues) throws DaoFunctionalException {
		return this.executeUpdate(queryString, paramNames, paramNames, null);
	}

	public int executeUpdate(String queryString, String[] paramNames, Object[] paramValues, final DbQueryAccessor accessor)
			throws DaoFunctionalException {
		if ((paramNames != null) && (paramValues != null) && (paramNames.length != paramValues.length))
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		final InputQuery inputQuery = QueryUtils.processInputQuery(paramNames, paramValues, queryString);
		Integer updateCount = (Integer) ((DbSessionImpl) dbSession).executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				Query queryObject = session.createSQLQuery(inputQuery.request());
				QueryUtils.prepareQuery(queryObject, accessor);
				QueryUtils.applyParameter(queryObject, inputQuery.getValues());
				return new Integer(queryObject.executeUpdate());
			}
		});
		return updateCount.intValue();
	}

	public Object executeUniqueResult(final String queryString, final String[] paramNames, final Object[] paramValues, final Class classBeanResult)
			throws DaoFunctionalException {
		return this.executeUniqueResult(queryString, paramNames, paramValues, classBeanResult, null);
	}

	public Object executeUniqueResult(final String queryString, final String[] paramNames, final Object[] paramValues, final Class classBeanResult,
			final DbQueryAccessor accessor) throws DaoFunctionalException {
		final Query queryObject = prepareQuery(queryString, paramNames, paramValues, classBeanResult, accessor);
		return ((DbSessionImpl) dbSession).executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				return queryObject.uniqueResult();
			}
		});
	}

	public List execute(final String queryString, final String[] paramNames, final Object[] paramValues, final Class classBeanResult)
			throws DaoFunctionalException {
		return this.execute(queryString, paramNames, paramValues, classBeanResult, null);
	}

	public List execute(final String queryString, final String[] paramNames, final Object[] paramValues, final Class classBeanResult,
			final DbQueryAccessor accessor) throws DaoFunctionalException {
		final Query queryObject = prepareQuery(queryString, paramNames, paramValues, classBeanResult, accessor);
		return (List) ((DbSessionImpl) dbSession).executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				return queryObject.list();
			}
		});
	}

	private Query prepareQuery(final String queryString, final String[] paramNames, final Object[] paramValues, final Class classBeanResult,
			final DbQueryAccessor accessor) throws DaoFunctionalException {
		if (paramNames.length != paramValues.length)
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		return (Query) ((DbSessionImpl) dbSession).executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				final InputQuery inputQuery = QueryUtils.processInputQuery(paramNames, paramValues, queryString);
				Query queryObject = session.createSQLQuery(inputQuery.request());
				System.out.println(classBeanResult.getName());
				if (classBeanResult != null
						&& (!classBeanResult.getName().startsWith("java") || classBeanResult.getName().equals(HashMap.class.getName())))
					queryObject.setResultTransformer(new AliasSqlToBeanResultTransformer(classBeanResult, dbSession.getProductName()
							.equalsIgnoreCase("ctree")));
				QueryUtils.prepareQuery(queryObject, accessor);
				QueryUtils.applyParameter(queryObject, inputQuery.getValues());
				return queryObject;
			}
		});
	}

}
