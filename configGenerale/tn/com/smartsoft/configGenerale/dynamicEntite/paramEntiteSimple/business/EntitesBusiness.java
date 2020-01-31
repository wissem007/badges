package tn.com.smartsoft.configGenerale.dynamicEntite.paramEntiteSimple.business;

import java.io.Serializable;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.business.EntiteBusiness;
import tn.com.smartsoft.iap.dictionary.decoupage.subModule.beans.SubModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBean;

public class EntitesBusiness extends EntiteBusiness {
	
	private static final long serialVersionUID = 1L;
	
	public Serializable create(DataBusinessBean dataBusinessBean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			EntiteBean bean = (EntiteBean) dataBusinessBean;

			bean.setCreatedById(userContext.getUser().getUserName());
			bean.setCreatedDate(DateUtils.getCourantTimestamp());
			bean.setTypeEntiteId((long) 2);
			bean.setAuditable(Boolean.FALSE);
			bean.setPersistant(Boolean.FALSE);
			bean.setDbTable(bean.getEntiteId());
			bean.setJavaClass(bean.getEntiteId());
			bean.setSubModuleId("EntiteSimple");
			bean.setSujetId("EntiteSimple");
			createSubModule(bean.getModuleId(), userContext);
			

			save = persistantSupport.save(bean);
			
			saveAudit(bean, userContext, businessParams.getCreateActionId());
			daoSession.commitTransaction();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	
	private void createSubModule(String moduleId, UserContext userContext) throws FunctionalException {

		DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
		
		SubModuleBean subModuleBean = new SubModuleBean();
		subModuleBean.setModuleId(moduleId);
		subModuleBean.setSubModuleId("EntiteSimple");
		subModuleBean.setLibelle("Entité Simple");
		subModuleBean.setRang((long) 1);
		persistantSupport.saveOrUpdate(subModuleBean);
		createSujet(subModuleBean, userContext);

	}
	
	private void createSujet(SubModuleBean subModuleBean, UserContext userContext) throws FunctionalException {

		DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
		
		SujetBean sujetBean = new SujetBean();
		sujetBean.setModuleId(subModuleBean.getModuleId());
		sujetBean.setSubModuleId("EntiteSimple");
		sujetBean.setSujetId("EntiteSimple");
		sujetBean.setLibelle("Entité Simple");
		sujetBean.setRang((long) 1);
		persistantSupport.saveOrUpdate(sujetBean);

	}
	
	public void update(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();

			persistantSupport.update(bean);
			saveAudit(bean, userContext, businessParams.getUpdateActionId());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
}
