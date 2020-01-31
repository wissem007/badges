package tn.com.smartsoft.iap.dictionary.securite.groupedRole.business;

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
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.EntiteRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.GroupedRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.dao.GroupedRoleDao;
import tn.com.smartsoft.iap.dictionary.securite.role.beans.ItemRoleBean;

public class GroupedRoleBusiness extends GenericEntiteBusiness {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<DataBusinessBean> getGroupedRoles(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		List<DataBusinessBean> dataList = ((GroupedRoleDao) entiteDao).getGroupedRoles(daoSession, userContext.userId(), false, bean);
		if (parseListBean != null)
			dataList = (List<DataBusinessBean>) parseListBean.parse(dataList);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	public List<DataBusinessBean> getItemRoles(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		List<DataBusinessBean> dataList = ((GroupedRoleDao) entiteDao).getItemRoles(daoSession, userContext.userId(), false, bean);
		if (parseListBean != null)
			dataList = (List<DataBusinessBean>) parseListBean.parse(dataList);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	public List<DataBusinessBean> getActionRoles(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		List<DataBusinessBean> dataList = ((GroupedRoleDao) entiteDao).getActionRoles(daoSession, userContext.userId(), false, bean);
		if (parseListBean != null)
			dataList = (List<DataBusinessBean>) parseListBean.parse(dataList);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	public List<DataBusinessBean> getFieldRoles(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		List<DataBusinessBean> dataList = ((GroupedRoleDao) entiteDao).getFieldRoles(daoSession, userContext.userId(), false, bean);
		if (parseListBean != null)
			dataList = (List<DataBusinessBean>) parseListBean.parse(dataList);
		return dataList;
	}

	public Serializable create(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		if (bean instanceof GroupedRoleBean)
			return createGroupedRole(bean, userContext);
		else
			return createEntiteRole(bean, userContext);
	}

	public Serializable createGroupedRole(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);

			daoSession.beginTransaction();
			daoSession.clear();
			dbSequenceSupport.setSequenceValues(bean);

			GroupedRoleBean groupedRoleBean = (GroupedRoleBean) bean;
			groupedRoleBean.setRoleId(Long.valueOf(groupedRoleBean.getRoleId()).toString());
			save = persistantSupport.save(bean);
			saveAudit(bean, userContext, businessParams.getCreateActionId());
			daoSession.commitTransaction();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Serializable createEntiteRole(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);

			dbSequenceSupport.setSequenceValues(bean);

			EntiteRoleBean entiteRoleBean = (EntiteRoleBean) bean;
			entiteRoleBean.setRoleId(Long.valueOf(entiteRoleBean.getRoleId()).toString());

			List itemFieldRoles = entiteRoleBean.getItemFieldRoles();
			List itemActionRoles = entiteRoleBean.getItemActionRoles();
			List itemRoles = new ArrayList();

			itemRoles.addAll(itemActionRoles);
			itemRoles.addAll(itemFieldRoles);

			createItemRoles(userContext, entiteRoleBean, itemRoles);
			entiteRoleBean.setItemRoles(itemRoles);

			save = persistantSupport.save(entiteRoleBean);
			saveAudit(bean, userContext, businessParams.getCreateActionId());

			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void update(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			EntiteRoleBean rowBean = (EntiteRoleBean) this.getById(bean, this.businessParams.getUpdateActionId().getActionBeanId(), userContext);
			List dataList = rowBean.getItemRoles();
			daoSession.clear();

			if (bean instanceof EntiteRoleBean) {
				EntiteRoleBean entiteRoleBean = (EntiteRoleBean) bean;

				List itemFieldRoles = entiteRoleBean.getItemFieldRoles();
				List itemActionRoles = entiteRoleBean.getItemActionRoles();

				List itemRoles = new ArrayList();
				itemRoles.addAll(itemActionRoles);
				itemRoles.addAll(itemFieldRoles);

				updateItemRoles(itemRoles, dataList, entiteRoleBean, userContext);
			}

			persistantSupport.update(bean);
			saveAudit(bean, userContext, businessParams.getUpdateActionId());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public void updateItemRoles(List<ItemRoleBean> newlist, List dataList, EntiteRoleBean entiteRoleBean, UserContext userContext) throws FunctionalException {

		List<ItemRoleBean> createList = new ArrayList<ItemRoleBean>();

		if (newlist != null && newlist.size() > 0)
			for (int i = 0; i < newlist.size(); i++) {
				ItemRoleBean bean = (ItemRoleBean) newlist.get(i);
				if (!StringUtils.isEmpty(bean.getLibelle()))
					if (bean.getRoleId() == null || bean.getRoleId().trim().equals("")) {
						createList.add(bean);
					} else {
						ItemRoleBean dataBean = (ItemRoleBean) ListUtils.findByProperty(dataList, "roleId", bean.getRoleId());

						if (dataBean != null) {
							dataList.remove(dataBean);
						}
					}
			}

		this.deleteList(dataList, userContext);
		createItemRoles(userContext, entiteRoleBean, createList);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createItemRoles(UserContext userContext, EntiteRoleBean entiteRoleBean, List itemRoles) throws DaoFunctionalException {

		DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);

		if (itemRoles != null && itemRoles.size() > 0)
			for (int i = 0; i < itemRoles.size(); i++) {
				ItemRoleBean itemRoleBean = (ItemRoleBean) itemRoles.get(i);
				if (itemRoleBean != null) {
					dbSequenceSupport.setSequenceValues(itemRoleBean);
					itemRoleBean.setPrentRoleId(entiteRoleBean.getRoleId());
					itemRoleBean.setModuleId(entiteRoleBean.getModuleId());
					itemRoleBean.setRoleId(Long.valueOf(itemRoleBean.getRoleId()).toString());
				}
			}

		entiteRoleBean.setItemRoles(itemRoles);
	}
}
