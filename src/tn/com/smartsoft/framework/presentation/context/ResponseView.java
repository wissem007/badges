package tn.com.smartsoft.framework.presentation.context;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.log.Logger;

public abstract class ResponseView {
	protected WebContext		context;
	protected String			contentType;
	protected Properties		headers		= new Properties();
	// private static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
	// private static final String DEFAULT_HEADER_CONTENT_DISPOSITION = "attachment; filename";
	private Map<String, String>	parameters	= new HashMap<String, String>();
	protected Logger			log			= Logger.getLogger(getClass());
	
	public ResponseView() {
		super();
	}
	
	public String getParameter(String name) {
		return this.parameters.get(name);
	}
	
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	protected void populateFileName(String fileName, String extension) {
		populateFileName(false, fileName, extension);
	}
	
	protected void populateFileName(boolean isView, String fileName, String extension) {
		String name = isView ? "filename" : "attachment; filename";
		if (StringUtils.isBlank(fileName)) {
			this.headers.setProperty("Content-Disposition", name + "=tmp_" + System.currentTimeMillis() + "." + extension);
		} else {
			this.headers.setProperty("Content-Disposition", name + "=" + fileName + "_" + System.currentTimeMillis() + "." + extension);
		}
	}
	
	protected void populateContentLength(int contentLength) {
		context.response().setHeader("Content-Length", "" + contentLength);
	}
	
	protected void populateContentType() {
		context.response().setHeader("Content-Type", contentType);
	}
	
	protected void populateHeaders() {
		for (Enumeration<?> en = headers.propertyNames(); en.hasMoreElements();) {
			String key = (String) en.nextElement();
			context.response().setHeader(key, headers.getProperty(key));
		}
	}
	
	public void setHeader(String name, String value) {
		headers.setProperty(name, value);
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public void setContext(WebContext context) {
		this.context = context;
	}
	
	public abstract void response(Object model);
	
}
