package tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.presentation.controler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.beans.EleveDemandeCartesBean;
import tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.beans.StatuDemandes;
import tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.business.EleveDemandeCartesBusiness;
import tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.presentation.model.EleveDemandeCartesModel;
import tn.com.smartsoft.backoffice.eleves.eleves.beans.EleveSaisonsBean;
import tn.com.smartsoft.backoffice.referentiel.eleveClasses.beans.EleveClassesBean;
import tn.com.smartsoft.backoffice.referentiel.eleveClasses.business.EleveClassesBusiness;
import tn.com.smartsoft.backoffice.referentiel.saison.business.SaisonsBusiness;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;

public class EleveDemandeCartesControler extends GenericEntiteControler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EleveClassesBusiness eleveClassesBusiness;
	private SaisonsBusiness saisonsBusiness;

	public void setSaisonsBusiness(SaisonsBusiness saisonsBusiness) {
		this.saisonsBusiness = saisonsBusiness;
	}

	public void setEleveClassesBusiness(EleveClassesBusiness eleveClassesBusiness) {
		this.eleveClassesBusiness = eleveClassesBusiness;
	}

	public void doFilter(ListenerContext context) throws FunctionalException {
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		try {
			Boolean isTraiter = context.userAction().getActionBeanId().getActionId().equalsIgnoreCase("traiter");
			Boolean isImprimer = context.userAction().getActionBeanId().getActionId().equalsIgnoreCase("imprimer");
			EleveDemandeCartesModel entiteModel = (EleveDemandeCartesModel) context.userAction().getDataObject();
			EleveDemandeCartesBean searcheBean = (EleveDemandeCartesBean) entiteModel.getSearcheBean();
			if (isTraiter)
				searcheBean.setStatuDemandesId(StatuDemandes.STATUS_EN_COURS);
			else if (isImprimer)
				searcheBean.setStatuDemandesId(StatuDemandes.STATUS_VERS_IMPRESION);
			List<DataBusinessBean> list = entiteBusiness.getByCriteria(searcheBean, context.userAction().getActionBeanId(), context.userDesktop().userContext());
			entiteModel.setListBean(list);
			jsonItemResponseModel.addData("empty", list.isEmpty());
			if (list.isEmpty())
				throw new FunctionalException(controlerParams.getMessageEmptyRow());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
		}
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}

	public void validerTraitement(ListenerContext context) throws FunctionalException {
		UserContext userContext = context.userDesktop().userContext();
		EleveDemandeCartesModel entiteModel = (EleveDemandeCartesModel) context.userAction().getDataObject();
		List<DataBusinessBean> listBean = entiteModel.getListBean();
		((EleveDemandeCartesBusiness) entiteBusiness).updateTaraitement(listBean, userContext);
		context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageCreateValid());
		context.userDesktop().userDesktopNavigation().goToDefaultUserAction(context.webContext());
	}

	public void validerImpression(ListenerContext context) throws FunctionalException {
		UserContext userContext = context.userDesktop().userContext();
		EleveDemandeCartesModel entiteModel = (EleveDemandeCartesModel) context.userAction().getDataObject();
		List<DataBusinessBean> listBean = entiteModel.getListBean();
		List<DataBusinessBean> listnew = ((EleveDemandeCartesBusiness) entiteBusiness).updateImpresion(listBean, userContext);
		entiteModel.setListBean(listnew);
		context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageCreateValid());
		context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
	}

	public void doImpression(ListenerContext context) throws FunctionalException {
		Map<String, Object> paramValues = new HashMap<String, Object>();
		context.userAction().goToReport("reportListCartes", paramValues, context.webContext());
	}

	public void onInitList(ListenerContext context) throws FunctionalException {
		ActionBeanId actionBeanId = context.userAction().getUserActionId().getActionBeanId();
		UserContext userContext = context.userDesktop().userContext();
		EleveDemandeCartesModel entiteModel = (EleveDemandeCartesModel) context.userAction().getDataObject();
		List<DataBusinessBean> listClasses = eleveClassesBusiness.getByCriteria(new EleveClassesBean(), actionBeanId, userContext);
		entiteModel.setListClasses(listClasses);
		List<DataBusinessBean> listSaisons = saisonsBusiness.getByCriteria(new EleveSaisonsBean(), actionBeanId, userContext);
		entiteModel.setListSaisons(listSaisons);
		List<DataBusinessBean> listStatus = ((EleveDemandeCartesBusiness) entiteBusiness).getStatus(new EleveSaisonsBean(), actionBeanId, userContext);
		entiteModel.setListStatus(listStatus);
	}

	public void onPhotoSelected(ListenerContext context) throws FunctionalException {
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}
}
