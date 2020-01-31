package tn.com.smartsoft.commons.web.markup.filter;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import tn.com.smartsoft.commons.web.markup.Filter;

public class StringFilter extends java.util.Hashtable implements Filter {

	public StringFilter() {
		super(4);
	}

	public String getInfo() {
		return "StringFilter";
	}

	public String process(String to_process) {
		System.out.println("\nString to Process in StringFilter = " + to_process);
		String[] value = split(to_process);
		StringBuffer new_value = new StringBuffer();
		for (int x = 0; x < value.length; x++) {
			if (hasAttribute(value[x]))
				new_value.append((String) get(value[x]));
			else
				new_value.append(value[x]);
			if (x != value.length - 1)
				new_value.append(" ");
		}
		return (new_value.toString());
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

	private String[] split(String to_split) {

		if (to_split == null || to_split.length() == 0) {
			String[] array = new String[0];
			return array;
		}

		StringBuffer sb = new StringBuffer(to_split.length() + 50);
		StringCharacterIterator sci = new StringCharacterIterator(to_split);
		int length = 0;

		for (char c = sci.first(); c != CharacterIterator.DONE; c = sci.next()) {
			if (String.valueOf(c).equals(" "))
				length++;
			else if (sci.getEndIndex() - 1 == sci.getIndex())
				length++;
		}

		String[] array = new String[length];
		length = 0;
		String tmp = new String();
		for (char c = sci.first(); c != CharacterIterator.DONE; c = sci.next()) {
			if (String.valueOf(c).equals(" ")) {
				array[length] = tmp;
				tmp = new String();
				length++;
			} else if (sci.getEndIndex() - 1 == sci.getIndex()) {
				tmp = tmp + String.valueOf(sci.last());
				array[length] = tmp;
				tmp = new String();
				length++;
			} else
				tmp += String.valueOf(c);
		}
		return (array);
	}
}
