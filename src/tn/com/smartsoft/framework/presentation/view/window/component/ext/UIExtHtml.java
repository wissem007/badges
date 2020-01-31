package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtHtmlRender;

public class UIExtHtml extends UIExtAttrebutComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tag;
	private boolean isExperssionContent;
	private String content;
	private boolean isRenderEnd = true;
	protected boolean isRenderBegin = true;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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

	public boolean isRenderBegin() {
		return isRenderBegin;
	}

	public void setRenderBegin(boolean isRenderBegin) {
		this.isRenderBegin = isRenderBegin;
	}

	public boolean isRenderEnd() {
		return isRenderEnd;
	}

	public void setRenderEnd(boolean isRenderEnd) {
		this.isRenderEnd = isRenderEnd;
	}

	public boolean isExperssionContent() {
		return isExperssionContent;
	}

	public void setExperssionContent(boolean isExperssionContent) {
		this.isExperssionContent = isExperssionContent;
	}

	public UIRender getRender() {
		return new UIExtHtmlRender(this);
	}

}
