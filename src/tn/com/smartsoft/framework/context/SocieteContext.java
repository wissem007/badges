package tn.com.smartsoft.framework.context;

import java.util.List;

import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.configGenerale.organisationSociete.societe.beans.SocieteBean;

public interface SocieteContext {
	public List<OrganismeBean> getListOrganismes();
	
	public abstract SocieteBean getSocieteBean();
	
	public abstract OrganismeContext getUserOrganisme(Long organismeId);
	
	public abstract List<OrganismeContext> getUserOrganismes();
	
	public OrganismeContext switchUserOrganisme(Long organismeId);
	
	public OrganismeContext getCurrentUserOrganisme();
	
	public OrganismeContext switchDefaultUserOrganisme(Long organismeId);
}