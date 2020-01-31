package tn.com.smartsoft.backoffice.eleves.eleves.dao;

import java.util.List;

import tn.com.smartsoft.backoffice.referentiel.saison.beans.SaisonsBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.GenericEntiteDao;

public interface ElevesDao extends GenericEntiteDao {
	public List<DataBusinessBean> getHistorys(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);

	public SaisonsBean getOpenSaison(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);

	public void deleteHistorys(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);

}
