package tn.com.smartsoft.iap.system.dao;

import java.util.List;

import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.iap.dictionary.decoupage.application.beans.ApplicationBean;

public interface ApplicationDictionnaireDao {
	@SuppressWarnings("unchecked")
	public List getEntiteDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getPropertyDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getActionDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getModuleDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getSubModuleDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getViewDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getViewLibelleDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getViewActionDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getLibelleDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getDeviseDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getActionTemplateDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public ApplicationBean getApplicationDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getUserTypeDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getListMenus(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getRoleDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getToolActionDictionary(DbSession dbSession);

	@SuppressWarnings("unchecked")
	public List getMessageDictionary(DbSession dbSession);
}
