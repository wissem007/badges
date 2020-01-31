package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtDropDownFieldRender;

public class UIExtDropDownField extends UIExtTriggerField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIExtComponent component;
	private Boolean lazyInit;
	private String componentAlign;
	private Boolean allowBlur;
	private String mode;
	private String syncValue;
	
	public UIExtComponent getComponent() {
		return component;
	}
	
	public void setComponent(UIExtComponent component) {
		component.setParent(this);
		this.component = component;
	}
	
	public UIRender getRender() {
		return new UIExtDropDownFieldRender(this);
	}
	
	public Boolean getLazyInit() {
		return lazyInit;
	}
	
	public void setLazyInit(Boolean lazyInit) {
		this.lazyInit = lazyInit;
	}
	
	public String getComponentAlign() {
		return componentAlign;
	}
	
	public void setComponentAlign(String componentAlign) {
		this.componentAlign = componentAlign;
	}
	
	public Boolean getAllowBlur() {
		return allowBlur;
	}
	
	public void setAllowBlur(Boolean allowBlur) {
		this.allowBlur = allowBlur;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getSyncValue() {
		return syncValue;
	}
	
	public void setSyncValue(String syncValue) {
		this.syncValue = syncValue;
	}
}
