package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import java.util.List;
import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtFieldTrigger;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtTriggerField;

public class UIExtTriggerFieldRender extends UIExtTextFieldRender{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private JsTable				triggersConfig;

	public UIExtTriggerFieldRender(UIExtTriggerField renderComponent) {
		super(renderComponent, "Ext.sss.TriggerField");
	}
	public void afterRender(UIRenderContext context) {
		super.afterRender(context);
		UIExtTriggerField extTriggerField = (UIExtTriggerField) renderComponent;
		List<String> columnIds = extTriggerField.getFieldTriggerIds();
		triggersConfig = new JsTable();
		for (int i = 0; i < columnIds.size(); i++) {
			UIExtFieldTrigger trigger = extTriggerField.getFieldTrigger(columnIds.get(i));
			UIExtFieldTriggerRender triggerRender = (UIExtFieldTriggerRender) trigger.getRender();
			triggerRender.setParentRender(this);
			triggerRender.setTriggersConfig(triggersConfig);
			triggerRender.render(context);
		}
	}
	public void loadConfigs(UIRenderContext context) {
		UIExtTriggerField extTriggerField = (UIExtTriggerField) renderComponent;
		super.loadConfigs(context);
		extComponentJs().addObjectValue("triggersConfig", triggersConfig.toJs());
		extComponentJs().addObjectValue("onCustomTriggerClick", extTriggerField.getOnCustomTriggerClick());
	}
}
