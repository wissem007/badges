package tn.com.smartsoft.iap.dictionary.decoupage.application.beans;

import java.io.Serializable;

import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.administration.referentiel.file.beans.FileBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;

public class ApplicationBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long applicationId;
	private String moduleBaseId;
	private Long deviseId;
	private Long paysId;
	private Long fileId;
	private String systemModuleId;
	private String version;
	private FileBean logoFile;
	private DeviseBean deviseBase;
	private ModuleBean moduleBase;
	private ModuleBean moduleystem;
	private PayBean payBean;

	public ApplicationBean() {
		super();
	}

	public Serializable getId() {
		return this.getApplicationId();
	}

	public void setId(Long id) {
		this.setApplicationId(id);
	}

	public Long getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getModuleBaseId() {
		return this.moduleBaseId;
	}

	public void setModuleBaseId(String moduleBaseId) {
		this.moduleBaseId = moduleBaseId;
	}

	public Long getDeviseId() {
		return this.deviseId;
	}

	public void setDeviseId(Long deviseId) {
		this.deviseId = deviseId;
	}

	public Long getFileId() {
		return this.fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getSystemModuleId() {
		return this.systemModuleId;
	}

	public void setSystemModuleId(String systemModuleId) {
		this.systemModuleId = systemModuleId;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public FileBean getLogoFile() {
		return this.logoFile;
	}

	public void setLogoFile(FileBean logoFile) {
		this.logoFile = logoFile;
	}

	public DeviseBean getDeviseBase() {
		return this.deviseBase;
	}

	public void setDeviseBase(DeviseBean deviseBase) {
		this.deviseBase = deviseBase;
	}

	public ModuleBean getModuleBase() {
		return this.moduleBase;
	}

	public void setModuleBase(ModuleBean moduleBase) {
		this.moduleBase = moduleBase;
	}

	public ModuleBean getModuleystem() {
		return this.moduleystem;
	}

	public void setModuleystem(ModuleBean moduleystem) {
		this.moduleystem = moduleystem;
	}

	public Long getPaysId() {
		return paysId;
	}

	public void setPaysId(Long paysId) {
		this.paysId = paysId;
	}

	public PayBean getPayBean() {
		return payBean;
	}

	public void setPayBean(PayBean payBean) {
		this.payBean = payBean;
	}

}