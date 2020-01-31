package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.UIExtFieldSet;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtPanelRender;

public class UIExtFieldSetRender extends UIExtPanelRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtFieldSetRender(UIExtFieldSet renderComponent) {
		super(renderComponent, "Ext.form.FieldSet");
	}

	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
	}
}
