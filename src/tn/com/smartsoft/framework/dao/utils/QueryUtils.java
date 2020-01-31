package tn.com.smartsoft.framework.dao.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.FreemarkerUtils;
import tn.com.smartsoft.framework.dao.DbQueryAccessor;

public class QueryUtils {

	private static final char PARAMETER_PREFIX = ':';

	public static void prepareQuery(Query queryObject, DbQueryAccessor queryAccessor) {
		if (queryAccessor == null)
			return;
		if (queryAccessor.isCacheQueries()) {
			queryObject.setCacheable(true);
			if (queryAccessor.getQueryCacheRegion() != null)
				queryObject.setCacheRegion(queryAccessor.getQueryCacheRegion());
		}
		if (queryAccessor.getFetchSize() > 0)
			queryObject.setFetchSize(queryAccessor.getFetchSize());
		if (queryAccessor.getMaxResults() > 0)
			queryObject.setMaxResults(queryAccessor.getMaxResults());
	}

	public static void prepareCriteria(Criteria criteria, DbQueryAccessor queryAccessor) {
		if (queryAccessor == null)
			return;
		if (queryAccessor.isCacheQueries()) {
			criteria.setCacheable(true);
			if (queryAccessor.getQueryCacheRegion() != null)
				criteria.setCacheRegion(queryAccessor.getQueryCacheRegion());
		}
		if (queryAccessor.getFetchSize() > 0)
			criteria.setFetchSize(queryAccessor.getFetchSize());
		if (queryAccessor.getMaxResults() > 0)
			criteria.setMaxResults(queryAccessor.getMaxResults());
	}

	public static InputQuery processInputQuery(Map params, String templateQuery) throws TechnicalException {

		String query = FreemarkerUtils.process(params, templateQuery);
		Map valuesToBind = new HashMap();
		query = constructSQL(query, params, valuesToBind);
		return new InputQuery(query, valuesToBind);
	}

	public static InputQuery processInputQuery(String[] paramNames, Object[] paramValues, String templateQuery) throws TechnicalException {
		Map params = new HashMap();
		for (int i = 0; i < paramValues.length; i++) {
			params.put(paramNames[i], paramValues[i]);
		}
		return processInputQuery(params, templateQuery);
	}

	private static String constructSQL(String sql, Map parameterValues, Map valuesToBind) throws TechnicalException {
		StringBuffer buf = new StringBuffer(sql.length());
		int fromIndex = 0;
		int prefixIndex = sql.indexOf(PARAMETER_PREFIX);
		while (prefixIndex != -1) {
			buf.append(sql.substring(fromIndex, prefixIndex));
			if (prefixIndex + 1 > sql.length()) {
				buf.append(PARAMETER_PREFIX);

			} else {
				int length = 1;
				if (Character.isJavaIdentifierStart(sql.charAt(prefixIndex + 1))) {
					do {
						length++;
					} while ((prefixIndex + length < sql.length())
							&& ((Character.isJavaIdentifierPart(sql.charAt(prefixIndex + length))) || (sql.charAt(prefixIndex + length) == PropertyUtils.NESTED_DELIM)));
					String parameterName = sql.substring(prefixIndex + 1, prefixIndex + length);
					String beanName = getBeanName(parameterName);
					String parmName = "";
					if (parameterValues != null && (parameterValues.containsKey(beanName))) {
						parmName = parameterName.replaceAll("\\.", "_");
						if (!valuesToBind.containsKey(parmName)) {
							Object parameterValue = null;
							try {
								parameterValue = PropertyUtils.getProperty(parameterValues, parameterName);
							} catch (Exception e) {
								throw new TechnicalException(e);
							}
							valuesToBind.put(parmName, parameterValue);
						}
						buf.append(":").append(parmName);
					} else {
						buf.append(PARAMETER_PREFIX).append(parameterName);
					}
					fromIndex = prefixIndex + length;

				} else {
					buf.append(PARAMETER_PREFIX);
					fromIndex = prefixIndex + 1;
				}
			}
			prefixIndex = sql.indexOf(PARAMETER_PREFIX, fromIndex);

		}
		buf.append(sql.substring(fromIndex));
		return buf.toString();

	}

	private static String getBeanName(String parameterName) throws TechnicalException {
		int nestedIndex = parameterName.indexOf(PropertyUtils.NESTED_DELIM);
		if (nestedIndex == -1) {
			return parameterName;
		} else {
			return parameterName.substring(0, nestedIndex);
		}
	}

	public static void applyParameter(Query queryObject, Map values) throws HibernateException {
		for (Iterator iterator = values.keySet().iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			applyParameter(queryObject, name, values.get(name));
		}
	}

	public static void applyParameter(Query queryObject, String paramName, Object value) throws HibernateException {
		if (value instanceof Collection)
			queryObject.setParameterList(paramName, (Collection) value);
		else if (value instanceof Object[])
			queryObject.setParameterList(paramName, (Object[]) value);
		else
			queryObject.setParameter(paramName, value);
	}
}
