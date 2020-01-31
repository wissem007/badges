package tn.com.smartsoft.iap.dictionary.decoupage.sujet.dao;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.GenericEntiteDao;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;

public interface SujetDao extends GenericEntiteDao {

	public List<DataBusinessBean> getlistAction(DbSession dbSession, String userId, boolean istherEvenn, DataBusinessBean bean);

	public void deleteAction(DbSession dbSession, String userId, boolean istherEvenn, ActionBean bean);
	

}
