package tn.com.smartsoft.framework.presentation.servlets;

import javax.servlet.ServletContext;

import tn.com.smartsoft.commons.web.utils.Configuration;

public class ServletContextConfiguration extends Configuration {
	private final String name;
	private ServletContext context;

	public ServletContextConfiguration(String prefix, ServletContext context) {
		this.name = prefix;
		this.context = context;
	}

	public String getName() {
		return name;
	}

	public Configuration getChild(String child) {
		String childName = postfixWith(child);
		String value = context.getInitParameter(childName);
		if (value == null) {

		}
		return new ServletContextConfiguration(childName, context);
	}

	public Configuration[] getChildren(String name) {
		return new Configuration[] { getChild(name) };
	}

	public String getAttribute(String paramName) {
		String attributeName = postfixWith(paramName);
		String value = context.getInitParameter(attributeName);
		if (value == null) {

		}
		return value;
	}

	public String getValue() {
		String value = context.getInitParameter(name);
		if (value == null) {

		}
		return value;
	}

	private String postfixWith(String child) {
		return name + '.' + child;
	}
}
