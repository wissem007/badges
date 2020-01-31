package tn.com.smartsoft.framework.presentation.utils;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.context.defauld.HttpRequestEventInfo;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopElement;

public class ResolveEventPathUtils {
	public static String resolveEventPath(DesktopElement desktopElement, WebContext context, String page, String componentId, String event) {
		String path = "";
		boolean isin = false;
		if (StringUtils.isNotBlank(page)) {
			path = path + page;
			isin = true;
		}
		if (StringUtils.isNotBlank(componentId)) {
			if (isin)
				path = path + HttpRequestEventInfo.SEP_PATH;
			path = path + componentId;
			isin = true;
		}
		if (StringUtils.isNotBlank(event)) {
			isin = true;
			if (isin)
				path = path + HttpRequestEventInfo.SEP_PATH;
			path = path + event;
		}
		if (isin)
			path = path + HttpRequestEventInfo.SEP_PATH;
		path = path + desktopElement.desktopPartType();
		return context.getContextPath().getChildPath(path).getPath();
	}
}
