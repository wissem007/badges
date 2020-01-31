package tn.com.smartsoft.framework.dao;

import org.hibernate.stat.Statistics;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.definition.DataSourceDefinition;

public interface DbSession{
	
	public abstract void clear() throws DaoFunctionalException;
	public abstract void flush() throws DaoFunctionalException;
	public void beginTransaction() throws DaoFunctionalException;
	public void commitTransaction() throws DaoFunctionalException;
	public void rollbackTransaction() throws DaoFunctionalException;
	public abstract void close() throws DaoFunctionalException;
	public abstract Statistics getStatistics();
	public String getName();
	public abstract DbSessionAccessor accessor();
	public abstract DbQuerySupport sqlQuerySupport();
	public abstract DbQuerySupport querySupport();
	public abstract DbPersistantSupport persistantSupport(UserContext userContext);
	public abstract DbSequenceSupport sequenceSupport(UserContext userContext);
	public abstract AuditSupport auditSupport(UserContext userContext);
	public String getProductName();
	public DbEntiteConfiguration getDbEntiteConfiguration(Class<?> entityName);
	public DataSourceDefinition getDataSourceDefinition();
	public void evict(final Object bean) throws DaoFunctionalException;
}
