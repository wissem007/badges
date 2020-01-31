package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import tn.com.smartsoft.framework.beans.Money;
import tn.com.smartsoft.framework.presentation.formater.NumericFormater;
import tn.com.smartsoft.framework.presentation.formater.NumericKeyFormater;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtNumberField;

public class UIExtNumberFieldRender extends UIExtTextFieldRender{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public UIExtNumberFieldRender(UIExtNumberField renderComponent, String jsObjectName) {
		super(renderComponent, jsObjectName);
	}
	public UIExtNumberFieldRender(UIExtNumberField renderComponent) {
		super(renderComponent, "Ext.ux.NumberField");
	}
	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtNumberField extNumberField = (UIExtNumberField) renderComponent;
		if (extNumberField.model().getFormatter() instanceof NumericKeyFormater || extNumberField.model().getFormatter() instanceof NumericFormater) {
			extNumberField.setAllowDecimals(false);
			extComponentJs().addBooleanValue("isMony", false);
		} else {
			extNumberField.setAllowDecimals(true);
			if (extNumberField.getDecimalPrecision() != -1) {
				extNumberField.setDecimalPrecision(extNumberField.getDecimalPrecision());
			} else {
				extNumberField.setDecimalPrecision(new Money().getDevise().getNombreDecimales().intValue());
			}
		}
		extComponentJs().addBooleanValue("allowDecimals", extNumberField.isAllowDecimals());
		extComponentJs().addStringValue("decimalSeparator", extNumberField.getDecimalSeparator());
		extComponentJs().addIntValue("decimalPrecision", extNumberField.getDecimalPrecision());
		extComponentJs().addBooleanValue("allowNegative", extNumberField.isAllowNegative());
		extComponentJs().addObjectValue("maxValue", extNumberField.getMaxValue());
		extComponentJs().addObjectValue("minValue", extNumberField.getMinValue());
		extComponentJs().addStringValue("minText", extNumberField.getMessageMaxValueKey());
		extComponentJs().addStringValue("maxText", extNumberField.getMessageMinValueKey());
	}
}
