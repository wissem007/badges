package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtFileFieldRender;

public class UIExtFileField extends UIExtTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String emptyText = "Select an Fichier";
	private String buttonText = "";
	private String iconCls = "upload-icon";

	public UIExtFileField() {
		super();
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public String getEmptyText() {
		return emptyText;
	}

	public void setEmptyText(String emptyText) {
		this.emptyText = emptyText;
	}

	public UIRender getRender() {
		return new UIExtFileFieldRender(this);
	}
}
