package tn.com.smartsoft.configGenerale.organisationSociete.societe.presentation.controler;

import java.util.List;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;
import tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.beans.GovernoratBean;
import tn.com.smartsoft.configGenerale.organisationSociete.localite.beans.LocaliteBean;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;
import tn.com.smartsoft.configGenerale.organisationSociete.societe.beans.SocieteBean;
import tn.com.smartsoft.configGenerale.organisationSociete.societe.business.SocieteBusiness;
import tn.com.smartsoft.configGenerale.organisationSociete.societe.presentation.model.SocieteModel;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;

@SuppressWarnings("rawtypes")
public class SocieteControler extends GenericEntiteControler{

	private static final long		serialVersionUID	= 1L;
	protected GenericEntiteBusiness	paysBusiness;
	protected GenericEntiteBusiness	activiteBusiness;
	protected GenericEntiteBusiness	localiteBusiness;
	protected GenericEntiteBusiness	deviseBusiness;
	protected GenericEntiteBusiness	gouvernoratBusiness;
	protected GenericEntiteBusiness	personnaliteBusiness;
	protected GenericEntiteBusiness	siteBusiness;
	protected GenericEntiteBusiness	sousActivitesBusiness;
	protected GenericEntiteBusiness	secteurActivitesBusiness;
	protected GenericEntiteBusiness	conventionCollectiveBusiness;

	public void setSecteurActivitesBusiness(GenericEntiteBusiness secteurActivitesBusiness) {
		this.secteurActivitesBusiness = secteurActivitesBusiness;
	}
	public void setSousActivitesBusiness(GenericEntiteBusiness sousActivitesBusiness) {
		this.sousActivitesBusiness = sousActivitesBusiness;
	}
	public void setSiteBusiness(GenericEntiteBusiness siteBusiness) {
		this.siteBusiness = siteBusiness;
	}
	public void setPersonnaliteBusiness(GenericEntiteBusiness personnaliteBusiness) {
		this.personnaliteBusiness = personnaliteBusiness;
	}
	public void setPaysBusiness(GenericEntiteBusiness paysBusiness) {
		this.paysBusiness = paysBusiness;
	}
	public void setLocaliteBusiness(GenericEntiteBusiness localiteBusiness) {
		this.localiteBusiness = localiteBusiness;
	}
	public void setDeviseBusiness(GenericEntiteBusiness deviseBusiness) {
		this.deviseBusiness = deviseBusiness;
	}
	public void setGouvernoratBusiness(GenericEntiteBusiness gouvernoratBusiness) {
		this.gouvernoratBusiness = gouvernoratBusiness;
	}
	public void setActiviteBusiness(GenericEntiteBusiness activiteBusiness) {
		this.activiteBusiness = activiteBusiness;
	}
	public void setConventionCollectiveBusiness(GenericEntiteBusiness conventionCollectiveBusiness) {
		this.conventionCollectiveBusiness = conventionCollectiveBusiness;
	}
	public void onRenderPage(ListenerContext context) throws FunctionalException {
		SocieteModel entiteModel = (SocieteModel) context.userAction().getDataObject();
		if (entiteModel.getDetailBean() == null) entiteModel.setDetailBean(new SocieteBean());
		((SocieteBean) entiteModel.getDetailBean()).setPaysId(context.getPaysId());
		onInitListeGouvernorat(context);
	}
	public void onInitList(ListenerContext context) throws FunctionalException {
		SocieteModel entiteModel = (SocieteModel) context.userAction().getDataObject();
		List<DataBusinessBean> listPays = paysBusiness.getByCriteria(new PayBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListPays(listPays);
		List<DataBusinessBean> listLocalite = localiteBusiness.getByCriteria(new LocaliteBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop()
				.userContext());
		entiteModel.setListLocalite(listLocalite);
		List<DataBusinessBean> listDevise = deviseBusiness.getByCriteria(new DeviseBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListDevise(listDevise);
		List<DataBusinessBean> listGouvernorat = gouvernoratBusiness.getByCriteria(new GovernoratBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop()
				.userContext());
		entiteModel.setListGouvernorat(listGouvernorat);
		/* List<DataBusinessBean> listSite = siteBusiness.getByCriteria(new MboxBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop() .userContext());
		 * entiteModel.setListSite(listSite); */
	}
	public void onInitListeGouvernorat(ListenerContext context) throws FunctionalException {
		SocieteModel entiteModel = (SocieteModel) context.userAction().getDataObject();
		GovernoratBean governoratBean = new GovernoratBean();
		governoratBean.setPaysId(((SocieteBean) entiteModel.getDetailBean()).getPaysId());
		List<DataBusinessBean> listGouvernorat = gouvernoratBusiness.getByCriteria(governoratBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop()
				.userContext());
		entiteModel.setListGouvernorat(listGouvernorat);
	}
	public void onInitListeLocalite(ListenerContext context) throws FunctionalException {
		SocieteModel entiteModel = (SocieteModel) context.userAction().getDataObject();
		LocaliteBean localiteBean = new LocaliteBean();
		localiteBean.setGovernoratId(((SocieteBean) entiteModel.getDetailBean()).getGovernoratId());
		((SocieteBean) entiteModel.getDetailBean()).setLocalite(new LocaliteBean());
		List<DataBusinessBean> listLocalite = localiteBusiness.getByCriteria(localiteBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListLocalite(listLocalite);
	}
	public void doValiderCreate(ListenerContext context) {
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			SocieteBusiness societeBusiness = (SocieteBusiness) entiteBusiness;
			societeBusiness.create((SocieteBean) entiteModel.getDetailBean(), context.userDesktop().userContext());
			entiteModel.setDetailBean(null);
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageCreateValid());
			context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
	public void doValiderUpdate(ListenerContext context) {
		try {
			SocieteModel entiteModel = (SocieteModel) context.userAction().getDataObject();
			SocieteBusiness societeBusiness = (SocieteBusiness) entiteBusiness;
			SocieteBean societeBean = (SocieteBean) entiteModel.getDetailBean();
			societeBusiness.update(societeBean, context.userDesktop().userContext());
			entiteModel.getListBean().set(entiteModel.getBeanIndex(), entiteModel.getDetailBean());
			entiteModel.setDetailBean(null);
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageUpdateValid());
			context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
	public void doValiderDelete(ListenerContext context) {
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			SocieteBusiness societeBusiness = (SocieteBusiness) entiteBusiness;
			societeBusiness.delete((SocieteBean) entiteModel.getDetailBean(), context.userDesktop().userContext());
			entiteModel.getListBean().remove(entiteModel.getBeanIndex());
			entiteModel.setDetailBean(null);
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageUpdateValid());
			context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
	public void doIndexBean(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		DataBusinessBean rowBean = entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.setDetailBean(rowBean);
		onInitListeGouvernorat(context);
		onInitListeLocalite(context);
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		jsonItemResponseModel.addData("data", context.userAction().currentWindow().getFieldValues());
		jsonItemResponseModel.addData("index", entiteModel.getBeanIndex());
		jsonItemResponseModel.addData("size", entiteModel.getListBean().size());
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}
	public void doSelectedBean(ListenerContext context) throws FunctionalException {
		SocieteModel entiteModel = (SocieteModel) context.userAction().getDataObject();
		SocieteBusiness societeBusiness = (SocieteBusiness) entiteBusiness;
		UIExtGrid grid = (UIExtGrid) context.source();
		entiteModel.setBeanIndex(grid.getSelectedRow());
		DataBusinessBean rowBean = entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = societeBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.setDetailBean(rowBean);
		onInitListeGouvernorat(context);
		onInitListeLocalite(context);
		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}
}
