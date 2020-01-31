package tn.com.smartsoft.configGenerale.administration.organismeProprietaires.business;

import java.io.Serializable;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.configGenerale.administration.organismeProprietaires.beans.OrganismeProprietairesBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;

public class OrganismeProprietairesBusiness extends GenericEntiteBusiness{
	
	private static final long	serialVersionUID	= 1L;
	
	public Serializable create(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			bean.setCreatedById(userContext.getUser().getUserId());
			bean.setCreatedDate(DateUtils.getCourantTimestamp());
			// newbean.getAutModules().clear();
			createDetail(bean);
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
			save = persistantSupport.save(bean);
			daoSession.commitTransaction();
			((OrganismeProprietairesBean) bean).getOrganisme().getParentSociete();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	private void createDetail(DataBusinessBean bean) {
		OrganismeProprietairesBean organismeProprietaires = (OrganismeProprietairesBean) bean;
		organismeProprietaires.getAutModules().clear();
		if (organismeProprietaires.getModules() != null && organismeProprietaires.getModules().size() > 0) {
			for (int i = 0; i < organismeProprietaires.getModules().size(); i++) {
				ModuleBean moduleBean = (ModuleBean) organismeProprietaires.getModules().get(i);
				if (moduleBean.getEtatBusiness() != null && moduleBean.getEtatBusiness()) {
					organismeProprietaires.getAutModules().add(moduleBean.getModuleId());
				}
			}
		}
	}
	public void update(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();
			createDetail(bean);
			bean.setUpdatedById(userContext.getUser().getUserId());
			bean.setUpdatedDate(DateUtils.getCourantTimestamp());
			persistantSupport.update(bean);
			// saveAudit(bean, userContext, businessParams.getUpdateActionId());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	public void delete(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			createDetail(bean);
			persistantSupport.delete(bean);
			// saveAudit(bean, userContext, businessParams.getDeleteActionId());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
}
