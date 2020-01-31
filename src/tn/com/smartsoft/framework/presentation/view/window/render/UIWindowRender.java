package tn.com.smartsoft.framework.presentation.view.window.render;

import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.web.markup.Doctype;
import tn.com.smartsoft.commons.web.markup.html.Body;
import tn.com.smartsoft.commons.web.markup.html.Div;
import tn.com.smartsoft.commons.web.markup.html.Html;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;
import tn.com.smartsoft.framework.presentation.view.window.UIDefaultWindow;
import tn.com.smartsoft.framework.presentation.view.window.UIGenericRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.framework.presentation.view.window.comman.UIRenderUtils;

public class UIWindowRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIWindowRender(UIDefaultWindow renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		UIWindow uiWindow = (UIWindow) renderComponent;
		render(context, uiWindow.getRoot(), false);
	}

	public void render(UIRenderContext context, UIGenericRendrableComponent rendrableComponentRoot, boolean isComponetRender) {
		UIWindow uiWindow = (UIWindow) renderComponent;
		context.renderln(new Doctype.Html401Strict().createStartTag());
		context.renderln(new Html().createStartTag());
		uiWindow.getScriptRender().render(context);
		context.renderln(1, new Body().createStartTag());
		renderScript(context, rendrableComponentRoot, isComponetRender);
		Div div = new Div();
		div.setID("exportDivFile");
		div.setStyle("display:none;");
		context.renderln(1, div.createStartTag() + div.createEndTag());
		context.renderln(1, new Body().createEndTag());
		context.renderln(new Html().createEndTag());
	}

	private void renderScript(UIRenderContext context, UIGenericRendrableComponent rendrableComponentRoot, boolean isComponetRender) {
		UIWindow uiWindow = (UIWindow) renderComponent;
		UserDesktop userDesktop = uiWindow.userDesktop();
		context.setTitelModule(UIRenderUtils.getLibelleValue(userDesktop.moduleExplorer().getLibelleId()));
		context.setTitelShortcutMenu(UIRenderUtils.getLibelleValue("preferenceMenu"));
		context.setDisplayedMenuBar(userDesktop.isDisplayedMenuBar());
		context.setDisplayedModuleBar(userDesktop.isDisplayedModuleBar());
		context.setDisplayedStatusBar(userDesktop.isDisplayedStatusBar());
		context.setDisplayedShortcutMenu(userDesktop.menuBar() != null && userDesktop.menuBar().getUserMenus() != null && userDesktop.menuBar().getUserMenus().size() > 0);
		context.setAppContainerLayout(uiWindow.getLayout());
		context.setAppContainerLayoutConfig(uiWindow.getLayoutConfigs());
		if (StringUtils.isNotBlank(uiWindow.getFirstFocus()))
			context.setFirstFocusFieldId(uiWindow.getFirstFocus());
		if (userDesktop.userContext() != null && userDesktop.userContext().getCurrentUserSociete() != null && userDesktop.userContext().getCurrentUserSociete().getSocieteBean() != null)
			context.setSociteLabel(userDesktop.userContext().getCurrentUserSociete().getSocieteBean().getLibelle());
		context.setPath(context.webContext().getContextPath().getChildPath(uiWindow.getId()).getPath());
		context.setCollapsedModule(userDesktop.moduleExplorer().isCollapsed());
		context.setDisplayToolbar(uiWindow.getRoot().isToolBar());

		context.initRender();
		if (userDesktop.moduleExplorer() != null)
			userDesktop.moduleExplorer().getRender().render(context);
		if (userDesktop.menuBar() != null)
			userDesktop.menuBar().getRender().render(context);
		rendrableComponentRoot.getRender().render(context);
		context.addMessageJs(userDesktop.messagesHandler().getMessages());
		userDesktop.messagesHandler().resetMessage();
		context.renderScript();
	}

}
