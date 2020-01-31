package tn.com.smartsoft.framework.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public interface DbCallback {
	Object doExecute(Session session) throws HibernateException;
}