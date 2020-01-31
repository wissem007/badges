package tn.com.smartsoft.backoffice.referentiel.eleveClasses.presentation.controler;

import java.util.List;

import tn.com.smartsoft.backoffice.referentiel.eleveClasseNiveaux.beans.EleveClasseNiveauxBean;
import tn.com.smartsoft.backoffice.referentiel.eleveClasseNiveaux.business.EleveClasseNiveauxBusiness;
import tn.com.smartsoft.backoffice.referentiel.eleveClasses.presentation.model.EleveClassesModel;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;

public class EleveClassesControler extends GenericEntiteControler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EleveClasseNiveauxBusiness eleveClasseNiveauxBusiness;

	public EleveClasseNiveauxBusiness getEleveClasseNiveauxBusiness() {
		return eleveClasseNiveauxBusiness;
	}

	public void setEleveClasseNiveauxBusiness(EleveClasseNiveauxBusiness eleveClasseNiveauxBusiness) {
		this.eleveClasseNiveauxBusiness = eleveClasseNiveauxBusiness;
	}

	public void onInitList(ListenerContext context) throws FunctionalException {
		ActionBeanId actionBeanId = context.userAction().getUserActionId().getActionBeanId();
		UserContext userContext = context.userDesktop().userContext();
		EleveClassesModel entiteModel = (EleveClassesModel) context.userAction().getDataObject();
		List<DataBusinessBean> listClasseNiveau = eleveClasseNiveauxBusiness.getByCriteria(new EleveClasseNiveauxBean(), actionBeanId, userContext);
		entiteModel.setListClasseNiveau(listClasseNiveau);
	}
}
