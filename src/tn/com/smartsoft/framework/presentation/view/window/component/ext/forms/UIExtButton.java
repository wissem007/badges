package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtBoxComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.render.UIExtButtonRender;

public class UIExtButton extends UIExtBoxComponent{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private String				viewActionId;
	private Boolean				isIcon				= true;
	private Boolean				isLabel				= true;
	private Long				actionTemplateId;
	private String				iconPath;
	private String				text				= "invalid ViewActionId";
	
	public Long getActionTemplateId() {
		return actionTemplateId;
	}
	public void setActionTemplateId(Long actionTemplateId) {
		this.actionTemplateId = actionTemplateId;
	}
	public String getViewActionId() {
		return viewActionId;
	}
	public void setViewActionId(String viewActionId) {
		this.viewActionId = viewActionId;
	}
	public boolean isIcon() {
		return isIcon;
	}
	public void setIcon(Boolean isIcon) {
		this.isIcon = isIcon;
	}
	public Boolean isLabel() {
		return isLabel;
	}
	public void setLabel(Boolean isLabel) {
		this.isLabel = isLabel;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public UIRender getRender() {
		return new UIExtButtonRender(this);
	}
}
