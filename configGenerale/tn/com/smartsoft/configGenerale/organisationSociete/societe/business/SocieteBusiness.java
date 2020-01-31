package tn.com.smartsoft.configGenerale.organisationSociete.societe.business;

import java.io.Serializable;
import java.sql.Timestamp;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.configGenerale.organisationSociete.societe.beans.SocieteBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbSequenceSupport;

public class SocieteBusiness extends GenericEntiteBusiness {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Serializable create(SocieteBean bean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);

		try {
			if (bean.getFile() != null && bean.getFile().getSizeData() > 0) {
				Long fileId = (Long) dbSequenceSupport.getSequenceValue(bean, "fileId");
				bean.getFile().setFileId(fileId);
				bean.setFileId(fileId);
			}
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();
			
			dbSequenceSupport.setSequenceValues(bean);
			
			bean.setCreatedById(userContext.getUser().getUserId());
			bean.setCreatedDate(DateUtils.getCourantTimestamp());
			bean.setParentSocieteId(bean.getSocieteId());
			if (bean.getFile() != null && bean.getFile().getSizeData() > 0)
				persistantSupport.save(bean.getFile());
			save = persistantSupport.save(bean);
			// Création Organisme
			OrganismeBean organismeBean = new OrganismeBean();
			organismeBean.setOrganismeId(new Long(0));
			organismeBean.setLibelle(bean.getLibelle());
			organismeBean.setAbreviation(bean.getAbreviation());
			organismeBean.setSocieteId(bean.getSocieteId());
			organismeBean.setParentSocieteId(bean.getSocieteId());
			organismeBean.setAdresse(bean.getAdresse());
			organismeBean.setCreatedById(bean.getCreatedById());
			organismeBean.setCreatedDate(bean.getCreatedDate());
			organismeBean.setEmail(bean.getEmail());
			organismeBean.setEmail(bean.getEmail());
			organismeBean.setSiteWeb(bean.getSiteWeb());
			organismeBean.setLocaliteId(bean.getLocaliteId());
			organismeBean.setTelephone1(bean.getTelephone1());
			organismeBean.setTelephone2(bean.getTelephone2());
			organismeBean.setFax(bean.getFax());
			organismeBean.setAdresse(bean.getAdresse());
			persistantSupport.save(organismeBean);
						
			
			// Fin Création Organisme
			saveAudit(bean, userContext, businessParams.getCreateActionId());
			daoSession.commitTransaction();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

	public void update(SocieteBean bean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);
			daoSession.beginTransaction();
			daoSession.clear();
			bean.setUpdatedById(userContext.getUser().getUserId());
			Timestamp courantDate = DateUtils.getCourantTimestamp();
			bean.setUpdatedDate(courantDate);
			if (bean.getFile() != null && bean.getFile().getSizeData() > 0) {
				if (bean.getFile().getFileId() == null) {
					Long fileId = (Long) dbSequenceSupport.getSequenceValue(bean, "fileId");
					bean.getFile().setFileId(fileId);
					bean.setFileId(fileId);
				}
				persistantSupport.saveOrUpdate(bean.getFile());
			}
			OrganismeBean organismeBean = new OrganismeBean();
			organismeBean.setOrganismeId(new Long(0));
			organismeBean.setLibelle(bean.getLibelle());
			organismeBean.setAbreviation(bean.getAbreviation());
			organismeBean.setSocieteId(bean.getSocieteId());
			organismeBean.setUpdatedById(userContext.getUser().getUserId());
			organismeBean.setUpdatedDate(courantDate);
			organismeBean.setEmail(bean.getEmail());
			organismeBean.setEmail(bean.getEmail());
			organismeBean.setSiteWeb(bean.getSiteWeb());
			organismeBean.setLocaliteId(bean.getLocaliteId());
			organismeBean.setTelephone1(bean.getTelephone1());
			organismeBean.setTelephone2(bean.getTelephone2());
			organismeBean.setFax(bean.getFax());
			organismeBean.setAdresse(bean.getAdresse());			
			persistantSupport.saveOrUpdate(organismeBean);
			persistantSupport.update(bean);
			saveAudit(bean, userContext, businessParams.getUpdateActionId());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

	public void delete(SocieteBean bean, UserContext userContext) throws FunctionalException {
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			Long fileId = bean.getFileId();
			OrganismeBean organismeBean = new OrganismeBean();
			organismeBean.setOrganismeId(new Long(0));
			organismeBean.setLibelle(bean.getLibelle());
			organismeBean.setAbreviation(bean.getAbreviation());
			organismeBean.setSocieteId(bean.getSocieteId());
			persistantSupport.delete(organismeBean);
			persistantSupport.delete(bean);
			if (bean.getFile() != null) {
				bean.getFile().setFileId(fileId);
				persistantSupport.delete(bean.getFile());
			}
			saveAudit(bean, userContext, businessParams.getDeleteActionId());
			daoSession.commitTransaction();
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}

}
