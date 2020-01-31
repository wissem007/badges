package tn.com.smartsoft.framework.presentation.formater;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.iap.dictionary.graphique.userType.beans.UserTypeBean;

public abstract class Formater implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Logger log = Logger.getLogger(this);
	private String userTypeId;
	private List<Class<Object>> targetsClass;
	private String jsClassFormater;

	public abstract String getAsString(Object target);

	public abstract Object getAsObject(Object target, Class<?> targetClass);

	public abstract boolean validate(Object target, Class<?> targetClass);

	public UserTypeBean getUserType() {
		return ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getUserTypeBean(userTypeId);
	}

	public boolean isConvertableClass(Class<?> targetClass) {
		return targetsClass.contains(targetClass);
	}

	public String getJsClassFormater() {
		return jsClassFormater;
	}

	public void setJsClassFormater(String jsClassFormatter) {
		this.jsClassFormater = jsClassFormatter;
	}

	public List<Class<Object>> getTargetsClass() {
		if (targetsClass == null)
			targetsClass = new ArrayList<Class<Object>>();
		return targetsClass;
	}

	public Class<Object> getDefaultTargetClass() {
		return getTargetsClass().get(0);
	}

	public void setTargetsClass(List<Class<Object>> targetsClass) {
		this.targetsClass = targetsClass;
	}

	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}
}
