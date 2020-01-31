package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtFileField;

public class UIExtFileFieldRender extends UIExtTextFieldRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtFileFieldRender(UIExtFileField renderComponent) {
		super(renderComponent, "Ext.ux.form.FileUploadField");
	}

	public void loadConfigs(UIRenderContext context) {
		UIExtFileField extFileField = (UIExtFileField) renderComponent;
		super.loadConfigs(context);

		extComponentJs().addStringValue("emptyText", extFileField.getEmptyText());
		extComponentJs().addStringValue("buttonText", extFileField.getButtonText());
		extComponentJs().addObjectValue("buttonCfg", "{iconCls : \"" + extFileField.getIconCls() + "\"}");

	}
}
