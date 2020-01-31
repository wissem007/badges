package tn.com.smartsoft.framework.presentation.view.window.utils;

import tn.com.smartsoft.framework.presentation.view.tags.handler.GenericTagHandler;

public class UITagCreateUtils {

	private static final String PREIX_STYLE_FIELD_GRID_COL_ATR = "preixStyleField";
	private static final String LABEL_FIELD_GRID_COL_ATR = "labelField";
	private static final String XTYPE_ATR = "xtype";
	private static final String WIDTH_ATR = "width";
	private static final String DATA_INDEX_ATR = "data-index";
	private static final String EXT_GRID_COLUMN_TAG = "ext-grid-column";
	public static final String EXT_PANEL_TAG = "ext-panel";
	public static final String EXT_GRID_ROW_HEADER_TAG = "ext-grid-row-header";
	public static final String EXT_STORE_FIELD_TAG = "ext-store-field";
	public static final String GRID_COLUMN_HEADER_TAG = "ext-grid-column-header";
	public static final String EXT_STORE_TAG = "ext-store";

	public static final String ID_ATTR_NAME = "id";
	public static final String PROPERTY_ATR = "property";
	public static final String COLSPAN_ATR = "colspan";
	public static final String HEADER_ATR = "header";
	public static final String ALIGN_ATR = "align";
	public static final String TITLE_EXP_ATR = "title-exp";
	public static final String COLUMN_WIDTH_ATR = "columnWidth";
	public static final String HEIGHT_ATR = "height";
	public static final String BORDER_ATR = "border";
	public static final String FIXED_NUMBER_ROW_ATR = "fixed-number-row";
	public static final String EDITABLE_ATR = "editable";
	public static final String AUTO_HEIGHT_ATR = "auto-height";
	public static final String ALIGN_CENTER_ATR_VALUE = "center";
	public static final String IS_EXPAND_ALL_STORE_ATR = "is-expand-all";
	public static final String PARENT_PROPERTY_STORE_ATR = "parent-property";
	public static final String ID_PROPERTY_STORE_ATR = "id-property";
	public static final String MODEL_NAME_STORE_ATR = "model-name";
	public static final String EDITABLE_HANDLER_GRID_ATR = "editable-handler";
	public static final String TYPE_TREE_GRID_ATR = "type-tree";
	public static final String IS_TREE_GRID_ATR = "is-tree";
	public static final String EXT_GRID_TAG = "ext-grid";

	public static GenericTagHandler createStoreField(String id, String property) {
		return new GenericTagHandler(EXT_STORE_FIELD_TAG, ID_ATTR_NAME, PROPERTY_ATR, id, property);
	}

	public static GenericTagHandler createStoreField(String property) {
		return new GenericTagHandler(EXT_STORE_FIELD_TAG, ID_ATTR_NAME, PROPERTY_ATR, property + "Index", property);
	}

	public static GenericTagHandler createGrid(String heigh, boolean border, boolean autoHeight, boolean editable, boolean fixedNumber, boolean isTree, String typeTree, String editableHandler) {
		GenericTagHandler tagGrid = new GenericTagHandler(EXT_GRID_TAG, new String[] { HEIGHT_ATR, BORDER_ATR, AUTO_HEIGHT_ATR, EDITABLE_ATR, FIXED_NUMBER_ROW_ATR, IS_TREE_GRID_ATR,
				TYPE_TREE_GRID_ATR, EDITABLE_HANDLER_GRID_ATR }, new Object[] { heigh, border, autoHeight, editable, fixedNumber, isTree, typeTree, editableHandler });
		return tagGrid;
	}

	public static GenericTagHandler createGridTreeColunm(String dataIndex, String width, String xtype, String labelField, String preixStyleField) {
		GenericTagHandler tagGridColunm = new GenericTagHandler(EXT_GRID_COLUMN_TAG, new String[] { DATA_INDEX_ATR, WIDTH_ATR, XTYPE_ATR, LABEL_FIELD_GRID_COL_ATR, PREIX_STYLE_FIELD_GRID_COL_ATR },
				new Object[] { dataIndex, width, xtype, labelField, preixStyleField });
		return tagGridColunm;
	}

	public static GenericTagHandler createFieldCheckbox(String checkedValue, String uncheckedValue) {
		return new GenericTagHandler("ext-field-checkbox", "checked-value", "unchecked-value", checkedValue, uncheckedValue);
	}

	public static GenericTagHandler createGridColunm(String dataIndex, String width, String xtype) {
		GenericTagHandler tagGridColunm = new GenericTagHandler(EXT_GRID_COLUMN_TAG, new String[] { DATA_INDEX_ATR, WIDTH_ATR, XTYPE_ATR }, new Object[] { dataIndex, width, xtype });
		return tagGridColunm;
	}

	public static GenericTagHandler createPanel(String id, String titleExp, String columnWidth) {
		GenericTagHandler tagPanel = new GenericTagHandler(EXT_PANEL_TAG, ID_ATTR_NAME, TITLE_EXP_ATR, COLUMN_WIDTH_ATR, id, titleExp, columnWidth);
		return tagPanel;
	}

	public static GenericTagHandler createTreeStore(String modelName, String idProperty, String parentProperty, boolean isExpandAll) {
		GenericTagHandler tagStore = new GenericTagHandler(EXT_STORE_TAG, new String[] { MODEL_NAME_STORE_ATR, ID_PROPERTY_STORE_ATR, PARENT_PROPERTY_STORE_ATR, IS_EXPAND_ALL_STORE_ATR },
				new Object[] { modelName, idProperty, parentProperty, isExpandAll });
		return tagStore;
	}

	public static GenericTagHandler createGridRowHeader() {
		return new GenericTagHandler(EXT_GRID_ROW_HEADER_TAG);
	}

	public static GenericTagHandler createGridColumnHeader(String colspan, String header, String align) {
		return new GenericTagHandler(GRID_COLUMN_HEADER_TAG, COLSPAN_ATR, HEADER_ATR, ALIGN_ATR, colspan, header, align);
	}

	public static GenericTagHandler addGridColumnHeader(String colspan, String header) {
		return new GenericTagHandler(colspan, header, ALIGN_CENTER_ATR_VALUE);
	}
}
