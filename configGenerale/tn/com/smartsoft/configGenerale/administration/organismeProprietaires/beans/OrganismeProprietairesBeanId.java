package tn.com.smartsoft.configGenerale.administration.organismeProprietaires.beans;

import java.io.Serializable;
import tn.com.smartsoft.commons.utils.ValueUtils;

public class OrganismeProprietairesBeanId implements Serializable{
	
	private static final long	serialVersionUID	= 1L;
	private Long				organismeId;
	private Long				societeId;
	
	public OrganismeProprietairesBeanId() {
		super();
	}
	public OrganismeProprietairesBeanId(Long organismeId, Long societeId) {
		super();
		this.organismeId = organismeId;
		this.societeId = societeId;
	}
	public OrganismeProprietairesBeanId(OrganismeProprietairesBean organismeProprietairesBean) {
		super();
		this.setOrganismeId(organismeProprietairesBean.getOrganismeId());
		this.setSocieteId(organismeProprietairesBean.getSocieteId());
	}
	public void copyValue(OrganismeProprietairesBean objectValue) {
		objectValue.setOrganismeId(this.getOrganismeId());
		objectValue.setSocieteId(this.getSocieteId());
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
	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getOrganismeId() == null ? 0 : this.getOrganismeId().hashCode());
		result = 37 * result + (this.getSocieteId() == null ? 0 : this.getSocieteId().hashCode());
		return result;
	}
	public boolean equals(Object other) {
		if ((this == other)) return true;
		if ((other == null)) return false;
		if (!(other instanceof OrganismeProprietairesBeanId)) return false;
		OrganismeProprietairesBeanId castOther = (OrganismeProprietairesBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getOrganismeId(), castOther.getOrganismeId());
		result = result && ValueUtils.equals(this.getSocieteId(), castOther.getSocieteId());
		return result;
	}
}