package tn.com.smartsoft.framework.dao.utils;

import java.util.Iterator;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.framework.dao.DbSession;

public abstract class DbSessionUtils {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void commitTransaction(DbSession dbSession) throws DaoFunctionalException {
		if (dbSession != null)
			dbSession.commitTransaction();
	}

	public static void rollbackTransaction(DbSession dbSession) throws DaoFunctionalException {
		if (dbSession != null)
			dbSession.rollbackTransaction();
	}

	public static void commitTransactionAndClose(DbSession dbSession) throws DaoFunctionalException {
		if (dbSession != null) {
			dbSession.commitTransaction();
			dbSession.close();
		}
	}

	public static void rollbackTransactionAndClose(DbSession dbSession) throws DaoFunctionalException {
		if (dbSession != null) {
			dbSession.rollbackTransaction();
			dbSession.close();
		}
	}

	public static void clearResource() {
		DbSessionThreadLocal.clearResource();
	}

	public static void commitTransactionAndCloseAll() throws DaoFunctionalException {
		Map map = DbSessionThreadLocal.getResourceMap();
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			Object sesionName = iter.next();
			commitTransactionAndClose((DbSession) DbSessionThreadLocal.getResource(sesionName));
		}
		clearResource();
	}

	public static void rollbackTransactionAndCloseAll() throws DaoFunctionalException {
		Map map = DbSessionThreadLocal.getResourceMap();
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			Object sesionName = iter.next();
			rollbackTransactionAndClose((DbSession) DbSessionThreadLocal.getResource(sesionName));
		}
		clearResource();
	}
}
