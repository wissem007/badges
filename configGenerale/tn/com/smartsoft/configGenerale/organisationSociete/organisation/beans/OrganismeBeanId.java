package tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans;

import java.io.Serializable;

import tn.com.smartsoft.commons.utils.ValueUtils;

public class OrganismeBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long organismeId;
	private Long societeId;

	public OrganismeBeanId() {
		super();
	}

	public OrganismeBeanId(OrganismeBean organismeBean) {
		super();
		this.setOrganismeId(organismeBean.getOrganismeId());
		this.setSocieteId(organismeBean.getSocieteId());
	}

	public OrganismeBeanId(Long organismeId, Long societeId) {
		super();
		this.organismeId = organismeId;
		this.societeId = societeId;
	}

	public void copyValue(OrganismeBean objectValue) {
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
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OrganismeBeanId))
			return false;
		OrganismeBeanId castOther = (OrganismeBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getOrganismeId(), castOther.getOrganismeId());
		result = result && ValueUtils.equals(this.getSocieteId(), castOther.getSocieteId());
		return result;
	}
}