package tn.com.smartsoft.framework.dao.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import tn.com.smartsoft.commons.exceptions.DaoException;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.DaoTechnicalException;
import tn.com.smartsoft.framework.dao.impl.DbCallback;
import tn.com.smartsoft.framework.dao.impl.DbSessionImpl;

public class DbOperationUtilis {

	public static Object execute(DbCallback action, DbSessionImpl dbSession) throws DaoFunctionalException {
		try {
			Session session = dbSession.session();
			dbSession.accessor().enableFilters();
			Object result = action.doExecute(session);
			return result;
		} catch (HibernateException ex) {
			DaoException daoException = dbSession.accessor().translateHibernateException(ex);
			if (dbSession.accessor().isDAOFunctionalException(daoException)) {
				throw (DaoFunctionalException) daoException;
			} else {
				throw (DaoTechnicalException) daoException;
			}
		} catch (Exception ex) {
			throw new DaoTechnicalException(ex);
		}
	}
}
