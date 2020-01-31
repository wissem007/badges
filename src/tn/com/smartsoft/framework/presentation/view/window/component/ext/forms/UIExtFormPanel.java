package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtPanel;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.render.UIExtFormPanelRender;

public class UIExtFormPanel extends UIExtPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String monitorValid = "monitorValid";
	private Boolean standardSubmit = Boolean.TRUE;
	private Integer minButtonWidth;

	public int getMinButtonWidth() {
		return minButtonWidth;
	}

	public void setMinButtonWidth(int minButtonWidth) {
		this.minButtonWidth = minButtonWidth;
	}

	public UIExtFormPanel() {
		super();
	}

	public String getMonitorValid() {
		return monitorValid;
	}

	public void setMonitorValid(String monitorValid) {
		this.monitorValid = monitorValid;
	}

	public boolean isStandardSubmit() {
		return standardSubmit;
	}

	public void setStandardSubmit(Boolean standardSubmit) {
		this.standardSubmit = standardSubmit;
	}

	public UIRender getRender() {
		return new UIExtFormPanelRender(this);
	}

}
