package tn.com.smartsoft.iap.dictionary.decoupage.entite.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.commons.utils.ListUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.PropertyBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.dao.EntiteDao;

public class EntiteBusiness extends GenericEntiteBusiness {

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
		EntiteBean entiteBean = (EntiteBean) dataBusinessBean;
		entiteBean.setListProprietes(new ArrayList(entiteBean.getPropertys().values()));
		entiteBean.initListProperties();
		return entiteBean;
	}

	// *********************************************************************************************
	@SuppressWarnings("unchecked")
	public List<DataBusinessBean> getListProprietes(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		List<DataBusinessBean> dataList = ((EntiteDao) entiteDao).getlistProprietes(daoSession, userContext.userId(), false, bean);
		return dataList;
	}

	// *********************************************************************************************

	public void deleteProprietes(PropertyBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		((EntiteDao) entiteDao).deleteProprietes(daoSession, userContext.userId(), false, bean);

	}

	// *********************************************************************************************

	public Serializable create(DataBusinessBean dataBusinessBean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			EntiteBean bean = (EntiteBean) dataBusinessBean;

			bean.setCreatedById(userContext.getUser().getUserName());
			bean.setCreatedDate(DateUtils.getCourantTimestamp());

			save = persistantSupport.save(bean);
			createDetailEntite(userContext, bean.getListProprietes(), bean.getEntiteId(), bean.getSujetId(), bean.getSubModuleId(), bean.getModuleId());

			saveAudit(bean, userContext, businessParams.getCreateActionId());
			daoSession.commitTransaction();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

	// *********************************************************************************************

	public void createDetailEntite(UserContext userContext, List createList, String entiteId, String sujet, String submodule, String module) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			if (createList != null && createList.size() > 0) {
				for (int i = 0; i < createList.size(); i++) {
					PropertyBean propertyBean = (PropertyBean) createList.get(i);

					propertyBean.setEntiteId(entiteId);
					propertyBean.setSujetId(sujet);
					propertyBean.setModuleId(module);
					propertyBean.setSubModuleId(submodule);

					propertyBean.setPropertyName(propertyBean.getPropertyName());
					persistantSupport.save(propertyBean);
				}
			}
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

	// *********************************************************************************************

	public void update(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			EntiteBean rowBean = (EntiteBean) this.getById(bean, this.businessParams.getUpdateActionId().getActionBeanId(), userContext);
			List<PropertyBean> dataList = new ArrayList(rowBean.getPropertys().values());
			daoSession.clear();

			EntiteBean masterBean = (EntiteBean) bean;
			updateDetails(dataList, masterBean, userContext);
			persistantSupport.update(bean);
			saveAudit(bean, userContext, businessParams.getUpdateActionId());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

	public void updateDetails(List<PropertyBean> dataList, EntiteBean masterBean, UserContext userContext) throws FunctionalException {

		List<PropertyBean> createList = new ArrayList<PropertyBean>();
		List<PropertyBean> updateList = new ArrayList<PropertyBean>();

		if (masterBean.getListProprietes() != null && masterBean.getListProprietes().size() > 0)
			for (int i = 0; i < masterBean.getListProprietes().size(); i++) {
				PropertyBean bean = (PropertyBean) masterBean.getListProprietes().get(i);
				if (StringUtils.isEmpty(bean.getEntiteId())) {
					createList.add(bean);
				} else {
					PropertyBean dataBean = (PropertyBean) ListUtils.findByProperty(dataList, "propertyName", bean.getPropertyName());

					if (dataBean != null) {
						dataList.remove(dataBean);
						updateList.add(bean);
					}
				}
			}

		this.updateList(updateList, userContext);
		this.deleteList(dataList, userContext);
		createDetailEntite(userContext, createList, masterBean.getEntiteId(), masterBean.getSujetId(), masterBean.getSubModuleId(), masterBean.getModuleId());

	}

	// *********************************************************************************************

	public void deleteDetailEntite(EntiteBean entiteBean, UserContext userContext) throws FunctionalException {
		PropertyBean propertyBean = new PropertyBean();
		propertyBean.setEntiteId(entiteBean.getEntiteId());
		propertyBean.setModuleId(entiteBean.getModuleId());
		propertyBean.setSubModuleId(entiteBean.getSubModuleId());
		propertyBean.setSujetId(entiteBean.getSujetId());

		deleteProprietes(propertyBean, businessParams.getDeleteActionId().getActionBeanId(), userContext);
	}

	// *********************************************************************************************
}
