package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.UIExtButton;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtPanelRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.toolbar.UIExtNavbar;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.toolbar.UIExtToolbar;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public class UIExtPanel extends UIExtContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String titleExp;
	private UIExtToolbar topBar;
	private UIExtToolbar bottomBar;
	private UIExtNavbar navbar;
	private Boolean frame;
	private Boolean border;
	private String bodyStyle;
	private String buttonAlign = "center";
	private String labelAlign = "left";
	private String labelWidth = "100";
	private UIDefaultComponentHandler<UIExtButton> buttonHandler = new UIDefaultComponentHandler<UIExtButton>(this);
	private UIDefaultComponentHandler<UIExtButton> toolsHandler = new UIDefaultComponentHandler<UIExtButton>(this);

	public String getLabelAlign() {
		return labelAlign;
	}

	public void setLabelAlign(String labelAlign) {
		this.labelAlign = labelAlign;
	}

	public String getLabelWidth() {
		return labelWidth;
	}

	public void setLabelWidth(String labelWidth) {
		this.labelWidth = labelWidth;
	}

	public String getBodyStyle() {
		return bodyStyle;
	}

	public void setBodyStyle(String bodyStyle) {
		this.bodyStyle = bodyStyle;
	}

	public UIExtToolbar getTopBar() {
		return topBar;
	}

	public void setTopBar(UIExtToolbar topBar) {
		if (topBar != null)
			topBar.setParent(this);
		this.topBar = topBar;
	}

	public UIExtToolbar getBottomBar() {
		return bottomBar;
	}

	public void setBottomBar(UIExtToolbar bottomBar) {
		if (bottomBar != null)
			bottomBar.setParent(this);
		this.bottomBar = bottomBar;
	}

	public String getTitleExp() {
		return titleExp;
	}

	public String getTitle() {
		if (StringUtils.isBlank(getTitleExp()))
			return null;
		Object libelle = getWindow().evalExpression(getTitleExp());
		if (libelle != null)
			return libelle.toString();
		else
			return "invalid expression label with :" + getTitleExp();
	}

	public void setTitleExp(String titleExp) {
		this.titleExp = titleExp;
	}

	public Boolean isBorder() {
		return border;
	}

	public void setBorder(Boolean border) {
		this.border = border;
	}

	public Boolean isFrame() {
		return frame;
	}

	public void setFrame(Boolean frame) {
		this.frame = frame;
	}

	public void addButton(UIExtButton button) {
		buttonHandler.addItem(button);
	}

	public List<String> getButtonIds() {
		return buttonHandler.itemIds();
	}

	public UIExtButton getButton(String id) {
		return (UIExtButton) buttonHandler.getItem(id);
	}

	public String getButtonAlign() {
		return buttonAlign;
	}

	public void setButtonAlign(String buttonAlign) {
		this.buttonAlign = buttonAlign;
	}

	public void addTool(UIExtButton button) {
		toolsHandler.addItem(button);
	}

	public List<String> getToolIds() {
		return toolsHandler.itemIds();
	}

	public UIExtButton getTool(String id) {
		return (UIExtButton) toolsHandler.getItem(id);
	}

	public UIExtNavbar getNavbar() {
		return navbar;
	}

	public void setNavbar(UIExtNavbar navbar) {
		this.navbar = navbar;
	}

	public UIRender getRender() {
		return new UIExtPanelRender(this);
	}

}
