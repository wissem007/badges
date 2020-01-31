package tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.dao;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.GenericEntiteDao;

public interface EleveDemandeCartesDao extends GenericEntiteDao {
	public List<DataBusinessBean> getStatus(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);

}
