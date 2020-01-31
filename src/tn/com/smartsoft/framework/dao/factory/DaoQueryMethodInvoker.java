package tn.com.smartsoft.framework.dao.factory;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.aop.advice.InvocationAdvice;
import tn.com.smartsoft.commons.exceptions.utils.ExceptionUtils;
import tn.com.smartsoft.framework.dao.DaoParseBean;
import tn.com.smartsoft.framework.dao.DbQueryAccessor;
import tn.com.smartsoft.framework.dao.DbQuerySupport;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.definition.DaoQueryMethodDefinition;
import tn.com.smartsoft.framework.dao.definition.DaoSqlQueryMethodDefinition;
import tn.com.smartsoft.framework.dao.definition.DbQueryAccessorDefinition;
import tn.com.smartsoft.framework.dao.impl.DbQueryAccessorImpl;

public class DaoQueryMethodInvoker implements InvocationAdvice {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DaoParseBean daoParseBean;

	protected DaoQueryMethodDefinition methodDefinition;

	public DaoQueryMethodInvoker(DaoQueryMethodDefinition methodDefinition, DaoParseBean daoParseBean) {
		super();
		this.methodDefinition = methodDefinition;
		this.daoParseBean = daoParseBean;
	}

	public Object invoke(Method method, Object[] arguments, Object target) throws Throwable {
		try {
			DaoQueryMethodDefinition qMethodDefinition = (DaoQueryMethodDefinition) methodDefinition;
			DbQueryAccessorDefinition accessorDefinition = qMethodDefinition.getAccessor();
			DbQueryAccessor accessor = new DbQueryAccessorImpl();
			int sessionArgRang = methodDefinition.getSessionArgRang() < 0 ? 0 : methodDefinition.getSessionArgRang();
			String prefixArgName = StringUtils.isBlank(methodDefinition.getPrefixArgName()) ? "arg" : methodDefinition.getPrefixArgName();
			DbSession session = (DbSession) arguments[sessionArgRang];
			DbQuerySupport dbQuerySupport;
			if (qMethodDefinition instanceof DaoSqlQueryMethodDefinition) {
				dbQuerySupport = session.sqlQuerySupport();
			} else {
				dbQuerySupport = session.querySupport();
			}
			if (accessorDefinition != null) {
				accessor.setCacheQueries(accessorDefinition.isCacheQueries());
				accessor.setFetchSize(accessorDefinition.getFetchSize());
				accessor.setLockModeEntite(accessorDefinition.getLockModeEntite());
				accessor.setMaxResults(accessorDefinition.getMaxResults());
				accessor.setQueryCacheRegion(accessorDefinition.getQueryCacheRegion());
			}
			String[] paramNames = new String[arguments.length];
			Object[] paramValues = new Object[arguments.length];
			for (int i = 0; i < arguments.length; i++) {
				paramNames[i] = prefixArgName + i;
				paramValues[i] = arguments[i];
			}
			if (!qMethodDefinition.isUpdateQuery()) {
				if (method.getReturnType().isAssignableFrom(List.class)) {
					List<?> listResulat = dbQuerySupport.execute(qMethodDefinition.getQuery(), paramNames, paramValues, qMethodDefinition.getClassResultat(), accessor);
					if (daoParseBean != null) {
						listResulat = daoParseBean.parse(listResulat);
					}
					return listResulat;
				} else {
					Object o = dbQuerySupport.executeUniqueResult(qMethodDefinition.getQuery(), paramNames, paramValues, qMethodDefinition.getClassResultat(), accessor);
					if (daoParseBean != null) {
						o = daoParseBean.parse(o);
					}
					return o;
				}
			} else {
				int r = dbQuerySupport.executeUpdate(qMethodDefinition.getQuery(), paramNames, paramValues, accessor);
				if (!method.getReturnType().isAssignableFrom(void.class)) {
					return r;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			ExceptionUtils.throwException(e);
			return null;
		}
	}
}
