package tn.com.smartsoft.configGenerale.dynamicEntite.paramChampDynamique.presentation.controler;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.ListUtils;
import tn.com.smartsoft.configGenerale.dynamicEntite.paramChampDynamique.presentation.model.PropertysModel;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.PropertyBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.graphique.userType.beans.UserTypeBean;

public class PropertysControler extends GenericEntiteControler {

	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness moduleBusiness;
	protected GenericEntiteBusiness entiteDbBusiness;
	protected GenericEntiteBusiness userTypeBusiness;

	public void setEntiteDbBusiness(GenericEntiteBusiness entiteDbBusiness) {
		this.entiteDbBusiness = entiteDbBusiness;
	}

	public void setModuleBusiness(GenericEntiteBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}

	public void setUserTypeBusiness(GenericEntiteBusiness userTypeBusiness) {
		this.userTypeBusiness = userTypeBusiness;
	}

	public void onInitList(ListenerContext context) throws FunctionalException {
		PropertysModel entiteModel = (PropertysModel) context.userAction().getDataObject();

		List<DataBusinessBean> listModule = moduleBusiness.getByCriteria(new ModuleBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListModule(listModule);
		List<DataBusinessBean> listUserType = userTypeBusiness.getByCriteria(new UserTypeBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListUserType(listUserType);
	}

	public void getEntiteFilter(ListenerContext context) throws FunctionalException {
		PropertysModel entiteModel = (PropertysModel) context.userAction().getDataObject();
		PropertyBean bean = (PropertyBean) entiteModel.getSearcheBean();
		getEntite(context, bean);

	}

	public void getEntite(ListenerContext context) throws FunctionalException {
		PropertysModel entiteModel = (PropertysModel) context.userAction().getDataObject();
		PropertyBean bean = (PropertyBean) entiteModel.getDetailBean();
		getEntite(context, bean);

	}

	public void getEntite(ListenerContext context, PropertyBean bean) throws FunctionalException {
		PropertysModel entiteModel = (PropertysModel) context.userAction().getDataObject();
		EntiteBean entiteBean = new EntiteBean();
		List<Long> typeEntiteList = new ArrayList<Long>();
		//typeEntiteList.add((long) 2);
		typeEntiteList.add((long) 3);

		entiteBean.setModuleId(bean.getModuleId());
		entiteBean.setTypeEntiteList(typeEntiteList);

		List<DataBusinessBean> listEntite = entiteDbBusiness.getByCriteria(entiteBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListEntite(listEntite);

	}

	public void getModuleRefFilter(ListenerContext context) throws FunctionalException {
		PropertysModel entiteModel = (PropertysModel) context.userAction().getDataObject();
		PropertyBean bean = (PropertyBean) entiteModel.getSearcheBean();
		getModuleRef(context, bean);

	}

	public void getModuleRef(ListenerContext context) throws FunctionalException {
		PropertysModel entiteModel = (PropertysModel) context.userAction().getDataObject();
		PropertyBean bean = (PropertyBean) entiteModel.getDetailBean();
		getModuleRef(context, bean);

	}

	public void getModuleRef(ListenerContext context, PropertyBean bean) throws FunctionalException {
		PropertysModel entiteModel = (PropertysModel) context.userAction().getDataObject();
		ModuleBean module = new ModuleBean();
		module.setActivateAndType(Boolean.TRUE);

		List<DataBusinessBean> listModuleRef = moduleBusiness.getByCriteria(module, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListModuleRef(listModuleRef);

	}

	public void getEntiteRefFilter(ListenerContext context) throws FunctionalException {
		PropertysModel entiteModel = (PropertysModel) context.userAction().getDataObject();
		PropertyBean bean = (PropertyBean) entiteModel.getSearcheBean();
		getEntiteRef(context, bean);

	}

	public void getEntiteRef(ListenerContext context) throws FunctionalException {
		PropertysModel entiteModel = (PropertysModel) context.userAction().getDataObject();
		PropertyBean bean = (PropertyBean) entiteModel.getDetailBean();
		getEntiteRef(context, bean);

	}

	public void getEntiteRef(ListenerContext context, PropertyBean bean) throws FunctionalException {
		PropertysModel entiteModel = (PropertysModel) context.userAction().getDataObject();
		EntiteBean entiteBean = (EntiteBean) ListUtils.findByProperty(entiteModel.getListEntite(), "entiteId", bean.getEntiteId());
		long niveau = entiteBean.getNiveauApplicatifId();

		EntiteBean entiteRefBean = new EntiteBean();
		List<Long> niveauApplicatifList = new ArrayList<Long>();		
		if (niveau == 1) {
			niveauApplicatifList.add((long) 1);
			niveauApplicatifList.add((long) 2);
			niveauApplicatifList.add((long) 3);
		} else if (niveau == 2) {
			niveauApplicatifList.add((long) 2);
			niveauApplicatifList.add((long) 3);
		} else if (niveau == 3) {
			niveauApplicatifList.add((long) 3);
		}
		entiteRefBean.setModuleId(bean.getRefModuleId());
		entiteRefBean.setNiveauApplicatifList(niveauApplicatifList);

		List<DataBusinessBean> listEntiteRef = entiteDbBusiness.getByCriteria(entiteRefBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListEntiteRef(listEntiteRef);

	}

	public void doValiderCreate(ListenerContext context) {
		try {
			PropertysModel entiteModel = (PropertysModel) context.userAction().getDataObject();
			PropertyBean bean = (PropertyBean) entiteModel.getDetailBean();
			EntiteBean entiteBean = (EntiteBean) ListUtils.findByProperty(entiteModel.getListEntite(), "entiteId", bean.getEntiteId());
			EntiteBean entiteRefBean = (EntiteBean) ListUtils.findByProperty(entiteModel.getListEntiteRef(), "entiteId", bean.getRefEntiteId());

			bean.setSubModuleId(entiteBean.getSubModuleId());
			bean.setSujetId(entiteBean.getSujetId());
			bean.setPersistant(Boolean.FALSE);
			bean.setAuditable(Boolean.FALSE);
			bean.setDynamique(Boolean.TRUE);
			if(entiteRefBean!=null)
			{
				bean.setRefSubModuleId(entiteRefBean.getSubModuleId());
				bean.setRefSujetId(entiteRefBean.getSujetId());
			}

			entiteBusiness.create(bean, context.userDesktop().userContext());
			entiteModel.setDetailBean(null);
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageCreateValid());
			context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
	
	public void onRenderDetailWindow(ListenerContext context) throws FunctionalException {
		PropertysModel entiteModel = (PropertysModel) context.userAction().getDataObject();
		UIWindow window = context.userAction().getWindow(controlerParams.getDetailWindowId());
		if (window == null)
			window = context.userAction().createWindowIfNecessary(context.webContext(), controlerParams.getDetailWindowId());

		if (entiteModel.getDetailBean() == null)
			entiteModel.setDetailBean(new PropertyBean());
		
		getEntite(context);
		getEntiteRef(context);
	}
}
