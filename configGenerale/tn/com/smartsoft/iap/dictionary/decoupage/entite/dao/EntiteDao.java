package tn.com.smartsoft.iap.dictionary.decoupage.entite.dao;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.GenericEntiteDao;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.PropertyBean;

public interface EntiteDao extends GenericEntiteDao {

	public List<DataBusinessBean> getlistProprietes(DbSession daoSession, String userId, boolean istherEvenn, DataBusinessBean bean);

	public void deleteProprietes(DbSession dbSession, String userId, boolean istherEvenn, PropertyBean bean);

}
