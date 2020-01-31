package tn.com.digivoip.framework.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import tn.com.digivoip.framework.exception.DataAccessException;

public class HibernateUtils{

	private static Log					log					= LogFactory.getLog(HibernateUtils.class);
	private static Configuration		configuration;
	private static SessionFactory		sessionFactory;
	@SuppressWarnings("rawtypes")
	private static final ThreadLocal	threadSession		= new ThreadLocal();
	@SuppressWarnings("rawtypes")
	private static final ThreadLocal	threadTransaction	= new ThreadLocal();
	@SuppressWarnings("rawtypes")
	private static final ThreadLocal	threadInterceptor	= new ThreadLocal();

	public static void loadCfg() {
		try {
			HibernateUtils.configuration = new Configuration();
			HibernateUtils.sessionFactory = HibernateUtils.configuration.configure("/tn/com/digivoip/test/hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
			HibernateUtils.log.error("Building SessionFactory failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	public static SessionFactory getSessionFactory() {
		return HibernateUtils.sessionFactory;
	}
	public static Configuration getConfiguration() {
		return HibernateUtils.configuration;
	}
	public static void rebuildSessionFactory() throws DataAccessException {
		synchronized (HibernateUtils.sessionFactory) {
			try {
				HibernateUtils.sessionFactory = HibernateUtils.getConfiguration().buildSessionFactory();
			} catch (Exception ex) {
				throw new DataAccessException(ex);
			}
		}
	}
	public static void rebuildSessionFactory(Configuration cfg) throws DataAccessException {
		synchronized (HibernateUtils.sessionFactory) {
			try {
				HibernateUtils.sessionFactory = cfg.buildSessionFactory();
				HibernateUtils.configuration = cfg;
			} catch (Exception ex) {
				throw new DataAccessException(ex);
			}
		}
	}
	@SuppressWarnings("unchecked")
	public static Session getSession() throws DataAccessException {
		Session s = (Session) HibernateUtils.threadSession.get();
		try {
			if (s == null) {
				HibernateUtils.log.debug("Opening new Session for this thread.");
				if (HibernateUtils.getInterceptor() != null) {
					HibernateUtils.log.debug("Using interceptor: " + HibernateUtils.getInterceptor().getClass());
					s = HibernateUtils.getSessionFactory().openSession(HibernateUtils.getInterceptor());
				} else {
					s = HibernateUtils.getSessionFactory().openSession();
				}
				HibernateUtils.threadSession.set(s);
			}
		} catch (HibernateException ex) {
			throw new DataAccessException(ex);
		}
		return s;
	}
	@SuppressWarnings("unchecked")
	public static void closeSession() throws DataAccessException {
		try {
			Session s = (Session) HibernateUtils.threadSession.get();
			HibernateUtils.threadSession.set(null);
			if (s != null && s.isOpen()) {
				HibernateUtils.log.debug("Closing Session of this thread.");
				s.close();
			}
		} catch (HibernateException ex) {
			throw new DataAccessException(ex);
		}
	}
	@SuppressWarnings("unchecked")
	public static void beginTransaction() throws DataAccessException {
		Transaction tx = (Transaction) HibernateUtils.threadTransaction.get();
		try {
			if (tx == null) {
				HibernateUtils.log.debug("Starting new database transaction in this thread.");
				tx = HibernateUtils.getSession().beginTransaction();
				HibernateUtils.threadTransaction.set(tx);
			}
		} catch (HibernateException ex) {
			throw new DataAccessException(ex);
		}
	}
	@SuppressWarnings("unchecked")
	public static void commitTransaction() throws DataAccessException {
		Transaction tx = (Transaction) HibernateUtils.threadTransaction.get();
		try {
			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
				HibernateUtils.log.debug("Committing database transaction of this thread.");
				tx.commit();
			}
			HibernateUtils.threadTransaction.set(null);
		} catch (HibernateException ex) {
			HibernateUtils.rollbackTransaction();
			throw new DataAccessException(ex);
		}
	}
	@SuppressWarnings("unchecked")
	public static void rollbackTransaction() throws DataAccessException {
		Transaction tx = (Transaction) HibernateUtils.threadTransaction.get();
		try {
			HibernateUtils.threadTransaction.set(null);
			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
				HibernateUtils.log.debug("Tyring to rollback database transaction of this thread.");
				tx.rollback();
			}
		} catch (HibernateException ex) {
			throw new DataAccessException(ex);
		} finally {
			HibernateUtils.closeSession();
		}
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static void reconnect(Session session) throws DataAccessException {
		try {
			session.reconnect();
			HibernateUtils.threadSession.set(session);
		} catch (HibernateException ex) {
			throw new DataAccessException(ex);
		}
	}
	@SuppressWarnings("unchecked")
	public static Session disconnectSession() throws DataAccessException {
		Session session = HibernateUtils.getSession();
		try {
			HibernateUtils.threadSession.set(null);
			if (session.isConnected() && session.isOpen()) {
				session.disconnect();
			}
		} catch (HibernateException ex) {
			throw new DataAccessException(ex);
		}
		return session;
	}
	@SuppressWarnings("unchecked")
	public static void registerInterceptor(Interceptor interceptor) {
		HibernateUtils.threadInterceptor.set(interceptor);
	}
	private static Interceptor getInterceptor() {
		Interceptor interceptor = (Interceptor) HibernateUtils.threadInterceptor.get();
		return interceptor;
	}
}