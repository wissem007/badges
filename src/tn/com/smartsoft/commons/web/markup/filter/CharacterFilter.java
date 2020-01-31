package tn.com.smartsoft.commons.web.markup.filter;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import tn.com.smartsoft.commons.web.markup.Filter;
import tn.com.smartsoft.commons.web.markup.util.Entities;

public class CharacterFilter extends java.util.Hashtable implements Filter {
	{
		addAttribute("\"", Entities.QUOT);
		addAttribute("'", Entities.LSQUO);
		addAttribute("&", Entities.AMP);
		addAttribute("<", Entities.LT);
		addAttribute(">", Entities.GT);
	}

	public CharacterFilter() {
		super(4);
	}

	public String getInfo() {
		return "CharacterFilter";
	}

	public Filter addAttribute(String name, Object attribute) {
		this.put(name, attribute);
		return this;
	}

	public Filter removeAttribute(String name) {
		try {
			this.remove(name);
		} catch (Exception e) {}
		return this;
	}

	public boolean hasAttribute(String key) {
		return (this.containsKey(key));
	}

	public String process(String to_process) {
		if (to_process == null || to_process.length() == 0)
			return "";

		StringBuffer bs = new StringBuffer(to_process.length() + 50);
		StringCharacterIterator sci = new StringCharacterIterator(to_process);
		String tmp = null;

		for (char c = sci.first(); c != CharacterIterator.DONE; c = sci.next()) {
			tmp = String.valueOf(c);

			if (hasAttribute(tmp))
				tmp = (String) this.get(tmp);

			bs.append(tmp);
		}
		return (bs.toString());
	}

	public static void main(String[] args) {
		CharacterFilter CharacterFilter = new CharacterFilter();
		String e = CharacterFilter.process("< reerre \" erererer && erereer");
		System.out.println(e);
	}
}
