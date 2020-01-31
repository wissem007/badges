package tn.com.smartsoft.iap.administration.securite.utilisateur.dao;

import java.util.List;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.GenericEntiteDao;

public interface UserDao extends GenericEntiteDao{
	
	public List<DataBusinessBean> getByCriteriaNoDetail(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);
}
