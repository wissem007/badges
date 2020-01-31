package tn.com.smartsoft.configGenerale.dynamicEntite.entiteSimple.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.type.ComponentType;
import org.hibernate.type.Type;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.ClassUtilities;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.configGenerale.dynamicEntite.entiteSimple.beans.DataValuesBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbEntiteConfiguration;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;

public class DataValuesBusiness extends GenericEntiteBusiness {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
// public DataBusinessBean getEntiteRefById(DataValuesBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
// if (bean != null && userContext != null)
// bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
// EntiteBean entiteBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getEntiteBean(bean.getRefEntiteId());
// if (entiteBean.getTypeEntiteId().equals(new Long(2))) {
// if (entiteBean.getNiveauApplicatifId() != null && entiteBean.getNiveauApplicatifId().intValue() > 1)
// bean.setSocieteId(userContext.getCurrentUserSocieteId());
// if (entiteBean.getNiveauApplicatifId() != null && entiteBean.getNiveauApplicatifId().intValue() > 2)
// bean.setOrganismeId(userContext.getCurrentUserOrganismeId());
// return getById(bean, actionId, userContext);
// }
// DbEntiteConfiguration dbEntiteConfiguration = getDaoSession().getDbEntiteConfiguration(ClassUtilities.getJavaClass(entiteBean.getJavaClass()));
// Serializable id = bean.creatIdRefBean(dbEntiteConfiguration.getClassMetadata().getIdentifierType().getReturnedClass());
// DataBusinessBean dataBusinessBean = (DataBusinessBean) getDaoSession().persistantSupport(userContext).get(ClassUtilities.getJavaClass(entiteBean.getJavaClass()), id);
// DataValuesBean beanRow = new DataValuesBean();
// beanRow.loadFromRefBean(dataBusinessBean, bean.getLibelleEntiteRefExp());
// return beanRow;
// }
	
	public List<DataBusinessBean> getListRefEntite(DataValuesBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		if (bean != null)
			bean.setParentSocieteId(userContext.getCurrentUserSocieteId());
		EntiteBean entiteBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getEntiteBean(bean.getRefEntiteId());
		if (entiteBean.getTypeEntiteId().equals(new Long(2))) {
			Long organismeId = null;
			Long societeId = null;
			if (entiteBean.getNiveauApplicatifId() != null && entiteBean.getNiveauApplicatifId().intValue() > 1)
				societeId = userContext.getCurrentUserSocieteId();
			if (entiteBean.getNiveauApplicatifId() != null && entiteBean.getNiveauApplicatifId().intValue() > 2)
				organismeId = userContext.getCurrentUserOrganismeId();
			bean.setSocieteId(societeId);
			bean.setOrganismeId(organismeId);
			return entiteDao.getByCriteria(daoSession, userContext.userId(), false, bean);
		}
		@SuppressWarnings("rawtypes")
		Class javaClass = ClassUtilities.getJavaClass(entiteBean.getJavaClass());
		Object[] eatb = getEtabId(entiteBean, userContext, javaClass);
		List<?> dataList = getDaoSession().persistantSupport(userContext).getAll(javaClass, (Long) eatb[0], (Long) eatb[1], (Boolean) eatb[2]);
		List<DataBusinessBean> dataLists = new ArrayList<DataBusinessBean>();
		for (int i = 0; i < dataList.size(); i++) {
			DataValuesBean beanRow = new DataValuesBean();
			DataBusinessBean dataBusinessBean = (DataBusinessBean) dataList.get(i);
			beanRow.loadFromRefBean(dataBusinessBean, bean.getLibelleEntiteRefExp());
			dataLists.add(beanRow);
		}
		return dataLists;
	}
	
	private Object[] getEtabId(EntiteBean entiteBean, UserContext userContext, Class<?> javaClass) {
		Long organismeId = null;
		Long societeId = null;
		boolean isSoc = false;
		boolean isEtab = false;
		Boolean isInId = false;
		if (entiteBean.getNiveauApplicatifId() != null && entiteBean.getNiveauApplicatifId().intValue() > 1)
			societeId = userContext.getCurrentUserSocieteId();
		if (entiteBean.getNiveauApplicatifId() != null && entiteBean.getNiveauApplicatifId().intValue() > 2)
			organismeId = userContext.getCurrentUserOrganismeId();
		if (entiteBean.getNiveauApplicatifId() != null && entiteBean.getNiveauApplicatifId().intValue() > 1) {
			DbEntiteConfiguration dbEntiteConfiguration = getDaoSession().getDbEntiteConfiguration(javaClass);
			Type type = dbEntiteConfiguration.getClassMetadata().getIdentifierType();
			if (type instanceof ComponentType) {
				String[] ps = ((ComponentType) type).getPropertyNames();
				for (int i = 0; i < ps.length; i++) {
					if (ps[i].equalsIgnoreCase("societeId")) {
						isSoc = true;
						isInId = true;
					} else if (ps[i].equalsIgnoreCase("organismeId")) {
						isEtab = true;
						isInId = true;
					}
				}
			}
			if (!isSoc && !isEtab) {
				String[] ps = dbEntiteConfiguration.getClassMetadata().getPropertyNames();
				for (int i = 0; i < ps.length; i++) {
					if (ps[i].equalsIgnoreCase("societeId")) {
						isSoc = true;
					} else if (ps[i].equalsIgnoreCase("organismeId")) {
						isEtab = true;
					}
				}
			}
			if (!isSoc)
				societeId = null;
			if (!isEtab)
				organismeId = null;
		}
		return new Object[] { societeId, organismeId, isInId };
	}
	
	public Serializable create(DataBusinessBean dataBusinessBean, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			DataValuesBean bean = (DataValuesBean) dataBusinessBean;
			bean.setCreatedById(userContext.getUser().getUserName());
			bean.setCreatedDate(DateUtils.getCourantTimestamp());
			bean.setSubModuleId("EntiteSimple");
			bean.setSujetId("EntiteSimple");
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
