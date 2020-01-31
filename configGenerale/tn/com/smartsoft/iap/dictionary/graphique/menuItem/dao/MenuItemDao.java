package tn.com.smartsoft.iap.dictionary.graphique.menuItem.dao;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.GenericEntiteDao;

public interface MenuItemDao extends GenericEntiteDao {
	public List<DataBusinessBean> getAllByCriteria(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);

}
