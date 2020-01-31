package tn.com.smartsoft.framework.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbSequenceSupport;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.utils.SequenceUtils;

public class DbSequenceSupportImpl implements DbSequenceSupport {
	private DbSession dbSession;
	private UserContext userContext;
	
	public DbSequenceSupportImpl(DbSession dbSession, UserContext userContext) {
		super();
		this.dbSession = dbSession;
		this.userContext = userContext;
	}
	
	public Serializable getSequenceValue(Object bean, String property, Map<String, Object> context) throws DaoFunctionalException {
		return SequenceUtils.generate(dbSession, userContext, bean, property, context);
	}
	
	public List<Serializable> getSequenceValue(@SuppressWarnings("rawtypes") List beans, String property, Map<String, Object> context) throws DaoFunctionalException {
		return SequenceUtils.generate(dbSession, userContext, beans, property, context);
	}
	
	public Serializable getSequenceValue(Object bean, String property) throws DaoFunctionalException {
		return getSequenceValue(bean, property, null);
	}
	
	public List<Serializable> getSequenceValue(@SuppressWarnings("rawtypes") List beans, String property) throws DaoFunctionalException {
		return getSequenceValue(beans, property, null);
	}
	
	public void setSequenceValues(Object bean) throws DaoFunctionalException {
		setSequenceValues(bean, null);
	}
	
	public void setSequenceValues(Object bean, Map<String, Object> context) throws DaoFunctionalException {
		SequenceUtils.loadGenerateValues(dbSession, userContext, bean, context);
	}
	
}
