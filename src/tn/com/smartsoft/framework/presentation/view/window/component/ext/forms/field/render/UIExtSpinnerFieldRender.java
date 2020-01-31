package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtSpinnerField;

public class UIExtSpinnerFieldRender extends UIExtNumberFieldRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtSpinnerFieldRender(UIExtSpinnerField renderComponent) {
		super(renderComponent, "Ext.ux.form.SpinnerField");
	}

	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtSpinnerField uiInputPropertyText = (UIExtSpinnerField) renderComponent;
		extComponentJs().addObjectValue("incrementValue", uiInputPropertyText.getIncrementValue());
		extComponentJs().addObjectValue("alternateIncrementValue", uiInputPropertyText.getAlternateIncrementValue());
		extComponentJs().addBooleanValue("accelerate", uiInputPropertyText.isAccelerate());
	}
}
