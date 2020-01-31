package tn.com.smartsoft.configGenerale.organisationSociete.organisation.presentation.controler;

import java.util.List;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.beans.GovernoratBean;
import tn.com.smartsoft.configGenerale.organisationSociete.localite.beans.LocaliteBean;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.business.OrganismeBusiness;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.presentation.model.OrganismeModel;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.iap.dictionary.decoupage.module.business.ModuleBusiness;

public class OrganismeControler extends GenericEntiteControler{

	private static final long		serialVersionUID	= 1L;
	protected GenericEntiteBusiness	parentSocieteBusiness;
	protected GenericEntiteBusiness	gouvernoratBusiness;
	protected GenericEntiteBusiness	paysBusiness;
	protected GenericEntiteBusiness	localiteBusiness;

	public void setGouvernoratBusiness(GenericEntiteBusiness gouvernoratBusiness) {
		this.gouvernoratBusiness = gouvernoratBusiness;
	}
	public void setPaysBusiness(GenericEntiteBusiness paysBusiness) {
		this.paysBusiness = paysBusiness;
	}
	public void setLocaliteBusiness(GenericEntiteBusiness localiteBusiness) {
		this.localiteBusiness = localiteBusiness;
	}
	public void setParentSocieteBusiness(GenericEntiteBusiness parentSocieteBusiness) {
		this.parentSocieteBusiness = parentSocieteBusiness;
	}
	public void onInitListUpd(ListenerContext context) throws FunctionalException {
		OrganismeModel entiteModel = (OrganismeModel) context.userAction().getDataObject();
		List<DataBusinessBean> listParentSociete = parentSocieteBusiness.getByCriteria(new OrganismeBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop()
				.userContext());
		entiteModel.setListParentSociete(listParentSociete);
		List listPays = paysBusiness.getByCriteria(new PayBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListPays(listPays);
		if ((((OrganismeBean) entiteModel.getDetailBean()).getLocalite() != null) && (((OrganismeBean) entiteModel.getDetailBean()).getLocalite().getGovernorat() != null)) {
			GovernoratBean governoratBean = new GovernoratBean();
			governoratBean.setPaysId(((OrganismeBean) entiteModel.getDetailBean()).getLocalite().getGovernorat().getPaysId());
			List listGouvernorat = gouvernoratBusiness.getByCriteria(governoratBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			entiteModel.setListGovernorat(listGouvernorat);
		}
		if (((OrganismeBean) entiteModel.getDetailBean()).getLocalite() != null) {
			LocaliteBean localiteBean = new LocaliteBean();
			localiteBean.setGovernoratId(((OrganismeBean) entiteModel.getDetailBean()).getLocalite().getGovernoratId());
			List listLocalite = localiteBusiness.getByCriteria(localiteBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			entiteModel.setListLocalite(listLocalite);
		}
	}
	public void onInitListView(ListenerContext context) throws FunctionalException {
		OrganismeModel entiteModel = (OrganismeModel) context.userAction().getDataObject();
		List listPays = paysBusiness.getByCriteria(new PayBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListPays(listPays);
		List listGouvernorat = gouvernoratBusiness.getByCriteria(new GovernoratBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListGovernorat(listGouvernorat);
		List listLocalite = localiteBusiness.getByCriteria(new LocaliteBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListLocalite(listLocalite);
		List<DataBusinessBean> listParentSociete = parentSocieteBusiness.getByCriteria(new OrganismeBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop()
				.userContext());
		entiteModel.setListParentSociete(listParentSociete);
	}
	public void doValiderCreate(ListenerContext context) {
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			OrganismeBusiness organismeBusiness = (OrganismeBusiness) entiteBusiness;
			organismeBusiness.create((OrganismeBean) entiteModel.getDetailBean(), context.userDesktop().userContext());
			entiteModel.setDetailBean(null);
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageCreateValid());
			context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
	public void onInitList(ListenerContext context) throws FunctionalException {
		OrganismeModel entiteModel = (OrganismeModel) context.userAction().getDataObject();
		List<DataBusinessBean> listParentSociete = parentSocieteBusiness.getByCriteria(new OrganismeBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop()
				.userContext());
		entiteModel.setListParentSociete(listParentSociete);
		List<DataBusinessBean> listPays = paysBusiness.getByCriteria(new PayBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListPays(listPays);
	}
	public void onInitListGouvernorat(ListenerContext context) throws FunctionalException {
		OrganismeModel entiteModel = (OrganismeModel) context.userAction().getDataObject();
		if (((OrganismeBean) entiteModel.getDetailBean()).getLocalite() != null) {
			if (((OrganismeBean) entiteModel.getDetailBean()).getLocalite().getGovernorat() != null) {
				GovernoratBean governoratBean = new GovernoratBean();
				governoratBean.setPaysId(((OrganismeBean) entiteModel.getDetailBean()).getLocalite().getGovernorat().getPaysId());
				List listGouvernorat = gouvernoratBusiness.getByCriteria(governoratBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
				entiteModel.setListGovernorat(listGouvernorat);
			}
		}
	}
	public void onInitListLocalite(ListenerContext context) throws FunctionalException {
		OrganismeModel entiteModel = (OrganismeModel) context.userAction().getDataObject();
		LocaliteBean localiteBean = new LocaliteBean();
		if (((OrganismeBean) entiteModel.getDetailBean()).getLocalite() != null) {
			localiteBean.setGovernoratId(((OrganismeBean) entiteModel.getDetailBean()).getLocalite().getGovernoratId());
			List listLocalite = localiteBusiness.getByCriteria(localiteBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			entiteModel.setListLocalite(listLocalite);
		}
	}
	public void onRenderPage(ListenerContext context) throws FunctionalException {
		OrganismeModel entiteModel = (OrganismeModel) context.userAction().getDataObject();
		if (entiteModel.getDetailBean() == null) entiteModel.setDetailBean(new OrganismeBean());
		LocaliteBean localite = new LocaliteBean();
		GovernoratBean gov = new GovernoratBean();
		gov.setPays(context.getPays());
		gov.setPaysId(context.getPaysId());
		localite.setGovernorat(gov);
		((OrganismeBean) entiteModel.getDetailBean()).setLocalite(localite);
	}
}
