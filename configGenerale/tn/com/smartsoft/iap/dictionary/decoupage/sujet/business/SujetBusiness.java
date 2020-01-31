package tn.com.smartsoft.iap.dictionary.decoupage.sujet.business;

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
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DaoParseBean;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbSequenceSupport;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.dao.SujetDao;
import tn.com.smartsoft.iap.dictionary.graphique.menuItem.beans.MenuItemBean;
import tn.com.smartsoft.iap.system.utils.GenerateRoleUtils;

public class SujetBusiness extends GenericEntiteBusiness {
	
	private DaoParseBean parentSubModule;
	
	public DaoParseBean getParseListSubModuleBean() {
		return parentSubModule;
	}
	
	public void setParseListSubModuleBean(DaoParseBean parseListSubModuleBean) {
		this.parentSubModule = parseListSubModuleBean;
	}
	
	private static final long serialVersionUID = 1L;
	
	// *********************************************************************************************
	
	public DataBusinessBean getById(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
//		List<MenuItemBean> menuBeans = ApplicationConfiguration.applicationManager().presentationBeanFactory().getCacheDictionaryManager().getMenusModule("grhRef");
//		
//		GenerateRoleUtils.generateRole(menuBeans,daoSession);
		if (bean != null)
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
		DataBusinessBean dataBusinessBean = (DataBusinessBean) daoSession.persistantSupport(userContext).get(bean.getClass(), bean.getId());
		if (parseBean != null)
			dataBusinessBean = (DataBusinessBean) parseBean.parse(dataBusinessBean);
		
		List listActions = this.getlistAction(dataBusinessBean, actionId, userContext);
		((SujetBean) dataBusinessBean).setListActions(listActions);
		return dataBusinessBean;
	}
	
	@SuppressWarnings("unchecked")
	public List<DataBusinessBean> getlistAction(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		
		List<DataBusinessBean> dataList = ((SujetDao) entiteDao).getlistAction(daoSession, userContext.userId(), false, bean);
		return dataList;
	}
	
	// *********************************************************************************************
	
	public void deleteAction(ActionBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		((SujetDao) entiteDao).deleteAction(daoSession, userContext.userId(), false, bean);
		
	}
	
	// *********************************************************************************************
	
	public Serializable create(DataBusinessBean dataBusinessBean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			SujetBean bean = (SujetBean) dataBusinessBean;
			
			bean.setCreatedById(userContext.getUser().getUserName());
			bean.setCreatedDate(DateUtils.getCourantTimestamp());
			
			save = persistantSupport.save(bean);
			createDetailSujet(userContext, bean.getListActions(), bean.getSujetId(), bean.getSubModuleId(), bean.getModuleId());
			
			saveAudit(bean, userContext, businessParams.getCreateActionId());
			daoSession.commitTransaction();
			return save;
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
			SujetBean rowBean = (SujetBean) this.getById(bean, this.businessParams.getUpdateActionId().getActionBeanId(), userContext);
			List<ActionBean> dataList = new ArrayList(rowBean.getListActions());
			daoSession.clear();
			
			SujetBean masterBean = (SujetBean) bean;
			updateDetails(dataList, masterBean, userContext);
			persistantSupport.update(bean);
			saveAudit(bean, userContext, businessParams.getUpdateActionId());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	
	public void updateDetails(List<ActionBean> dataList, SujetBean masterBean, UserContext userContext) throws FunctionalException {
		
		List<ActionBean> createList = new ArrayList<ActionBean>();
		List<ActionBean> updateList = new ArrayList<ActionBean>();
		
		if (masterBean.getListActions() != null && masterBean.getListActions().size() > 0)
			for (int i = 0; i < masterBean.getListActions().size(); i++) {
				ActionBean bean = (ActionBean) masterBean.getListActions().get(i);
				if (!StringUtils.isEmpty(bean.getActionId())) {
					createList.add(bean);
				} else {
					SujetBean dataBean = (SujetBean) ListUtils.findByProperty(dataList, "actionId", bean.getActionId());
					
					if (dataBean != null) {
						dataList.remove(dataBean);
						updateList.add(bean);
					}
				}
			}
		
		this.updateList(updateList, userContext);
		this.deleteList(dataList, userContext);
		createDetailSujet(userContext, createList, masterBean.getSujetId(), masterBean.getSubModuleId(), masterBean.getModuleId());
		
	}
	
	// *********************************************************************************************
	public void createDetailSujet(UserContext userContext, List createList, String sujetId, String submodule, String module) throws FunctionalException {
		
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);
			if (createList != null && createList.size() > 0) {
				for (int i = 0; i < createList.size(); i++) {
					ActionBean actionBean = (ActionBean) createList.get(i);
					dbSequenceSupport.setSequenceValues(actionBean);
					
					actionBean.setSujetId(sujetId);
					actionBean.setModuleId(module);
					actionBean.setSubModuleId(submodule);
					actionBean.setActionTemplateId(actionBean.getActionTemplateId());
					
					actionBean.setActionId(actionBean.getActionId());
					persistantSupport.save(actionBean);
				}
			}
			
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	
	// *********************************************************************************************
	
	public void deleteDetailSujet(SujetBean sujetBean, UserContext userContext) throws FunctionalException {
		ActionBean actionBean = new ActionBean();
		actionBean.setActionId(sujetBean.getActionBean().getActionId());
		actionBean.setSujetId(sujetBean.getSujetId());
		actionBean.setModuleId(sujetBean.getModuleId());
		actionBean.setSubModuleId(sujetBean.getSubModuleId());
		
		deleteAction(actionBean, businessParams.getDeleteActionId().getActionBeanId(), userContext);
		
	}
	
	// *********************************************************************************************
	
}
