package tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.beans;

import java.io.Serializable;
import tn.com.smartsoft.commons.utils.ValueUtils;

public class EleveDemandeCartesBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long eleveDemandeCarteId;
	private Long eleveSaisonId;

	public EleveDemandeCartesBeanId() {
		super();
	}

	public EleveDemandeCartesBeanId(EleveDemandeCartesBean eleveDemandeCartesBean) {
		super();
		this.setEleveDemandeCarteId(eleveDemandeCartesBean.getEleveDemandeCarteId());
		this.setEleveSaisonId(eleveDemandeCartesBean.getEleveSaisonId());
	}

	public void copyValue(EleveDemandeCartesBean objectValue) {
		objectValue.setEleveDemandeCarteId(this.getEleveDemandeCarteId());
		objectValue.setEleveSaisonId(this.getEleveSaisonId());

	}

	public Long getEleveDemandeCarteId() {
		return this.eleveDemandeCarteId;
	}

	public void setEleveDemandeCarteId(Long eleveDemandeCarteId) {
		this.eleveDemandeCarteId = eleveDemandeCarteId;
	}

	public Long getEleveSaisonId() {
		return this.eleveSaisonId;
	}

	public void setEleveSaisonId(Long eleveSaisonId) {
		this.eleveSaisonId = eleveSaisonId;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getEleveDemandeCarteId() == null ? 0 : this.getEleveDemandeCarteId().hashCode());
		result = 37 * result + (this.getEleveSaisonId() == null ? 0 : this.getEleveSaisonId().hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EleveDemandeCartesBeanId))
			return false;
		EleveDemandeCartesBeanId castOther = (EleveDemandeCartesBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getEleveDemandeCarteId(), castOther.getEleveDemandeCarteId());
		result = result && ValueUtils.equals(this.getEleveSaisonId(), castOther.getEleveSaisonId());
		return result;
	}
}