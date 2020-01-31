package tn.com.smartsoft.commons.xml.parser;

import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.converters.ClassConverter;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.xml.utils.ParserDefinition;

public abstract class XmlElement {
	private ParserDefinition parserDefinition;
	private String ressourceLocation;

	public String getRessourceLocation() {
		return ressourceLocation;
	}

	public XmlElement setRessourceLocation(String ressourceLocation) {
		this.ressourceLocation = ressourceLocation;
		return this;
	}

	public ParserDefinition getParserDefinition() {
		return parserDefinition;
	}

	public XmlElement setParserDefinition(ParserDefinition parserDefinition) {
		this.parserDefinition = parserDefinition;
		return this;
	}

	public String parseValue(String value) {
		if (parserDefinition != null)
			return parserDefinition.parse(value);
		return value;
	}

	public String getValue(String defaultValue) {
		String value = getValue();
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	public int getAttributeAsInteger(String name) {
		return Integer.parseInt(getAttribute(name));
	}

	public long getAttributeAsLong(String name) {
		return Long.parseLong(getAttribute(name));
	}

	public float getAttributeAsFloat(String name) {
		return Float.parseFloat(getAttribute(name));
	}

	public double getAttributeAsDouble(String name) {
		return Double.parseDouble(getAttribute(name));
	}

	public boolean getAttributeAsBoolean(String name) {
		return Boolean.valueOf(getAttribute(name)).booleanValue();
	}

	public Class<?> getAttributeAsClass(String name) {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null) {
				classLoader = ClassConverter.class.getClassLoader();
			}
			return (classLoader.loadClass(getAttribute(name)));
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	public Class<?> getAttributeAsClass(String name, Class<?> defaultValue) {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null) {
				classLoader = ClassConverter.class.getClassLoader();
			}
			return (classLoader.loadClass(getAttribute(name)));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public Locale getAttributeAsLocale(String name) {
		return new Locale(getAttribute(name));
	}

	public Locale getAttributeAsLocale(String name, Locale defaultValue) {
		try {
			return getAttributeAsLocale(name);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public int getAttributeAsInteger(String name, int defaultValue) {
		try {
			return getAttributeAsInteger(name);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public long getAttributeAsLong(String name, long defaultValue) {
		try {
			return getAttributeAsLong(name);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public float getAttributeAsFloat(String name, float defaultValue) {
		try {
			return getAttributeAsFloat(name);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public double getAttributeAsDouble(String name, double defaultValue) {
		try {
			return getAttributeAsDouble(name);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public boolean getAttributeAsBoolean(String name, boolean defaultValue) {
		try {
			return getAttributeAsBoolean(name);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public int getValueAsInteger() {
		return Integer.parseInt(getValue());
	}

	public float getValueAsFloat() {
		return Float.parseFloat(getValue());
	}

	public double getValueAsDouble() {
		return Double.parseDouble(getValue());
	}

	public boolean getValueAsBoolean() {
		return Boolean.valueOf(getValue()).booleanValue();
	}

	public long getValueAsLong() {
		return Long.parseLong(getValue());
	}

	public int getValueAsInteger(int defaultValue) {
		try {
			return getValueAsInteger();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public long getValueAsLong(long defaultValue) {
		try {
			return getValueAsLong();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public float getValueAsFloat(float defaultValue) {
		try {
			return getValueAsFloat();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public double getValueAsDouble(double defaultValue) {
		try {
			return getValueAsDouble();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public boolean getValueAsBoolean(boolean defaultValue) {
		try {
			return getValueAsBoolean();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public Object getAttributeAsObject(String name) {
		try {
			return getAttributeAsClass(name).newInstance();
		} catch (Exception e) {
			throw new TechnicalException(e);
		}

	}

	public Object getAttributeAsObject(String name, Object defaultValue) {
		try {
			return getAttributeAsObject(name);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public abstract String getAttribute(String name, String defaultValue);

	public abstract String getAttribute(String name);

	public abstract List<String> getAttributeNames();

	public abstract XmlElement getChild(String name);

	public abstract List<XmlElement> getChildren();

	public abstract List<XmlElement> getChildren(String name);

	public abstract String getChildValue(String name, String defaultValue);

	public abstract String getChildValue(String name);

	public abstract String getName();

	public abstract XmlElement getParent();

	public abstract String getValue();
}