package tn.com.smartsoft.backoffice.eleves.eleves.presentation.controler;

import java.util.List;

import tn.com.smartsoft.backoffice.eleves.eleves.beans.EleveSaisonsBean;
import tn.com.smartsoft.backoffice.eleves.eleves.presentation.model.ElevesModel;
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

public class ElevesControler extends GenericEntiteControler {

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

	public void onInitList(ListenerContext context) throws FunctionalException {
		ActionBeanId actionBeanId = context.userAction().getUserActionId().getActionBeanId();
		UserContext userContext = context.userDesktop().userContext();
		ElevesModel entiteModel = (ElevesModel) context.userAction().getDataObject();
		List<DataBusinessBean> listClasses = eleveClassesBusiness.getByCriteria(new EleveClassesBean(), actionBeanId, userContext);
		entiteModel.setListClasses(listClasses);
		List<DataBusinessBean> listSaisons = saisonsBusiness.getByCriteria(new EleveSaisonsBean(), actionBeanId, userContext);
		entiteModel.setListSaisons(listSaisons);
	}

	public void onPhotoSelected(ListenerContext context) throws FunctionalException {
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}
}
