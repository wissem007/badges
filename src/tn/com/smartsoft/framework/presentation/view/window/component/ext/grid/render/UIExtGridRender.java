package tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.render;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.web.js.JsClass;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGridColumn;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGridHeaderColumn;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtHeaderRowGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtPanelRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtStore;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.render.UIExtStoreRender;
import tn.com.smartsoft.iap.dictionary.graphique.message.beans.MessageBean;

public class UIExtGridRender extends UIExtPanelRender{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private String				storeId				= null;
	private String				columnsId			= null;
	private String				jsInstanceViewName	= null;
	private boolean				isSummary			= false;
	private boolean				isLocking			= false;
	
	public UIExtGridRender(UIExtGrid renderComponent) {
		super(renderComponent, getJsGridClass(renderComponent));
	}
	public void afterRender(UIRenderContext context) {
		super.afterRender(context);
		UIExtGrid extGrid = (UIExtGrid) renderComponent;
		columnsId = "colModel" + extGrid.getId();
		storeId = extGrid.getStoreId();
		rendreColumns(context);
	}
	public void beforRender(UIRenderContext context) {
		super.beforRender(context);
	}
	private void rendreColumns(UIRenderContext context) {
		UIExtGrid extGrid = (UIExtGrid) renderComponent;
		JsTable jsColumns = new JsTable();
		List<String> columnIds = extGrid.getColumnIds();
		UIExtStore store = extGrid.getStore();
		store.setLiveGrid(extGrid.isLiveGrid() && !extGrid.isEditable());
		store.setTree(extGrid.isTree());
		store.setTypeTree(extGrid.getTypeTree());
		for (int i = 0; i < columnIds.size(); i++) {
			UIExtGridColumn column = extGrid.getColumn((String) columnIds.get(i));
			UIExtGridColumnRender gridColumnRender = (UIExtGridColumnRender) column.getRender();
			gridColumnRender.setParentRender(this);
			gridColumnRender.setJsColumnModel(jsColumns);
			gridColumnRender.render(context);
		}
		StringBuffer jsRecordres = new StringBuffer();
		jsRecordres.append("var ").append(columnsId).append("=new ").append(getJsColumnModelClass(isLocking)).append("({");
		jsRecordres.append("columns:").append(jsColumns.toJs());
		jsRecordres.append("});\n");
		context.addComponentsToBufferJs(jsRecordres.toString());
		renderView(context, isLocking);
		if (extGrid.isLocalStore()) {
			UIExtStoreRender renderStore = (UIExtStoreRender) store.getRender();
			renderStore.render(context);
		}
	}
	private void renderHeaderColumn(UIExtGrid extGrid, StringBuffer jsRecordres) {
		JsTable jsColumns;
		List<String> listHederId = extGrid.getHeaderRowIds();
		if (!listHederId.isEmpty()) {
			jsRecordres.append("rows: [");
			for (int i = 0; i < listHederId.size(); i++) {
				UIExtHeaderRowGrid headerRowGrid = extGrid.getHeaderRow(listHederId.get(i));
				jsColumns = new JsTable();
				List<String> listColHederId = headerRowGrid.getColumnIds();
				for (int j = 0; j < listColHederId.size(); j++) {
					UIExtGridHeaderColumn headerColumn = headerRowGrid.getColumn(listColHederId.get(j));
					JsMap jsColumn = new JsMap();
					Object libelle = extGrid.getWindow().evalExpression(headerColumn.getHeader());
					if (libelle == null) libelle = "";
					jsColumn.addStringValue("header", libelle.toString());
					jsColumn.addIntValue("rowspan", headerColumn.getRowspan());
					jsColumn.addIntValue("colspan", headerColumn.getColspan());
					if (StringUtils.isNotBlank(headerColumn.getAlign())) jsColumn.addStringValue("align", headerColumn.getAlign());
					else jsColumn.addStringValue("align", "center");
					jsColumns.addObjectValue(jsColumn.toJs());
				}
				if (i != 0) jsRecordres.append(",");
				jsRecordres.append(jsColumns.toJs());
			}
			jsRecordres.append("]");
		}
	}
	private void renderView(UIRenderContext context, boolean isLocking) {
		UIExtGrid extGrid = (UIExtGrid) renderComponent;
		jsInstanceViewName = extComponentJs().getInstanceName() + "View";
		JsMap configsView = new JsMap();
		configsView.addObjectValue("nearLimit", 100);
		JsMap jsLoad = new JsMap();
		jsLoad.addStringValue("msg", "Loading...");
		configsView.addObjectValue("loadMask", jsLoad.toJs());
		configsView.addObjectValue("nearLimit", 100);
		if (extGrid.getStore().isGouped()) {
			configsView.addObjectValue("forceFit", true);
			configsView.addObjectValue("groupTextTpl", extGrid.getGroupTextTpl());
			configsView.addBooleanValue("showGroupName", extGrid.getShowGroupName());
			configsView.addBooleanValue("enableNoGroups", extGrid.getEnableNoGroups());
			configsView.addBooleanValue("enableGroupingMenu", extGrid.getEnableGroupingMenu());
			configsView.addBooleanValue("hideGroupedColumn", extGrid.getHideGroupedColumn());
		}
		JsClass jsViewObject = new JsClass(getJsGridViewClass(extGrid, isLocking));
		configsView.addObjectValue("getRowClass", extGrid.getRowClass());
		context.addComponentsToBufferJs(jsViewObject.invokeNewInstance(jsInstanceViewName, true, configsView.toJs(), false));
	}
	public void loadConfigs(UIRenderContext context) {
		UIExtGrid extGrid = (UIExtGrid) renderComponent;
		super.loadConfigs(context);
		MessageBean messageBean = context.getApplicationCacheDictionaryManager().getMessageBean("0300040");
		messageBean = messageBean == null ? new MessageBean() : messageBean;
		extComponentJs().addStringValue("displayMsg", messageBean.getLibelle());
		extComponentJs().addObjectValue("store", storeId);
		extComponentJs().addObjectValue("fixedNumberRow", extGrid.getFixedNumberRow());
		extComponentJs().addObjectValue("maxNumberRow", extGrid.getMaxNumberRow());
		extComponentJs().addObjectValue("colModel", columnsId);
		extComponentJs().addObjectValue("newRecordHandler", extGrid.getNewRecordHandler());
		extComponentJs().addBooleanValue("isUpAndDowRowMenu", extGrid.getUpAndDowRowMenu());
		extComponentJs().addBooleanValue("isSavedMenu", extGrid.getSavedMenu());
		extComponentJs().addObjectValue("isAddRow", extGrid.getAddRow());
		extComponentJs().addObjectValue("isDeleteRow", extGrid.getDeleteRow());
		extComponentJs().addObjectValue("editableHandler", extGrid.getEditableHandler());
		JsMap jsLoad = new JsMap();
		jsLoad.addStringValue("msg", "Loading...");
		extComponentJs().addObjectValue("loadMask", jsLoad.toJs());
		List<String> columnIds = extGrid.getColumnIds();
		if (extGrid.isTree() && columnIds.size() > 0) {
			UIExtGridColumn column = extGrid.getColumn((String) columnIds.get(0));
			String masterColumnId = StringUtils.isNotBlank(extGrid.getMasterColumnId()) ? extGrid.getMasterColumnId() : column.getId();
			String autoExpandColumn = StringUtils.isNotBlank(extGrid.getAutoExpandColumn()) ? extGrid.getMasterColumnId() : column.getId();
			extComponentJs().addStringValue("masterColumnId", masterColumnId);
			extComponentJs().addStringValue("autoExpandColumn", autoExpandColumn);
		}
		extComponentJs().addObjectValue("selModel", getJsRowSelectionModelClass(extGrid));
		extComponentJs().addObjectValue("view", jsInstanceViewName);
		loadJsPlugins(extGrid);
		if (extGrid.isLiveGrid() && !extGrid.isEditable()) extComponentJs().addObjectValue("bbar", "new Ext.ux.grid.livegrid.Toolbar({view: " + jsInstanceViewName + " })");
	}
	private void loadJsPlugins(UIExtGrid extGrid) {
		JsTable jsPlugins = new JsTable();
		List<String> listHederId = extGrid.getHeaderRowIds();
		if (!listHederId.isEmpty()) {
			StringBuffer jsRecordres = new StringBuffer();
			renderHeaderColumn(extGrid, jsRecordres);
			jsPlugins.addObjectValue("new Ext.ux.grid.ColumnHeaderGroup({" + jsRecordres + "})");
		}
		if (extGrid.getIsSearch()) {
			JsMap configsSearch = new JsMap();
			configsSearch.addStringValue("iconCls", "icon-zoom");
			configsSearch.addStringValue("mode", "local");
			configsSearch.addStringValue("position", "icon-zoom");
			configsSearch.addStringValue("iconCls", "icon-zoom");
			List<String> columnIds = extGrid.getColumnIds();
			JsTable readonlyIndexes = new JsTable();
			JsTable disableIndexes = new JsTable();
			for (int i = 0; i < columnIds.size(); i++) {
				UIExtGridColumn column = extGrid.getColumn((String) columnIds.get(i));
				if (column.getDisableIndexesSearche()) {
					disableIndexes.addStringOption(column.getDataIndex());
				}
				if (column.getReadonlyIndexeSearche()) {
					readonlyIndexes.addStringOption(column.getDataIndex());
				}
			}
			configsSearch.addObjectValue("readonlyIndexes", readonlyIndexes.toJs());
			configsSearch.addObjectValue("disableIndexes", disableIndexes.toJs());
			jsPlugins.addObjectValue("new Ext.ux.grid.Search(" + configsSearch.toJs() + ")");
		}
		if (isSummary) jsPlugins.addObjectValue("new Ext.ux.grid.GridSummary()");
		if (extGrid.getStore().isGouped()) jsPlugins.addObjectValue("new Ext.ux.grid.GroupSummary()");
		extComponentJs().addObjectValue("plugins", jsPlugins.toJs());
	}
	private static String getJsGridClass(UIExtGrid renderComponent) {
		if (renderComponent.isLiveGrid()) return renderComponent.isEditable() ? "Ext.ux.grid.EditorGridPanel" : "Ext.ux.grid.livegrid.GridPanel";
		else if (renderComponent.isTree()) return !renderComponent.isEditable() ? "Ext.tree.GridPanel" : "Ext.tree.EditorGridPanel";
		return renderComponent.isEditable() ? "Ext.ux.grid.EditorGridPanel" : "Ext.ux.grid.GridPanel";
	}
	private String getJsColumnModelClass(boolean isLocking) {
		return isLocking ? "Ext.ux.grid.LockingColumnModel" : "Ext.grid.ColumnModel";
	}
	private String getJsGridViewClass(UIExtGrid extGrid, boolean isLocking) {
		if (extGrid.getStore().isGouped()) return "Ext.grid.GroupingView";
		if (extGrid.isLiveGrid() && !extGrid.isEditable()) return "Ext.ux.grid.livegrid.GridView";
		else if (extGrid.isTree()) return "Ext.tree.GridView";
		else if (!isLocking) return "Ext.grid.GridView";
		else return "Ext.ux.grid.LockingGridView";
	}
	public void setSummary(boolean isSummary) {
		if (this.isSummary) return;
		this.isSummary = isSummary;
	}
	public void setLocking(Boolean isLocking) {
		this.isLocking = isLocking != null && isLocking.booleanValue() ? true : this.isLocking;
	}
	private String getJsRowSelectionModelClass(UIExtGrid extGrid) {
		if (extGrid.isLiveGrid() && !extGrid.isEditable()) return "new Ext.ux.grid.livegrid.RowSelectionModel()";
		return extGrid.isEditable() ? "new Ext.grid.CellSelectionModel()" : "new Ext.grid.RowSelectionModel()";
	}
}
