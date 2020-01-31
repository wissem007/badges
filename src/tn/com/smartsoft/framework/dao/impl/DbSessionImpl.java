package tn.com.smartsoft.framework.dao.impl;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;

import tn.com.smartsoft.commons.exceptions.DaoException;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.DaoTechnicalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.AuditSupport;
import tn.com.smartsoft.framework.dao.DbEntiteConfiguration;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbQuerySupport;
import tn.com.smartsoft.framework.dao.DbSequenceSupport;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.DbSessionAccessor;
import tn.com.smartsoft.framework.dao.definition.DataSourceDefinition;
import tn.com.smartsoft.framework.dao.definition.DbDefinition;
import tn.com.smartsoft.framework.dao.exception.DbExceptionTranslator;
import tn.com.smartsoft.framework.dao.utils.DbOperationUtilis;
import tn.com.smartsoft.framework.dao.utils.DbSessionThreadLocal;

public class DbSessionImpl implements DbSession, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(DbSession.class);
	private DataSourceDefinition dataSourceDefinition;
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction = null;
	private DbDefinition dbDefinition;
	private String name;
	
	public DbSessionImpl(String name, DbDefinition dbDefinition) {
		super();
		this.name = name;
		this.dbDefinition = dbDefinition;
		this.dataSourceDefinition = this.dbDefinition.getDataBaseDefinition().getDataSourceDefinition(name);
		this.sessionFactory = dataSourceDefinition.getSessionFactory();
	}
	
	public String getProductName() {
		return dataSourceDefinition.getProductName();
	}
	
	public DataSourceDefinition getDataSourceDefinition() {
		return dataSourceDefinition;
	}
	
	public void beginTransaction() throws DaoFunctionalException {
		try {
			if (this.transaction == null) {
				log.debug("Starting new database transaction in this thread.");
				this.transaction = this.session().beginTransaction();
				clearAndFlush();
			}
		} catch (HibernateException e) {
			this.transaction = null;
			this.throwDAOException(e);
		}
	}
	
	public void commitTransaction() throws DaoFunctionalException {
		try {
			if ((this.transaction != null) && !this.transaction.wasCommitted() && !this.transaction.wasRolledBack()) {
				log.debug("Committing database transaction of this thread.");
				this.transaction.commit();
				clearAndFlush();
				log.debug("Database transaction Committed.");
			}
			this.transaction = null;
		} catch (HibernateException e) {
			this.throwDAOException(e);
		}
	}
	
	public void rollbackTransaction() throws DaoFunctionalException {
		try {
			if ((this.transaction != null) && !this.transaction.wasCommitted() && !this.transaction.wasRolledBack()) {
				log.debug("Trying to rollback database transaction of this thread.");
				this.transaction.rollback();
				clearAndFlush();
				log.debug("Database transaction rolled back.");
			}
			this.transaction = null;
		} catch (HibernateException e) {
			this.throwDAOException(e);
		}
	}
	
	public void close() throws DaoFunctionalException {
		try {
			if ((this.session != null) && this.session.isOpen()) {
				log.debug("Deamde explicite de fermeture d'une session Hibernate ouverte.");
				clearAndFlush();
				this.session.close();
			}
			this.session = null;
			DbSessionThreadLocal.removeResource(name);
		} catch (HibernateException e) {
			this.session = null;
			throwDAOException(e);
		}
	}
	
	public String getName() {
		return dataSourceDefinition.getName();
	}
	
	public Session session() throws DaoFunctionalException {
		try {
			if (DbSessionThreadLocal.getResource(name) == null && DbSessionThreadLocal.getResource(name) != this) {
				DbSessionThreadLocal.addResource(name, this);
			}
			if (this.session == null) {
				this.transaction = null;
				log.debug("Opening new Session for this thread.");
				this.session = this.sessionFactory.openSession();
				return this.session;
			} else if (!this.session.isOpen() || !this.session.isConnected()) {
				this.session = null;
				return this.session();
			}
		} catch (HibernateException e) {
			throwDAOException(e);
		}
		return this.session;
	}
	
	public Statistics getStatistics() {
		return sessionFactory.getStatistics();
	}
	
	public boolean isDAOFunctionalException(DaoException daoException) {
		return exceptionTranslator().isDAOFunctionalException(daoException);
	}
	
	public DbExceptionTranslator exceptionTranslator() {
		return new DbExceptionTranslator(dataSourceDefinition, dbDefinition);
	}
	
	public DbEntiteConfiguration getDbEntiteConfiguration(Class<?> entityName) {
		return new DbEntiteConfiguration(dataSourceDefinition.getClassMapping(entityName.getName()), sessionFactory.getClassMetadata(entityName), dbDefinition);
	}
	
	public void throwDAOException(HibernateException e) throws DaoFunctionalException {
		DaoException daoException = translateDAOException(e);
		if (isDAOFunctionalException(daoException))
			throw (DaoFunctionalException) daoException;
		else
			throw (DaoTechnicalException) daoException;
	}
	
	public DaoException translateDAOException(HibernateException ex) {
		return exceptionTranslator().translateDaoException(ex);
	}
	
	public DbSessionAccessor accessor() {
		return new DbSessionAccessorImpl(this);
	}
	
	public Object executeDbCallback(DbCallback action) throws DaoFunctionalException {
		return DbOperationUtilis.execute(action, this);
	}
	
	public void clearAndFlush() {
		try {
			session.clear();
			session.flush();
		} catch (HibernateException e) {
			log.warn(e);
		}
	}
	
	public void clear() throws DaoFunctionalException {
		executeDbCallback(new DbCallback() {
			
			public Object doExecute(Session session) {
				session.clear();
				return null;
			}
		});
	}
	
	public void evict(final Object bean) throws DaoFunctionalException {
		executeDbCallback(new DbCallback() {
			
			public Object doExecute(Session session) {
				session.evict(bean);
				return null;
			}
		});
	}
	
	public void flush() throws DaoFunctionalException {
		executeDbCallback(new DbCallback() {
			
			public Object doExecute(Session session) {
				session.flush();
				return null;
			}
		});
	}
	
	public DbPersistantSupport persistantSupport(UserContext userContext) {
		return new DbPersistantSupportImpl(this);
	}
	
	public DbQuerySupport sqlQuerySupport() {
		return new DbSqlQuerySupportImpl(this);
	}
	
	public DbQuerySupport querySupport() {
		return new DbQuerySupportImpl(this);
	}
	
	public DbSequenceSupport sequenceSupport(UserContext userContext) {
		return new DbSequenceSupportImpl(this, userContext);
	}
	
	public AuditSupport auditSupport(UserContext userContext) {
		return new AuditSupportImpl(this, userContext);
	}
}
