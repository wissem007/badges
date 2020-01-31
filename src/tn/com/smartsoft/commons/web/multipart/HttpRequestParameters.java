package tn.com.smartsoft.commons.web.multipart;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import tn.com.smartsoft.commons.web.multipart.MultipartFile;
import tn.com.smartsoft.commons.web.multipart.MultipartHttpServletRequest;
import tn.com.smartsoft.commons.web.multipart.MultipartResolver;
import tn.com.smartsoft.commons.web.multipart.MultipartResolverFactory;

public class HttpRequestParameters implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected HttpServletRequest request;
	private boolean isMultipart = false;

	public HttpRequestParameters(HttpServletRequest request) {
		super();
		MultipartResolver multipartResolver = MultipartResolverFactory.create();
		this.request = request;
		if (multipartResolver.isMultipart(request)) {
			this.request = multipartResolver.resolveMultipart(request);
			isMultipart = true;
		}
	}

	public boolean containsParameter(String name) {
		return request.getParameter(name) != null;
	}

	public MultipartFile getFile(String name) {
		if (isMultipart())
			return ((MultipartHttpServletRequest) request).getFile(name);
		return null;
	}

	public Map<String, MultipartFile> getFileMap() {
		if (isMultipart())
			return ((MultipartHttpServletRequest) request).getFileMap();
		return null;
	}

	public Iterator<String> getFileNames() {
		if (isMultipart())
			return ((MultipartHttpServletRequest) request).getFileNames();
		return null;
	}

	public String getParameter(String name) {
		return (String) request.getParameter(name);
	}

	public String getParameter(String name, String defaultValue) {
		if (request.getParameter(name) == null)
			return defaultValue;
		return getParameter(name);

	}

	public boolean getParameterAsBoolean(String name) {
		return Boolean.valueOf(getParameter(name)).booleanValue();
	}

	public boolean getParameterAsBoolean(String name, boolean defaultValue) {
		if (request.getParameter(name) == null)
			return defaultValue;
		return getParameterAsBoolean(name);

	}

	public int getParameterAsInteger(String name) {
		return Integer.parseInt(getParameter(name));
	}

	public int getParameterAsInteger(String name, int defaultValue) {
		if (request.getParameter(name) == null)
			return defaultValue;
		return getParameterAsInteger(name);
	}

	public String[] getParameterAsStrings(String name) {
		return request.getParameterValues(name);
	}

	@SuppressWarnings("unchecked")
	public String[] getParameterNames() {
		Collection result = request.getParameterMap().keySet();
		return (String[]) result.toArray(new String[result.size()]);
	}

	public boolean isMultipart() {
		return isMultipart;
	}

	public boolean getParameterAsBoolean(String name, String trueValue, boolean defaultValue) {
		if (request.getParameter(name) == null)
			return defaultValue;
		return request.getParameter(name).equalsIgnoreCase(trueValue);
	}

}
