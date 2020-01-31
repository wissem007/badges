package tn.com.smartsoft.framework.dao.factory;

import java.io.Serializable;

import org.hibernate.HibernateException;

import tn.com.smartsoft.commons.exceptions.DaoException;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.DaoTechnicalException;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.dao.DaoParseBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.definition.DataSourceDefinition;
import tn.com.smartsoft.framework.dao.definition.DbDefinition;
import tn.com.smartsoft.framework.dao.exception.DbExceptionTranslator;
import tn.com.smartsoft.framework.dao.impl.DaoParseBeanImpl;
import tn.com.smartsoft.framework.dao.impl.DbSessionImpl;

public class DaoFactoryImpl implements Serializable, DaoFactory {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DbDefinition dbDefinition;

	public DaoFactoryImpl(DbDefinition dbDefinition) {
		super();
		this.dbDefinition = dbDefinition;
	}

	public Object createDaoBean(ComponentId daoBeanId) throws DaoFunctionalException {
		return DaoBeanFactoryUtils.createDaoBean(dbDefinition, this, daoBeanId);
	}

	public DaoParseBean createDaoParseBean(ComponentId daoParseBeanId) throws DaoFunctionalException {
		DaoParseBeanImpl daoParseBean = new DaoParseBeanImpl(dbDefinition.getDaoParseBeanDefinition(daoParseBeanId));
		return daoParseBean;
	}

	public DbSession createDbSession(String sesionName) throws DaoFunctionalException {
		try {
			return new DbSessionImpl(sesionName, dbDefinition);
		} catch (HibernateException e) {
			DataSourceDefinition dataSourceDefinition = this.dbDefinition.getDataBaseDefinition().getDataSourceDefinition(sesionName);
			DbExceptionTranslator exceptionTranslator = new DbExceptionTranslator(dataSourceDefinition, dbDefinition);
			DaoException daoException = exceptionTranslator.translateDaoException(e);
			if (exceptionTranslator.isDAOFunctionalException(daoException))
				throw (DaoFunctionalException) daoException;
			else
				throw (DaoTechnicalException) daoException;
		}
	}

}
