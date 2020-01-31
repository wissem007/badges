package tn.com.smartsoft.configGenerale.dynamicEntite.entiteSimple.beans;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.utils.BeanObjectUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBeanId;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class DataValuesBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String dataValueId;
	private String entiteId;
	private String moduleId;
	private String subModuleId;
	private String sujetId;
	private String libelle;
	private String abreviation;
	private Long ordre;
	private Long organismeId;
	private Long societeId;
	private DataBusinessBean refDataBusinessBean;
	private String libelleEntiteRefExp;
	
	public DataValuesBean() {
		super();
	}
	
	public DataValuesBean(EntiteBeanId entiteBeanId) {
		super();
		this.entiteId = entiteBeanId.getEntiteId();
		this.sujetId = entiteBeanId.getSujetId();
		this.subModuleId = entiteBeanId.getSubModuleId();
		this.moduleId = entiteBeanId.getModuleId();
	}
	
	public void loadFromRefBean(DataBusinessBean dataBusinessBean, String libelleExp) {
		this.setDataValueId(new JSONSerializer().exclude("class").serialize(dataBusinessBean.getId()));
		this.setLibelle(String.valueOf(BeanObjectUtils.getPropertyValue(dataBusinessBean, libelleExp)));
		this.setRefDataBusinessBean(dataBusinessBean);
	}
	
	public Serializable creatIdRefBean(Class<?> refBeanIdClass) {
		return (Serializable) new JSONDeserializer<Object>().deserialize(this.getDataValueId(), refBeanIdClass);
	}
	
	public Serializable getId() {
		return new DataValuesBeanId(this);
	}
	
	public Long getOrganismeId() {
		return organismeId;
	}
	
	public void setOrganismeId(Long organismeId) {
		this.organismeId = organismeId;
	}
	
	public Long getSocieteId() {
		return societeId;
	}
	
	public void setSocieteId(Long societeId) {
		this.societeId = societeId;
	}
	
	public DataBusinessBean getRefDataBusinessBean() {
		return refDataBusinessBean;
	}
	
	public void setRefDataBusinessBean(DataBusinessBean refDataBusinessBean) {
		this.refDataBusinessBean = refDataBusinessBean;
	}
	
	public void setId(DataValuesBeanId id) {
		id.copyValue(this);
	}
	
	public String getDataValueId() {
		return this.dataValueId;
	}
	
	public void setDataValueId(String dataValueId) {
		this.dataValueId = dataValueId;
	}
	
	public String getEntiteId() {
		return this.entiteId;
	}
	
	public void setEntiteId(String entiteId) {
		this.entiteId = entiteId;
	}
	
	public String getModuleId() {
		return this.moduleId;
	}
	
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	public String getSubModuleId() {
		return this.subModuleId;
	}
	
	public void setSubModuleId(String subModuleId) {
		this.subModuleId = subModuleId;
	}
	
	public String getSujetId() {
		return this.sujetId;
	}
	
	public void setSujetId(String sujetId) {
		this.sujetId = sujetId;
	}
	
	public String getLibelle() {
		return this.libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public String getAbreviation() {
		return this.abreviation;
	}
	
	public void setAbreviation(String abreviation) {
		this.abreviation = abreviation;
	}
	
	public Long getOrdre() {
		return this.ordre;
	}
	
	public EntiteBeanId getRefEntiteId() {
		return new EntiteBeanId(entiteId, sujetId, subModuleId, moduleId);
	}
	
	public String getLibelleEntiteRefExp() {
		if (StringUtils.isBlank(libelleEntiteRefExp))
			return "libelle";
		return libelleEntiteRefExp;
	}
	
	public void setLibelleEntiteRefExp(String libelleEntiteRefExp) {
		this.libelleEntiteRefExp = libelleEntiteRefExp;
	}
	
	public void setOrdre(Long ordre) {
		this.ordre = ordre;
	}
}