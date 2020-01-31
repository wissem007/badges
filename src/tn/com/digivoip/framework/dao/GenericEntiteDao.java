package tn.com.digivoip.framework.dao;

import java.util.List;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.dao.DbSession;

public interface GenericEntiteDao {
	public List<DataBusinessBean> getByCriteria(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);
}
