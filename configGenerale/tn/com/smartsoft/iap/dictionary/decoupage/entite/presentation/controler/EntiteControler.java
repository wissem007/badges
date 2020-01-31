package tn.com.smartsoft.iap.dictionary.decoupage.entite.presentation.controler;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.ListUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.PropertyBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.presentation.model.EntiteModel;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.niveauApplicatif.beans.NiveauApplicatifBean;
import tn.com.smartsoft.iap.dictionary.decoupage.niveauApplicatif.business.NiveauApplicatifBusiness;
import tn.com.smartsoft.iap.dictionary.decoupage.sequences.beans.SequenceBean;
import tn.com.smartsoft.iap.dictionary.decoupage.subModule.beans.SubModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBean;
import tn.com.smartsoft.iap.dictionary.decoupage.typeEntites.beans.TypeEntitesBean;
import tn.com.smartsoft.iap.dictionary.graphique.userType.beans.UserTypeBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.FieldRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.business.GroupedRoleBusiness;

public class EntiteControler extends GenericEntiteControler {

	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness moduleBusiness;
	protected GenericEntiteBusiness subModuleBusiness;
	protected GenericEntiteBusiness sujetBusiness;
	protected GenericEntiteBusiness sequencesBusiness;
	protected GenericEntiteBusiness userTypeBusiness;
	protected GroupedRoleBusiness groupedRoleBusiness;
	protected GenericEntiteBusiness typeEntitesBusiness;
	protected GenericEntiteBusiness niveauApplicatifBusiness;

	public void setNiveauApplicatifBusiness(GenericEntiteBusiness niveauApplicatifBusiness) {
		this.niveauApplicatifBusiness = niveauApplicatifBusiness;
	}

	public void setTypeEntitesBusiness(GenericEntiteBusiness typeEntitesBusiness) {
		this.typeEntitesBusiness = typeEntitesBusiness;
	}

	public void setSequencesBusiness(GenericEntiteBusiness sequencesBusiness) {
		this.sequencesBusiness = sequencesBusiness;
	}

	public void setUserTypeBusiness(GenericEntiteBusiness userTypeBusiness) {
		this.userTypeBusiness = userTypeBusiness;
	}

	public void setModuleBusiness(GenericEntiteBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}

	public void setSubModuleBusiness(GenericEntiteBusiness subModuleBusiness) {
		this.subModuleBusiness = subModuleBusiness;
	}

	public void setSujetBusiness(GenericEntiteBusiness sujetBusiness) {
		this.sujetBusiness = sujetBusiness;
	}

	public void setGroupedRoleBusiness(GroupedRoleBusiness groupedRoleBusiness) {
		this.groupedRoleBusiness = groupedRoleBusiness;
	}

	public void onInitListEntite(ListenerContext context) throws FunctionalException {
		EntiteModel entiteModel = (EntiteModel) context.userAction().getDataObject();
		List listModule = moduleBusiness.getByCriteria(new ModuleBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListModule(listModule);
		if (context.userAction().getUserActionId().getActionBeanId().getActionId().equals("creer")) {
			if (entiteModel.getDetailBean() == null) {
				EntiteBean entiteBean = new EntiteBean();
				entiteBean.setIsAuditable(true);
				entiteBean.setPersistant(true);
				entiteModel.setDetailBean(entiteBean);
				ArrayList listProprietes = new ArrayList();
				listProprietes.add(new PropertyBean());
				entiteModel.setListProprietes(listProprietes);
			}

		} else {
			if (entiteModel.getSearcheBean() == null)
				entiteModel.setSearcheBean(new EntiteBean());

			EntiteBean entiteBean = (EntiteBean) entiteModel.getSearcheBean();
			entiteBean.setIsAuditable(true);
			entiteBean.setPersistant(true);
		}

		List listSequence = sequencesBusiness.getByCriteria(new SequenceBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSequence(listSequence);

		List listUserType = userTypeBusiness.getByCriteria(new UserTypeBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListUserType(listUserType);

		List listTypeEntites = typeEntitesBusiness.getByCriteria(new TypeEntitesBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		List listRefEntites = entiteBusiness.getByCriteria(new EntiteBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		
		List listNiveauApplicatif = niveauApplicatifBusiness.getByCriteria(new NiveauApplicatifBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		
		entiteModel.setListNiveauApplicatif(listNiveauApplicatif);
		entiteModel.setListTypeEntites(listTypeEntites);
		entiteModel.setListRefEntites(listRefEntites);

	}

	public void getSubModule(ListenerContext context) throws FunctionalException {
		EntiteModel entiteModel = (EntiteModel) context.userAction().getDataObject();
		SubModuleBean subModuleBean = new SubModuleBean();
		FieldRoleBean fieldRoleBean = new FieldRoleBean();
		String modId;
		String action = context.userAction().getUserActionId().getActionBeanId().getActionId();
		if (action != null && action.equals("creer")) {
			modId = ((EntiteBean) entiteModel.getDetailBean()).getModuleId();
		} else {
			modId = ((EntiteBean) entiteModel.getSearcheBean()).getModuleId();
		}
		subModuleBean.setModuleId(modId);
		fieldRoleBean.setModuleId(modId);

		List listSubModule = subModuleBusiness.getByCriteria(subModuleBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSubModule(listSubModule);

		List listFieldRoles = groupedRoleBusiness.getFieldRoles(fieldRoleBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());

		entiteModel.setListSubModule(listSubModule);
		entiteModel.setListFieldRoles(listFieldRoles);
		JsonItemResponseModel.reponseEmpty(context);

	}

	public void getSujet(ListenerContext context) throws FunctionalException {
		EntiteModel entiteModel = (EntiteModel) context.userAction().getDataObject();
		SujetBean sujetBean = new SujetBean();
		String smodId,modId;
		String action = context.userAction().getUserActionId().getActionBeanId().getActionId();
		if (action != null && action.equals("creer")) {
			smodId = ((EntiteBean) entiteModel.getDetailBean()).getSubModuleId();
			modId = ((EntiteBean) entiteModel.getDetailBean()).getModuleId();
		} else {
			smodId = ((EntiteBean) entiteModel.getSearcheBean()).getSubModuleId();
			modId = ((EntiteBean) entiteModel.getSearcheBean()).getModuleId();
		}
		sujetBean.setModuleId(modId);
		sujetBean.setSubModuleId(smodId);
		List listSujet = sujetBusiness.getByCriteria(sujetBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSujet(listSujet);
		JsonItemResponseModel.reponseEmpty(context);

	}

	public void doValiderCreate(ListenerContext context) {
		try {
			EntiteModel entiteModel = (EntiteModel) context.userAction().getDataObject();
			EntiteBean detailBean = (EntiteBean) entiteModel.getDetailBean();
			detailBean.initListProperties(entiteModel.getListRefEntites());
			entiteBusiness.create(entiteModel.getDetailBean(), context.userDesktop().userContext());
			entiteModel.setDetailBean(null);

			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageCreateValid());

			context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
	
	public void doValiderUpdate(ListenerContext context) throws FunctionalException {
		try { 
			EntiteModel entiteModel = (EntiteModel) context.userAction().getDataObject();
			EntiteBean detailBean = (EntiteBean) entiteModel.getDetailBean();
			detailBean.initListProperties(entiteModel.getListRefEntites());
			entiteBusiness.update(entiteModel.getDetailBean(), context.userDesktop().userContext());
			entiteBusiness.getDaoSession().clear();
			DataBusinessBean rowBean = entiteBusiness.getById(entiteModel.getDetailBean(), context.userAction().getActionBeanId(), context.userContext());
			entiteModel.getListBean().set(entiteModel.getBeanIndex(), rowBean);
			entiteModel.setDetailBean(null);
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageUpdateValid());
			context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}

	public void doSelectedBean(ListenerContext context) throws FunctionalException {
		EntiteModel entiteModel = (EntiteModel) context.userAction().getDataObject();
		UIExtGrid grid = (UIExtGrid) context.source();
		entiteModel.setBeanIndex(grid.getSelectedRow());
		EntiteBean rowBean = (EntiteBean) entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = (EntiteBean) entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.setDetailBean(rowBean);

		SubModuleBean subModuleBean = new SubModuleBean();
		subModuleBean.setModuleId(rowBean.getModuleId());
		List listSubModule = subModuleBusiness.getByCriteria(subModuleBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSubModule(listSubModule);

		SujetBean sujetBean = new SujetBean();
		sujetBean.setSubModuleId(rowBean.getSubModuleId());
		List listSujet = sujetBusiness.getByCriteria(sujetBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSujet(listSujet);

		FieldRoleBean fieldRoleBean = new FieldRoleBean();
		fieldRoleBean.setModuleId(rowBean.getModuleId());
		List listFieldRoles = groupedRoleBusiness.getFieldRoles(fieldRoleBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListFieldRoles(listFieldRoles);

		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}

}
