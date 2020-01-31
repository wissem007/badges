package tn.com.smartsoft.framework.presentation.view.desktop.menubars.render;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.commons.web.utils.HtmlEncodeUtil;
import tn.com.smartsoft.framework.context.OrganismeContext;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuAction;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuBar;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuNode;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UISplitButton;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.comman.UIRenderUtils;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEventListener;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UILinkedEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIEventRenderUtils;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserMenuBean;

public class UIMenuBarRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIMenuBarRender(UIMenuBar renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		UIMenuBar menuBar = (UIMenuBar) renderComponent;
		if (!menuBar.isDisplayed())
			return;
		context.addComponentsToBufferJs("//****************************Menu start**************************\n");
		for (int i = 0; i < menuBar.getMenus().size(); i++) {
			UIMenuNode menu = menuBar.getMenus().get(i);
			UIRender render = menu.getRender();
			render.setParentRender(this);
			render.render(context);
		}
		if (StringUtils.isNotBlank(menuBar.getDisplayMenuTitle()))
			context.addLabelAction(menuBar.getDisplayMenuTitle() + (menuBar.getMenuActions() != null ? " >" : ""));
		if (menuBar.getMenuActions() != null) {
			UISplitButton menuActions = menuBar.getMenuActions();
			UIRender render = menuActions.getRender();
			render.setParentRender(this);
			render.render(context);
		}
		if (StringUtils.isNotBlank(menuBar.getDisplayActionTitle()))
			context.addLabelAction((menuBar.getMenuActions() != null ? " >" : "") + menuBar.getDisplayActionTitle());
		context.addStringAction("->");

		if (menuBar.getSocieteActions() != null) {
			UISplitButton societeActions = menuBar.getSocieteActions();
			OrganismeContext currentUserOrganisme = context.webContext().userDesktop().userContext().getCurrentUserOrganisme();
			String label = currentUserOrganisme.getOrganismeBean().getLibelle();
			societeActions.setLibelle(label);
			UIRender render = societeActions.getRender();
			render.setParentRender(this);
			render.render(context);
		}
		if (menuBar.getUserActions() != null) {
			UISplitButton userActions = menuBar.getUserActions();
			UIRender render = userActions.getRender();
			render.setParentRender(this);
			render.render(context);
		}
		context.addComponentsToBufferJs("//****************************Menu end**************************\n");
		List<UserMenuBean> userMenus = menuBar.getUserMenus();
		if (userMenus != null) {
			for (int i = 0; i < userMenus.size(); i++) {
				UserMenuBean userMenuBean = userMenus.get(i);
				renderJsShortcutMenu(context, userMenuBean);
			}
		}

	}

	public void renderJsShortcutMenu(UIRenderContext context, UserMenuBean userMenuBean) {
		UIMenuBar menuBar = (UIMenuBar) renderComponent;
		UIMenuAction menuActionBean = (UIMenuAction) menuBar.findMenu(String.valueOf(userMenuBean.getMenuId()));
		if (menuActionBean == null)
			return;
		String libelle = null;
		if (StringUtils.isBlank(userMenuBean.getLibelle())) {
			UIMenuNode menuNode = (UIMenuNode) menuActionBean.getParent();
			if (menuNode != null)
				userMenuBean.setLibelle(menuNode.getLibelle() + "(" + menuActionBean.getLibelle() + ")");
			else
				userMenuBean.setLibelle(menuActionBean.getLibelle());
		}
		libelle = HtmlEncodeUtil.encodeHtml(userMenuBean.getLibelle());
		JsMap module = new JsMap();
		module.addStringValue("text", libelle);
		module.addStringValue("icon", UIRenderUtils.getImagePath(menuActionBean.getIconUrl()).getPath());
		UILinkedEvent linkedEvent = new UILinkedEvent(ClientEvent.ON_CLICK, (UIEventListener) null);
		linkedEvent.setComponentId(menuActionBean.getId());
		linkedEvent.setActionPattern(menuBar.desktopPartType().toString());
		UIEventRenderUtils.renderHrefEventToHandelConfig(context, this, linkedEvent, module);
		
		context.addShortcutMenu(module);
	}
}
