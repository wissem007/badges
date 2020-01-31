package tn.com.smartsoft.framework.dao.impl;

import java.io.Serializable;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;

import tn.com.smartsoft.commons.exceptions.DaoException;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.DaoTechnicalException;
import tn.com.smartsoft.framework.dao.DbSessionAccessor;

public class DbSessionAccessorImpl implements DbSessionAccessor, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int flushMode = FLUSH_AUTO;

	private String[] filterNames;

	private DbSessionImpl dbSession;

	public DbSessionAccessorImpl(DbSessionImpl dbSession) {
		super();
		this.dbSession = dbSession;
	}

	public void setFlushMode(int flushMode) {
		this.flushMode = flushMode;
	}

	public int getFlushMode() {
		return this.flushMode;
	}

	public void setFilterName(String filter) {
		this.filterNames = new String[] { filter };
	}

	public void setFilterNames(String[] filterNames) {
		this.filterNames = filterNames;
	}

	public String[] getFilterNames() {
		return this.filterNames;
	}

	public FlushMode applyFlushMode(boolean existingTransaction) throws DaoFunctionalException {
		try {
			if (this.getFlushMode() == FLUSH_NEVER) {
				if (existingTransaction) {
					FlushMode previousFlushMode = dbSession.session().getFlushMode();
					if (!previousFlushMode.lessThan(FlushMode.COMMIT)) {
						dbSession.session().setFlushMode(FlushMode.NEVER);
						return previousFlushMode;
					}
				} else
					dbSession.session().setFlushMode(FlushMode.NEVER);
			} else if (this.getFlushMode() == FLUSH_EAGER) {
				if (existingTransaction) {
					FlushMode previousFlushMode = dbSession.session().getFlushMode();
					if (!previousFlushMode.equals(FlushMode.AUTO)) {
						dbSession.session().setFlushMode(FlushMode.AUTO);
						return previousFlushMode;
					}
				} else {
					// rely on default FlushMode.AUTO
				}
			} else if (this.getFlushMode() == FLUSH_COMMIT) {
				if (existingTransaction) {
					FlushMode previousFlushMode = dbSession.session().getFlushMode();
					if (previousFlushMode.equals(FlushMode.AUTO) || previousFlushMode.equals(FlushMode.ALWAYS)) {
						dbSession.session().setFlushMode(FlushMode.COMMIT);
						return previousFlushMode;
					}
				} else
					dbSession.session().setFlushMode(FlushMode.COMMIT);
			} else if (this.getFlushMode() == FLUSH_ALWAYS)
				if (existingTransaction) {
					FlushMode previousFlushMode = dbSession.session().getFlushMode();
					if (!previousFlushMode.equals(FlushMode.ALWAYS)) {
						dbSession.session().setFlushMode(FlushMode.ALWAYS);
						return previousFlushMode;
					}
				} else
					dbSession.session().setFlushMode(FlushMode.ALWAYS);

		} catch (HibernateException e) {
			dbSession.throwDAOException(e);
		}
		return null;
	}

	public void flushIfNecessary(boolean existingTransaction) throws DaoFunctionalException {
		try {
			if ((this.getFlushMode() == FLUSH_EAGER) || (!existingTransaction && (this.getFlushMode() != FLUSH_NEVER))) {
				dbSession.session().flush();
			}
		} catch (HibernateException e) {
			dbSession.throwDAOException(e);
		}
	}

	public void enableFilters() throws DaoFunctionalException {
		String[] filterNames = this.getFilterNames();
		if (filterNames != null)
			for (int i = 0; i < filterNames.length; i++)
				dbSession.session().enableFilter(filterNames[i]);
	}

	public void disableFilters() throws DaoFunctionalException {
		String[] filterNames = this.getFilterNames();
		if (filterNames != null)
			for (int i = 0; i < filterNames.length; i++)
				dbSession.session().disableFilter(filterNames[i]);
	}

	public boolean isDAOFunctionalException(DaoException daoException) {
		return dbSession.isDAOFunctionalException(daoException);
	}

	public void throwDAOException(HibernateException e) throws DaoFunctionalException {
		DaoException daoException = translateHibernateException(e);
		if (isDAOFunctionalException(daoException))
			throw (DaoFunctionalException) daoException;
		else
			throw (DaoTechnicalException) daoException;
	}

	public DaoException translateHibernateException(HibernateException ex) {
		return dbSession.translateDAOException(ex);
	}

}
