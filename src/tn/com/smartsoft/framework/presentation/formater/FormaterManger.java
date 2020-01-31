package tn.com.smartsoft.framework.presentation.formater;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.log.Logger;

public class FormaterManger implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String, Formater> mapedFormater = Collections.synchronizedMap(new LinkedHashMap<String, Formater>());
	protected Map<Class<Object>, String> mapedTypeClass = Collections.synchronizedMap(new LinkedHashMap<Class<Object>, String>());
	protected Logger log = Logger.getLogger(this);

	public void registerFormatter(String userType, Formater formater) {
		mapedFormater.put(userType, formater);
	}

	public Map<Class<Object>, String> getMapedTypeClass() {
		return mapedTypeClass;
	}

	public String getMapedTypeClass(Class<?> javaType) {
		return (String) mapedTypeClass.get(javaType);
	}

	public Map<String, Formater> getMapedFormater() {
		return mapedFormater;
	}

	public Formater getFormatter(Class<Object> targetType) {
		return getFormatter((String) mapedTypeClass.get(targetType));
	}

	public Formater getFormatter(String userType) {
		Formater formatter = (Formater) mapedFormater.get(userType);
		if (formatter == null)
			throw new TechnicalException("no Formatter from userType :" + userType);
		return formatter;
	}

	public boolean isConvertableClass(String userType, Class<?> targetType) {
		Formater formatter = getFormatter(userType);
		return formatter.isConvertableClass(targetType);
	}

	public String getAsString(String userType, Object value) {
		Formater formatter = getFormatter(userType);
		return formatter.getAsString(value);
	}

	public String getAsString(Object value) {
		if (value == null)
			return "";
		Class<?> targetType = value.getClass();
		if (mapedTypeClass.get(targetType) == null)
			targetType = String.class;
		Formater formatter = getFormatter((String) mapedTypeClass.get(targetType));
		return formatter.getAsString(value);
	}

	public Object getAsObject(String userType, Object value, Class<?> targetClass) {
		Formater formatter = getFormatter(userType);
		return formatter.getAsObject(value, targetClass);
	}

	public boolean validate(String userType, Object value, Class<?> targetClass) {
		Formater formatter = getFormatter(userType);
		return formatter.validate(value, null);
	}

}
