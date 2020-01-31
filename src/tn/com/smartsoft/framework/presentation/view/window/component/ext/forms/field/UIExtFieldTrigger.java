package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import tn.com.smartsoft.framework.presentation.view.window.UIGenericRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtFieldTriggerRender;

public class UIExtFieldTrigger extends UIGenericRendrableComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String iconCls;
	private String qtip;
	private String hideTrigger;
	
	public String getIconCls() {
		return iconCls;
	}
	
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public String getQtip() {
		return qtip;
	}
	
	public void setQtip(String qtip) {
		this.qtip = qtip;
	}
	
	public String getHideTrigger() {
		return hideTrigger;
	}
	
	public void setHideTrigger(String hideTrigger) {
		this.hideTrigger = hideTrigger;
	}
	
	public UIRender getRender() {
		return new UIExtFieldTriggerRender(this);
	}
	
}
