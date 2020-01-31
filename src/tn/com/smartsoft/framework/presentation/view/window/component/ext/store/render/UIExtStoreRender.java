package tn.com.smartsoft.framework.presentation.view.window.component.ext.store.render;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIEventRenderUtils;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtFieldStore;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtJsonStore;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;

public class UIExtStoreRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIExtComponentJs extComponentJs;
	private UIExtComponentJs extReaderComponentJs;

	public UIExtStoreRender(UIExtJsonStore renderComponent) {
		super(renderComponent);
		this.extComponentJs = new UIExtComponentJs(jsStoreClass(renderComponent), renderComponent.getId());
		this.extReaderComponentJs = new UIExtComponentJs(jsReaderStoreClass(renderComponent), renderComponent.getId() + "Reader");
	}

	private String jsReaderStoreClass(UIExtJsonStore renderComponent) {
		return renderComponent.isLiveGrid() ? "Ext.ux.grid.livegrid.JsonReader" : "Ext.data.JsonReader";
	}

	private String jsStoreClass(UIExtJsonStore renderComponent) {
		if (renderComponent.isGouped())
			return "Ext.data.GroupingStore";
		return renderComponent.isLiveGrid() ? "Ext.ux.grid.livegrid.Store" : !renderComponent.isTree() ? "Ext.ux.data.Store" : renderComponent.getTypeTree() == 0 ? "Ext.tree.AdjacencyListStore"
				: "Ext.tree.NestedSetStore";
	}

	public void render(UIRenderContext context) {
		UIExtJsonStore extComponent = (UIExtJsonStore) renderComponent;
		UIEventRenderUtils.renderEvents(context, extComponent, parentRender, extComponentJs);
		renderReader(context);
		loadConfigs(context);
		context.addComponentsToBufferJs(extComponentJs.newInstance());
	}

	public void loadConfigs(UIRenderContext context) {
		UIExtJsonStore extComponent = (UIExtJsonStore) renderComponent;
		this.extComponentJs.addObjectValue("autoDestroy", extComponent.isAutoDestroy());
		this.extComponentJs.addBooleanValue("isExpandAllParam", extComponent.getExpandAll());
		this.extComponentJs.addObjectValue("autoSave", extComponent.isAutoSave());
		this.extComponentJs.addObjectValue("autoLoad", extComponent.isAutoLoad());
		this.extComponentJs.addStringValue("id", extComponent.getId());
		this.extComponentJs.addObjectValue("desktop", context.getDesktopJs().getInsatanceName());
		this.extComponentJs.addObjectValue("bufferSize", 300);
		this.extComponentJs.addObjectValue("reader", extReaderComponentJs.getInstanceName());
		this.extComponentJs.addStringValue("queryParam", extComponent.getQueryParam());
		if (extComponent.isGouped()) {
			this.extComponentJs.addObjectValue("remoteGroup", true);
		}
		this.extComponentJs.addObjectValue("remoteSort", true);
		if (extComponent.isRemote()) {
			this.extComponentJs.addUrlValue("url", ClientEvent.ON_INIT, extComponent, context);
		}
		this.extComponentJs.addStringValue("groupField", extComponent.getGroupField());
		JsMap relaodAction = new JsMap();
		if (!extComponent.getReloadRequestParams().isEmpty())
			relaodAction.addValue("params", extComponent.getReloadRequestParams().toJs(), false);
		if (!extComponent.getReloadRequestFieldsParams().isEmpty())
			relaodAction.addValue("paramsField", extComponent.getReloadRequestFieldsParams().toJs(), false);
		relaodAction.addBooleanValue("allField", extComponent.getReloadAllRequestField());
		relaodAction.addStringValue("handlerRequestParams", extComponent.getReloadHandlerRequestParams());
		this.extComponentJs.addObjectValue("relaodAction", relaodAction.toJs());
	}

	public void renderReader(UIRenderContext context) {
		UIExtJsonStore extComponent = (UIExtJsonStore) renderComponent;
		this.extReaderComponentJs.addStringValue("root", extComponent.getRoot());
		this.extReaderComponentJs.addStringValue("idProperty", extComponent.getIdProperty());
		this.extReaderComponentJs.addStringValue("messageProperty", extComponent.getMessageProperty());
		this.extReaderComponentJs.addStringValue("successProperty", extComponent.getSuccessProperty());
		this.extReaderComponentJs.addStringValue("versionProperty", "version");
		this.extReaderComponentJs.addStringValue("totalProperty", extComponent.getTotalProperty());
		this.extReaderComponentJs.addStringValue("id", "index");
		loadFieldConfig(extComponent);
		context.addComponentsToBufferJs(this.extReaderComponentJs.newInstance());
	}

	private void addFieldConfig(JsTable jsTable, String name, String type) {
		JsMap fields = new JsMap();
		fields.addStringValue("name", name);
		fields.addStringValue("type", type);
		jsTable.addObjectValue(fields.toJs());
	}

	private void loadFieldConfig(UIExtJsonStore extComponent) {
		JsTable jsTable = new JsTable();
		addFieldConfig(jsTable, "index", "string");
		if (extComponent.isTree()) {
			addFieldConfig(jsTable, UIExtJsonStore.TREE_PARENT_NAME, "auto");
			addFieldConfig(jsTable, UIExtJsonStore.TREE_ISLEAF_NAME, "bool");
			addFieldConfig(jsTable, UIExtJsonStore.TREE_LEVEL_NAME, "int");
			addFieldConfig(jsTable, UIExtJsonStore.TREE_LFT_NAME, "int");
			addFieldConfig(jsTable, UIExtJsonStore.TREE_RGT_NAME, "int");
		}
		JsTable jsTableSort = new JsTable();
		for (int i = 0; i < extComponent.fieldSize(); i++) {
			UIExtFieldStore fieldStore = extComponent.getField(i);
			JsMap fields = new JsMap();
			JsMap fieldsSort = new JsMap();
			fields.addStringValue("name", fieldStore.getId());
			fields.addStringValue("convert", fieldStore.getJsConvert());
			fields.addStringValue("sortType", fieldStore.getSortType());
			fields.addObjectValue("useNull", fieldStore.isUseNull());
			fields.addStringValue("sortDir", (fieldStore.isAsc() == null ? true : fieldStore.isAsc()) ? "ASC" : "DESC");
			fieldsSort.addStringValue("field", fieldStore.getId());
			fieldsSort.addStringValue("direction", "ASC");
			jsTableSort.addObjectValue(fieldsSort.toJs());
			jsTable.addObjectValue(fields.toJs());
		}
		this.extComponentJs.addObjectValue("sortInfo", jsTableSort.toJs());
		this.extReaderComponentJs.addObjectValue("fields", jsTable.toJs());
	}

}
