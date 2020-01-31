package tn.com.smartsoft.framework.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;

public interface DbPersistantSupport {
	public abstract Object get(final Class<?> entityClass, final Serializable id, final LockMode lockMode) throws DaoFunctionalException;
	
	public Object get(final Class<?> entityClass, Serializable id, final Long societeId, final Long organismeId, final boolean isInId) throws DaoFunctionalException;
	
	public List<?> getAll(final Class<?> entityClass) throws DaoFunctionalException;
	
	public abstract Object get(Class<?> entityClass, Serializable id) throws DaoFunctionalException;
	
	public abstract Serializable save(final Object entity) throws DaoFunctionalException;
	
	public abstract void update(Object entity) throws DaoFunctionalException;
	
	public abstract void update(final Object entity, final LockMode lockMode) throws DaoFunctionalException;
	
	public abstract void delete(Object entity) throws DaoFunctionalException;
	
	public abstract void delete(final Object entity, final LockMode lockMode) throws DaoFunctionalException;
	
	public abstract void saveOrUpdate(final Object entity) throws DaoFunctionalException;
	
	public abstract void replicate(final Object entity, final ReplicationMode replicationMode) throws DaoFunctionalException;
	
	public abstract void persist(final Object entity) throws DaoFunctionalException;
	
	public abstract Object merge(final Object entity) throws DaoFunctionalException;
	
	public abstract boolean contains(final Object entity) throws DaoFunctionalException;
	
	public abstract void lock(final Object entity, final LockMode lockMode) throws DaoFunctionalException;
	
	public List<?> getAll(final Class<?> entityClass, final Long societeId, final Long organismeId, final boolean isInId) throws DaoFunctionalException;
}