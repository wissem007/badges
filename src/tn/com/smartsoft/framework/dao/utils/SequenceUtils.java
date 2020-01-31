package tn.com.smartsoft.framework.dao.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;
import org.hibernate.id.IdentifierGenerationException;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.commons.utils.OgnlUtil;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.PropertyBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sequences.beans.SequenceBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sequences.beans.SequenceValueBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sequences.beans.SequenceValueBeanId;

public class SequenceUtils {
	
	public static final String DEFAULT_REGLE_ANNEE = "annee";
	public static final String DEFAULT_REGLE_MOIS = "mois";
	public static final String DEFAULT_REGLE_JOUR = "jours";
	public static final String DEFAULT_REGLE_BEAN = "row";
	public static final String DEFAULT_REGLE_SEQ = "sequence";
	public static final String DEFAULT_REGLE_ORGANISATION = "org";
	public static final String DEFAULT_REGLE_USER = "user";
	public static final String DEFAULT_REGLE_SOCIETE = "societe";
	
	public static void loadGenerateValues(DbSession dbSession, UserContext userContext, Object bean, Map<String, Object> context) throws DaoFunctionalException {
		EntiteBean entiteBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getEntiteBean(bean.getClass());
		Map<String, PropertyBean> propertys = entiteBean.getPropertys();
		Iterator<String> iterator = propertys.keySet().iterator();
		while (iterator.hasNext()) {
			String propertyName = (String) iterator.next();
			if (StringUtils.isNotBlank(entiteBean.getProperty(propertyName).getSequenceId()))
				try {
					Object valueGene = generate(bean, userContext, dbSession.persistantSupport(userContext), entiteBean.getProperty(propertyName), context);
					PropertyUtils.setProperty(bean, propertyName, valueGene);
				} catch (Exception e) {
					throw new TechnicalException(e);
				}
		}
	}
	
	public static Serializable generate(DbSession dbSession, UserContext userContext, Object bean, String propertyName, Map<String, Object> context) throws DaoFunctionalException {
		DbPersistantSupport dbPersistantSupport = dbSession.persistantSupport(userContext);
		EntiteBean entiteBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getEntiteBean(bean.getClass());
		return generate(bean, userContext, dbPersistantSupport, entiteBean.getProperty(propertyName), context);
	}
	
	public static List<Serializable> generate(DbSession dbSession, UserContext userContext, @SuppressWarnings("rawtypes") List beans, String propertyName, Map<String, Object> context)
			throws DaoFunctionalException {
		if (beans.size() == 0)
			return null;
		DbPersistantSupport dbPersistantSupport = dbSession.persistantSupport(userContext);
		EntiteBean entiteBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getEntiteBean(beans.get(0).getClass());
		return generate(beans, userContext, dbPersistantSupport, entiteBean.getProperty(propertyName), context);
	}
	
	private static Serializable generate(Object bean, UserContext userContext, DbPersistantSupport dbPersistantSupport, PropertyBean propertyBean, Map<String, Object> context)
			throws DaoFunctionalException {
		if (propertyBean.getSequence() == null)
			return null;
		String sequenceValueId = getVersionValue(propertyBean.getSequence(), bean, userContext, context);
		SequenceValueBeanId sequenceValueBeanId = new SequenceValueBeanId(sequenceValueId, propertyBean.getSequenceId());
		SequenceValueBean sequenceValueBean = (SequenceValueBean) dbPersistantSupport.get(SequenceValueBean.class, sequenceValueBeanId, LockMode.UPGRADE);
		Long value;
		if (sequenceValueBean == null) {
			sequenceValueBean = new SequenceValueBean();
			sequenceValueBean.setId(sequenceValueBeanId);
			sequenceValueBean.setLastValue(new Long(1));
			dbPersistantSupport.save(sequenceValueBean);
			value = new Long(1);
		} else {
			value = new Long(sequenceValueBean.getLastValue().intValue() + 1);
			sequenceValueBean.setLastValue(value);
		}
		String geneValue = generateSeqValue(propertyBean.getSequence(), userContext, value.intValue(), bean, context);
		Class<?> returnClass = null;
		try {
			returnClass = PropertyUtils.getPropertyType(bean, propertyBean.getPropertyName());
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
		return transformValue(geneValue, returnClass);
	}
	
	private static List<Serializable> generate(@SuppressWarnings("rawtypes") List beans, UserContext userContext, DbPersistantSupport dbPersistantSupport, PropertyBean propertyBean,
			Map<String, Object> context) throws DaoFunctionalException {
		if (propertyBean.getSequence() == null)
			return null;
		if (beans.size() == 0)
			return null;
		String sequenceValueId = getVersionValue(propertyBean.getSequence(), beans.get(0), userContext, context);
		SequenceValueBeanId sequenceValueBeanId = new SequenceValueBeanId(sequenceValueId, propertyBean.getSequenceId());
		SequenceValueBean sequenceValueBean = (SequenceValueBean) dbPersistantSupport.get(SequenceValueBean.class, sequenceValueBeanId, LockMode.UPGRADE);
		Long value;
		if (sequenceValueBean == null) {
			sequenceValueBean = new SequenceValueBean();
			sequenceValueBean.setId(sequenceValueBeanId);
			sequenceValueBean.setLastValue(new Long(beans.size() + 1));
			dbPersistantSupport.save(sequenceValueBean);
			value = new Long(1);
		} else {
			value = new Long(sequenceValueBean.getLastValue().intValue() + 1);
			sequenceValueBean.setLastValue(new Long(sequenceValueBean.getLastValue().intValue() + beans.size() + 1));
		}
		Class<?> returnClass = null;
		try {
			returnClass = PropertyUtils.getPropertyType(beans.get(0), propertyBean.getPropertyName());
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
		List<Serializable> listId = new ArrayList<Serializable>();
		for (int i = 0; i < beans.size(); i++) {
			String geneValue = generateSeqValue(propertyBean.getSequence(), userContext, value.intValue(), beans.get(i), context);
			value = new Long(value.intValue() + 1);
			listId.add(transformValue(geneValue, returnClass));
		}
		return listId;
	}
	
	public static String generateSeqValue(SequenceBean sequenceBean, UserContext userContext, int seqValue, Object bean, Map<String, Object> context) {
		String resultat;
		String valueExpression = sequenceBean.getValueExpression();
		seqValue = seqValue < sequenceBean.getStartNumber().intValue() ? sequenceBean.getStartNumber().intValue() : seqValue;
		Map<String, Object> root = new HashMap<String, Object>();
		if (context != null)
			root.putAll(context);
		if (!root.containsKey(DEFAULT_REGLE_ANNEE))
			root.put(DEFAULT_REGLE_ANNEE, prepareValue(DateUtils.getAnnees(DateUtils.getCourantDate()), 4));
		if (!root.containsKey(DEFAULT_REGLE_MOIS))
			root.put(DEFAULT_REGLE_MOIS, prepareValue(DateUtils.getMois(DateUtils.getCourantDate()), 2));
		if (!root.containsKey(DEFAULT_REGLE_JOUR))
			root.put(DEFAULT_REGLE_JOUR, prepareValue(DateUtils.getJours(DateUtils.getCourantDate()), 2));
		if (!root.containsKey(DEFAULT_REGLE_BEAN))
			root.put(DEFAULT_REGLE_BEAN, bean);
		if (!root.containsKey(DEFAULT_REGLE_SEQ))
			root.put(DEFAULT_REGLE_SEQ, prepareValue(seqValue, sequenceBean.getLengthNumber().intValue()));
		if (userContext != null && !root.containsKey(DEFAULT_REGLE_ORGANISATION))
			root.put(DEFAULT_REGLE_ORGANISATION, userContext.getCurrentUserOrganismeId());
		if (userContext != null && !root.containsKey(DEFAULT_REGLE_SOCIETE))
			root.put(DEFAULT_REGLE_SOCIETE, userContext.getCurrentUserSocieteId());
		if (userContext != null && !root.containsKey(DEFAULT_REGLE_USER))
			root.put(DEFAULT_REGLE_USER, userContext.userId());
		Object exp = OgnlUtil.parseExpression(valueExpression);
		resultat = String.valueOf(OgnlUtil.getValue(exp, root));
		return resultat;
	}
	
	public static String getVersionValue(SequenceBean sequenceBean, Object bean, UserContext userContext, Map<String, Object> context) {
		String resultat = "";
		Map<String, Object> root = new HashMap<String, Object>();
		if (context != null)
			root.putAll(context);
		if (!root.containsKey(DEFAULT_REGLE_ANNEE))
			root.put(DEFAULT_REGLE_ANNEE, prepareValue(DateUtils.getAnnees(DateUtils.getCourantDate()), 4));
		if (!root.containsKey(DEFAULT_REGLE_MOIS))
			root.put(DEFAULT_REGLE_MOIS, prepareValue(DateUtils.getMois(DateUtils.getCourantDate()), 2));
		if (!root.containsKey(DEFAULT_REGLE_JOUR))
			root.put(DEFAULT_REGLE_JOUR, prepareValue(DateUtils.getJours(DateUtils.getCourantDate()), 2));
		if (!root.containsKey(DEFAULT_REGLE_BEAN))
			root.put(DEFAULT_REGLE_BEAN, bean);
		if (userContext != null && !root.containsKey(DEFAULT_REGLE_ORGANISATION))
			root.put(DEFAULT_REGLE_ORGANISATION, userContext.getCurrentUserOrganismeId());
		if (userContext != null && !root.containsKey(DEFAULT_REGLE_SOCIETE))
			root.put(DEFAULT_REGLE_SOCIETE, userContext.getCurrentUserSocieteId());
		if (userContext != null && !root.containsKey(DEFAULT_REGLE_USER))
			root.put(DEFAULT_REGLE_USER, userContext.userId());
		if (StringUtils.isNotBlank(sequenceBean.getValueGroupExpression())) {
			Object exp = OgnlUtil.parseExpression(sequenceBean.getValueGroupExpression());
			resultat = String.valueOf(OgnlUtil.getValue(exp, root));
		}
		return resultat;
	}
	
	private static String prepareValue(int seqValue, int p) {
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
	
	public static Serializable transformValue(String geneValue, Class<?> returnClass) {
		if (returnClass == Long.class) {
			return new Long(geneValue);
		} else if (returnClass == Integer.class) {
			return new Integer(geneValue);
		} else if (returnClass == Short.class) {
			return new Short(geneValue);
		} else if (returnClass == String.class) {
			return String.valueOf(geneValue);
		} else {
			throw new IdentifierGenerationException("this id generator generates long, integer, short");
		}
	}
}
