package tn.com.smartsoft.configGenerale.administration.organismeProprietaires.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;

public class OrganismeProprietairesBean extends DataBusinessBean {
	
	private static final long		serialVersionUID	= 1L;
	private Long					organismeId;
	private Long					societeId;
	private String					displayName;
	private OrganismeBean			organisme;
	private List<DataBusinessBean>	modules				= new ArrayList<DataBusinessBean>();
	private Set<String>				autModules			= new HashSet<String>();
	
	public OrganismeProprietairesBean(Long organismeId, Long societeId) {
		super();
		this.organismeId = organismeId;
		this.societeId = societeId;
	}
	
	public OrganismeProprietairesBean(Long organismeId, Long societeId, String displayName) {
		super();
		this.organismeId = organismeId;
		this.societeId = societeId;
		this.displayName = displayName;
		this.organisme = new OrganismeBean(organismeId, societeId, displayName);
	}
	
	public OrganismeProprietairesBean(OrganismeBean organisme) {
		super();
		this.organismeId = organisme.getOrganismeId();
		this.societeId = organisme.getSocieteId();
		this.displayName = organisme.getLibelle();
		this.organisme = organisme;
	}
	
	public OrganismeProprietairesBean() {
		super();
	}
	
	public ModuleBean getModules(int index) {
		return (ModuleBean) modules.get(index);
	}
	
	public Serializable getId() {
		return new OrganismeProprietairesBeanId(this);
	}
	
	public void setId(OrganismeProprietairesBeanId id) {
		id.copyValue(this);
	}
	
	public Long getOrganismeId() {
		return this.organismeId;
	}
	
	public void setOrganismeId(Long organismeId) {
		this.organismeId = organismeId;
	}
	
	public Long getSocieteId() {
		return this.societeId;
	}
	
	public void setSocieteId(Long societeId) {
		this.societeId = societeId;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public OrganismeBean getOrganisme() {
		return this.organisme;
	}
	
	public void setOrganisme(OrganismeBean organisme) {
		this.organisme = organisme;
	}
	
	public List<DataBusinessBean> getModules() {
		return this.modules;
	}
	
	public void setModules(List<DataBusinessBean> modules) {
		this.modules = modules;
	}
	
	public Set<String> getAutModules() {
		if (autModules == null)
			autModules = new HashSet<String>();
		return autModules;
	}
	
	public void setAutModules(Set<String> autModules) {
		this.autModules = autModules;
	}
}