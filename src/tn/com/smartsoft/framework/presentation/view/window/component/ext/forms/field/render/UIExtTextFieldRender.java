package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.framework.presentation.view.action.model.PropertyModel;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtBoxComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtTextField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGridColumn;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtGenericBoxComponentRender;

public class UIExtTextFieldRender extends UIExtGenericBoxComponentRender{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public UIExtTextFieldRender(UIExtBoxComponent renderComponent, String jsObjectName) {
		super(renderComponent, jsObjectName);
	}
	public UIExtTextFieldRender(UIExtTextField renderComponent) {
		super(renderComponent, "Ext.form.TextField");
	}
	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtTextField uiInputPropertyText = (UIExtTextField) renderComponent;
		if (!isParentGridColumn()) {
			String value = uiInputPropertyText.getCustomValue();
			if (uiInputPropertyText.getNextElementId() != null && uiInputPropertyText.getNextElementId().startsWith("#")) extComponentJs().addObjectValue("nextElementId", uiInputPropertyText.getNextElementId());
			else extComponentJs().addStringValue("nextElementId", uiInputPropertyText.getNextElementId());
			PropertyModel viewPropertyModel = uiInputPropertyText.model();
			extComponentJs().addBooleanValue("disabled", !uiInputPropertyText.isEditable());
			extComponentJs().addStringValue("disabledClass", "field-disabled-3s");
			extComponentJs().addStringValue("name", uiInputPropertyText.getId());
			extComponentJs().addStringValue("id", uiInputPropertyText.getId());
			extComponentJs().addBooleanValue("hideLabel", uiInputPropertyText.isHideLabel());
			extComponentJs().addStringValue("fieldLabel", uiInputPropertyText.getLibelle());
			extComponentJs().addStringValue("nextElementId", uiInputPropertyText.getNextElementId());
			extComponentJs().addStringValue("indicatorIcon", uiInputPropertyText.getIndicatorIcon());
			extComponentJs().addStringValue("indicatorTip", uiInputPropertyText.getIndicatorTip());
			extComponentJs().addStringValue("indicatorText", uiInputPropertyText.getIndicatorText());
			extComponentJs().addStringValue("indicatorCls", uiInputPropertyText.getIndicatorCls());
			extComponentJs().addStringValue("note", uiInputPropertyText.getNote());
			extComponentJs().addStringValue("noteAlign", uiInputPropertyText.getNoteAlign());
			extComponentJs().addStringValue("noteCls", uiInputPropertyText.getNoteCls());
			if (viewPropertyModel == null) {
				extComponentJs().addStringValue("value", "invalide inputProperty :" + uiInputPropertyText.getId());
			} else {
				if (!viewPropertyModel.isEncrypted()) {
					if (StringUtils.isNotBlank(value)) {
						if (StringUtils.lastIndexOf(value, ";") == value.length() - 1) value = value + ";";
						extComponentJs().addStringValue("value", value);
					}
				} else extComponentJs().addStringValue("inputType", "password");
			}
		}
		if (uiInputPropertyText.getMaxLength() > 0) extComponentJs().addIntValue("maxLength", uiInputPropertyText.getMaxLength());
		extComponentJs().addBooleanValue("allowBlank", uiInputPropertyText.isAllowBlank());
		extComponentJs().addBooleanValue("allowBlank", uiInputPropertyText.isAllowBlank());
		extComponentJs().addBooleanValue("selectOnFocus", true);
	}
	protected UIExtGridColumn getParentGridColumn() {
		UIExtTextField uiInputPropertyText = (UIExtTextField) renderComponent;
		return uiInputPropertyText.getParentGridColumn();
	}
	protected boolean isParentGridColumn() {
		UIExtTextField uiInputPropertyText = (UIExtTextField) renderComponent;
		return uiInputPropertyText.isParentGridColumn();
	}
}
