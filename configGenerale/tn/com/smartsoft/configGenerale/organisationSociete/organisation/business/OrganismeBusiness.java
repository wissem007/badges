package tn.com.smartsoft.configGenerale.organisationSociete.organisation.business;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.dao.OrganismeDao;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbSequenceSupport;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;

public class OrganismeBusiness extends GenericEntiteBusiness{

	private static final long	serialVersionUID	= 1L;

	public Serializable create(OrganismeBean bean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();
			dbSequenceSupport.setSequenceValues(bean);
			bean.setCreatedById(userContext.getUser().getUserId());
			bean.setCreatedDate(DateUtils.getCourantTimestamp());
			bean.setParentSocieteId(bean.getSocieteId());
			save = persistantSupport.save(bean);
			saveAudit(bean, userContext, businessParams.getCreateActionId());
			daoSession.commitTransaction();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	public List<DataBusinessBean> getListOrganisme(OrganismeBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		List<DataBusinessBean> dataList = ((OrganismeDao) entiteDao).getListOrganisme(daoSession, userContext.userId(), false, bean);
		return dataList;
	}
}
