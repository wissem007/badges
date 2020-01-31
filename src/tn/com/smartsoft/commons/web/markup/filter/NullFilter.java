package tn.com.smartsoft.commons.web.markup.filter;

import tn.com.smartsoft.commons.web.markup.Filter;

public class NullFilter implements Filter {

	public Filter addAttribute(String name, Object attribute) {
		return this;
	}

	public Filter removeAttribute(String name) {
		return this;
	}

	public boolean hasAttribute(String name) {
		return false;
	}

	public String process(String to_process) {
		return to_process;
	}

	public String getInfo() {
		return "info";
	}

}
