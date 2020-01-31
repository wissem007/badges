package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtFieldTrigger;

public class UIExtFieldTriggerRender extends UIRender {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JsTable triggersConfig;
	
	public UIExtFieldTriggerRender(UIExtFieldTrigger renderComponent) {
		super(renderComponent);
	}
	
	public void render(UIRenderContext context) {
		UIExtFieldTrigger fieldTrigger = (UIExtFieldTrigger) renderComponent;
		if (!fieldTrigger.isRendred())
			return;
		JsMap jsTrigger = new JsMap();
		jsTrigger.addStringValue("cls", fieldTrigger.getIconCls());
		jsTrigger.addStringValue("qtip", fieldTrigger.getQtip());
		jsTrigger.addStringValue("hideTrigger", fieldTrigger.getHideTrigger());
		String[] atts = fieldTrigger.geAttributesName();
		for (int i = 0; i < atts.length; i++) {
			String value = fieldTrigger.getAttribut(atts[i]);
			if (StringUtils.isNotBlank(value))
				jsTrigger.addValue(atts[i], value);
		}
		if (triggersConfig != null)
			triggersConfig.addObjectValue(jsTrigger.toJs());
	}
	
	public void setTriggersConfig(JsTable triggersConfig) {
		this.triggersConfig = triggersConfig;
	}
	
}
