package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtLegendsComponentRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtStore;

public class UIExtLegendsComponent extends UIExtComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String labelField;
	protected String colorField;
	private String storeId;
	private UIExtStore store;
	private Boolean isLocalStore = Boolean.TRUE;

	public void setStore(UIExtStore store) {
		if (store != null) {
			store.setParent(this);
			this.store = store;
			isLocalStore = Boolean.TRUE;
			this.storeId = store.getId();
		}
	}

	public UIExtStore getStore() {
		if (store == null) {
			store = (UIExtStore) getWindow().findChild(getStoreId());
		}
		if (store == null) {
			throw new TechnicalException("no store for id " + getStoreId());
		}
		return store;
	}

	public String getStoreId() {
		return storeId;
	}

	public String getLabelField() {
		return labelField;
	}

	public void setLabelField(String labelField) {
		this.labelField = labelField;
	}

	public String getColorField() {
		return colorField;
	}

	public void setColorField(String colorField) {
		this.colorField = colorField;
	}

	public Boolean getIsLocalStore() {
		return isLocalStore;
	}

	public void setIsLocalStore(Boolean isLocalStore) {
		this.isLocalStore = isLocalStore;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public Boolean isLocalStore() {
		return isLocalStore;
	}

	public UIRender getRender() {
		return new UIExtLegendsComponentRender(this);
	}
}
