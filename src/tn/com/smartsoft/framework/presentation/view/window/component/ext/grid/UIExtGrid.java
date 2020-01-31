package tn.com.smartsoft.framework.presentation.view.window.component.ext.grid;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.report.component.RCompositeFieldText;
import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.framework.presentation.view.action.model.ItemModel;
import tn.com.smartsoft.framework.presentation.view.action.request.CompositeRequestComponent;
import tn.com.smartsoft.framework.presentation.view.action.request.CompositeRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.ControlerRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.RequestField;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.report.RGenericTemplate;
import tn.com.smartsoft.framework.presentation.view.report.RTemplatetModel;
import tn.com.smartsoft.framework.presentation.view.report.RValue;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtPanel;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIAjaxEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtComboBoxField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.editor.UIControlerRequestGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.editor.UIControlerRequestSelectedRow;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.render.UIExtGridRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtStore;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public class UIExtGrid extends UIExtPanel implements CompositeRequestField,RequestField{
	
	/**
	 * 
	 */
	private static final long								serialVersionUID		= 1L;
	private UIDefaultComponentHandler<UIExtGridColumn>		columnHandler			= new UIDefaultComponentHandler<UIExtGridColumn>(this);
	private UIDefaultComponentHandler<UIExtHeaderRowGrid>	headerRowHandler		= new UIDefaultComponentHandler<UIExtHeaderRowGrid>(this);
	private CompositeRequestComponent						compositeRequestField	= new CompositeRequestComponent(this);
	private int												selectedRow;
	private int[]											selectedTreeRow;
	private String											storeId;
	private UIExtStore										store;
	private Boolean											isLocalStore			= Boolean.TRUE;
	private Boolean											isLiveGrid				= Boolean.FALSE;
	protected Boolean										editable				= Boolean.FALSE;
	protected boolean										editableSet				= false;
	private Hashtable<String, String>						mappedColumnFieldIndex	= new Hashtable<String, String>();
	private Integer											maxNumberRow			= 0;
	private Boolean											fixedNumberRow			= false;
	private Boolean											isTree					= Boolean.FALSE;
	private String											masterColumnId;
	private String											autoExpandColumn;
	private Integer											typeTree				= 0;
	private String											newRecordHandler;
	private Boolean											isUpAndDowRowMenu		= Boolean.TRUE;
	private Boolean											isSavedMenu				= Boolean.TRUE;
	private String											isAddRow;
	private String											isDeleteRow;
	private String											editableHandler;
	private String											groupTextTpl			= "'" + "{text} ({[values.rs.length]})" + "'";
	private Boolean											showGroupName			= true;
	private Boolean											enableNoGroups			= false;
	private Boolean											enableGroupingMenu		= false;
	private Boolean											hideGroupedColumn		= true;
	private String											rowClass;
	private Boolean											isSearch				= false;
	
	public UIExtGrid() {
		super();
		this.compositeRequestField.addRequestField("selectedRow", new UIControlerRequestSelectedRow(this));
		this.addEvent(new UIAjaxEvent("save", "saveData", this));
		this.addEvent(new UIAjaxEvent("checkAll", "checkAll", this));
	}
	public void saveData(ListenerContext context) throws FunctionalException {}
	public void checkAll(ListenerContext context) throws FunctionalException {
		List<String> columnIds = getColumnIds();
		for (int i = 0; i < columnIds.size(); i++) {
			String cid = columnIds.get(i);
			UIExtGridColumn gridColumn = getColumn(cid);
			if (context.requestParameters().containsParameter(getId() + "_chechAll_" + gridColumn.getDataIndex())) {
				boolean isCheck = context.requestParameters().getParameterAsBoolean(getId() + "_chechAll_" + gridColumn.getDataIndex());
				int count = getStore().getValue().size();
				for (int j = 0; j < count; j++) {
					gridColumn.getFiledStore().setValue(j, isCheck);
				}
			}
		}
	}
	public String getRowClass() {
		return rowClass;
	}
	public void setRowClass(String rowClass) {
		this.rowClass = rowClass;
	}
	public Integer getTypeTree() {
		return typeTree;
	}
	public void setTypeTree(Integer typeTree) {
		this.typeTree = typeTree;
	}
	public Boolean getShowGroupName() {
		return showGroupName;
	}
	public void setShowGroupName(Boolean showGroupName) {
		this.showGroupName = showGroupName;
	}
	public Boolean getHideGroupedColumn() {
		return hideGroupedColumn;
	}
	public void setHideGroupedColumn(Boolean hideGroupedColumn) {
		this.hideGroupedColumn = hideGroupedColumn;
	}
	public Boolean getEnableNoGroups() {
		return enableNoGroups;
	}
	public void setEnableNoGroups(Boolean enableNoGroups) {
		this.enableNoGroups = enableNoGroups;
	}
	public int[] getSelectedTreeRow() {
		return selectedTreeRow;
	}
	public void setSelectedTreeRow(int[] selectedTreeRow) {
		this.selectedTreeRow = selectedTreeRow;
	}
	public Boolean getEnableGroupingMenu() {
		return enableGroupingMenu;
	}
	public void setEnableGroupingMenu(Boolean enableGroupingMenu) {
		this.enableGroupingMenu = enableGroupingMenu;
	}
	public String getGroupTextTpl() {
		return groupTextTpl;
	}
	public void setGroupTextTpl(String groupTextTpl) {
		this.groupTextTpl = groupTextTpl;
	}
	public String getAutoExpandColumn() {
		return autoExpandColumn;
	}
	public void setAutoExpandColumn(String autoExpandColumn) {
		this.autoExpandColumn = autoExpandColumn;
	}
	public String getMasterColumnId() {
		return masterColumnId;
	}
	public void setMasterColumnId(String masterColumnId) {
		this.masterColumnId = masterColumnId;
	}
	public Integer getMaxNumberRow() {
		return maxNumberRow;
	}
	public void setMaxNumberRow(Integer maxNumberRow) {
		this.maxNumberRow = maxNumberRow;
	}
	public Boolean getFixedNumberRow() {
		return fixedNumberRow;
	}
	public void setFixedNumberRow(Boolean fixedNumberRow) {
		this.fixedNumberRow = fixedNumberRow;
	}
	public void addHeaderRow(UIExtHeaderRowGrid column) {
		headerRowHandler.addItem(column);
	}
	public List<String> getHeaderRowIds() {
		return headerRowHandler.itemIds();
	}
	public UIExtHeaderRowGrid getHeaderRow(String id) {
		return (UIExtHeaderRowGrid) headerRowHandler.getItem(id);
	}
	public void removeHeaderRow(String id) {
		headerRowHandler.removeItem(id);
	}
	public void removeHeaderRow(int start) {
		headerRowHandler.removeItem(start);
	}
	public int headerRowSize() {
		return headerRowHandler.itemSize();
	}
	public void addColumn(UIExtGridColumn column) {
		if (StringUtils.isNotBlank(column.getDataIndex())) mappedColumnFieldIndex.put(column.getDataIndex(), column.getId());
		columnHandler.addItem(column);
	}
	public UIExtGridColumn getColumnByIndex(String dataIndex) {
		if (mappedColumnFieldIndex.containsKey(dataIndex)) return (UIExtGridColumn) columnHandler.getItem(mappedColumnFieldIndex.get(dataIndex));
		return null;
	}
	public void removeColumn(String id) {
		columnHandler.removeItem(id);
	}
	public void removeColumn(String[] id) {
		columnHandler.removeItem(id);
	}
	public void removeColumn(int start, int end) {
		columnHandler.removeItem(start, end);
	}
	public void removeColumn(int start) {
		columnHandler.removeItem(start);
	}
	public List<String> getColumnIds() {
		return columnHandler.itemIds();
	}
	public UIExtGridColumn getColumn(String id) {
		return (UIExtGridColumn) columnHandler.getItem(id);
	}
	public Boolean isLocalStore() {
		return isLocalStore;
	}
	public Boolean isLiveGrid() {
		return isLiveGrid;
	}
	public void setLiveGrid(Boolean isLiveGrid) {
		this.isLiveGrid = isLiveGrid;
	}
	public Boolean isTree() {
		return isTree;
	}
	public void setTree(Boolean isTree) {
		this.isTree = isTree;
	}
	public void setStore(UIExtStore store) {
		if (store != null) {
			store.setParent(this);
			this.store = store;
			isLocalStore = Boolean.TRUE;
			this.storeId = store.getId();
		}
	}
	public boolean isEditable() {
		ItemModel model = getUserAction().getModel().findFieldModel(getStore().getModelName());
		if (!editableSet && model != null) editable = !ActionMode.isReadMode(model.getMode()) && !ActionMode.isDeleteMode(model.getMode());
		return editable;
	}
	public void setEditable(boolean editable) {
		editableSet = true;
		this.editable = editable;
	}
	public UIExtStore getStore() {
		if (store == null) {
			store = (UIExtStore) getWindow().findChild(getStoreId());
		}
		if (store == null) { throw new TechnicalException("no store for id " + getStoreId()); }
		return store;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public void setSelectedRow(int selectedRow) {
		this.selectedRow = selectedRow;
	}
	public int getSelectedRow() {
		return selectedRow;
	}
	public UIRender getRender() {
		return new UIExtGridRender(this);
	}
	public String getNewRecordHandler() {
		return newRecordHandler;
	}
	public void setNewRecordHandler(String newRecordHandler) {
		this.newRecordHandler = newRecordHandler;
	}
	public Boolean getUpAndDowRowMenu() {
		return isUpAndDowRowMenu;
	}
	public void setUpAndDowRowMenu(Boolean isUpAndDowRowMenu) {
		this.isUpAndDowRowMenu = isUpAndDowRowMenu;
	}
	public Boolean getSavedMenu() {
		return isSavedMenu;
	}
	public void setSavedMenu(Boolean isSavedMenu) {
		this.isSavedMenu = isSavedMenu;
	}
	public String getAddRow() {
		return isAddRow;
	}
	public void setAddRow(String isAddRow) {
		this.isAddRow = isAddRow;
	}
	public String getDeleteRow() {
		return isDeleteRow;
	}
	public void setDeleteRow(String isDeleteRow) {
		this.isDeleteRow = isDeleteRow;
	}
	public String getEditableHandler() {
		return editableHandler;
	}
	public void setEditableHandler(String editableHandler) {
		this.editableHandler = editableHandler;
	}
	public RTemplatetModel createExportPdfModel(WebContext context) {
		RGenericTemplate rTemplate = new RGenericTemplate();
		RTemplatetModel dynaJasperReport = new RTemplatetModel(rTemplate, getStore().getModelName(), getTitle());
		dynaJasperReport.addParam("title", getTitle());
		Map<String, String> fieldValue = getWindow().getFieldValues();
		Iterator<String> it = fieldValue.keySet().iterator();
		while (it.hasNext()) {
			String name = (String) it.next();
			UIComponent component = getWindow().findChild(name);
			if (component != null && component instanceof UIExtField) {
				UIExtField field = (UIExtField) component;
				if (StringUtils.isNotBlank(fieldValue.get(name))) {
					if (field instanceof UIExtComboBoxField) dynaJasperReport.addParam(name + "Filter", ((UIExtComboBoxField) field).getDisplayValue());
					else dynaJasperReport.addParam(name + "Filter", field.getCustomValue());
					dynaJasperReport.addParam("isFilter", new Boolean(true));
					RCompositeFieldText compositeFieldText = rTemplate.addCompositeFieldText();
					compositeFieldText.addFieldText(field.getLibelle(), name + "Filter", 100, 100, String.class);
				}
			}
		}
		List<String> columnIds = this.getColumnIds();
		for (int i = 0; i < columnIds.size(); i++) {
			UIExtGridColumn column = this.getColumn((String) columnIds.get(i));
			int width = 105;
			if (StringUtils.isNumeric(column.getWidth())) {
				width = Integer.parseInt(column.getWidth());
			}
			if (column.isPrint() && column.getFiledStore() != null) rTemplate.addColumnField(column.getLibelle(), column.getFiledStore().getProperty(), RValue.class, width);
		}
		return dynaJasperReport;
	}
	public UIObject getRequestField(String childFieldId) {
		return compositeRequestField.getRequestField(childFieldId);
	}
	public ControlerRequestField getControlerRequestField() {
		return new UIControlerRequestGrid(this);
	}
	public Boolean getIsSearch() {
		return isSearch;
	}
	public void setIsSearch(Boolean isSearch) {
		this.isSearch = isSearch;
	}
}
