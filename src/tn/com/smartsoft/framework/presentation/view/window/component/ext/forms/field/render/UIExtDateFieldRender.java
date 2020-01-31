package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtDateField;

public class UIExtDateFieldRender extends UIExtTextFieldRender {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UIExtDateFieldRender(UIExtDateField renderComponent) {
		super(renderComponent, "Ext.form.DateField");
	}
	
	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		 
		extComponentJs().addStringValue("emptyText", "__/__/____");
		extComponentJs().addObjectValue("plugins", "[new Ext.sss.InputTextMask('X[0|1|2|3]X9/X[0|1]X9/9999', true)]");
	}
}
