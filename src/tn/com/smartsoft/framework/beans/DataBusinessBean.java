package tn.com.smartsoft.framework.beans;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import tn.com.smartsoft.commons.utils.ConverterUtil;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.configGenerale.organisationSociete.societe.beans.SocieteBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserBean;

public abstract class DataBusinessBean implements IdentifiableBean,BeanObject{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private Timestamp			createdDate;
	private UserBean			createdBy;
	private UserBean			updatedBy;
	private String				createdByName;
	private String				updatedByName;
	private Timestamp			updatedDate;
	private String				createdById;
	private String				updatedById;
	private Long				parentOrganismeId;
	private OrganismeBean		parentOrganisme;
	protected Long				parentSocieteId;
	private SocieteBean			parentSociete;
	private Boolean				etatBusiness		= Boolean.FALSE;
	private Map<String, Object>	dynamiqueValue		= new HashMap<String, Object>();
	
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String getUpdatedByName() {
		return updatedByName;
	}
	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}
	public String getCreatedById() {
		return createdById;
	}
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	public String getUpdatedById() {
		return updatedById;
	}
	public Boolean getEtatBusiness() {
		return etatBusiness;
	}
	public void setEtatBusiness(Boolean etatBusiness) {
		this.etatBusiness = etatBusiness;
	}
	public void setUpdatedById(String updatedById) {
		this.updatedById = updatedById;
	}
	public UserBean getCreatedBy() {
		return createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public void setCreatedBy(UserBean createdBy) {
		this.createdBy = createdBy;
	}
	public UserBean getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(UserBean updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Long getParentOrganismeId() {
		return parentOrganismeId;
	}
	public void setParentOrganismeId(Long parentOrganismeId) {
		this.parentOrganismeId = parentOrganismeId;
	}
	public OrganismeBean getParentOrganisme() {
		return parentOrganisme;
	}
	public void setParentOrganisme(OrganismeBean parentOrganisme) {
		this.parentOrganisme = parentOrganisme;
	}
	public Long getParentSocieteId() {
		return parentSocieteId;
	}
	public void setParentSocieteId(Long parentSocieteId) {
		this.parentSocieteId = parentSocieteId;
	}
	public SocieteBean getParentSociete() {
		return parentSociete;
	}
	public void setParentSociete(SocieteBean parentSociete) {
		this.parentSociete = parentSociete;
	}
	public void setDynamiqueValue(Map<String, Object> dynamiqueValue) {
		this.dynamiqueValue = dynamiqueValue;
	}
	public void setDynamicPropertyValue(String property, Object value) {
		if (value == null) getDynamiqueValue().remove(property);
		else getDynamiqueValue().put(property, value);
	}
	public String[] getDynamicPropertyNames() {
		Set<String> keySet = getDynamiqueValue().keySet();
		String[] ns = new String[getDynamiqueValue().size()];
		return keySet.toArray(ns);
	}
	public Object getDynamicPropertyValue(String property) {
		return getDynamiqueValue().get(property);
	}
	public void setSimpleDynamiqueValue(Map<String, Object> simpleDynamiqueValue) {
		if (simpleDynamiqueValue != null) getDynamiqueValue().putAll(simpleDynamiqueValue);
	}
	public Map<String, Object> getSimpleDynamiqueValue() {
		HashMap<String, Object> sdynamiqueValue = new HashMap<String, Object>();
		if (dynamiqueValue == null) return sdynamiqueValue;
		Set<String> keySet = dynamiqueValue.keySet();
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			String key = iterator.next();
			Object value = dynamiqueValue.get(key);
			if (ConverterUtil.isFiledType(value.getClass())) sdynamiqueValue.put(key, value.toString());
		}
		return sdynamiqueValue;
	}
	public Map<String, Object> getDynamiqueValue() {
		if (dynamiqueValue == null) dynamiqueValue = new HashMap<String, Object>();
		return dynamiqueValue;
	}
}
