package tn.com.digivoip.framework.presentation.contoler;

import java.io.Serializable;

public class GenericEntiteControlerParams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String listWindowId;
	protected String filterWindowId;
	protected String detailWindowId;
	protected String displayGridId;
	protected String listLibelleId;
	protected String messageCreateValid;
	protected String messageDeleteValid;
	protected String messageUpdateValid;
	protected String messageEmptyRow;

	public String getMessageEmptyRow() {
		return messageEmptyRow;
	}

	public void setMessageEmptyRow(String messageEmptyRow) {
		this.messageEmptyRow = messageEmptyRow;
	}

	public String getListWindowId() {
		return listWindowId;
	}

	public String getFilterWindowId() {
		return filterWindowId;
	}

	public String getDetailWindowId() {
		return detailWindowId;
	}

	public String getDisplayGridId() {
		return displayGridId;
	}

	public String getListLibelleId() {
		return listLibelleId;
	}

	public String getMessageCreateValid() {
		return messageCreateValid;
	}

	public String getMessageDeleteValid() {
		return messageDeleteValid;
	}

	public String getMessageUpdateValid() {
		return messageUpdateValid;
	}

	public void setListWindowId(String listWindowId) {
		this.listWindowId = listWindowId;
	}

	public void setFilterWindowId(String filterWindowId) {
		this.filterWindowId = filterWindowId;
	}

	public void setDetailWindowId(String detailWindowId) {
		this.detailWindowId = detailWindowId;
	}

	public void setDisplayGridId(String displayGridId) {
		this.displayGridId = displayGridId;
	}

	public void setMessageDeleteValid(String messageDeleteValid) {
		this.messageDeleteValid = messageDeleteValid;
	}

	public void setMessageUpdateValid(String messageUpdateValid) {
		this.messageUpdateValid = messageUpdateValid;
	}

	public void setListLibelleId(String listLibelleId) {
		this.listLibelleId = listLibelleId;
	}

	public void setMessageCreateValid(String messageCreateValid) {
		this.messageCreateValid = messageCreateValid;
	}

}
