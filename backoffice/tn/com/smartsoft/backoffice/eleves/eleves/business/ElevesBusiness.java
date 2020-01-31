package tn.com.smartsoft.backoffice.eleves.eleves.business;

import java.io.Serializable;
import java.util.List;

import tn.com.smartsoft.backoffice.eleves.eleves.beans.EleveSaisonsBean;
import tn.com.smartsoft.backoffice.eleves.eleves.beans.ElevesBean;
import tn.com.smartsoft.backoffice.eleves.eleves.dao.ElevesDao;
import tn.com.smartsoft.backoffice.referentiel.saison.beans.SaisonsBean;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.commons.utils.ListUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbSequenceSupport;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;

public class ElevesBusiness extends GenericEntiteBusiness {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataBusinessBean getById(DataBusinessBean dataBusinessBean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		ElevesBean bean = (ElevesBean) super.getById(dataBusinessBean, actionId, userContext);
		List<DataBusinessBean> historys = this.getHistorys(bean, actionId, userContext);
		bean.setHistorys(historys);
		SaisonsBean saisons = getOpenSaison(bean, actionId, userContext);
		if (saisons != null) {
			EleveSaisonsBean eleveSaison = (EleveSaisonsBean) ListUtils.findByProperty(historys, "eleveSaisonId", saisons.getSaisonId());
			if (eleveSaison == null) {
				eleveSaison = new EleveSaisonsBean();
				eleveSaison.setSaison(saisons);
				eleveSaison.setEleveSaisonId(saisons.getSaisonId());
				eleveSaison.setEtatBusiness(false);
			} else {
				eleveSaison.setEtatBusiness(true);
			}
			bean.setEleveSaison(eleveSaison);
		}
		return bean;
	}

	public Serializable create(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		ElevesBean eleve = (ElevesBean) bean;
		EleveSaisonsBean eleveSaison = eleve.getEleveSaison();
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);
			daoSession.beginTransaction();
			bean.setCreatedById(userContext.getUser().getUserId());
			bean.setCreatedDate(DateUtils.getCourantTimestamp());
			// new
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
			dbSequenceSupport.setSequenceValues(bean);
			save = persistantSupport.save(bean);
			if (eleveSaison != null) {
				eleveSaison.setCodePermanent(eleve.getCodePermanent());
				persistantSupport.save(eleveSaison);
			}
			// saveAudit(bean, userContext, businessParams.getCreateActionId());
			daoSession.commitTransaction();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

	public void update(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		try {
			ElevesBean eleve = (ElevesBean) bean;
			EleveSaisonsBean eleveSaison = eleve.getEleveSaison();
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();
			bean.setUpdatedById(userContext.getUser().getUserId());
			bean.setUpdatedDate(DateUtils.getCourantTimestamp());
			persistantSupport.update(bean);
			if (eleveSaison != null) {
				if (eleveSaison.getEtatBusiness()) {
					persistantSupport.update(eleveSaison);
				} else {
					eleveSaison.setCodePermanent(eleve.getCodePermanent());
					persistantSupport.save(eleveSaison);
				}
			}
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
			deleteHistorys(bean, userContext);
			persistantSupport.delete(bean);
			// saveAudit(bean, userContext, businessParams.getDeleteActionId());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

	public SaisonsBean getOpenSaison(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		SaisonsBean dataList = ((ElevesDao) entiteDao).getOpenSaison(daoSession, userContext.userId(), false, bean);
		return dataList;
	}

	public List<DataBusinessBean> getHistorys(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		List<DataBusinessBean> dataList = ((ElevesDao) entiteDao).getHistorys(daoSession, userContext.userId(), false, bean);
		return dataList;
	}

	public void deleteHistorys(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		((ElevesDao) entiteDao).deleteHistorys(daoSession, userContext.userId(), false, bean);
	}
}
