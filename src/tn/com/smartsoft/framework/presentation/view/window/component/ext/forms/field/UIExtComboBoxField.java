package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.comboBox.UIExtComboBoxItem;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtComboBoxFieldRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtFieldStore;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtStore;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public class UIExtComboBoxField extends UIExtTextField {
	
	/**
	 * 
	 */
	private static final long								serialVersionUID	= 1L;
	private String											storeId;
	private UIExtStore										store;
	private boolean											isLocalStore		= false;
	private String											tpl;
	private UIDefaultComponentHandler<UIExtComboBoxItem>	itemHandler			= new UIDefaultComponentHandler<UIExtComboBoxItem>(this);
	private String											displayProperty;
	private String											mode;
	private Boolean											forceSelection		= true;
	private String											displayRowCondition;
	private Boolean											suggestOption		= true;
	private String											filterField;
	private String											matchChar;
	private String											keyProperty;
	private String											keyFieldIndex;
	
	public String getFilterField() {
		return filterField;
	}
	
	public void setFilterField(String filterField) {
		this.filterField = filterField;
	}
	
	public String getMatchChar() {
		return matchChar;
	}
	
	public void setMatchChar(String matchChar) {
		this.matchChar = matchChar;
	}
	
	public Boolean isForceSelection() {
		return forceSelection;
	}
	
	public Boolean getSuggestOption() {
		return suggestOption;
	}
	
	public void setSuggestOption(Boolean suggestOption) {
		this.suggestOption = suggestOption;
	}
	
	public void setForceSelection(Boolean forceSelection) {
		this.forceSelection = forceSelection;
	}
	
	public String getDisplayRowCondition() {
		return displayRowCondition;
	}
	
	public void setDisplayRowCondition(String displayRowCondition) {
		this.displayRowCondition = displayRowCondition;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public UIExtComboBoxField() {
		super();
	}
	
	public String getDisplayProperty() {
		return displayProperty;
	}
	
	public void setDisplayProperty(String displayProperty) {
		this.displayProperty = displayProperty;
	}
	
	public void removeItem(String[] id) {
		itemHandler.removeItem(id);
	}
	
	public void removeItem(int start, int end) {
		itemHandler.removeItem(start, end);
	}
	
	public void removeItem(int start) {
		itemHandler.removeItem(start);
	}
	
	public void addItem(UIExtComboBoxItem component) {
		itemHandler.addItem(component);
	}
	
	public List<String> itemIds() {
		return itemHandler.itemIds();
	}
	
	public int itemSize() {
		return itemHandler.itemSize();
	}
	
	public UIExtComboBoxItem getItem(String id) {
		return itemHandler.getItem(id);
	}
	
	public String getTpl() {
		return tpl;
	}
	
	public void setTpl(String tpl) {
		this.tpl = tpl;
	}
	
	public String getStoreId() {
		return storeId;
	}
	
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	public UIExtStore getStore() {
		if (store == null) {
			store = (UIExtStore) getWindow().findChild(getStoreId());
		}
		return store;
	}
	
	public Object getSelectedRecord() {
		if (isParentGridColumn()) {
			return null;
		}
		return getStore().findById(getValue());
	}
	
	public Object getSelectedRecord(int row) {
		if (!isParentGridColumn()) {
			return null;
		}
		return getStore().findById(getValue(row));
	}
	
	public int getSelectedRecordIndex() {
		if (isParentGridColumn()) {
			return -1;
		}
		return getStore().findIndexById(getValue());
	}
	
	public int getSelectedRecordIndex(int row) {
		if (!isParentGridColumn()) {
			return -1;
		}
		return getStore().findIndexById(getValue(row));
	}
	
	public Object getDisplayValue(int row) {
		if (!isParentGridColumn()) {
			return null;
		}
		if (StringUtils.isNotBlank(getDisplayProperty())) {
			UIExtFieldStore fieldStore = ((UIExtGrid) getParent()).getStore().getField(getDisplayProperty());
			if (fieldStore != null)
				return fieldStore.getValue(row);
		}
		int index = getSelectedRecordIndex(row);
		if (index != -1)
			return getStore().getField(getDisplayField()).getValue(index);
		return null;
	}
	
	public String getDisplayCustomValue(int row) {
		if (!isParentGridColumn()) {
			return null;
		}
		if (StringUtils.isNotBlank(getDisplayProperty())) {
			UIExtFieldStore fieldStore = ((UIExtGrid) getParent()).getStore().getField(getDisplayProperty());
			if (fieldStore != null)
				return fieldStore.getCustomValue(row);
		}
		int index = getSelectedRecordIndex(row);
		if (index != -1)
			return getStore().getField(getDisplayField()).getCustomValue(index);
		else
			return "";
	}
	
	public Object getDisplayValue() {
		if (isParentGridColumn()) {
			return null;
		}
		if (StringUtils.isNotBlank(getDisplayProperty())) {
			return getUserAction().getModel().getValue(getDisplayProperty());
		}
		int index = getSelectedRecordIndex();
		if (index != -1)
			return getStore().getField(getDisplayField()).getValue(index);
		return null;
	}
	
	public String getDisplayCustomValue() {
		if (isParentGridColumn()) {
			return null;
		}
		if (StringUtils.isNotBlank(getDisplayProperty())) {
			return getUserAction().getModel().getCustomValue(getDisplayProperty());
		}
		int index = getSelectedRecordIndex();
		if (index != -1)
			return getStore().getField(getDisplayField()).getCustomValue(index);
		else
			return "";
	}
	
	public String getKeyProperty() {
		return keyProperty;
	}
	
	public void setKeyProperty(String keyProperty) {
		this.keyProperty = keyProperty;
	}
	
	public String getKeyFieldIndex() {
		return keyFieldIndex;
	}
	
	public void setKeyFieldIndex(String keyFieldIndex) {
		this.keyFieldIndex = keyFieldIndex;
	}
	
	public void setStore(UIExtStore store) {
		if (store != null) {
			store.setParent(this);
			this.store = store;
			isLocalStore = true;
			this.storeId = store.getId();
		}
	}
	
	public boolean isLocalStore() {
		return isLocalStore;
	}
	
	public UIRender getRender() {
		return new UIExtComboBoxFieldRender(this);
	}
}
