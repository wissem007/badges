package tn.com.smartsoft.commons.web.utils;

import java.util.Locale;

public abstract class Configuration {

	public abstract String getName();

	public abstract Configuration getChild(String child);

	public abstract Configuration[] getChildren(String name);

	public abstract String getAttribute(String paramName);

	public abstract String getValue();

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

	public String getAttribute(String name, String defaultValue) {
		try {
			return getAttribute(name);
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

	public String getValue(String defaultValue) {
		try {
			return getValue();
		} catch (Exception e) {
			return defaultValue;
		}
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
}
