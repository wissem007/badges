package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import tn.com.smartsoft.framework.presentation.view.window.UIGenericRendrableComponent;

public abstract class UIExtComponent extends UIGenericRendrableComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String cls;
	protected String ctCls;
	protected UIExtAutoEl autoEl;
	protected String html;
	protected String tag;
	protected String anchor;
	protected boolean hidden = false;
	protected boolean isAddedToParent = true;
	
	public String getCls() {
		return cls;
	}
	
	public void setCls(String cls) {
		this.cls = cls;
	}
	
	public boolean isAddedToParent() {
		return isAddedToParent;
	}
	
	public void setAddedToParent(boolean isAddedToParent) {
		this.isAddedToParent = isAddedToParent;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public String getAnchor() {
		return anchor;
	}
	
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	
	public String getCtCls() {
		return ctCls;
	}
	
	public void setCtCls(String ctCls) {
		this.ctCls = ctCls;
	}
	
	public UIExtAutoEl getAutoEl() {
		return autoEl;
	}
	
	public void setAutoEl(UIExtAutoEl autoEl) {
		this.autoEl = autoEl;
	}
	
	public String getHtml() {
		return html;
	}
	
	public void setHtml(String html) {
		this.html = html;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
