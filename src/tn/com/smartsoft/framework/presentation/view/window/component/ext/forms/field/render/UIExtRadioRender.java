package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtRadio;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtComponentRender;

public class UIExtRadioRender extends UIExtComponentRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtRadioRender(UIExtRadio renderComponent) {
		super(renderComponent, "Ext.form.Radio");
	}

	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtRadio radio = (UIExtRadio) renderComponent;
		extComponentJs().addStringValue("name", radio.getName());
		extComponentJs().addObjectValue("checked", radio.isChecked());
		extComponentJs().addStringValue("inputValue", radio.getInputValue());
		extComponentJs().addStringValue("boxLabel", radio.getBoxLabel());
	}

	public void renderChilds(UIRenderContext context) {
		// TODO Auto-generated method stub

	}

}
