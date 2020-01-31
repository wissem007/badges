package tn.com.smartsoft.framework.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.commons.utils.OgnlUtil;
import tn.com.smartsoft.commons.utils.ValueUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.AuditSupport;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.iap.administration.securite.audit.beans.AuditActionBean;
import tn.com.smartsoft.iap.administration.securite.audit.beans.AuditEntiteBean;
import tn.com.smartsoft.iap.administration.securite.audit.beans.AuditPropertyBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.PropertyBean;

public class AuditSupportImpl implements Serializable, AuditSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_REGLE_BEAN = "bean";
	private static final String DEFAULT_REGLE_ANNEE = "annee";
	private static final String DEFAULT_REGLE_MOIS = "mois";
	private static final String DEFAULT_REGLE_JOUR = "jours";
	private static final String DEFAULT_REGLE_ORGANISATION = "org";
	private static final String DEFAULT_REGLE_SOCIETE = "societe";
	private static final String DEFAULT_REGLE_USER = "user";

	private DbSession dbSession;
	private UserContext userContext;

	private AuditActionBean auditActionBean;
	private ActionBeanId actionBeanId;
	private ActionBean actionBean;

	public AuditSupportImpl(DbSession dbSession, UserContext userContext) {
		super();
		this.dbSession = dbSession;
		this.userContext = userContext;
	}

	public void begin(ActionBeanId actionBeanId, Object beanRoot) throws FunctionalException {
		this.actionBeanId = actionBeanId;
		this.actionBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getActionBean(actionBeanId);
		if (actionBean == null || actionBean.isAuditable()==null||!actionBean.isAuditable())
			return;
		this.auditActionBean = new AuditActionBean();
		if (StringUtils.isNotBlank(actionBean.getAuditValueExpression())) {
			Map<String, Object> root = new HashMap<String, Object>();
			root.put(DEFAULT_REGLE_ANNEE, prepareValue(DateUtils.getAnnees(DateUtils.getCourantDate()), 4));
			root.put(DEFAULT_REGLE_MOIS, prepareValue(DateUtils.getMois(DateUtils.getCourantDate()), 2));
			root.put(DEFAULT_REGLE_JOUR, prepareValue(DateUtils.getJours(DateUtils.getCourantDate()), 2));
			root.put(DEFAULT_REGLE_BEAN, beanRoot);
			root.put(DEFAULT_REGLE_ORGANISATION, userContext.getCurrentUserOrganismeId());
			root.put(DEFAULT_REGLE_USER, userContext.userId());
			root.put(DEFAULT_REGLE_SOCIETE, userContext.getCurrentUserSocieteId());
			Object exp = OgnlUtil.parseExpression(actionBean.getAuditValueExpression());
			auditActionBean.setAuditValue(String.valueOf(OgnlUtil.getValue(exp, root)));
		}
		auditActionBean.setActionId(this.actionBeanId.getActionId());
		auditActionBean.setSujetId(this.actionBeanId.getSujetId());
		auditActionBean.setSubModuleId(this.actionBeanId.getSubModuleId());
		auditActionBean.setModuleId(this.actionBeanId.getModuleId());
		auditActionBean.setUserId(userContext.userId());
		auditActionBean.setSocieteId(userContext.getCurrentUserSocieteId());
		auditActionBean.setOrganismeId(userContext.getCurrentUserOrganismeId());
		auditActionBean.setDateAudit(new Date());
		this.dbSession.sequenceSupport(userContext).setSequenceValues(auditActionBean);
	}

	public AuditEntiteBean addAuditEntite(DataBusinessBean bean, DataBusinessBean oldBean, ActionMode mode) throws FunctionalException {
		AuditEntiteBean auditEntiteBean = createAuditEntite(bean, oldBean, mode);
		if (actionBean == null || actionBean.isAuditable()==null ||!actionBean.isAuditable())
			return null;
		auditActionBean.getAuditEntites().add(auditEntiteBean);
		return auditEntiteBean;
	}

	public AuditEntiteBean addAuditEntite(AuditEntiteBean masterAuditEntiteBean, DataBusinessBean bean, DataBusinessBean oldBean, ActionMode mode) throws FunctionalException {
		AuditEntiteBean auditEntiteBean = createAuditEntite(bean, oldBean, mode);
		if (masterAuditEntiteBean != null) {
			auditEntiteBean.setParentEntiteId(masterAuditEntiteBean.getEntiteId());
			auditEntiteBean.setParentAuditEntiteId(masterAuditEntiteBean.getAuditEntiteId());
			masterAuditEntiteBean.getDetailAuditEntite().add(auditEntiteBean);
		}
		return auditEntiteBean;
	}

	public void save() throws FunctionalException {
		if (actionBean == null || actionBean.isAuditable()==null || !actionBean.isAuditable())
			return;
		dbSession.persistantSupport(userContext).save(auditActionBean);
		auditActionBean = null;
		actionBeanId = null;
		actionBean = null;
	}

	private AuditEntiteBean createAuditEntite(DataBusinessBean bean, DataBusinessBean oldBean, ActionMode mode) throws FunctionalException {
		if (actionBean == null || actionBean.isAuditable()==null|| !actionBean.isAuditable())
			return null;
		AuditEntiteBean auditEntiteBean = new AuditEntiteBean();
		EntiteBean entiteBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getEntiteBean(bean.getClass());
		if (!entiteBean.isAuditable())
			return null;
		this.dbSession.sequenceSupport(userContext).setSequenceValues(auditEntiteBean);
		auditEntiteBean.setActionId(this.auditActionBean.getActionId());
		auditEntiteBean.setSujetId(entiteBean.getSujetId());
		auditEntiteBean.setSubModuleId(entiteBean.getSubModuleId());
		auditEntiteBean.setModuleId(entiteBean.getModuleId());
		auditEntiteBean.setEntiteId(entiteBean.getEntiteId());
		auditEntiteBean.setAuditActionId(auditActionBean.getAuditActionId());
		auditEntiteBean.setActionTypeId(mode.getActionTypeId());
		if (StringUtils.isNotBlank(entiteBean.getDisplayKeyFormat())) {
			Map<String, Object> root = new HashMap<String, Object>();
			root.put(DEFAULT_REGLE_ANNEE, prepareValue(DateUtils.getAnnees(DateUtils.getCourantDate()), 4));
			root.put(DEFAULT_REGLE_MOIS, prepareValue(DateUtils.getMois(DateUtils.getCourantDate()), 2));
			root.put(DEFAULT_REGLE_JOUR, prepareValue(DateUtils.getJours(DateUtils.getCourantDate()), 2));
			root.put(DEFAULT_REGLE_BEAN, bean);
			root.put(DEFAULT_REGLE_ORGANISATION, userContext.getCurrentUserOrganismeId());
			root.put(DEFAULT_REGLE_USER, userContext.userId());
			root.put(DEFAULT_REGLE_SOCIETE, userContext.getCurrentUserSocieteId());
			Object exp = OgnlUtil.parseExpression(entiteBean.getDisplayKeyFormat());
			auditEntiteBean.setObjectIdValue(String.valueOf(OgnlUtil.getValue(exp, root)));
		} else {
			auditEntiteBean.setObjectIdValue(ApplicationConfiguration.applicationManager().formaterManger().getAsString(bean.getId()));
		}
		if (mode.equals(ActionMode.CREATE)) {
			loadAuditEntiteCreateMode(bean, oldBean, auditEntiteBean, entiteBean);
		} else if (mode.equals(ActionMode.READ)) {
			loadAuditEntiteReadMode(bean, oldBean, auditEntiteBean, entiteBean);
		} else if (mode.equals(ActionMode.DELETE)) {
			loadAuditEntiteDeleteMode(bean, oldBean, auditEntiteBean, entiteBean);
		} else if (mode.equals(ActionMode.UPDATE)) {
			loadAuditEntiteUpdateMode(bean, oldBean, auditEntiteBean, entiteBean);
		}
		return auditEntiteBean;
	}

	private void loadAuditEntiteCreateMode(DataBusinessBean bean, DataBusinessBean oldBean, AuditEntiteBean auditEntiteBean, EntiteBean entiteBean) {

	}

	private void loadAuditEntiteUpdateMode(DataBusinessBean bean, DataBusinessBean oldBean, AuditEntiteBean auditEntiteBean, EntiteBean entiteBean) {
		Map<String, PropertyBean> propertys = entiteBean.getPropertys();
		Set<String> keys = propertys.keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String propertyName = (String) iterator.next();
			PropertyBean property = entiteBean.getProperty(propertyName);
			if (property.getAuditable()) {
				createAuditProperty(bean, oldBean, property, auditEntiteBean);
			}
		}
	}

	private void loadAuditEntiteDeleteMode(DataBusinessBean bean, DataBusinessBean oldBean, AuditEntiteBean auditEntiteBean, EntiteBean entiteBean) {
	}

	private void loadAuditEntiteReadMode(DataBusinessBean bean, DataBusinessBean oldBean, AuditEntiteBean auditEntiteBean, EntiteBean entiteBean) {
	}

	private void createAuditProperty(DataBusinessBean bean, DataBusinessBean oldBean, PropertyBean property, AuditEntiteBean auditEntiteBean) {
		Object oldValue = null;
		Object newValue = null;
		try {
			newValue = PropertyUtils.getProperty(bean, property.getPropertyName());
			oldValue = PropertyUtils.getProperty(oldBean, property.getPropertyName());
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
		if (ValueUtils.equals(oldValue, newValue)) {
			return;
		}
		AuditPropertyBean auditPropertyBean = new AuditPropertyBean();
		auditPropertyBean.setActionId(auditEntiteBean.getActionId());
		auditPropertyBean.setSujetId(auditEntiteBean.getSujetId());
		auditPropertyBean.setSubModuleId(auditEntiteBean.getSubModuleId());
		auditPropertyBean.setModuleId(auditEntiteBean.getModuleId());
		auditPropertyBean.setEntiteId(auditEntiteBean.getEntiteId());
		auditPropertyBean.setAuditEntiteId(auditEntiteBean.getAuditEntiteId());
		auditPropertyBean.setAuditActionId(auditEntiteBean.getAuditActionId());
		auditPropertyBean.setPropertyName(property.getPropertyName());
		auditPropertyBean.setOldValue(ApplicationConfiguration.applicationManager().formaterManger().getAsString(property.getUserTypeId(), oldValue));
		auditPropertyBean.setNewValue(ApplicationConfiguration.applicationManager().formaterManger().getAsString(property.getUserTypeId(), newValue));
		auditEntiteBean.getAuditPropertys().add(auditPropertyBean);
	}

	private String prepareValue(int seqValue, int p) {
		String value = String.valueOf(seqValue);
		if (p < value.length())
			return value;
		StringBuffer var = new StringBuffer("");
		for (int i = 0; i < p - value.length(); i++) {
			var.append("0");
		}
		var.append(value);
		return var.toString();
	}
}
