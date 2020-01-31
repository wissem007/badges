package tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.render;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtAttrebutComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtCheckboxField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtComboBoxField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGridColumn;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIEventRenderUtils;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtAttrebutComponentRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtComponentRender;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;

public class UIExtGridColumnRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JsTable jsColumnModel;
	private static Map<String, String> mappedXType = new HashMap<String, String>();
	static {
		mappedXType.put("defauld", "Ext.grid.Column");
		mappedXType.put("boolean", "Ext.grid.BooleanColumn");
		mappedXType.put("number", "Ext.grid.NumberColumn");
		mappedXType.put("date", "Ext.grid.DateColumn");
		mappedXType.put("template", "Ext.grid.TemplateColumn");
		mappedXType.put("action", "Ext.grid.ActionColumn");
		mappedXType.put("check", "Ext.ux.grid.CheckColumn");
		mappedXType.put("combo", "Ext.ux.grid.ComboColumn");
		mappedXType.put("treeCheck", "Ext.tree.grid.CheckColumn");
		mappedXType.put("checklive", "Ext.ux.liveGrid.CheckColumn");
	}

	public UIExtGridColumnRender(UIExtGridColumn renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		UIExtGridColumn extGridColumn = (UIExtGridColumn) renderComponent;
		if (!extGridColumn.isRendred())
			return;
		UIExtComponentJs jsColumn = new UIExtComponentJs(mappedXType.get(extGridColumn.getXtype()), extGridColumn.getId());
		UIExtGridRender extGridRender = (UIExtGridRender) this.getParentRender();
		UIExtGrid extGrid = (UIExtGrid) extGridColumn.getParent();
		extGridRender.setLocking(extGridColumn.isLocked());
		extGridRender.setSummary(StringUtils.isNotBlank(extGridColumn.getSummaryType()));
		jsColumn.addStringValue("id", extGridColumn.getId());
		jsColumn.addStringValue("dataIndex", extGridColumn.getDataIndex());
		jsColumn.addStringValue("header", extGridColumn.getLibelle());
		jsColumn.addObjectValue("locked", extGridColumn.isLocked());
		jsColumn.addStringValue("cls", extGridColumn.getCls());
		jsColumn.addValue("width", extGridColumn.getWidth());
		jsColumn.addBooleanValue("sortable", extGridColumn.isSortable());
		jsColumn.addObjectValue("renderer", extGridColumn.getRenderer());
		jsColumn.addStringValue("align", extGridColumn.getAlign());
		jsColumn.addStringValue("summaryType", extGridColumn.getSummaryType());
		jsColumn.addObjectValue("summaryRenderer", extGridColumn.getSummaryRenderer());
		jsColumn.addBooleanValue("hideable", extGridColumn.getHideable());
		jsColumn.addStringValue("dataIndexDisplay", extGridColumn.getDataIndexDisplay());
		jsColumn.addBooleanValue("resizable", extGridColumn.isResizable());
		jsColumn.addStringValue("gridId", extGrid.getId());
		UIExtField field = extGridColumn.getEditor();
		if (field != null) {
			if (field instanceof UIExtCheckboxField) {
				jsColumn.addStringValue("submitOffValue", ((UIExtCheckboxField) field).getUnCheckedValue());
				jsColumn.addStringValue("hideable", ((UIExtCheckboxField) field).getAttribut("hideable"));
				jsColumn.addStringValue("submitOnValue", ((UIExtCheckboxField) field).getCheckedValue());
				jsColumn.addBooleanValue("editable", extGrid.isEditable() ? extGrid.isEditable() : extGridColumn.getForceEditable());
			} else if (field instanceof UIExtComboBoxField) {
				if(StringUtils.isBlank(extGridColumn.getRenderer())&& StringUtils.isNotBlank(extGridColumn.getDataIndexDisplay())){
					jsColumn.addObjectValue("renderer", "suggestRender");
		 		}else if(StringUtils.isNotBlank(extGridColumn.getRenderer())&& StringUtils.isNotBlank(extGridColumn.getDataIndexDisplay())){
		 			jsColumn.addObjectValue("renderer", "function(value, metaData, record, rowIndex, colIndex, store){return suggestRender.call(this,value, metaData, record, rowIndex, colIndex, store,"+extGridColumn.getRenderer()+");}");
	 	 		}
				jsColumn.addObjectValue("editorStoreId", ((UIExtComboBoxField) field).getStoreId());
				jsColumn.addStringValue("valueField", ((UIExtComboBoxField) field).getStore().getIdProperty());
				jsColumn.addStringValue("displayField", ((UIExtComboBoxField) field).getDisplayField());
			}
			if (extGrid.isEditable() && !(field instanceof UIExtCheckboxField)) {
				UIExtComponentRender fieldRender = (UIExtComponentRender) field.getRender();
				fieldRender.setParentRender(this);
				fieldRender.setTabRender(getTabRender() + "  ");
				fieldRender.render(context);
				jsColumn.addObjectValue("editor", fieldRender.extComponentJs().getInstanceName());
			}
		}
		jsColumn.addValues(extGridColumn);
		UIEventRenderUtils.renderEvents(context, extGridColumn, getParentRender(), jsColumn);
		UIEvent uiEvent = extGridColumn.getEvent("handler");
		if (uiEvent != null) {
			UIEventRenderUtils.renderHrefEventToHandelConfig(context, this, uiEvent, jsColumn);
		}
		if (extGrid.isEditable() || extGridColumn.getXtype().equalsIgnoreCase("checklive"))
			jsColumn.addBooleanValue("sortable", false);
		jsColumnModel.addObjectValue(jsColumn.newInstance(false).getScript());
		for (int i = 0; i < extGridColumn.attrebutComponentSize(); i++) {
			UIExtAttrebutComponent child = extGridColumn.getAttrebutComponent((String) extGridColumn.attrebutComponentIds().get(i));
			UIRendrableComponent rendrableComponent = (UIRendrableComponent) child;
			UIExtAttrebutComponentRender uiRender = (UIExtAttrebutComponentRender) rendrableComponent.getRender();
			uiRender.setParentRender(this);
			uiRender.render(context, jsColumn);
		}
	}

	public void setJsColumnModel(JsTable jsColumnModel) {
		this.jsColumnModel = jsColumnModel;
	}
}
