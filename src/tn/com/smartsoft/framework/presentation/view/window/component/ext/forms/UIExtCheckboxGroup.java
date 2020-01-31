package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms;

import java.util.List;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtCheckboxField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.render.UIExtCheckboxGroupRender;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public class UIExtCheckboxGroup extends UIExtComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String libelleExp;
	private String columns;
	private String itemCls;
	private Boolean isVertical = Boolean.FALSE;
	private UIDefaultComponentHandler<UIExtCheckboxField> checkboxs = new UIDefaultComponentHandler<UIExtCheckboxField>(this);

	public String getItemCls() {
		return itemCls;
	}

	public void setItemCls(String itemCls) {
		this.itemCls = itemCls;
	}

	public String getLibelleExp() {
		return libelleExp;
	}

	public String getLibelle() {
		return getWindow().evalExpressionToString(getLibelleExp());
	}

	public void addItem(UIExtCheckboxField component) {
		checkboxs.addItem(component);
	}

	public UIExtCheckboxField findItem(String id) {
		return checkboxs.findItem(id);
	}

	public UIExtCheckboxField getItem(String id) {
		return checkboxs.getItem(id);
	}

	public List<UIExtCheckboxField> getItems() {
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

	public UIRender getRender() {
		return new UIExtCheckboxGroupRender(this);
	}

}
