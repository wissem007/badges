package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import java.util.List;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtRadioFieldRender;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public class UIExtRadioField extends UIExtTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String libelleExp;
	private String columns;
	private String itemCls;
	private Boolean isVertical = Boolean.FALSE;
	private UIDefaultComponentHandler<UIExtRadio> checkboxs = new UIDefaultComponentHandler<UIExtRadio>(this);

	public String getItemCls() {
		return itemCls;
	}

	public void setItemCls(String itemCls) {
		this.itemCls = itemCls;
	}

	public String getLibelleExp() {
		return libelleExp;
	}

	public void addItem(UIExtRadio component) {
		checkboxs.addItem(component);
	}

	public UIExtRadio findItem(String id) {
		return checkboxs.findItem(id);
	}

	public UIExtRadio getItem(String id) {
		return checkboxs.getItem(id);
	}

	public List<UIExtRadio> getItems() {
		return checkboxs.getItems();
	}

	public List<String> itemIds() {
		return checkboxs.itemIds();
	}

	public int itemSize() {
		return checkboxs.itemSize();
	}

	public void removeItem(String id) {
		checkboxs.removeItem(id);
	}

	public void setLibelleExp(String libelleExp) {
		this.libelleExp = libelleExp;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public Boolean isVertical() {
		return isVertical;
	}

	public void setVertical(Boolean isVertical) {
		this.isVertical = isVertical;
	}

	public boolean isChecked(String radioValue) {
		Object value = getValue();
		if (value == null && radioValue == null) {
			return true;
		} else if (value == null || radioValue == null) {
			return false;
		}
		return value.toString().equals(radioValue);
	}

	public UIRender getRender() {
		return new UIExtRadioFieldRender(this);
	}

}
