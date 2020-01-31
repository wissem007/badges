package tn.com.smartsoft.iap.dictionary.graphique.messageType.business;

import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.dao.DaoParseBean;

public class MessageTypeBusiness extends GenericEntiteBusiness {

	private DaoParseBean parentSubModule;

	public DaoParseBean getParseListSubModuleBean() {
		return parentSubModule;
	}

	public void setParseListSubModuleBean(DaoParseBean parseListSubModuleBean) {
		this.parentSubModule = parseListSubModuleBean;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

}
