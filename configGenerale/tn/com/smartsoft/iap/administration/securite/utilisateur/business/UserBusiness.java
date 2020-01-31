package tn.com.smartsoft.iap.administration.securite.utilisateur.business;

import java.io.Serializable;
import java.util.List;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbSequenceSupport;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.dao.UserDao;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.system.business.PasswordHashing;

public class UserBusiness extends GenericEntiteBusiness{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	protected PasswordHashing	passwordHashing;
	
	public void setPasswordHashing(PasswordHashing passwordHashing) {
		this.passwordHashing = passwordHashing;
	}
	public Serializable create(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			UserBean userBean = (UserBean) bean;
			userBean.setPasseWord(passwordHashing.encryptPassword(userBean.getPasseWord()));
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);
			dbSequenceSupport.setSequenceValues(bean);
			bean.setCreatedById(userContext.getUser().getUserId());
			bean.setCreatedDate(DateUtils.getCourantTimestamp());
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
			((UserBean) bean).setActive(true);
			save = persistantSupport.save(bean);
			daoSession.commitTransaction();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	public void update(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();
			bean.setUpdatedById(userContext.getUser().getUserId());
			bean.setUpdatedDate(DateUtils.getCourantTimestamp());
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
			persistantSupport.update(bean);
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	public void delete(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
			persistantSupport.delete(bean);
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	public void autoriser(UserBean bean, UserContext userContext) throws FunctionalException {
		try {
			daoSession.beginTransaction();
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	public List<DataBusinessBean> getByCriteriaNoDetail(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		if (bean != null) bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
		List<DataBusinessBean> dataList = ((UserDao) entiteDao).getByCriteriaNoDetail(daoSession, userContext.userId(), false, bean);
		if (parseListBean != null) dataList = (List<DataBusinessBean>) parseListBean.parse(dataList);
		return dataList;
	}
}
