package tn.com.smartsoft.configGenerale.organisationSociete.localite.business;

import java.io.Serializable;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.configGenerale.organisationSociete.localite.beans.LocaliteBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbSequenceSupport;

public class LocaliteBusiness extends GenericEntiteBusiness {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Serializable create(LocaliteBean bean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			
			bean.setCreatedById(userContext.getUser().getUserId());
			bean.setCreatedDate(DateUtils.getCourantTimestamp());
			DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);
			dbSequenceSupport.setSequenceValues(bean);
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
			save = persistantSupport.save(bean);
			daoSession.commitTransaction();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
}
