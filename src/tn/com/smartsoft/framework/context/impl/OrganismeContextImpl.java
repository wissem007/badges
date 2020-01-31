package tn.com.smartsoft.framework.context.impl;

import org.apache.commons.lang.SerializationUtils;
import tn.com.smartsoft.configGenerale.administration.organismeProprietaires.beans.OrganismeProprietairesBean;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.framework.context.OrganismeContext;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIModuleExplorer;

public class OrganismeContextImpl implements OrganismeContext{
	
	/**
	 * 
	 */
	private static final long			serialVersionUID	= 1L;
	private OrganismeProprietairesBean	organismeProprietairesBean;
	private UIModuleExplorer			moduleExplorer;
	private UserContext					userContext;
	
	public UIModuleExplorer getModuleExplorer() {
		return moduleExplorer;
	}
	public void setModuleExplorer(UIModuleExplorer moduleExplorer) {
		this.moduleExplorer = moduleExplorer;
	}
	public UserContext getUserContext() {
		return userContext;
	}
	public OrganismeContextImpl(OrganismeProprietairesBean organismeProprietairesBean, UserContext userContext) {
		super();
		this.organismeProprietairesBean = organismeProprietairesBean;
		this.userContext = userContext;
	}
	public OrganismeBean getOrganismeBean() {
		return (OrganismeBean) SerializationUtils.clone(organismeProprietairesBean.getOrganisme());
	}
	public void setOrganismeProprietairesBean(OrganismeProprietairesBean organismeProprietairesBean) {
		this.organismeProprietairesBean = organismeProprietairesBean;
	}
	public boolean isAutModules(String idModule) {
		return organismeProprietairesBean.getAutModules().contains(idModule);
	}
	public String getDispalyLabele() {
		return organismeProprietairesBean.getDisplayName();
	}
}
