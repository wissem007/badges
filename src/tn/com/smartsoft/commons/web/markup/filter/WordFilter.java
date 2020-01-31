package tn.com.smartsoft.commons.web.markup.filter;

import java.util.StringTokenizer;

import tn.com.smartsoft.commons.web.markup.Filter;

public class WordFilter extends java.util.Hashtable implements Filter {
	public WordFilter() {
		super(4);
	}

	public String getInfo() {
		return "WordFilter";
	}

	public String process(String to_process) {
		if (to_process == null || to_process.length() == 0)
			return "";

		String tmp = "";
		// the true at the end is the key to making it work
		StringTokenizer st = new StringTokenizer(to_process, " ", true);
		StringBuffer newValue = new StringBuffer(to_process.length() + 50);
		while (st.hasMoreTokens()) {
			tmp = st.nextToken();
			if (hasAttribute(tmp))
				newValue.append((String) get(tmp));
			else
				newValue.append(tmp);
		}
		return newValue.toString();
	}

	public Filter addAttribute(String attribute, Object entity) {
		put(attribute, entity);
		return (this);
	}

	public Filter removeAttribute(String attribute) {
		try {
			remove(attribute);
		} catch (NullPointerException exc) { // don't really care if this
			// throws a null pointer
			// exception
		}
		return (this);
	}

	public boolean hasAttribute(String attribute) {
		return (containsKey(attribute));
	}
}
