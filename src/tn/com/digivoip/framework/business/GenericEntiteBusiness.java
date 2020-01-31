package tn.com.digivoip.framework.business;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import tn.com.digivoip.framework.dao.GenericEntiteDao;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.AuditSupport;
import tn.com.smartsoft.framework.dao.DaoParseBean;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;

public class GenericEntiteBusiness implements Serializable{
	
	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1L;
	protected GenericEntiteDao				entiteDao;
	protected DbSession						daoSession;
	protected GenericEntiteBusinessParams	businessParams;
	protected DaoParseBean					parseBean;
	protected DaoParseBean					parseListBean;
	
	public void setParseBean(DaoParseBean parseBean) {
		this.parseBean = parseBean;
	}
	public DaoParseBean getParseListBean() {
		return parseListBean;
	}
	public void setParseListBean(DaoParseBean parseListBean) {
		this.parseListBean = parseListBean;
	}
	public void setBusinessParams(GenericEntiteBusinessParams businessParams) {
		this.businessParams = businessParams;
	}
	public GenericEntiteBusinessParams getBusinessParams() {
		return this.businessParams;
	}
	public void setEntiteDao(GenericEntiteDao entiteDao) {
		this.entiteDao = entiteDao;
	}
	public void setDaoSession(DbSession daoSession) {
		this.daoSession = daoSession;
	}
	public DbSession getDaoSession() {
		return this.daoSession;
	}
	public Serializable create(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			if (userContext != null) {
				bean.setCreatedById(userContext.getUser().getUserId());
			} else {
				bean.setCreatedById("1");
			}
			bean.setCreatedDate(DateUtils.getCourantTimestamp());
			// new
			if (userContext != null) {
				bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
			} else {
				bean.setParentSocieteId(new Long(1));
			}
			save = persistantSupport.save(bean);
			// saveAudit(bean, userContext, businessParams.getCreateActionId());
			daoSession.commitTransaction();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	public Serializable createNotCommit(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
		save = persistantSupport.save(bean);
		return save;
	}
	public void updateNotCommit(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
		if (userContext != null) {
			bean.setUpdatedById(userContext.getUser().getUserId());
		}
		bean.setUpdatedDate(DateUtils.getCourantTimestamp());
		persistantSupport.update(bean);
	}
	public void update(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();
			if (userContext != null) {
				bean.setUpdatedById(userContext.getUser().getUserId());
			}
			bean.setUpdatedDate(DateUtils.getCourantTimestamp());
			persistantSupport.update(bean);
			// saveAudit(bean, userContext, businessParams.getUpdateActionId());
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
			persistantSupport.delete(bean);
			// saveAudit(bean, userContext, businessParams.getDeleteActionId());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	@SuppressWarnings("rawtypes")
	public void deleteList(List deleteList, UserContext userContext) throws FunctionalException {
		DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
		if (deleteList != null && deleteList.size() > 0) {
			for (int i = 0; i < deleteList.size(); i++) {
				DataBusinessBean bean = (DataBusinessBean) deleteList.get(i);
				if (bean != null) {
					persistantSupport.delete(bean);
				}
			}
		}
	}
	@SuppressWarnings("rawtypes")
	public void createOrUpdateList(List list, UserContext userContext) throws FunctionalException {
		DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				DataBusinessBean bean = (DataBusinessBean) list.get(i);
				if (bean != null) {
					persistantSupport.saveOrUpdate(bean);
				}
			}
		}
	}
	@SuppressWarnings("rawtypes")
	public void updateList(List deleteList, UserContext userContext) throws FunctionalException {
		DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
		if (deleteList != null && deleteList.size() > 0) {
			for (int i = 0; i < deleteList.size(); i++) {
				DataBusinessBean bean = (DataBusinessBean) deleteList.get(i);
				if (bean != null) {
					persistantSupport.update(bean);
				}
			}
		}
	}
	@SuppressWarnings("rawtypes")
	public void createList(List list, UserContext userContext) throws FunctionalException {
		DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				DataBusinessBean bean = (DataBusinessBean) list.get(i);
				if (bean != null) {
					persistantSupport.save(bean);
				}
			}
		}
	}
	public DataBusinessBean getById(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		if (bean != null && userContext != null) {
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
		}
		DataBusinessBean dataBusinessBean = (DataBusinessBean) daoSession.persistantSupport(userContext).get(bean.getClass(), bean.getId());
		if (parseBean != null) {
			dataBusinessBean = (DataBusinessBean) parseBean.parse(dataBusinessBean);
		}
		return dataBusinessBean;
	}
	@SuppressWarnings("unchecked")
	public List<DataBusinessBean> getByCriteria(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		if (bean != null) {
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
		}
		List<DataBusinessBean> dataList = entiteDao.getByCriteria(daoSession, userContext.userId(), false, bean);
		// daoSession.beginTransaction();
		// saveAudit(bean, userContext,
		// businessParams.getGetByCriteriaActionId());
		// daoSession.commitTransaction();
		if (parseListBean != null) {
			dataList = (List<DataBusinessBean>) parseListBean.parse(dataList);
		}
		return dataList;
	}
	public Map<?, ?> getByCriteriaMap(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		if (bean != null) {
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
		}
		List<DataBusinessBean> dataList = entiteDao.getByCriteria(daoSession, userContext.userId(), false, bean);
		return parseListBean.parseMap(dataList);
	}
	protected void saveAudit(DataBusinessBean bean, UserContext userContext, ComponentId actionId) throws FunctionalException {
		AuditSupport auditSupport = daoSession.auditSupport(userContext);
		auditSupport.begin(actionId.getActionBeanId(), bean);
		auditSupport.addAuditEntite(bean, null, ActionMode.READ);
		auditSupport.save();
	}
}
