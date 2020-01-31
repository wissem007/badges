package tn.com.smartsoft.framework.dao;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;

import tn.com.smartsoft.commons.exceptions.DaoException;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;

public interface DbSessionAccessor {

	public static final int FLUSH_NEVER = 0;
	public static final int FLUSH_AUTO = 1;
	public static final int FLUSH_EAGER = 2;
	public static final int FLUSH_COMMIT = 3;
	public static final int FLUSH_ALWAYS = 4;

	public abstract void setFlushMode(int flushMode);

	public abstract int getFlushMode();

	public abstract void setFilterName(String filter);

	public abstract void setFilterNames(String[] filterNames);

	public abstract String[] getFilterNames();

	public abstract FlushMode applyFlushMode(boolean existingTransaction) throws DaoFunctionalException;

	public abstract void flushIfNecessary(boolean existingTransaction) throws DaoFunctionalException;

	public abstract void enableFilters() throws DaoFunctionalException;

	public abstract void disableFilters() throws DaoFunctionalException;

	public abstract boolean isDAOFunctionalException(DaoException daoException);

	public abstract void throwDAOException(HibernateException e) throws DaoFunctionalException;

	public abstract DaoException translateHibernateException(HibernateException ex);

}