package tn.com.smartsoft.framework.context;

import java.io.Serializable;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIModuleExplorer;

public interface OrganismeContext extends Serializable{
	
	public abstract OrganismeBean getOrganismeBean();
	public abstract String getDispalyLabele();
	public UIModuleExplorer getModuleExplorer();
	public void setModuleExplorer(UIModuleExplorer moduleExplorer);
	public boolean isAutModules(String idModule);
}