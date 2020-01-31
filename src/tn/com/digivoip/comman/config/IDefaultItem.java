package tn.com.digivoip.comman.config;

import tn.com.digivoip.framework.xml.XmlElement;

public interface IDefaultItem{

	XmlElement getRoot();
	/** ********************** composition pattern ********************* */
	XmlElement getElement(String pathToElement);
	XmlElement getChildElement(int index);
	int getChildCount();
	XmlElement getChildElement(String pathToElement, int index);
	boolean contains(String key);
	String get(String key);
	String getString(String pathToElement, String key);
	String getStringWithDefault(String pathToElement, String key, String defaultValue);
	void setString(String key, String newValue);
	void setString(String pathToElement, String key, String newValue);
	/** ************************** helper classes ************************** */
	int getInteger(String key);
	int getIntegerWithDefault(String key, int defaultValue);
	int getInteger(String pathToElement, String key);
	int getIntegerWithDefault(String pathToElement, String key, int defaultValue);
	void setInteger(String key, int value);
	void setInteger(String pathToElement, String key, int value);
	boolean getBooleanWithDefault(String key, boolean defaultValue);
	boolean getBoolean(String key);
	boolean getBoolean(String pathToElement, String key);
	boolean getBooleanWithDefault(String pathToElement, String key, boolean defaultValue);
	void setBoolean(String key, boolean value);
	void setBoolean(String pathToElement, String key, boolean value);
	boolean equals(Object obj);
	int hashCode();
	Object clone();
	String getStringWithDefault(String key, String defaultValue);
}