package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.comboBox;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtFieldStore;

public class UIExtDispalyItem extends UIExtComboBoxItem {
	private static final long serialVersionUID = 1L;
	private String dataIndex;
	private String width = "50";
	private String align = "left";
	private String libelleExp;

	public UIExtDispalyItem() {
		super();
	}

	public UIExtDispalyItem(String dataIndex, String width, String align, String libelleExp) {
		super();
		this.dataIndex = dataIndex;
		this.width = width;
		this.align = align;
		this.libelleExp = libelleExp;
	}

	public String getLibelleExp() {
		return libelleExp;
	}

	public UIExtFieldStore getFieldStore() {
		return getParentCombo().getStore().getField(dataIndex);
	}

	public String getLibelle() {
		boolean isheaderStore = StringUtils.isBlank(libelleExp) && getFieldStore() != null;
		return isheaderStore ? getFieldStore().model().getLibelle() : getWindow().evalExpressionToString(getLibelleExp());
	}

	public void setLibelleExp(String libelleExp) {
		this.libelleExp = libelleExp;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public String render() {
		JsMap items = new JsMap();
		items.addStringValue("header", getLibelle());
		items.addStringValue("dataIndex", getDataIndex());
		items.addValue("width", getWidth(), !StringUtils.isNumeric(getWidth()));
		items.addStringValue("align", getAlign());
		items.addBooleanValue("dispaly", true);
		items.addObjectValue("handlerValue", getHandlerValue());
		return items.toJs();
	}
}
