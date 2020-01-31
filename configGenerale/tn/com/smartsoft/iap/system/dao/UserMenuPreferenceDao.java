package tn.com.smartsoft.iap.system.dao;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.GenericEntiteDao;

public interface UserMenuPreferenceDao extends GenericEntiteDao {
	public List<DataBusinessBean> getMenuAction(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);

	public void deleteUserMenu(DbSession dbSession, String userId, DataBusinessBean bean);

}
