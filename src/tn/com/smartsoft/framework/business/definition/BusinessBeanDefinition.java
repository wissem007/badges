package tn.com.smartsoft.framework.business.definition;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.framework.beans.definition.BeanDefinition;

public class BusinessBeanDefinition extends BeanDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<DaoBeanRefDefinition> refDaoBeans = new ArrayList<DaoBeanRefDefinition>();
	private List<DaoBeanRefDefinition> refDaoSessions = new ArrayList<DaoBeanRefDefinition>();
	private List<DaoParseBeanRefDefinition> refDaoParseBeanRefs = new ArrayList<DaoParseBeanRefDefinition>();
	protected List<BusinessBeanRefDefinition> refBusinessBeans = new ArrayList<BusinessBeanRefDefinition>();

	public void addDaoBeanRef(DaoBeanRefDefinition daoBeanRef) {
		refDaoBeans.add(daoBeanRef);
	}

	public void addDaoSession(DaoBeanRefDefinition daoBeanRef) {
		refDaoSessions.add(daoBeanRef);
	}

	public void addDaoParseBeanRef(DaoParseBeanRefDefinition refDaoParseBean) {
		refDaoParseBeanRefs.add(refDaoParseBean);
	}

	public void addBusinessBeanRef(BusinessBeanRefDefinition businessBeanRefDefinition) {
		refBusinessBeans.add(businessBeanRefDefinition);
	}

	public List<BusinessBeanRefDefinition> getRefBusinessBeans() {
		return refBusinessBeans;
	}

	public List<DaoBeanRefDefinition> getRefDaoSessions() {
		return refDaoSessions;
	}

	public List<DaoBeanRefDefinition> getRefDaoBeans() {
		return refDaoBeans;
	}

	public List<DaoParseBeanRefDefinition> getRefDaoParseBeanRefs() {
		return refDaoParseBeanRefs;
	}
}
