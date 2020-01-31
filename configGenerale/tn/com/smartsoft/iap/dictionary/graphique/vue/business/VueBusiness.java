package tn.com.smartsoft.iap.dictionary.graphique.vue.business;

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
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewActionBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewLibelleBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.dao.VueDao;

public class VueBusiness extends GenericEntiteBusiness {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataBusinessBean getById(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		if (bean != null)
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
		DataBusinessBean dataBusinessBean = (DataBusinessBean) daoSession.persistantSupport(userContext).get(bean.getClass(), bean.getId());
		if (parseBean != null)
			dataBusinessBean = (DataBusinessBean) parseBean.parse(dataBusinessBean);

		List<DataBusinessBean> listLibelles = getlistLibelle(bean, actionId, userContext);
		List<DataBusinessBean> listActions = getlistAction(bean, actionId, userContext);

		((ViewBean) dataBusinessBean).setListLibelles(listLibelles);
		((ViewBean) dataBusinessBean).setListActions(listActions);
		return dataBusinessBean;
	}

	// *********************************************************************************************
	@SuppressWarnings("unchecked")
	public List<DataBusinessBean> getlistLibelle(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		List<DataBusinessBean> dataList = ((VueDao) entiteDao).getlistLibelle(daoSession, userContext.userId(), false, bean);
		return dataList;
	}

	// *********************************************************************************************
	@SuppressWarnings("unchecked")
	public List<DataBusinessBean> getlistAction(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		List<DataBusinessBean> dataList = ((VueDao) entiteDao).getlistAction(daoSession, userContext.userId(), false, bean);
		return dataList;
	}

	// *********************************************************************************************

	public void deleteViewLibelle(ViewLibelleBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		((VueDao) entiteDao).deleteViewLibelle(daoSession, userContext.userId(), false, bean);

	}

	// *********************************************************************************************

	public void deleteViewAction(ViewActionBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		((VueDao) entiteDao).deleteViewAction(daoSession, userContext.userId(), false, bean);

	}

	// *********************************************************************************************

	public Serializable create(DataBusinessBean dataBusinessBean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			ViewBean bean = (ViewBean) dataBusinessBean;

			bean.setCreatedById(userContext.getUser().getUserName());
			bean.setCreatedDate(DateUtils.getCourantTimestamp());

			save = persistantSupport.save(bean);
			createDetailViews(bean, userContext);

			saveAudit(bean, userContext, businessParams.getCreateActionId());
			daoSession.commitTransaction();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

	// *********************************************************************************************

	public void update(DataBusinessBean dataBusinessBean, UserContext userContext) throws FunctionalException {

		try {

			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.clear();
			daoSession.beginTransaction();
			ViewBean bean = (ViewBean) dataBusinessBean;
			bean.setUpdatedById(userContext.getUser().getUserName());
			bean.setUpdatedDate(DateUtils.getCourantTimestamp());

			deleteDetailViews(bean, userContext);
			persistantSupport.update(bean);
			createDetailViews(bean, userContext);

			saveAudit(bean, userContext, businessParams.getUpdateActionId());
			daoSession.commitTransaction();

		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}

	}

	// *********************************************************************************************
	public void createDetailViews(ViewBean bean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);
			if (bean.getListLibelles() != null && bean.getListLibelles().size() > 0) {
				for (int i = 0; i < bean.getListLibelles().size(); i++) {
					ViewLibelleBean viewLibelleBean = (ViewLibelleBean) bean.getListLibelles().get(i);
					dbSequenceSupport.setSequenceValues(viewLibelleBean);
					viewLibelleBean.setViewId(bean.getViewId());
					viewLibelleBean.setSujetId(bean.getSujetId());
					viewLibelleBean.setModuleId(bean.getModuleId());
					viewLibelleBean.setSubModuleId(bean.getSubModuleId());

					viewLibelleBean.setViewLibellesId(viewLibelleBean.getViewLibellesId());
					persistantSupport.save(viewLibelleBean);
				}
			}
			if (bean.getListActions() != null && bean.getListActions().size() > 0) {
				for (int i = 0; i < bean.getListActions().size(); i++) {
					ViewActionBean viewActionBean = (ViewActionBean) bean.getListActions().get(i);

					dbSequenceSupport.setSequenceValues(viewActionBean);
					viewActionBean.setViewId(bean.getViewId());
					viewActionBean.setSujetId(bean.getSujetId());
					viewActionBean.setModuleId(bean.getModuleId());
					viewActionBean.setSubModuleId(bean.getSubModuleId());

					viewActionBean.setViewActionId(viewActionBean.getViewActionId());
					persistantSupport.save(viewActionBean);
				}
			}

		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

	public void deleteDetailViews(ViewBean viewBean, UserContext userContext) throws FunctionalException {
		ViewLibelleBean viewLibelleBean = new ViewLibelleBean();
		viewLibelleBean.setViewId(viewBean.getViewId());
		viewLibelleBean.setModuleId(viewBean.getModuleId());
		viewLibelleBean.setSubModuleId(viewBean.getSubModuleId());
		viewLibelleBean.setSujetId(viewBean.getSujetId());

		deleteViewLibelle(viewLibelleBean, businessParams.getDeleteActionId().getActionBeanId(), userContext);

		ViewActionBean viewActionBean = new ViewActionBean();
		viewActionBean.setViewId(viewBean.getViewId());
		viewActionBean.setModuleId(viewBean.getModuleId());
		viewActionBean.setSubModuleId(viewBean.getSubModuleId());
		viewActionBean.setSujetId(viewBean.getSujetId());

		deleteViewAction(viewActionBean, businessParams.getDeleteActionId().getActionBeanId(), userContext);
	}

	// *********************************************************************************************

}
