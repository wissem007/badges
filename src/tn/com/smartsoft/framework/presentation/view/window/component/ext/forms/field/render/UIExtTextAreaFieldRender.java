package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtTextAreaField;

public class UIExtTextAreaFieldRender extends UIExtTextFieldRender{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public UIExtTextAreaFieldRender(UIExtTextAreaField renderComponent, boolean isHtml) {
		super(renderComponent, isHtml ? "Ext.form.HtmlEditor" : "Ext.form.TextArea");
	}
	public void loadConfigs(UIRenderContext context) {
		UIExtTextAreaField extTextAreaField = (UIExtTextAreaField) renderComponent;
		super.loadConfigs(context);
		if (extTextAreaField.getIsHtml()) extComponentJs().addObjectValue("plugins", "initHtmlEditorPlugins()");
		extComponentJs().addBooleanValue("disabled", false);
	}
}
