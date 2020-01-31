package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtCheckboxField;

public class UIExtCheckboxFieldRender extends UIExtTextFieldRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtCheckboxFieldRender(UIExtCheckboxField renderComponent) {
		super(renderComponent, "Ext.ux.form.Checkbox");
	}

	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtCheckboxField extCheckboxField = (UIExtCheckboxField) renderComponent;
		if (!isParentGridColumn()) {
			if (StringUtils.isNotBlank(extCheckboxField.getBoxLabelExp())) {
				String libelle = extCheckboxField.getBoxLabel();
				extComponentJs().addStringValue("boxLabel", libelle);
			} else if (extCheckboxField.isBoxLabel()) {
				extComponentJs().addStringValue("boxLabel", extCheckboxField.getLibelle());
			}
			String value = extCheckboxField.getCustomValue();
			if (StringUtils.isBlank(value)) {
				extComponentJs().addBooleanValue("checked", false);
			} else {
				extComponentJs().addBooleanValue("checked", value.equalsIgnoreCase(extCheckboxField.getCheckedValue()));
			}
		}
		extComponentJs().addStringValue("submitOnValue", extCheckboxField.getCheckedValue());
		extComponentJs().addStringValue("submitOffValue", extCheckboxField.getUnCheckedValue());

	}
}
