package tn.com.smartsoft.framework.presentation.view.window.component.ext.charts;

import java.util.List;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtBoxComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.charts.render.UIExtPieChartRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.comboBox.UIExtComboBoxItem;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtStore;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public class UIExtPieChart extends UIExtBoxComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String storeId;
	private UIExtStore store;
	private boolean isLocalStore = false;
	private UIDefaultComponentHandler<UIExtComboBoxItem> itemHandler = new UIDefaultComponentHandler<UIExtComboBoxItem>(this);
	private String dataField;
	private String categoryField;
	private String styleColorsProperty;
	private String displayLegend = "bottom";
	private String paddingLegend = "5";
	private String fontLegend;
	private String familyLegend;
	private String sizeLegend;
	
	public String getDisplayLegend() {
		return displayLegend;
	}
	
	public void setDisplayLegend(String displayLegend) {
		this.displayLegend = displayLegend;
	}
	
	public String getPaddingLegend() {
		return paddingLegend;
	}
	
	public void setPaddingLegend(String paddingLegend) {
		this.paddingLegend = paddingLegend;
	}
	
	public String getFontLegend() {
		return fontLegend;
	}
	
	public void setFontLegend(String fontLegend) {
		this.fontLegend = fontLegend;
	}
	
	public String getFamilyLegend() {
		return familyLegend;
	}
	
	public void setFamilyLegend(String familyLegend) {
		this.familyLegend = familyLegend;
	}
	
	public String getSizeLegend() {
		return sizeLegend;
	}
	
	public void setSizeLegend(String sizeLegend) {
		this.sizeLegend = sizeLegend;
	}
	
	public String getStyleColorsProperty() {
		return styleColorsProperty;
	}
	
	public void setStyleColorsProperty(String styleColorsProperty) {
		this.styleColorsProperty = styleColorsProperty;
	}
	
	public String getDataField() {
		return dataField;
	}
	
	public void setDataField(String dataField) {
		this.dataField = dataField;
	}
	
	public String getCategoryField() {
		return categoryField;
	}
	
	public void setCategoryField(String categoryField) {
		this.categoryField = categoryField;
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
		return new UIExtPieChartRender(this);
	}
}
