package tn.com.smartsoft.configGenerale.devises.cours.business;

import java.io.Serializable;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbSequenceSupport;
import tn.com.smartsoft.configGenerale.devises.cours.beans.CourBean;


public class CourBusiness extends GenericEntiteBusiness{
	
	public Serializable create(CourBean bean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
		 	DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);
		 	DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
		 	daoSession.clear();
			daoSession.beginTransaction();
			
		 	bean.setCreatedById(userContext.getUser().getUserId());
			bean.setCreatedDate(DateUtils.getCourantTimestamp());
			dbSequenceSupport.setSequenceValues(bean);
			save = persistantSupport.save(bean);
			saveAudit(bean, userContext, businessParams.getCreateActionId());
			daoSession.commitTransaction();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	
	

}
