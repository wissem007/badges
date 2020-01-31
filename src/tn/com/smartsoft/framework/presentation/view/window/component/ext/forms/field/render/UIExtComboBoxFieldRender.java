package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtComboBoxField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.comboBox.UIExtComboBoxItem;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtStore;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.render.UIExtStoreRender;

public class UIExtComboBoxFieldRender extends UIExtTextFieldRender {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String storeId = null;
	private boolean isCombo;
	
	public UIExtComboBoxFieldRender(UIExtComboBoxField renderComponent, boolean isCombo) {
		super(renderComponent, "Ext.ux.Suggest");
		this.isCombo = isCombo;
	}
	
	public UIExtComboBoxFieldRender(UIExtComboBoxField renderComponent) {
		this(renderComponent, true);
	}
	
	public void afterRender(UIRenderContext context) {
		super.afterRender(context);
		UIExtComboBoxField extComboBoxField = (UIExtComboBoxField) renderComponent;
		storeId = extComboBoxField.getStoreId();
		UIExtStore store = extComboBoxField.getStore();
		if (extComboBoxField.isLocalStore()) {
			UIExtStoreRender renderStore = (UIExtStoreRender) store.getRender();
			renderStore.render(context);
		}
		if (store != null)
			extComponentJs().addStringValue("queryParam", store.getQueryParam());
	}
	
	public void loadConfigs(UIRenderContext context) {
		UIExtComboBoxField extComboBoxField = (UIExtComboBoxField) renderComponent;
		super.loadConfigs(context);
		extComponentJs().addBooleanValue("isCombo", isCombo);
		if (StringUtils.isNotBlank(extComboBoxField.getDisplayRowCondition()))
			extComponentJs().addObjectValue("rowCondition", extComboBoxField.getDisplayRowCondition());
		extComponentJs().addStringValue("mode", extComboBoxField.getMode());
		if (!isParentGridColumn()) {
			String value = extComboBoxField.getCustomValue();
			String displayText = extComboBoxField.getDisplayCustomValue();
			extComponentJs().addStringValue("name", extComboBoxField.getId() + "Dislay");
			extComponentJs().addStringValue("hiddenName", extComboBoxField.getId());
			if (StringUtils.isNotBlank(value))
				extComponentJs().addStringValue("value", value);
			if (StringUtils.isNotBlank(displayText))
				extComponentJs().addStringValue("displayText", displayText);
		} else {
			extComponentJs().addStringValue("name", extComboBoxField.getId());
			extComponentJs().addStringValue("dataIndexDisplayColumn", extComboBoxField.getParentGridColumn().getDataIndexDisplay());
		}
		extComponentJs().addStringValue("valueField", extComboBoxField.getStore().getIdProperty());
		extComponentJs().addStringValue("displayField", extComboBoxField.getDisplayField());
		extComponentJs().addObjectValue("tpl", extComboBoxField.getTpl());
		extComponentJs().addObjectValue("store", storeId);
		extComponentJs().addBooleanValue("forceSelection", true);
		extComponentJs().addBooleanValue("suggestOption", extComboBoxField.getSuggestOption());
		extComponentJs().addStringValue("filterField", extComboBoxField.getFilterField());
		extComponentJs().addStringValue("matchChar", extComboBoxField.getMatchChar());
		loadDependecyFieldConfig(context, extComboBoxField);
	}
	
	private void loadDependecyFieldConfig(UIRenderContext context, UIExtComboBoxField extSuggestField) {
		JsTable tabitems = new JsTable();
		JsMap items = new JsMap();
		items.addStringValue("header", extSuggestField.getLibelle());
		items.addStringValue("dataIndex", extSuggestField.getDisplayField());
		items.addBooleanValue("dispaly", true);
		items.addValue("width", extSuggestField.getWidth(), !StringUtils.isNumeric(extSuggestField.getWidth()));
		items.addStringValue("align", "left");
		tabitems.addObjectValue(items);
		List<String> ids = extSuggestField.itemIds();
		for (int i = 0; i < ids.size(); i++) {
			UIExtComboBoxItem fieldItem = extSuggestField.getItem(ids.get(i));
			tabitems.addObjectValue(fieldItem.render());
		}
		extComponentJs().addObjectValue("linkedValues", tabitems.toJs());
	}
}
