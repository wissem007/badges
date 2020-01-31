package tn.com.smartsoft.iap.dictionary.graphique.menuItem.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.ListUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbSequenceSupport;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.graphique.menuAction.beans.MenuActionBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuItem.beans.MenuItemBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuItem.dao.MenuItemDao;

public class MenuItemBusiness extends GenericEntiteBusiness{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public List<DataBusinessBean> getAllByCriteria(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		if (bean != null) bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
		List<DataBusinessBean> dataList = ((MenuItemDao) entiteDao).getAllByCriteria(daoSession, userContext.userId(), false, bean);
		if (parseListBean != null) dataList = (List<DataBusinessBean>) parseListBean.parse(dataList);
		return dataList;
	}
	public Serializable create(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);
			daoSession.beginTransaction();
			dbSequenceSupport.setSequenceValues(bean);
			MenuItemBean menuItemBean = (MenuItemBean) bean;
			createMenuActions(userContext, menuItemBean.getMenuActions(), menuItemBean.getModuleId(), menuItemBean.getMenuId());
			save = persistantSupport.save(menuItemBean);
			saveAudit(bean, userContext, businessParams.getCreateActionId());
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
			MenuItemBean rowBean = (MenuItemBean) this.getById(bean, this.businessParams.getUpdateActionId().getActionBeanId(), userContext);
			List<MenuActionBean> dataList = rowBean.getMenuActions();
			daoSession.clear();
			MenuItemBean menuItemBean = (MenuItemBean) bean;
			updateMenuActions(dataList, menuItemBean, userContext);
			persistantSupport.update(bean);
			saveAudit(bean, userContext, businessParams.getUpdateActionId());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	public void updateMenuActions(List<MenuActionBean> dataList, MenuItemBean menuItemBean, UserContext userContext) throws FunctionalException {
		List<MenuActionBean> createList = new ArrayList<MenuActionBean>();
		if (menuItemBean.getMenuActions() != null && menuItemBean.getMenuActions().size() > 0) for (int i = 0; i < menuItemBean.getMenuActions().size(); i++) {
			MenuActionBean bean = (MenuActionBean) menuItemBean.getMenuActions().get(i);
			if (!StringUtils.isEmpty(bean.getLibelle())) if (bean.getMenuId() == null || bean.getMenuId().intValue() == 0) {
				createList.add(bean);
			} else {
				MenuActionBean dataBean = (MenuActionBean) ListUtils.findByProperty(dataList, "menuId", bean.getMenuId());
				if (dataBean != null) {
					dataList.remove(dataBean);
				}
			}
		}
		this.deleteList(dataList, userContext);
		createMenuActions(userContext, createList, menuItemBean.getModuleId(), menuItemBean.getMenuId());
	}
	public void createMenuActions(UserContext userContext, List<MenuActionBean> createList, String moduleId, Long menuId) throws DaoFunctionalException {
		DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);
		if (createList != null && createList.size() > 0) for (int i = 0; i < createList.size(); i++) {
			MenuActionBean menuActionBean = (MenuActionBean) createList.get(i);
			if (menuActionBean != null) {
				menuActionBean.setModuleId(moduleId);
				menuActionBean.setParentMenuId(menuId);
				dbSequenceSupport.setSequenceValues(menuActionBean);
				if (menuActionBean.getRang() == null) menuActionBean.setRang(new Long(1));
			}
		}
	}
}
