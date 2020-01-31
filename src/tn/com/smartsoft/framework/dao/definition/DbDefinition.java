package tn.com.smartsoft.framework.dao.definition;

import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;

public interface DbDefinition {
	public DataBaseDefinition getDataBaseDefinition();

	public EntiteBean getEntiteBean(Class<?> entiteClass);

	public DaoBeanDefinition getDaoBeanDefinition(ComponentId daoBeanId);

	public DaoParseBeanDefinition getDaoParseBeanDefinition(ComponentId daoParseBeanId);
}
