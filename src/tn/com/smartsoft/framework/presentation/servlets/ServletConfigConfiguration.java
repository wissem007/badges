package tn.com.smartsoft.framework.presentation.servlets;

import javax.servlet.ServletConfig;

import tn.com.smartsoft.commons.web.utils.Configuration;

public class ServletConfigConfiguration extends Configuration {
	private String name;
	private ServletConfig servletConfig;

	public ServletConfigConfiguration(final String prefix, final ServletConfig servletConfig) {

		this.name = prefix;
		this.servletConfig = servletConfig;
	}

	public String getAttribute(final String name) {
		String _name = postfixWith(name);
		String _value = servletConfig.getInitParameter(_name);
		if (_value == null) {

		}
		return _value;
	}

	public Configuration getChild(final String child) {
		String _name = postfixWith(child);
		if (servletConfig.getInitParameter(_name) == null) {

		}
		return new ServletConfigConfiguration(_name, servletConfig);
	}

	public Configuration[] getChildren(final String name) {
		return new Configuration[] { getChild(name) };
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		String _value = servletConfig.getInitParameter(name);
		if (_value == null) {

		}
		return _value;

	}

	private String postfixWith(final String child) {
		return new StringBuffer().append(name).append('.').append(child).toString();
	}
}
