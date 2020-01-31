package tn.com.smartsoft.framework.dao.impl;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbSession;

public class DbPersistantSupportImpl implements DbPersistantSupport, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DbSessionImpl dbSession;
	
	protected DbPersistantSupportImpl(DbSessionImpl dbSession) {
		super();
		this.dbSession = dbSession;
	}
	
	public Object get(final Class<?> entityClass, final Serializable id, final LockMode lockMode) throws DaoFunctionalException {
		return dbSession.executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				if (lockMode != null)
					return session.get(entityClass, id, lockMode);
				else
					return session.get(entityClass, id);
			}
		});
	}
	
	public List<?> getAll(final Class<?> entityClass) throws DaoFunctionalException {
		return (List<?>) dbSession.executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				Query query = session.createQuery("SELECT  bean FROM  " + entityClass.getName() + " AS bean");
				return query.list();
			}
		});
	}
	
	public List<?> getAll(final Class<?> entityClass, final Long societeId, final Long organismeId, final boolean isInId) throws DaoFunctionalException {
		return (List<?>) dbSession.executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(entityClass);
				if (societeId != null)
					criteria.add(Restrictions.eq(isInId ? "societeId" : "id.societeId", societeId));
				if (organismeId != null)
					criteria.add(Restrictions.eq(isInId ? "organismeId" : "id.organismeId", organismeId));
				return criteria.list();
			}
		});
	}
	
	public Object get(final Class<?> entityClass, Serializable id, final Long societeId, final Long organismeId, final boolean isInId) throws DaoFunctionalException {
		return (List<?>) dbSession.executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(entityClass);
				if (societeId != null)
					criteria.add(Restrictions.eq(isInId ? "societeId" : "id.societeId", societeId));
				if (organismeId != null)
					criteria.add(Restrictions.eq(isInId ? "organismeId" : "id.organismeId", organismeId));
				return criteria.uniqueResult();
			}
		});
	}
	
	public Object get(Class<?> entityClass, Serializable id) throws DaoFunctionalException {
		return this.get(entityClass, id, null);
	}
	
	public Serializable save(final Object entity) throws DaoFunctionalException {
		return (Serializable) dbSession.executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				return session.save(entity);
			}
		});
	}
	
	public void update(Object entity) throws DaoFunctionalException {
		this.update(entity, null);
	}
	
	public void update(final Object entity, final LockMode lockMode) throws DaoFunctionalException {
		dbSession.executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				session.update(entity);
				if (lockMode != null)
					session.lock(entity, lockMode);
				return null;
			}
		});
	}
	
	public void delete(Object entity) throws DaoFunctionalException {
		this.delete(entity, null);
	}
	
	public void delete(final Object entity, final LockMode lockMode) throws DaoFunctionalException {
		dbSession.executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				if (lockMode != null)
					session.lock(entity, lockMode);
				session.delete(entity);
				return null;
			}
		});
	}
	
	public void saveOrUpdate(final Object entity) throws DaoFunctionalException {
		dbSession.executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				session.saveOrUpdate(entity);
				return null;
			}
		});
	}
	
	public void replicate(final Object entity, final ReplicationMode replicationMode) throws DaoFunctionalException {
		dbSession.executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				session.replicate(entity, replicationMode);
				return null;
			}
		});
	}
	
	public void persist(final Object entity) throws DaoFunctionalException {
		dbSession.executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				session.persist(entity);
				return null;
			}
		});
	}
	
	public Object merge(final Object entity) throws DaoFunctionalException {
		return dbSession.executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				return session.merge(entity);
			}
		});
	}
	
	public boolean contains(final Object entity) throws DaoFunctionalException {
		Boolean result = (Boolean) dbSession.executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) {
				return (session.contains(entity) ? Boolean.TRUE : Boolean.FALSE);
			}
		});
		return result.booleanValue();
	}
	
	public void lock(final Object entity, final LockMode lockMode) throws DaoFunctionalException {
		dbSession.executeDbCallback(new DbCallback() {
			public Object doExecute(Session session) throws HibernateException {
				session.lock(entity, lockMode);
				return null;
			}
		});
	}
	
	public DbSession dbSession() {
		return dbSession;
	}
	
}
