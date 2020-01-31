package tn.com.smartsoft.iap.dictionary.graphique.vue.dao;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.GenericEntiteDao;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewActionBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewLibelleBean;

public interface VueDao extends GenericEntiteDao {

	public List<DataBusinessBean> getlistLibelle(DbSession daoSession, String userId, boolean istherEvenn, DataBusinessBean bean);

	public List<DataBusinessBean> getlistAction(DbSession daoSession, String userId, boolean istherEvenn, DataBusinessBean bean);

	public void deleteViewLibelle(DbSession dbSession, String userId, boolean istherEvenn, ViewLibelleBean bean);

	public void deleteViewAction(DbSession dbSession, String userId, boolean istherEvenn, ViewActionBean bean);

}
