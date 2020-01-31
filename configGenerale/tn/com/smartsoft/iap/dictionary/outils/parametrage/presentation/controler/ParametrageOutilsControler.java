package tn.com.smartsoft.iap.dictionary.outils.parametrage.presentation.controler;

import java.util.List;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.DefauldInvokerMethod;
import tn.com.smartsoft.commons.utils.InvokerMethod;
import tn.com.smartsoft.configGenerale.dynamicEntite.entiteSimple.beans.DataValuesBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtComboBoxField;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.business.ModuleBusiness;
import tn.com.smartsoft.iap.dictionary.graphique.menuItem.business.MenuItemBusiness;
import tn.com.smartsoft.iap.dictionary.outils.parametrage.presentation.model.OutilModel;
import tn.com.smartsoft.iap.system.business.ApplicationDictionnaireLoader;
import tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader;
import tn.com.smartsoft.iap.system.utils.ApplicationDictionnaireLoaderUtils;
import tn.com.smartsoft.iap.system.utils.GenerateRoleUtils;

public class ParametrageOutilsControler extends GenericEntiteControler{
	
	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1L;
	private ApplicationDictionnaireLoader	applicationDictionnaireLoader;
	private ModuleBusiness					moduleBusiness;
	private MenuItemBusiness				menuBusiness;
	
	public MenuItemBusiness getMenuBusiness() {
		return menuBusiness;
	}
	public void setMenuBusiness(MenuItemBusiness menuBusiness) {
		this.menuBusiness = menuBusiness;
	}
	public void setApplicationDictionnaireLoader(ApplicationDictionnaireLoader applicationDictionnaireLoader) {
		this.applicationDictionnaireLoader = applicationDictionnaireLoader;
	}
	public void setModuleBusiness(ModuleBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}
	public void doFilter(ListenerContext context) throws FunctionalException {
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			List<DataBusinessBean> list = menuBusiness.getAllByCriteria(entiteModel.getSearcheBean(), context.userAction().getActionBeanId(), context.userDesktop()
					.userContext());
			entiteModel.setListBean(list);
			jsonItemResponseModel.addData("empty", list.isEmpty());
			if (list.isEmpty()) throw new FunctionalException(controlerParams.getMessageEmptyRow());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
		}
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}
	public void onInitListModule(ListenerContext context) throws FunctionalException {
		OutilModel entiteModel = (OutilModel) context.userAction().getDataObject();
		ModuleBean module = new ModuleBean();
		module.setActivateAndType(Boolean.TRUE);
		List<DataBusinessBean> listModule = moduleBusiness.getByCriteria(module, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListModule(listModule);
	}
	public void onInitList(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		UserContext userContext = context.userDesktop().userContext();
		ActionBeanId actionBeanId = context.userAction().getActionBeanId();
		DataValuesBean bean = new DataValuesBean();
		bean.setEntiteId("applicationLoader");
		List<DataBusinessBean> listBean = entiteBusiness.getByCriteria(bean, actionBeanId, userContext);
		entiteModel.setListBean(listBean);
	}
	public void doValiderCreate(ListenerContext context) {
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			UIWindow window = context.userAction().getWindow(controlerParams.getDetailWindowId());
			if (window == null) window = context.userAction().createWindowIfNecessary(context.webContext(), controlerParams.getDetailWindowId());
			UIExtComboBoxField field = (UIExtComboBoxField) window.findChild("methodName");
			Integer index = (Integer) field.getSelectedRecord();
			DataValuesBean bean = (DataValuesBean) entiteModel.getListBean().get(index);
			ApplicationDictionnaireLoaderUtils applicationCacheDictionary = new ApplicationDictionnaireLoaderUtils();
			InvokerMethod invokerMethod = new DefauldInvokerMethod(applicationCacheDictionary, bean.getAbreviation());
			invokerMethod.run(applicationDictionnaireLoader, IApplicationDictionnaireLoader.class);
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageUpdateValid());
			doHomePage(context);
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
	public void doValiderCreateRole(ListenerContext context) {
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			// List<DataBusinessBean> menus =
			// getListParentMenus(entiteModel.getListBean());
			GenerateRoleUtils.generateRole(entiteModel.getListBean(), menuBusiness, context.userContext());
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageUpdateValid());
			context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
}
