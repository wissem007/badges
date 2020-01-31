package tn.com.smartsoft.iap.system.business;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserMenuBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.graphique.menuAction.beans.MenuActionBean;
import tn.com.smartsoft.iap.system.dao.UserMenuPreferenceDao;

public class UserMenuPreferenceBusiness extends GenericEntiteBusiness {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<DataBusinessBean> getMenuAction(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		if (bean != null)
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
		List<DataBusinessBean> dataList = ((UserMenuPreferenceDao) entiteDao).getMenuAction(daoSession, userContext.userId(), false, bean);
		List<DataBusinessBean> dataListRes = new ArrayList<DataBusinessBean>();
		for (int i = 0; i < dataList.size(); i++) {
			MenuActionBean menuActionBean = (MenuActionBean) dataList.get(i);
			if (userContext.isGranted(new ActionBeanId(menuActionBean.getActionId(), menuActionBean.getSujetId(), menuActionBean.getSubModuleId(), menuActionBean.getModuleId()))) {
				dataListRes.add(menuActionBean);
			}
		}
		return dataListRes;
	}

	public void upadteUserMenu(List<DataBusinessBean> userMenus, UserMenuBean userMenuMo, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			((UserMenuPreferenceDao) entiteDao).deleteUserMenu(daoSession, userContext.userId(), userMenuMo);
			for (int i = 0; i < userMenus.size(); i++) {
				UserMenuBean userMenuBean = (UserMenuBean) userMenus.get(i);
				persistantSupport.save(userMenuBean);
			}
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
}
