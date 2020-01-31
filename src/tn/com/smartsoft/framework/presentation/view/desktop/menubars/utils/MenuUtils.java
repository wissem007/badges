package tn.com.smartsoft.framework.presentation.view.desktop.menubars.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.utils.BeanComparator;
import tn.com.smartsoft.commons.utils.SorterType;
import tn.com.smartsoft.framework.configuration.impl.ComponentIdImpl;
import tn.com.smartsoft.framework.context.OrganismeContext;
import tn.com.smartsoft.framework.context.SocieteContext;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.factory.PresentationBeanFactory;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIListMenu;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenu;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuAction;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuBar;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuItem;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuNode;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UISocieteMenu;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UISplitButton;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UILinkedEvent;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.graphique.menu.beans.MenuBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuAction.beans.MenuActionBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuItem.beans.MenuItemBean;
import tn.com.smartsoft.iap.dictionary.graphique.toolAction.beans.ToolActionBean;

public class MenuUtils{

	public static List<String> getTitle(UIMenuNode node) {
		ArrayList<String> title = new ArrayList<String>();
		if (node == null) return title;
		UIMenuNode parentMenu = node;
		while (parentMenu != null) {
			title.add(parentMenu.getLibelle());
			parentMenu = (UIMenuNode) parentMenu.getParent();
		}
		return title;
	}
	public static String renderTitle(ListenerContext context, UIMenuNode node) {
		List<String> title = getTitle(node);
		return renderTitle(context, title);
	}
	public static String renderTitle(ListenerContext context, List<String> title) {
		String label = "";
		for (int i = title.size() - 1; i >= 0; i--) {
			if (StringUtils.isNotBlank(label)) label = label + ">";
			label = label + title.get(i);
		}
		return label;
	}
	public static UIMenuBar loadMenuBar(UserContext userContext, String moduleId, PresentationBeanFactory presentationBeanFactory) {
		UIMenuBar menuBar = new UIMenuBar(moduleId, userContext);
		List<MenuItemBean> menuBeans = presentationBeanFactory.getCacheDictionaryManager().getMenusModule(moduleId);
		List<?> toolActions = presentationBeanFactory.getCacheDictionaryManager().getToolActions();
		for (int i = 0; i < menuBeans.size(); i++) {
			MenuItemBean menuItemBean = (MenuItemBean) menuBeans.get(i);
			addMenu(userContext, menuItemBean, menuBar, presentationBeanFactory);
		}
		menuBar.getMenuHandler().sort();
		String userLabel = userContext.getUser().getDisplayName();
		UISplitButton userSplitButton = new UISplitButton("sssUserMenu", userLabel, userLabel, new Long(0), "user");
		for (int i = 0; i < toolActions.size(); i++) {
			ToolActionBean toolActionBean = (ToolActionBean) toolActions.get(i);
			addToolsMenu(userSplitButton, i, toolActionBean);
		}
		userSplitButton.getMenuHandler().sort();
		menuBar.setUserActions(userSplitButton);
		UISocieteMenu userSocieteSplitButton = new UISocieteMenu("sssUserSocieteMenu", userLabel, userLabel, new Long(0), "organisation");
		List<SocieteContext> lisSocieteContext = userContext.getUserSocietes();
		BeanComparator.sort(new String[] { "societeBean.libelle" }, SorterType.ASC, lisSocieteContext);
		for (int i = 0; i < lisSocieteContext.size(); i++) {
			SocieteContext societeContext = lisSocieteContext.get(i);
			addSocitesMenu(userSocieteSplitButton, i, societeContext);
		}
		menuBar.setSocieteActions(userSocieteSplitButton);
		return menuBar;
	}
	private static UIMenuNode loadMenu(UIMenuNode node, MenuBean menuBean) {
		node.setId(String.valueOf(menuBean.getMenuId()).replace("-", "_"));
		node.setLibelle(menuBean.getLibelle());
		node.setHelp(menuBean.getHelp());
		node.setRang(menuBean.getRang());
		node.setIconUrl(menuBean.getIconUrl());
		return node;
	}
	private static UIMenu createMenu(MenuItemBean menuItemBean) {
		return (UIMenu) loadMenu(new UIMenu(), menuItemBean);
	}
	private static UIMenuItem createMenuItem(MenuItemBean menuItemBean) {
		return (UIMenuItem) loadMenu(new UIMenuItem(), menuItemBean);
	}
	private static UIMenuAction createMenuAction(MenuActionBean menuActionBean) {
		UIMenuAction menuAction = (UIMenuAction) loadMenu(new UIMenuAction(), menuActionBean);
		menuAction.setUserActionId(new ComponentIdImpl((ActionBeanId) menuActionBean.getAction().getId()));
		boolean isMenuTemplate = menuActionBean.getAction() != null && menuActionBean.getAction().getActionTemplate() != null;
		menuAction.setLibelle(isMenuTemplate ? (menuActionBean.getAction().getActionTemplate().getIsLabel() ? menuActionBean.getAction().getActionTemplate().getLibelle() : menuActionBean
				.getLibelle()) : "");
		menuAction.setIconUrl(isMenuTemplate ? menuActionBean.getAction().getActionTemplate().getIconUrl() : "");
		return menuAction;
	}
	private static void addSocitesMenu(UISplitButton userOrgSplitButton, int i, SocieteContext societeContext) {
		UIMenuItem menuSociete = new UIMenuItem();
		menuSociete.setId("sos" + new Long(societeContext.getSocieteBean().getSocieteId()));
		menuSociete.setLibelle(societeContext.getSocieteBean().getLibelle());
		menuSociete.setHelp(societeContext.getSocieteBean().getLibelle());
		menuSociete.setRang(new Long(i + 1));
		menuSociete.setIconUrl("societe");
		menuSociete.addRequestParam("societeId", String.valueOf(societeContext.getSocieteBean().getSocieteId()), false);
		List<OrganismeContext> lisOrganisme = societeContext.getUserOrganismes();
		BeanComparator.sort(new String[] { "organismeBean.organismeId", "organismeBean.libelle" }, SorterType.ASC, lisOrganisme);
		for (int j = 0; j < lisOrganisme.size(); j++) {
			OrganismeContext organismeContext = lisOrganisme.get(j);
			UIMenuAction menuAction = new UIMenuAction();
			menuAction.setId("org" + new Long(organismeContext.getOrganismeBean().getOrganismeId()));
			menuAction.setLibelle(organismeContext.getDispalyLabele());
			menuAction.setHelp(organismeContext.getDispalyLabele());
			menuAction.setRang(new Long(i + 1));
			menuAction.setUserActionId(new ComponentIdImpl("system", "system", "system", "changeOrgAction"));
			menuAction.setIconUrl("organisation");
			menuAction.addRequestParam("organismeId", String.valueOf(organismeContext.getOrganismeBean().getOrganismeId()), false);
			menuAction.addRequestParam("societeId", String.valueOf(organismeContext.getOrganismeBean().getSocieteId()), false);
			menuSociete.addMenuAction(menuAction);
		}
		menuSociete.setUserActionId(new ComponentIdImpl("system", "system", "system", "changeSociteAction"));
		menuSociete.addEvent(new UILinkedEvent(ClientEvent.ON_CLICK, menuSociete, "onClikActionListener"));
		userOrgSplitButton.addMenu(menuSociete);
	}
	private static void addToolsMenu(UISplitButton userSplitButton, int i, ToolActionBean toolActionBean) {
		UIMenuAction menuAction = new UIMenuAction();
		menuAction.setId("sssUserAction" + String.valueOf(toolActionBean.getToolActionId()));
		menuAction.setLibelle(toolActionBean.getAction().getLibelle());
		menuAction.setHelp(toolActionBean.getAction().getHelp());
		menuAction.setRang(toolActionBean.getRang());
		menuAction.setUserActionId(new ComponentIdImpl((ActionBeanId) toolActionBean.getAction().getId()));
		boolean isMenuTemplate = toolActionBean.getAction() != null && toolActionBean.getAction().getActionTemplate() != null;
		menuAction.setLibelle(isMenuTemplate ? toolActionBean.getAction().getActionTemplate().getLibelle() : "");
		menuAction.setIconUrl(isMenuTemplate ? toolActionBean.getAction().getActionTemplate().getIconUrl() : "");
		userSplitButton.addMenu(menuAction);
	}
	private static void addMenu(UserContext userContext, MenuItemBean menuItemBean, UIListMenu listMenu, PresentationBeanFactory presentationBeanFactory) {
		if (menuItemBean.getMenuActions() != null && menuItemBean.getMenuActions().size() > 0) {
			UIMenuItem menuItem = createMenuItem(menuItemBean);
			for (int i = 0; i < menuItemBean.getMenuActions().size(); i++) {
				MenuActionBean menuAction = (MenuActionBean) menuItemBean.getMenuActions().get(i);
				if (userContext.isGranted(new ActionBeanId(menuAction.getActionId(), menuAction.getSujetId(), menuAction.getSubModuleId(), menuAction.getModuleId()))) {
					menuItem.addMenuAction(createMenuAction(menuAction));
				}
			}
			if (menuItem.getMenuActions().size() > 0) {
				menuItem.getMenuActions().sort();
				listMenu.addMenu(menuItem);
			}
		} else if (menuItemBean.getMenus() != null && menuItemBean.getMenus().size() > 0) {
			UIMenu menu = createMenu(menuItemBean);
			for (int i = 0; i < menuItemBean.getMenus().size(); i++) {
				MenuItemBean menuItemBeanChild = (MenuItemBean) menuItemBean.getMenus().get(i);
				addMenu(userContext, menuItemBeanChild, menu, presentationBeanFactory);
			}
			if (menu.getMenus().size() > 0) {
				menu.getMenuHandler().sort();
				listMenu.addMenu(menu);
			}
		}
	}
}
