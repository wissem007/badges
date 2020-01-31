package tn.com.smartsoft.framework.configuration;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;
import tn.com.smartsoft.framework.presentation.view.comman.MenuModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.application.beans.ApplicationBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.subModule.beans.SubModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.subModule.beans.SubModuleBeanId;
import tn.com.smartsoft.iap.dictionary.graphique.actionTemplate.beans.ActionTemplateBean;
import tn.com.smartsoft.iap.dictionary.graphique.libelle.beans.LibelleBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuItem.beans.MenuItemBean;
import tn.com.smartsoft.iap.dictionary.graphique.message.beans.MessageBean;
import tn.com.smartsoft.iap.dictionary.graphique.toolAction.beans.ToolActionBean;
import tn.com.smartsoft.iap.dictionary.graphique.userType.beans.UserTypeBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBeanId;
import tn.com.smartsoft.iap.dictionary.securite.role.beans.RoleBean;

public class ApplicationCacheDictionaryManager {
	private IApplicationCacheDictionary applicationDictionnaire;

	public ApplicationCacheDictionaryManager(IApplicationCacheDictionary applicationDictionnaire) {
		super();
		this.applicationDictionnaire = applicationDictionnaire;
	}

	public EntiteBean getEntiteBean(String javaClass) {
		return (EntiteBean) applicationDictionnaire.getEntiteDictionaryCacheByClass().get(javaClass);
	}

	public EntiteBean getEntiteBean(EntiteBeanId entiteBeanId) {
		return (EntiteBean) applicationDictionnaire.getEntiteDictionaryCache().get(entiteBeanId);
	}

	public EntiteBean getEntiteBean(Class<?> javaClass) {
		return getEntiteBean(javaClass.getName());
	}

	public MessageBean getMessageBean(String messagesId) {
		return (MessageBean) applicationDictionnaire.getMessageDictionaryCache().get(messagesId);
	}

	public ActionBean getActionBean(ActionBeanId actionBeanId) {
		return (ActionBean) applicationDictionnaire.getActionDictionaryCache().get(actionBeanId);
	}

	public ModuleBean getModuleBean(String moduleId) {
		return (ModuleBean) applicationDictionnaire.getModuleDictionaryCache().get(moduleId);
	}

	public SubModuleBean getSubModuleBean(SubModuleBeanId subModuleBeanId) {
		return (SubModuleBean) applicationDictionnaire.getSubModuleDictionaryCache().get(subModuleBeanId);
	}

	public ViewBean getViewBean(ViewBeanId viewBeanId) {
		return (ViewBean) applicationDictionnaire.getViewDictionaryCache().get(viewBeanId);
	}

	public LibelleBean getLibelleBean(String id) {
		return (LibelleBean) applicationDictionnaire.getLibelleDictionaryCache().get(id);
	}

	public RoleBean getRoleBean(String id) {
		return (RoleBean) applicationDictionnaire.getRoleDictionary().get(id);
	}

	public ActionTemplateBean getActionTemplateBean(Long id) {
		return (ActionTemplateBean) applicationDictionnaire.getActionTemplateDictionary().get(id);
	}

	public List<MenuItemBean> getMenusModule(String moduleId) {
		MenuModuleBean menuModuleBean = (MenuModuleBean) applicationDictionnaire.getMenuDictionaryCache().get(moduleId);
		if (menuModuleBean == null)
			return new ArrayList<MenuItemBean>();
		return menuModuleBean.getMenus();
	}

	public ApplicationBean getApplicationBean() {
		return applicationDictionnaire.getApplicationDictionary();
	}

	public UserTypeBean getUserTypeBean(String userTypeId) {
		return (UserTypeBean) applicationDictionnaire.getUserTypeDictionary().get(userTypeId);
	}

	public List<ToolActionBean> getToolActions() {
		List<ToolActionBean> list = new ArrayList<ToolActionBean>();
		List<?> keys = applicationDictionnaire.getToolActionDictionary().getKeys();
		for (int i = 0; i < keys.size(); i++) {
			Object key = keys.get(i);
			list.add((ToolActionBean) applicationDictionnaire.getToolActionDictionary().get(key));
		}
		return list;
	}

	public List<UserTypeBean> getUserTypeBeans() {
		List<UserTypeBean> list = new ArrayList<UserTypeBean>();
		List<?> keys = applicationDictionnaire.getUserTypeDictionary().getKeys();
		for (int i = 0; i < keys.size(); i++) {
			Object key = keys.get(i);
			list.add((UserTypeBean) applicationDictionnaire.getUserTypeDictionary().get(key));
		}
		return list;
	}

	public IApplicationCacheDictionary getApplicationDictionnaire() {
		return applicationDictionnaire;
	}

	public DeviseBean getDeviseBase() {
		return getApplicationBean().getDeviseBase();
	}

	public PayBean getPays() {
		return getApplicationBean().getPayBean();
	}

	public DeviseBean getDeviseBean(Long deviseId) {
		return (DeviseBean) applicationDictionnaire.getDeviseDictionaryCache().get(deviseId);
	}
}
