package tn.com.smartsoft.iap.system.utils;

import java.util.List;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.ListUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.graphique.menu.beans.MenuBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuAction.beans.MenuActionBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuItem.beans.MenuItemBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuItem.business.MenuItemBusiness;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.ActionRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.EntiteRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.GroupedRoleBean;

public class GenerateRoleUtils{
	
	public static void generateRole(List<DataBusinessBean> menus, MenuItemBusiness menuItemBusiness, UserContext userContext) throws FunctionalException {
		menuItemBusiness.getDaoSession().beginTransaction();
		DbPersistantSupport dbPersistantSupport = menuItemBusiness.getDaoSession().persistantSupport(null);
		for (int i = 0; i < menus.size(); i++) {
			if (menus.get(i).getEtatBusiness()) {
				MenuBean menu = (MenuBean) menus.get(i);
				if (menu instanceof MenuItemBean) {
					MenuItemBean menuItemBean = (MenuItemBean) menu;
					List<?> lis = ListUtils.selectByProperty(menus, "parentMenuId", menuItemBean.getMenuId());
					boolean isMI = false;
					boolean isMGro = false;
					for (int j = 0; j < lis.size(); j++) {
						if (lis.get(j) instanceof MenuActionBean) {
							isMI = true;
							break;
						}
					}
					for (int j = 0; j < lis.size(); j++) {
						if (lis.get(j) instanceof MenuItemBean) {
							isMGro = true;
							break;
						}
					}
					if (isMI && !isMGro) {
						EntiteRoleBean entiteRoleBean = new EntiteRoleBean();
						entiteRoleBean.setRoleId(String.valueOf(menuItemBean.getMenuId()));
						entiteRoleBean.setModuleId(menuItemBean.getModuleId());
						entiteRoleBean.setLibelle(menuItemBean.getLibelle());
						entiteRoleBean.setHelp(menuItemBean.getHelp());
						entiteRoleBean.setRang(menuItemBean.getRang());
						if (menuItemBean.getParentMenuId() != null) entiteRoleBean.setPrentRoleId(String.valueOf(menuItemBean.getParentMenuId()));
						menuItemBusiness.getDaoSession().evict(entiteRoleBean);
						dbPersistantSupport.saveOrUpdate(entiteRoleBean);
					} else {
						GroupedRoleBean groupedRoleBean = new GroupedRoleBean();
						groupedRoleBean.setRoleId(String.valueOf(menuItemBean.getMenuId()));
						groupedRoleBean.setModuleId(menuItemBean.getModuleId());
						groupedRoleBean.setLibelle(menuItemBean.getLibelle());
						groupedRoleBean.setHelp(menuItemBean.getHelp());
						groupedRoleBean.setRang(menuItemBean.getRang());
						if (menuItemBean.getParentMenuId() != null) groupedRoleBean.setPrentRoleId(String.valueOf(menuItemBean.getParentMenuId()));
						menuItemBusiness.getDaoSession().evict(groupedRoleBean);
						dbPersistantSupport.saveOrUpdate(groupedRoleBean);
					}
				} else {
					MenuActionBean menuAction = (MenuActionBean) menu;
					ActionRoleBean actionRole = new ActionRoleBean();
					actionRole.setRoleId(String.valueOf(menuAction.getMenuId()));
					actionRole.setModuleId(menuAction.getModuleId());
					actionRole.setLibelle(menuAction.getLibelle());
					actionRole.setHelp(menuAction.getHelp());
					actionRole.setRang(menuAction.getRang());
					actionRole.setPrentRoleId(String.valueOf(menuAction.getParentMenuId()));
					menuItemBusiness.getDaoSession().evict(actionRole);
					dbPersistantSupport.saveOrUpdate(actionRole);
					ActionBeanId actionId = new ActionBeanId(menuAction.getActionId(), menuAction.getSujetId(), menuAction.getSubModuleId(), menuAction.getModuleId());
					ActionBean actionBean = (ActionBean) dbPersistantSupport.get(ActionBean.class, actionId);
					actionBean.setRoleId(String.valueOf(menuAction.getMenuId()));
				}
			}
		}
		menuItemBusiness.getDaoSession().commitTransaction();
	}
	public static void addRole(MenuItemBean menusr, MenuItemBusiness menuItemBusiness, UserContext userContext) throws FunctionalException {
		DbPersistantSupport dbPersistantSupport = menuItemBusiness.getDaoSession().persistantSupport(null);
		if (menusr.getMenuActions() != null && menusr.getMenuActions().size() > 0) {
			EntiteRoleBean entiteRoleBean = new EntiteRoleBean();
			entiteRoleBean.setRoleId(String.valueOf(menusr.getMenuId()));
			entiteRoleBean.setModuleId(menusr.getModuleId());
			entiteRoleBean.setLibelle(menusr.getLibelle());
			entiteRoleBean.setHelp(menusr.getHelp());
			entiteRoleBean.setRang(menusr.getRang());
			if (menusr.getParentMenuId() != null) entiteRoleBean.setPrentRoleId(String.valueOf(menusr.getParentMenuId()));
			menuItemBusiness.getDaoSession().evict(entiteRoleBean);
			dbPersistantSupport.saveOrUpdate(entiteRoleBean);
			for (int i = 0; i < menusr.getMenuActions().size(); i++) {
				MenuActionBean menuAction = menusr.getMenuActions().get(i);
				ActionRoleBean actionRole = new ActionRoleBean();
				actionRole.setRoleId(String.valueOf(menuAction.getMenuId()));
				actionRole.setModuleId(menuAction.getModuleId());
				actionRole.setLibelle(menuAction.getLibelle());
				actionRole.setHelp(menuAction.getHelp());
				actionRole.setRang(menuAction.getRang());
				actionRole.setPrentRoleId(String.valueOf(menuAction.getParentMenuId()));
				menuItemBusiness.getDaoSession().evict(actionRole);
				dbPersistantSupport.saveOrUpdate(actionRole);
				ActionBeanId actionId = new ActionBeanId(menuAction.getActionId(), menuAction.getSujetId(), menuAction.getSubModuleId(), menuAction.getModuleId());
				ActionBean actionBean = (ActionBean) dbPersistantSupport.get(ActionBean.class, actionId);
				actionBean.setRoleId(String.valueOf(menuAction.getMenuId()));
			}
		} else {
			GroupedRoleBean groupedRoleBean = new GroupedRoleBean();
			groupedRoleBean.setRoleId(String.valueOf(menusr.getMenuId()));
			groupedRoleBean.setModuleId(menusr.getModuleId());
			groupedRoleBean.setLibelle(menusr.getLibelle());
			groupedRoleBean.setHelp(menusr.getHelp());
			groupedRoleBean.setRang(menusr.getRang());
			if (menusr.getParentMenuId() != null) groupedRoleBean.setPrentRoleId(String.valueOf(menusr.getParentMenuId()));
			menuItemBusiness.getDaoSession().evict(groupedRoleBean);
			dbPersistantSupport.saveOrUpdate(groupedRoleBean);
		}
	}
}
