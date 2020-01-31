package tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.business;

import java.io.Serializable;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.beans.FonctionUtilProfilBean;
import tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.beans.FonctionUtilisationBean;
import tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.dao.FonctionUtilisationDao;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbSequenceSupport;

public class FonctionUtilisationBusiness extends GenericEntiteBusiness{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public Serializable create(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			DbSequenceSupport sequenceSupport = daoSession.sequenceSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();
			sequenceSupport.setSequenceValues(bean);
			bean.setCreatedById(userContext.getUser().getUserId());
			bean.setCreatedDate(DateUtils.getCourantTimestamp());
			// newbean.getAutModules().clear();
			save = persistantSupport.save(bean);
			createDetail((FonctionUtilisationBean) bean, userContext);
			daoSession.commitTransaction();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	public void update(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();
			bean.setUpdatedById(userContext.getUser().getUserId());
			bean.setUpdatedDate(DateUtils.getCourantTimestamp());
			persistantSupport.update(bean);
			deleteDetail(bean, userContext);
			createDetail((FonctionUtilisationBean) bean, userContext);
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
			deleteDetail(bean, userContext);
			persistantSupport.delete(bean);
			// saveAudit(bean, userContext, businessParams.getDeleteActionId());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	private void createDetail(FonctionUtilisationBean fonctionUtilisation, UserContext userContext) throws DaoFunctionalException {
		DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
		if (fonctionUtilisation.getProfils() != null && fonctionUtilisation.getProfils().size() > 0) {
			for (int i = 0; i < fonctionUtilisation.getProfils().size(); i++) {
				FonctionUtilProfilBean fonctionUtilProfilBean = (FonctionUtilProfilBean) fonctionUtilisation.getProfils().get(i);
				if (fonctionUtilProfilBean.getModuleId() != null && fonctionUtilProfilBean.getProfileId() != null) {
					fonctionUtilProfilBean.setFonctionUtilisationId(fonctionUtilisation.getFonctionUtilisationId());
					persistantSupport.save(fonctionUtilProfilBean);
				}
			}
		}
	}
	public void deleteDetail(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		((FonctionUtilisationDao) entiteDao).deleteDetail(daoSession, userContext.userId(), false, bean);
	}
}
