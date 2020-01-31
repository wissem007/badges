package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtTimeField;

public class UIExtTimeFieldRender extends UIExtTextFieldRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtTimeFieldRender(UIExtTimeField renderComponent) {
		super(renderComponent, "Ext.form.TimeField");
	}

	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtTimeField extTimeField = (UIExtTimeField) renderComponent;
		extComponentJs().addObjectValue("maxValue", extTimeField.getMaxValue());
		extComponentJs().addObjectValue("minValue", extTimeField.getMinValue());
		extComponentJs().addStringValue("format", extTimeField.getFormat());
		extComponentJs().addStringValue("maxText", extTimeField.getMessageMaxValueKey());
		extComponentJs().addStringValue("minText", extTimeField.getMessageMinValueKey());
		
	}
}
