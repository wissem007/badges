package tn.com.smartsoft.framework.context.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.SerializationUtils;
import tn.com.smartsoft.commons.utils.BeanComparator;
import tn.com.smartsoft.commons.utils.SorterType;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.configGenerale.organisationSociete.societe.beans.SocieteBean;
import tn.com.smartsoft.framework.context.OrganismeContext;
import tn.com.smartsoft.framework.context.SocieteContext;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.iap.system.ConstantUser;

public class SocieteContextImpl implements SocieteContext{
	
	private SocieteBean					societeBean;
	private Map<Long, OrganismeContext>	childOrganismes	= new HashMap<Long, OrganismeContext>();
	private OrganismeContext			currentUserOrganisme;
	private UserContext					userContext;
	
	public SocieteContextImpl(SocieteBean societeBean, UserContext userContext) {
		super();
		this.societeBean = societeBean;
		this.userContext = userContext;
	}
	public SocieteBean getSocieteBean() {
		return (SocieteBean) SerializationUtils.clone(societeBean);
	}
	public void setSocieteBean(SocieteBean societeBean) {
		this.societeBean = societeBean;
	}
	public void addUserOrganisme(OrganismeContext organismeContext) {
		childOrganismes.put(organismeContext.getOrganismeBean().getOrganismeId(), organismeContext);
	}
	public OrganismeContext getUserOrganisme(Long organismeId) {
		return childOrganismes.get(organismeId);
	}
	public List<OrganismeContext> getUserOrganismes() {
		return new ArrayList<OrganismeContext>(childOrganismes.values());
	}
	public List<OrganismeBean> getListOrganismes() {
		List<OrganismeBean> listOrganisme = new ArrayList<OrganismeBean>();
		List<OrganismeContext> userOrganismes = getUserOrganismes();
		for (int i = 0; i < userOrganismes.size(); i++) {
			OrganismeContext organismeContext = (OrganismeContext) userOrganismes.get(i);
			listOrganisme.add(organismeContext.getOrganismeBean());
		}
		BeanComparator orgComparator = new BeanComparator("organismeId", SorterType.ASC, false);
		orgComparator.sort(listOrganisme);
		return listOrganisme;
	}
	public OrganismeContext switchDefaultUserOrganisme(Long organismeId) {
		if (!childOrganismes.containsKey(organismeId)) organismeId = childOrganismes.keySet().iterator().next();
		return switchUserOrganisme(organismeId);
	}
	public OrganismeContext switchUserOrganisme(Long organismeId) {
		currentUserOrganisme = getUserOrganisme(organismeId);
		userContext.addUserPreference(ConstantUser.ORGANISME_PREFERENTCE_KEY, String.valueOf(organismeId));
		userContext.addUserPreference(ConstantUser.SOCIETE_PREFERENTCE_KEY, String.valueOf(societeBean.getSocieteId()));
		return currentUserOrganisme;
	}
	public OrganismeContext getCurrentUserOrganisme() {
		return currentUserOrganisme;
	}
}
