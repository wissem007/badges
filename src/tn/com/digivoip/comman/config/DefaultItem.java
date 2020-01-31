package tn.com.digivoip.comman.config;

import java.util.StringTokenizer;
import tn.com.digivoip.framework.xml.XmlElement;

public class DefaultItem implements Cloneable,IDefaultItem{

	XmlElement	root;

	public DefaultItem(final XmlElement theRoot) {
		this.root = theRoot;
	}
	public XmlElement getRoot() {
		return root;
	}
	public XmlElement getElement(final String pathToElement) {
		return getElement(pathToElement, true);
	}
	public XmlElement getElement(final String pathToElement, final boolean create) {
		XmlElement child = getRoot();
		final StringTokenizer tok = new StringTokenizer(pathToElement, "/"); //$NON-NLS-1$
		while (tok.hasMoreTokens()) {
			final String token = tok.nextToken();
			XmlElement e = child.getElement(token);
			if ((e == null) && create) {
				e = child.addSubElement(token);
			}
			child = e;
		}
		return child;
	}
	public XmlElement getChildElement(final int index) {
		return getRoot().getElement(index);
	}
	public int getChildCount() {
		return getRoot().count();
	}
	public XmlElement getChildElement(final String pathToElement, final int index) {
		return getRoot().getElement(pathToElement).getElement(index);
	}
	public boolean contains(final String key) {
		return getRoot().getAttributes().containsKey(key);
	}
	public String get(final String key) {
		return getRoot().getAttribute(key);
	}
	public String getString(final String pathToElement, final String key) {
		final XmlElement element = getElement(pathToElement);
		if (element != null) {
			if ((key == null) || (key.length() == 0)) { return element.getData(); }
			return element.getAttribute(key);
		}
		return null;
	}
	public void setString(final String key, final String newValue) {
		getRoot().addAttribute(key, newValue);
	}
	public void setString(final String pathToElement, final String key, final String newValue) {
		XmlElement element = getElement(pathToElement);
		if (element == null) {
			element = root.addSubElement(pathToElement);
		}
		if (key == null) {
			element.setData(newValue);
		} else {
			element.addAttribute(key, newValue);
		}
	}
	/** ************************** helper classes ************************** */
	public int getInteger(final String key) {
		final String value = get(key);
		return Integer.parseInt(value);
	}
	public int getIntegerWithDefault(final String key, final int defaultValue) {
		String value = get(key);
		if (value == null) {
			value = new Integer(defaultValue).toString();
			setString(key, value);
		}
		try {
			return Integer.parseInt(value);
		} catch (final NumberFormatException e) {
			return defaultValue;
		}
	}
	public int getInteger(final String pathToElement, final String key) {
		final String value = getString(pathToElement, key);
		return Integer.parseInt(value);
	}
	public int getIntegerWithDefault(final String pathToElement, final String key, final int defaultValue) {
		String value = getString(pathToElement, key);
		if (value == null) {
			value = new Integer(defaultValue).toString();
			setString(pathToElement, key, value);
		}
		int result = -1;
		try {
			result = Integer.parseInt(value);
		} catch (final NumberFormatException e) {
			// this is no integer value
			return defaultValue;
		}
		return result;
	}
	public void setInteger(final String key, final int value) {
		setString(key, Integer.toString(value));
	}
	public void setInteger(final String pathToElement, final String key, final int value) {
		setString(pathToElement, key, Integer.toString(value));
	}
	public boolean getBooleanWithDefault(final String key, final boolean defaultValue) {
		String value = get(key);
		if (value == null) {
			value = Boolean.toString(defaultValue);
			setString(key, value);
		}
		return Boolean.valueOf(value).booleanValue();
	}
	public boolean getBoolean(final String key) {
		final String value = get(key);
		return Boolean.valueOf(value).booleanValue();
	}
	public boolean getBoolean(final String pathToElement, final String key) {
		final String value = getString(pathToElement, key);
		return Boolean.valueOf(value).booleanValue();
	}
	public boolean getBooleanWithDefault(final String pathToElement, final String key, final boolean defaultValue) {
		String value = getString(pathToElement, key);
		if (value == null) {
			value = Boolean.valueOf(defaultValue).toString();
			setString(pathToElement, key, value);
		}
		return Boolean.valueOf(value).booleanValue();
	}
	public void setBoolean(final String key, final boolean value) {
		setString(key, value ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
	}
	public void setBoolean(final String pathToElement, final String key, final boolean value) {
		setString(pathToElement, key, value ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
	}
	public boolean equals(final Object obj) {
		boolean equal = false;
		if ((obj != null) && (obj instanceof IDefaultItem)) {
			final DefaultItem other = (DefaultItem) obj;
			if ((root == other.root) || ((root != null) && root.equals(other.root))) {
				equal = true;
			}
		}
		return equal;
	}
	public int hashCode() {
		int hashCode = 43;
		if (root != null) {
			hashCode += (root.hashCode() * 97);
		}
		return hashCode;
	}
	public Object clone() {
		try {
			final DefaultItem other = (DefaultItem) super.clone();
			other.root = (XmlElement) root.clone(); // make a deep copy
			return other;
		} catch (final CloneNotSupportedException cnse) {
			throw new InternalError("Could not clone DefaultItem: " + cnse); //$NON-NLS-1$
		}
	}
	public String getStringWithDefault(final String key, final String defaultValue) {
		String result = getRoot().getAttribute(key);
		if (result == null) {
			result = defaultValue;
		}
		return result;
	}
	public String getStringWithDefault(final String pathToElement, final String key, final String defaultValue) {
		final String value = getString(pathToElement, key);
		if (value == null) {
			setString(pathToElement, key, value);
			return defaultValue;
		}
		return value;
	}
	public void notifyObservers(final String path) {
		final XmlElement e = getElement(path);
		e.notifyObservers();
	}
}