package tn.com.smartsoft.commons.web.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class WebUtil {
	public static final String SERVLET_PATERN_COMMANDE = "event";
	public static final String SERVLET_PATERN_HEADER_AJAX = "Smart-Ajax";

	public static String getResourcePath(HttpServletRequest request) {
		String path = (String) request.getAttribute("javax.servlet.include.servlet_path");
		String info = (String) request.getAttribute("javax.servlet.include.path_info");
		if (path == null) {
			path = request.getServletPath();
			info = request.getPathInfo();
		}
		if (info != null) {
			path += info;
		}
		return path;
	}

	public static boolean isAjax(HttpServletRequest request) {
		boolean ajax = false;
		String ajaxHeader = request.getHeader(SERVLET_PATERN_HEADER_AJAX);
		if (!StringUtils.isEmpty(ajaxHeader)) {
			if (ajaxHeader.equalsIgnoreCase("true")) {
				ajax = true;
			}
		}
		return ajax;
	}

	public static boolean isCommandEvent(String servletPatern) {
		return servletPatern.equals(SERVLET_PATERN_COMMANDE);
	}

	public static String getAbsoluteURL(String URI, String scheme, String server, int port) {
		StringBuffer buffer = new StringBuffer();
		if (URI.indexOf(':') >= 0)
			return URI;
		if (URI.substring(0, 1).equals("//")) {
			buffer.append(scheme);
			buffer.append(':');
			buffer.append(URI);
			return buffer.toString();
		}
		buffer.append(scheme);
		buffer.append("://");
		buffer.append(server);
		if (port > 0) {
			buffer.append(':');
			buffer.append(port);
		}
		if (URI.charAt(0) != '/')
			buffer.append('/');
		buffer.append(URI);
		return buffer.toString();
	}
}
