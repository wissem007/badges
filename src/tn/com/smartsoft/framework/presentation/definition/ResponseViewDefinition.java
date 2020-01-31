package tn.com.smartsoft.framework.presentation.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class ResponseViewDefinition implements IDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Class<?> className;
	private String contentType;
	private List<ResponseHeaderDefinition> headers = new ArrayList<ResponseHeaderDefinition>();
	private HashMap<String, String> parameters = new HashMap<String, String>();

	public String getName() {
		return name;
	}

	public void setParameter(String name, String value) {
		parameters.put(name, value);
	}

	public void setHeader(String name, String value) {
		headers.add(new ResponseHeaderDefinition(name, value));
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public List<ResponseHeaderDefinition> getHeaders() {
		return headers;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getClassName() {
		return className;
	}

	public void setClassName(Class<?> className) {
		this.className = className;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
