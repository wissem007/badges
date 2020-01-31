package tn.com.digivoip.framework.business;

import java.io.Serializable;
import tn.com.smartsoft.framework.configuration.ComponentId;

public class GenericEntiteBusinessParams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ComponentId createActionId;
	protected ComponentId updateActionId;
	protected ComponentId deleteActionId;
	protected ComponentId getByCriteriaActionId;

	public ComponentId getCreateActionId() {
		return createActionId;
	}

	public ComponentId getUpdateActionId() {
		return updateActionId;
	}

	public ComponentId getDeleteActionId() {
		return deleteActionId;
	}

	public ComponentId getGetByCriteriaActionId() {
		return getByCriteriaActionId;
	}

	public void setCreateActionId(ComponentId createActionId) {
		this.createActionId = createActionId;
	}

	public void setUpdateActionId(ComponentId updateActionId) {
		this.updateActionId = updateActionId;
	}

	public void setDeleteActionId(ComponentId deleteActionId) {
		this.deleteActionId = deleteActionId;
	}

	public void setGetByCriteriaActionId(ComponentId getByCriteriaActionId) {
		this.getByCriteriaActionId = getByCriteriaActionId;
	}
}
