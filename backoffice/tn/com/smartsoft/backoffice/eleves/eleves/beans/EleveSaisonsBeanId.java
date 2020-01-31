package tn.com.smartsoft.backoffice.eleves.eleves.beans;

import java.io.Serializable;
import tn.com.smartsoft.commons.utils.ValueUtils;

public class EleveSaisonsBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codePermanent;
	private Long eleveSaisonId;

	public EleveSaisonsBeanId() {
		super();
	}

	public EleveSaisonsBeanId(EleveSaisonsBean eleveSaisonsBean) {
		super();
		this.setCodePermanent(eleveSaisonsBean.getCodePermanent());
		this.setEleveSaisonId(eleveSaisonsBean.getEleveSaisonId());
	}

	public void copyValue(EleveSaisonsBean objectValue) {
		objectValue.setCodePermanent(this.getCodePermanent());
		objectValue.setEleveSaisonId(this.getEleveSaisonId());

	}

	public String getCodePermanent() {
		return this.codePermanent;
	}

	public void setCodePermanent(String codePermanent) {
		this.codePermanent = codePermanent;
	}

	public Long getEleveSaisonId() {
		return this.eleveSaisonId;
	}

	public void setEleveSaisonId(Long eleveSaisonId) {
		this.eleveSaisonId = eleveSaisonId;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getCodePermanent() == null ? 0 : this.getCodePermanent().hashCode());
		result = 37 * result + (this.getEleveSaisonId() == null ? 0 : this.getEleveSaisonId().hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EleveSaisonsBeanId))
			return false;
		EleveSaisonsBeanId castOther = (EleveSaisonsBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getCodePermanent(), castOther.getCodePermanent());
		result = result && ValueUtils.equals(this.getEleveSaisonId(), castOther.getEleveSaisonId());
		return result;
	}
}