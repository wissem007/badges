package tn.com.smartsoft.iap.dictionary.decoupage.subModule.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import java.io.Serializable;

public class SubModuleBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String subModuleId;
	private String moduleId;
	private String libelle;
	private Long rang;
	private String help;
	private ModuleBean parentModule;

	public SubModuleBean() {
		super();
	}

	public Serializable getId() {
		return new SubModuleBeanId(this);
	}

	public void setId(SubModuleBeanId id) {
		id.copyValue(this);
	}

	public String getSubModuleId() {
		return this.subModuleId;
	}

	public void setSubModuleId(String subModuleId) {
		this.subModuleId = subModuleId;
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

	public String getHelp() {
		return this.help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public ModuleBean getParentModule() {
		return this.parentModule;
	}

	public void setParentModule(ModuleBean parentModule) {
		this.parentModule = parentModule;
	}
}