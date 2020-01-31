package tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.business;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.beans.EleveDemandeCartesBean;
import tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.beans.StatuDemandes;
import tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.dao.EleveDemandeCartesDao;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;

public class EleveDemandeCartesBusiness extends GenericEntiteBusiness {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<DataBusinessBean> getStatus(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		if (bean != null)
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
		List<DataBusinessBean> dataList = ((EleveDemandeCartesDao) entiteDao).getStatus(daoSession, userContext.userId(), false, bean);
		return dataList;
	}

	public void updateTaraitement(List<DataBusinessBean> listBean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();
			for (int i = 0; i < listBean.size(); i++) {
				EleveDemandeCartesBean entity = (EleveDemandeCartesBean) listBean.get(i);
				if (!entity.getChecked()) {
					entity.setStatuDemandesId(StatuDemandes.STATUS_REJETE);
				} else {
					entity.getEleveSaison().setPhotoBdata(entity.getPhotoBdata());
					entity.getEleveSaison().setPhotoCtype(entity.getPhotoCtype());
					entity.getEleveSaison().setPhotoSdata(entity.getPhotoSdata());
					entity.getEleveSaison().setTelResponsable1(entity.getTelResponsable1());
					entity.getEleveSaison().setTelResponsable2(entity.getTelResponsable2());
					entity.setStatuDemandesId(StatuDemandes.STATUS_VERS_IMPRESION);
					persistantSupport.update(entity.getEleveSaison());
				}
				persistantSupport.update(entity);
			}
			// saveAudit(bean, userContext, businessParams.getUpdateActionId());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

	public List<DataBusinessBean> updateImpresion(List<DataBusinessBean> listBean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();
			List<DataBusinessBean> listnew = new ArrayList<DataBusinessBean>();
			for (int i = 0; i < listBean.size(); i++) {
				EleveDemandeCartesBean entity = (EleveDemandeCartesBean) listBean.get(i);
				if (entity.getChecked()) {
					entity.setDateImpresion(DateUtils.getCourantDate());
					persistantSupport.update(entity.getEleveSaison());
					listnew.add(entity);
					persistantSupport.update(entity);
				}
			}
			// saveAudit(bean, userContext, businessParams.getUpdateActionId());
			daoSession.commitTransaction();
			return listnew;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
}
