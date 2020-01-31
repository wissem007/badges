package tn.com.smartsoft.iap.dictionary.decoupage.module.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModuleBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String moduleId;
	private String libelle;
	private Long rang;
	private String version;
	private String help;
	private String iconUrl;
	private Long type = new Long(1);
	private Boolean activate = Boolean.TRUE;
	private List<DataBusinessBean> listPermissons;
	private Boolean activateAndType = Boolean.FALSE;
	private Boolean activateAndTypeOne = Boolean.FALSE;
	
	public List<DataBusinessBean> getListPermissons() {
		if (listPermissons == null)
			listPermissons = new ArrayList<DataBusinessBean>();
		return listPermissons;
	}
	
	public void setListPermissons(List<DataBusinessBean> listPermissons) {
		this.listPermissons = listPermissons;
	}
	
	public Boolean getActivateAndType() {
		return activateAndType;
	}
	
	public void setActivateAndType(Boolean activateAndType) {
		this.activateAndType = activateAndType;
	}
	
	public ModuleBean() {
		super();
	}
	
	public Long getType() {
		return type;
	}
	
	public void setType(Long type) {
		this.type = type;
	}
	
	public Boolean getActivate() {
		return activate;
	}
	
	public void setActivate(Boolean activate) {
		this.activate = activate;
	}
	
	public Serializable getId() {
		return this.getModuleId();
	}
	
	public void setId(String id) {
		this.setModuleId(id);
	}
	
	public String getModuleId() {
		return this.moduleId;
	}
	
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	public String getLibelle() {
		return this.libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public Long getRang() {
		return this.rang;
	}
	
	public void setRang(Long rang) {
		this.rang = rang;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getHelp() {
		return this.help;
	}
	
	public void setHelp(String help) {
		this.help = help;
	}
	
	public String getIconUrl() {
		return this.iconUrl;
	}
	
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public void setActivateAndTypeOne(Boolean activateAndTypeOne) {
		this.activateAndTypeOne = activateAndTypeOne;
	}

	public Boolean getActivateAndTypeOne() {
		return activateAndTypeOne;
	}
}