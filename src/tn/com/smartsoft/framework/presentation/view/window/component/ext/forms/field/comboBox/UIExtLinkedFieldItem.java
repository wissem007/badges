package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.comboBox;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtComboBoxField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGridColumn;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtFieldStore;

public class UIExtLinkedFieldItem extends UIExtComboBoxItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fieldRef;
	private String align = "left";
	protected String width;
	protected Boolean isDispaly = true;
	private String libelleExp;
	private Integer columnRef = -1;
	
	public UIExtLinkedFieldItem() {
		super();
		this.handlerValue = "defaultSuggestHandlerValue";
	}
	
	public UIExtField getField() {
		if (StringUtils.isNotBlank(fieldRef)) {
			UIComponent cmp = getWindow().findChild(fieldRef);
			if (cmp == null)
				throw new TechnicalException("invalid fieldRef :" + fieldRef);
			if (cmp instanceof UIExtField) {
				return (UIExtField) cmp;
			}
			throw new TechnicalException("invalid fieldRef :" + fieldRef);
		}
		if (columnRef != null) {
			UIExtComboBoxField comboBoxField = getParentCombo();
			if (!comboBoxField.isParentGridColumn())
				throw new TechnicalException("invalid columnRef :" + columnRef + "--> is not column field");
			UIExtGrid extGrid = ((UIExtGrid) comboBoxField.getParentGridColumn().getParent());
			if (extGrid.getColumnIds().size() <= columnRef.intValue() || columnRef.intValue() < 0) {
				throw new TechnicalException("invalid columnRef :" + columnRef + "--> invalid index");
			}
			UIExtGridColumn extGridColumn = extGrid.getColumn(extGrid.getColumnIds().get(columnRef.intValue()));
			return extGridColumn.getEditor();
		}
		throw new TechnicalException("invalid fieldRef :" + fieldRef);
	}
	
	public UIExtFieldStore getFieldStore() {
		return getParentCombo().getStore().getField(getField().getDisplayField());
	}
	
	public Boolean isDispaly() {
		return isDispaly;
	}
	
	public void setDispaly(Boolean isDispaly) {
		this.isDispaly = isDispaly;
	}
	
	public void setWidth(String width) {
		this.width = width;
	}
	
	public String getLibelleExp() {
		return libelleExp;
	}
	
	public void setLibelleExp(String libelleExp) {
		this.libelleExp = libelleExp;
	}
	
	public Integer getColumnRef() {
		return columnRef;
	}
	
	public void setColumnRef(Integer columnRef) {
		this.columnRef = columnRef;
	}
	
	public String getLibelle() {
		return StringUtils.isBlank(libelleExp) ? getFieldStore().model().getLibelle() : getWindow().evalExpressionToString(getLibelleExp());
	}
	
	public String getWidth() {
		if (StringUtils.isNotBlank(width))
			return width;
		return getField().getWidth();
	}
	
	public String getAlign() {
		return align;
	}
	
	public void setAlign(String align) {
		this.align = align;
	}
	
	public String getFieldRef() {
		return fieldRef;
	}
	
	public void setFieldRef(String fieldRef) {
		this.fieldRef = fieldRef;
	}
	
	public String render() {
		JsMap items = new JsMap();
		String[] atts = geAttributesName();
		for (int i = 0; i < atts.length; i++) {
			String value = getAttribut(atts[i]);
			items.addValue(atts[i], value, !StringUtils.isNumeric(value));
		}
		items.addStringValue("header", getLibelle());
		items.addStringValue("fieldRef", getFieldRef());
		items.addIntValue("columnRef", getColumnRef());
		items.addStringValue("dataIndex", getField().getDisplayField());
		items.addValue("width", getWidth(), !StringUtils.isNumeric(getWidth()));
		items.addStringValue("align", getAlign());
		items.addBooleanValue("dispaly", isDispaly());
		items.addObjectValue("handlerValue", getHandlerValue());
		return items.toJs();
	}
}
