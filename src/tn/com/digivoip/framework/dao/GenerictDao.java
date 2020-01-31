package tn.com.digivoip.framework.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import tn.com.digivoip.framework.exception.DataAccessException;

public class GenerictDao{

	public GenerictDao() {}
	public void saveOrUpdate(Object obj) {
		try {
			HibernateUtils.getSession().saveOrUpdate(obj);
		} catch (HibernateException e) {
			handleException(e);
		}
	}
	public void update(Object obj) {
		try {
			HibernateUtils.getSession().update(obj);
		} catch (HibernateException e) {
			handleException(e);
		}
	}
	public void save(Object obj) {
		try {
			HibernateUtils.getSession().save(obj);
		} catch (HibernateException e) {
			handleException(e);
		}
	}
	public void closeSession() throws DataAccessException {
		HibernateUtils.closeSession();
	}
	public void beginTransaction() throws DataAccessException {
		HibernateUtils.beginTransaction();
	}
	public void commitTransaction() throws DataAccessException {
		HibernateUtils.commitTransaction();
	}
	public void rollbackTransaction() throws DataAccessException {
		HibernateUtils.rollbackTransaction();
	}
	public void delete(Object obj) {
		try {
			HibernateUtils.getSession().delete(obj);
		} catch (HibernateException e) {
			handleException(e);
		}
	}
	public void deleteAll(Class<?> clazz) {
		try {
			Query query = HibernateUtils.getSession().createQuery("delete from " + clazz.getName());
			query.executeUpdate();
		} catch (HibernateException e) {
			handleException(e);
		}
	}
	public void deleteAll(String table) {
		try {
			Query query = HibernateUtils.getSession().createSQLQuery("truncate TABLE " + table);
			query.executeUpdate();
		} catch (HibernateException e) {
			handleException(e);
		}
	}
	public void deleteQuery(String _query) {
		try {
			Query query = HibernateUtils.getSession().createQuery(_query);
			query.executeUpdate();
		} catch (HibernateException e) {
			handleException(e);
		}
	}
	public Object find(Class<?> clazz, Serializable id) {
		Object obj = null;
		try {
			obj = HibernateUtils.getSession().get(clazz, id);
		} catch (HibernateException e) {
			handleException(e);
		}
		return obj;
	}
	public Object find(Class<?> clazz, String id) {
		Object obj = null;
		try {
			obj = HibernateUtils.getSession().get(clazz, id);
		} catch (HibernateException e) {
			handleException(e);
		}
		return obj;
	}
	@SuppressWarnings("rawtypes")
	public List findByColumn(Class<?> clazz, String column, String id) {
		List list = null;
		try {
			list = HibernateUtils.getSession().createQuery("from " + clazz.getName() + " where " + column + " = '" + id + "'").list();
		} catch (IndexOutOfBoundsException e) {
			throw (e);
		} catch (HibernateException e) {
			handleException(e);
		}
		return list;
	}
	public Object findObjectByColumn(Class<?> clazz, String column, String id) {
		Object obj = null;
		try {
			Query query = HibernateUtils.getSession().createQuery("from " + clazz.getName() + " where " + column + " = '" + id + "'");
			if (!query.list().isEmpty()) {
				obj = query.list().get(0);
			}
		} catch (IndexOutOfBoundsException e) {
			throw (e);
		} catch (HibernateException e) {
			handleException(e);
		}
		return obj;
	}
	@SuppressWarnings("rawtypes")
	public List findByQuery(String _query) {
		List objects = null;
		try {
			Query query = HibernateUtils.getSession().createQuery(_query);
			objects = query.list();
		} catch (HibernateException e) {
			handleException(e);
		}
		return objects;
	}
	public List findByQuery(String _query, Object[] value) {
		List obj = null;
		try {
			Query query = HibernateUtils.getSession().createQuery(_query);
			for (int i = 0; i < value.length; i++) {
				query.setParameter(i, value[i]);
			}
			obj = query.list();
		} catch (HibernateException e) {
			handleException(e);
		}
		return obj;
	}
	public Object findObjectByQuery(String _query) {
		Object obj = null;
		try {
			Query query = HibernateUtils.getSession().createQuery(_query);
			if (!query.list().isEmpty()) {
				obj = query.list().get(0);
			}
		} catch (HibernateException e) {
			handleException(e);
		}
		return obj;
	}
	public Object findPropertyByQuery(String _query) {
		Object obj = null;
		try {
			Query query = HibernateUtils.getSession().createQuery(_query);
			if (!query.list().isEmpty()) {
				obj = query.list().get(0);
			}
		} catch (HibernateException e) {
			handleException(e);
		}
		return obj;
	}
	@SuppressWarnings("rawtypes")
	public List findByQuery(String _query, Integer maxResults) {
		List objects = null;
		try {
			Query query = HibernateUtils.getSession().createQuery(_query).setMaxResults(maxResults);
			objects = query.list();
		} catch (HibernateException e) {
			handleException(e);
		}
		return objects;
	}
	@SuppressWarnings("rawtypes")
	public List findAll(Class clazz) {
		List objects = null;
		try {
			Query query = HibernateUtils.getSession().createQuery("SELECT  bean FROM  " + clazz.getName() + " AS bean");
			objects = query.list();
		} catch (HibernateException e) {
			handleException(e);
		}
		return objects;
	}
	protected void handleException(HibernateException e) throws DataAccessException {
		throw new DataAccessException(e);
	}
}
