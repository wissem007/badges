package tn.com.smartsoft.framework.presentation.definition;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import tn.com.smartsoft.framework.beans.definition.BeanDefinition;
import tn.com.smartsoft.framework.business.definition.BusinessBeanRefDefinition;

public class ControleBeanDefinition extends BeanDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected List<BusinessBeanRefDefinition> refBusinessBeans = new ArrayList<BusinessBeanRefDefinition>();
	protected String extendsName;

	public ControleBeanDefinition() {
	}

	public void addBusinessBeanRef(BusinessBeanRefDefinition businessBeanRefDefinition) {
		refBusinessBeans.add(businessBeanRefDefinition);
	}

	public String getExtendsName() {
		return extendsName;
	}

	public void setExtendsName(String extendsName) {
		this.extendsName = extendsName;
	}

	public boolean isExtends() {
		return StringUtils.isNotBlank(extendsName);
	}

	public List<BusinessBeanRefDefinition> getRefBusinessBeans() {
		return refBusinessBeans;
	}

	public void copyTo(ControleBeanDefinition controleBeanDefinition) {
		super.copyTo(controleBeanDefinition);
		controleBeanDefinition.refBusinessBeans.addAll(refBusinessBeans);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
