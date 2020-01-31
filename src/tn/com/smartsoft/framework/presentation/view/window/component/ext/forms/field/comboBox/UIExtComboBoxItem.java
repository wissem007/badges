package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.comboBox;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.UIGenericComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtComboBoxField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtFieldStore;

public class UIExtComboBoxItem extends UIGenericComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String handlerValue;

	public UIExtComboBoxField getParentCombo() {
		return (UIExtComboBoxField) getParent();
	}

	public UIExtFieldStore getFieldStore(String dataIndex) {
		return getParentCombo().getStore().getField(dataIndex);
	}

	public String getHandlerValue() {
		return handlerValue;
	}

	public void setHandlerValue(String handlerValue) {
		this.handlerValue = handlerValue;
	}

	public String render() {
		String[] atts = geAttributesName();
		JsMap items = new JsMap();
		for (int i = 0; i < atts.length; i++) {
			String value = getAttribut(atts[i]);
			items.addValue(atts[i], value);
		}
		items.addBooleanValue("dispaly", false);
		items.addObjectValue("handlerValue", getHandlerValue());
		return items.toJs();
	}
}
