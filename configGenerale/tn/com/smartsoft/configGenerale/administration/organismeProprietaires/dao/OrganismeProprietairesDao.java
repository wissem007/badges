package tn.com.smartsoft.configGenerale.administration.organismeProprietaires.dao;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.GenericEntiteDao;

public interface OrganismeProprietairesDao extends GenericEntiteDao {

	public void deleteDetail(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);

}
