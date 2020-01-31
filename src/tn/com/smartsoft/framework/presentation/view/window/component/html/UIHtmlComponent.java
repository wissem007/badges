package tn.com.smartsoft.framework.presentation.view.window.component.html;

import tn.com.smartsoft.framework.presentation.view.window.UIGenericRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.html.render.UIHtmlComponentRender;

public class UIHtmlComponent extends UIGenericRendrableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String htmlTag;

	public UIHtmlComponent(String htmlTag) {
		super();
		this.htmlTag = htmlTag;
	}

	public UIHtmlComponent() {

	}

	public String getCssClass() {
		return this.getAttribut("class");
	}

	public void setCssClass(String cssClass) {
		this.setAttribut("class", cssClass);
	}

	public String getStyle() {
		return this.getAttribut("style");
	}

	public void setStyle(String style) {
		this.setAttribut("style", style);
	}

	public String getTitle() {
		return this.getAttribut("title");
	}

	public void setTitle(String title) {
		this.setAttribut("title", title);
	}

	public String getDir() {
		return this.getAttribut("dir");
	}

	public void setDir(String dir) {
		this.setAttribut("dir", dir);
	}

	public String getAccesskey() {
		return this.getAttribut("accesskey");
	}

	public void setAccesskey(String accesskey) {
		this.setAttribut("accesskey", accesskey);
	}

	public String getTabIndex() {
		return this.getAttribut("tabIndex");
	}

	public void setTabIndex(String tabIndex) {
		this.setAttribut("tabIndex", tabIndex);
	}

	public void setId(String id) {
		this.setAttribut("id", id);
	}

	public String getId() {
		return this.getAttribut("id");
	}

	public String getHtmlTag() {
		return htmlTag;
	}

	public UIRender getRender() {
		return new UIHtmlComponentRender(this);
	}

}
