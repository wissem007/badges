package tn.com.smartsoft.iap.dictionary.graphique.actionTemplate.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import java.io.Serializable;

public class ActionTemplateBean extends DataBusinessBean{

	private static final long	serialVersionUID	= 1L;
	private Long				actionTemplateId;
	private String				libelle;
	private String				iconUrl;
	private String				shortCutValue;
	private Boolean				isLabel;

	public ActionTemplateBean() {
		super();
	}
	public Boolean getIsLabel() {
		return isLabel;
	}
	public void setIsLabel(Boolean isLabel) {
		this.isLabel = isLabel;
	}
	public Serializable getId() {
		return this.getActionTemplateId();
	}
	public void setId(Long id) {
		this.setActionTemplateId(id);
	}
	public Long getActionTemplateId() {
		return this.actionTemplateId;
	}
	public void setActionTemplateId(Long actionTemplateId) {
		this.actionTemplateId = actionTemplateId;
	}
	public String getLibelle() {
		return this.libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getIconUrl() {
		return this.iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getShortCutValue() {
		return this.shortCutValue;
	}
	public void setShortCutValue(String shortCutValue) {
		this.shortCutValue = shortCutValue;
	}
}