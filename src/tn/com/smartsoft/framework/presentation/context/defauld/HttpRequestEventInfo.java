package tn.com.smartsoft.framework.presentation.context.defauld;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import tn.com.smartsoft.commons.web.utils.WebUtil;
import tn.com.smartsoft.framework.presentation.context.RequestEventInfo;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopPartType;

public class HttpRequestEventInfo implements Serializable, RequestEventInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String SEP_PATH = ".";
	protected HttpServletRequest request;
	private String desktopPartType;
	private String componentSourceEvent;
	private String windowSourceEvent;
	private String event;

	public HttpRequestEventInfo(HttpServletRequest request) {
		super();
		this.request = request;
		this.resolvePath();
	}

	public HttpRequestEventInfo(String path) {
		this.resolvePath(path);
	}

	private void resolvePath() {
		resolvePath(WebUtil.getResourcePath(request));
	}

	public String getEvent() {
		return event;
	}

	private void resolvePath(String path) {
		String pagePath = StringUtils.substring(path, 1);
		this.desktopPartType = StringUtils.substring(pagePath, StringUtils.lastIndexOf(pagePath, ".") + 1);
		DesktopPartType desktopPartTypeOb = DesktopPartType.parse(desktopPartType);
		String[] sourceId = StringUtils.split(pagePath, SEP_PATH);
		if (!desktopPartTypeOb.isView()) {
			if (sourceId.length > 0)
				this.windowSourceEvent = sourceId[0];
			if (sourceId.length > 1)
				this.componentSourceEvent = sourceId[1];
			if (sourceId.length > 2)
				this.event = sourceId[2];
		} else {
			// if (sourceId.length != 2)
			// throw new TechnicalException("Invalid Path: " + path);
			this.windowSourceEvent = sourceId[1];
		}
	}

	public String componentSourceEvent() {
		return this.componentSourceEvent;
	}

	public String desktopPartType() {
		return this.desktopPartType;
	}

	public String windowSourceEvent() {
		return this.windowSourceEvent;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
