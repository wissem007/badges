package tn.com.smartsoft.framework.presentation.view.window.component.ext.store;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.action.model.PropertyModel;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;

public class UIExtFieldStore extends UIComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String property;
	private Boolean useNull;
	private String sortType;
	private Boolean isAsc;
	private String jsConvert;
	private Boolean isIndexed = Boolean.FALSE;

	public PropertyModel model() {
		property = StringUtils.remove(property, "[$i]");
		return (PropertyModel) getUserAction().findPropertyModel(getParentStore().getModelName() + "." + property);
	}

	public Boolean isIndexed() {
		return isIndexed;
	}

	public void setIndexed(Boolean isIndexed) {
		this.isIndexed = isIndexed;
	}

	public UIExtStore getParentStore() {
		UIExtStore store = (UIExtStore) getParent();
		return store;
	}

	public Object getValue(int index) {
		return getUserAction().getModel().getValue(getIndexedProperty(index));
	}

	public String getCustomValue(int index) {
		return getUserAction().getModel().getCustomValue(getIndexedProperty(index));
	}

	public String getIndexedProperty(int index) {
		return getIndexedProperty(property, index);
	}

	public String getIndexedProperty(String property, int index) {
		if (StringUtils.isBlank(property))
			return null;
		UIExtStore store = getParentStore();
		property = StringUtils.remove(property, "[$i]");
		String name = store.getModelName() + "[" + index + "]" + "." + property;
		return name;
	}

	public void setCustomValue(int index, Object value) {
		getUserAction().getModel().setCustomValue(getIndexedProperty(index), value);
	}

	public void setValue(int index, Object value) {
		getUserAction().getModel().setValue(getIndexedProperty(index), value);
	}

	public String getProperty() {
		property = StringUtils.remove(property, "[$i]");
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Boolean isUseNull() {
		return useNull;
	}

	public void setUseNull(Boolean useNull) {
		this.useNull = useNull;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getJsConvert() {
		return jsConvert;
	}

	public void setJsConvert(String jsConvert) {
		this.jsConvert = jsConvert;
	}

	public Boolean isAsc() {
		return isAsc;
	}

	public void setAsc(Boolean isAsc) {
		this.isAsc = isAsc;
	}
}
