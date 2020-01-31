package tn.com.smartsoft.iap.dictionary.securite.groupedRole.dao;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.GenericEntiteDao;

public interface GroupedRoleDao extends GenericEntiteDao {
	public List<DataBusinessBean> getGroupedRoles(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);
	public List<DataBusinessBean> getItemRoles(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);
	public List<DataBusinessBean> getActionRoles(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);
	public List<DataBusinessBean> getFieldRoles(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);
	
}
