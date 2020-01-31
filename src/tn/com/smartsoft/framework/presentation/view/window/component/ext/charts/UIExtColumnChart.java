package tn.com.smartsoft.framework.presentation.view.window.component.ext.charts;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtBoxComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.charts.render.UIExtColumnChartRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.comboBox.UIExtComboBoxItem;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGridColumn;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtStore;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public class UIExtColumnChart extends UIExtBoxComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String storeId;
	private UIExtStore store;
	private boolean isLocalStore = false;
	private UIDefaultComponentHandler<UIExtComboBoxItem> itemHandler = new UIDefaultComponentHandler<UIExtComboBoxItem>(this);
	private String xField;
	private String yField;

	private List listYFields = new ArrayList();

	public void addYField(UIExtYFieldChart column) {
		if (StringUtils.isNotBlank(column.getyField()))
			listYFields.add(column);
	}

	public List getListYFields() {
		return listYFields;
	}

	public void setListYFields(List listYFields) {
		this.listYFields = listYFields;
	}

	public String getxField() {
		return xField;
	}

	public void setxField(String xField) {
		this.xField = xField;
	}

	public String getyField() {
		return yField;
	}

	public void setyField(String yField) {
		this.yField = yField;
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
		return new UIExtColumnChartRender(this);
	}
}
