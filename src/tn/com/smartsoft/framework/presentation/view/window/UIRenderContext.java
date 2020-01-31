package tn.com.smartsoft.framework.presentation.view.window;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.utils.text.MessageUtil;
import tn.com.smartsoft.commons.web.js.JSEncodeUtil;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.beans.Money;
import tn.com.smartsoft.framework.configuration.ApplicationCacheDictionaryManager;
import tn.com.smartsoft.framework.configuration.ApplicationManager;
import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.definition.ImageDefinition;
import tn.com.smartsoft.framework.presentation.definition.WebDefinition;
import tn.com.smartsoft.framework.presentation.message.UIMessage;
import tn.com.smartsoft.framework.presentation.view.window.comman.UIRenderUtils;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtDesktopJs;
import tn.com.smartsoft.iap.dictionary.graphique.message.beans.MessageBean;

public class UIRenderContext implements UIObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIExtDesktopJs desktopJs;
	private UIJsBufferRender onlaodDesktopJs;
	private UIJsBufferRender onLastlaodDesktopJs;
	private UIRenderStringValue onSetFirstFocusFieldIdJs;
	private UIJsBufferRender functionsJs;
	private boolean isMessagesStart = false;
	private boolean isAlertMessage = false;
	private WebContext context;
	private UIJsBufferRender curentComponentsJsBuffer;
	private boolean isLodedDialog = false;

	public UIRenderContext(WebContext context) {
		super();
		this.onlaodDesktopJs = new UIJsBufferRender();
		this.onLastlaodDesktopJs = new UIJsBufferRender();
		this.functionsJs = new UIJsBufferRender();
		this.curentComponentsJsBuffer = onlaodDesktopJs;
		this.context = context;
		this.desktopJs = new UIExtDesktopJs();
	}

	public void initRender() {
		addFunctionJs(desktopJs.newInstance());
	}

	public void setCurentComponentsJsBuffer(UIJsBufferRender curentJsBufferRender) {
		if (curentJsBufferRender != null)
			this.curentComponentsJsBuffer = curentJsBufferRender;
		else
			this.curentComponentsJsBuffer = onlaodDesktopJs;
	}

	public boolean isLodedDialog() {
		return isLodedDialog;
	}

	public void setLodedDialog(boolean isLodedDialog) {
		this.isLodedDialog = isLodedDialog;
	}

	public void addToolsBarItem(JsMap toolsBarItem) {
		curentComponentsJsBuffer.addBufferRender(desktopJs.addToolsBarItem(toolsBarItem));
	}

	public void createToolSheet(String icon, String id) {
		curentComponentsJsBuffer.addBufferRender(desktopJs.createToolSheet(icon, id));
	}

	public void addModule(JsMap module) {
		curentComponentsJsBuffer.addBufferRender(desktopJs.addModule(module));
	}

	public void addShortcutMenu(JsMap shortcutMenu) {
		curentComponentsJsBuffer.addBufferRender(desktopJs.addShortcutMenu(shortcutMenu));
	}

	public void addOnStartUpAction(String cmpId, String actionId) {
		curentComponentsJsBuffer.addBufferRender(desktopJs.addOnStartUpAction(cmpId, actionId));
	}

	public void addMenu(JsMap menu) {
		curentComponentsJsBuffer.addBufferRender(desktopJs.addMenu(menu.toJs()));
	}

	public void addMenu(String menu) {
		curentComponentsJsBuffer.addBufferRender(desktopJs.addMenu(menu));
	}

	public void addObjectMenu(String menu) {
		curentComponentsJsBuffer.addBufferRender(desktopJs.addObjectAction(menu));
	}

	public void addObjectAction(String action) {
		curentComponentsJsBuffer.addBufferRender(desktopJs.addObjectAction(action));
	}

	public void addLabelAction(String action) {
		if (StringUtils.isNotBlank(action))
			curentComponentsJsBuffer.addBufferRender(desktopJs.addLabelAction(action));
	}

	public void addStringAction(String action) {
		if (StringUtils.isNotBlank(action))
			curentComponentsJsBuffer.addBufferRender(desktopJs.addStringAction(action));
	}

	public void addToolItem(String name) {
		curentComponentsJsBuffer.addBufferRender(desktopJs.addToolItem(name));
	}

	public void addToolItem(JsMap menu) {
		curentComponentsJsBuffer.addBufferRender(desktopJs.addToolItem(menu.toJs()));
	}

	public UIExtDesktopJs getDesktopJs() {
		return desktopJs;
	}

	public void addComponentsToBufferJs(UIRenderValue onlaodJs) {
		curentComponentsJsBuffer.addBufferRender(onlaodJs);
	}

	public void addComponentsToBufferJs(String onlaodJs) {
		curentComponentsJsBuffer.addBufferRender(onlaodJs);
	}

	public WebContext webContext() {
		return context;
	}

	public void addFunctionJs(UIRenderValue onlaodJs) {
		functionsJs.addBufferRender(onlaodJs);
	}

	public void setDisplayedShortcutMenu(boolean isShortcutMenuExplorer) {
		desktopJs.setDisplayedShortcutMenu(isShortcutMenuExplorer);
	}

	public void setDisplayedMenuBar(boolean isDisplayedMenuBar) {
		desktopJs.setDisplayedMenuBar(isDisplayedMenuBar);
	}

	public void setDisplayedModuleBar(boolean isDisplayedModuleBar) {
		desktopJs.setDisplayedModuleBar(isDisplayedModuleBar);
	}

	public void setDisplayedStatusBar(boolean isDisplayedStatusBar) {
		desktopJs.setDisplayedStatusBar(isDisplayedStatusBar);
	}

	public void setInsatanceName(String insatanceName) {
		desktopJs.setInsatanceName(insatanceName);
	}

	public UIRenderStringValue setFirstFocusFieldId(String fieldId) {
		this.onSetFirstFocusFieldIdJs = desktopJs.setFirstFocusFieldId(fieldId);
		return this.onSetFirstFocusFieldIdJs;
	}

	public void setLabelsAction(String labelsAction) {
		desktopJs.setLabelsAction(labelsAction);
	}

	public void setTitelModule(String titelModule) {
		desktopJs.setTitelModule(titelModule);
	}

	public void setTitelShortcutMenu(String titelShortcutMenu) {
		desktopJs.setTitelShortcutMenu(titelShortcutMenu);
	}

	public void addFunctionJs(final String onlaodJs) {
		addFunctionJs(new UIRenderStringValue(onlaodJs, "     "));
	}

	public void addEndlaodDesktopJs(UIRenderValue endLaodJs) {
		onLastlaodDesktopJs.addBufferRender(endLaodJs);
	}

	public void addEndlaodDesktopJs(final String endLaodJs) {
		addEndlaodDesktopJs(new UIRenderStringValue(endLaodJs, "     "));
	}

	public void addMessageJs(final String msg) {
		if (!isMessagesStart)
			addFunctionJs(new UIRenderStringValue("var alertMessage=\"\";", "     "));
		String message = "alertMessage=alertMessage+\"" + JSEncodeUtil.encodeJavaScript(msg) + "\";";
		addFunctionJs(new UIRenderStringValue(message, "     "));
		isMessagesStart = true;
	}

	public void addMessageJs(List<UIMessage> messages) {
		boolean isSup = messages.size() > 1;
		for (int i = 0; i < messages.size(); i++) {
			boolean isReourLigne = (messages.size() - 1) != i;
			addMessageJs(messages.get(i), isReourLigne, isSup);
			if (messages.size() - 1 != 1) {
			}
		}
	}

	public void addMessageJs(UIMessage message, boolean isReourLigne, boolean isSup) {
		MessageBean messageBean = getApplicationCacheDictionaryManager().getMessageBean(message.getCode());
		if (messageBean == null) {
			addMessageJs(message.getCode());
		} else {
			isAlertMessage = messageBean.getIsAlert();
			String msg = MessageUtil.substituteParamsFrLocal(messageBean.getLibelle(), message.getArguments());
			if (isSup)
				msg = "-" + msg;
			if (isReourLigne)
				msg += "<br>";
			addMessageJs(msg);
		}
	}

	public void renderScript() {
		this.renderStartJs(" ");
		renderln("smartsoft.Number.decimal=" + new Money().getDevise().getNombreDecimales().intValue() + ";");
		renderln("//********************** list des fonctions **************************** ");
		functionsJs.render(this);
		renderln("//********************** list des composants**************************** ");
		if (!isLodedDialog)
			renderln("Ext.onReady(function() {");
		else {
			renderln("var " + desktopJs.getInsatanceName() + "=Ext.getCmp('" + desktopJs.getInsatanceName() + "')");
		}
		onlaodDesktopJs.render(this);
		if (isMessagesStart) {
			renderln("//********************** alertMessage       **************************** ");
			if (!isAlertMessage)
				desktopJs.addStatusLabel("alertMessage").setTabRender(" ").render(this);
			else
				desktopJs.alert("alertMessage", "INFO", false).setTabRender("    ").render(this);
		}
		if (onSetFirstFocusFieldIdJs != null)
			onSetFirstFocusFieldIdJs.render(this);
		desktopJs.doInitDesktop().setTabRender(" ").render(this);
		onLastlaodDesktopJs.render(this);
		if (!isLodedDialog) {
			renderln("});");
		}
		this.renderEndJs(" ");
	}

	public void addNavBarItem(JsMap toolsBarItem) {
		curentComponentsJsBuffer.addBufferRender(desktopJs.addNavBarItem(toolsBarItem));
	}

	public void renderJsAddToolItem(String name) {
		addComponentsToBufferJs(desktopJs.addToolItem(name));
	}

	public void renderAddListenerContainer(String name, String handler) {
		addComponentsToBufferJs(desktopJs.addListenerContainer(name, handler));
	}

	public void renderJsStatusLabel(String statusLabel) {
		addComponentsToBufferJs(desktopJs.addStatusLabel(statusLabel));
	}

	public void renderJsAddToContainer(String component) {
		addComponentsToBufferJs(desktopJs.addToContainer(component));
	}

	public void renderJsMessage(String key, String messageValue, String messageType) {
		addComponentsToBufferJs(desktopJs.addMessage(key, messageValue, messageType));
	}

	public UIRenderStringValue createServerAction(JsMap action) {
		return desktopJs.createServerAction(action);
	}

	public UIRenderStringValue createHandelServerAction(JsMap action) {
		return desktopJs.createHandelServerAction(action);
	}

	public ApplicationCacheDictionaryManager getApplicationCacheDictionaryManager() {
		return UIRenderUtils.getApplicationCacheDictionaryManager();
	}

	public ApplicationManager getApplicationManager() {
		return UIRenderUtils.getApplicationManager();
	}

	public ImageDefinition getImagePath(String pathId) {
		return UIRenderUtils.getImagePath(pathId);
	}

	public WebDefinition getWebDefinition() {
		return UIRenderUtils.getWebDefinition();
	}

	public void render(int nbSpace, String value) {
		UIRenderUtils.render(context, nbSpace, value);
	}

	public void render(String value, String tabRender) {
		UIRenderUtils.render(context, value, tabRender);
	}

	public void render(String value) {
		UIRenderUtils.render(context, value);
	}

	public void renderCss(String src, String tabRender) {
		UIRenderUtils.renderCss(context, src, tabRender);
	}

	public void renderEndJs(String tabRender) {
		UIRenderUtils.renderEndJs(context, tabRender);
	}

	public void renderJs(String url, String tabRender) {
		UIRenderUtils.renderJs(context, url, tabRender);
	}

	public void renderln(int nbSpace, String value) {
		UIRenderUtils.renderln(context, nbSpace, value);
	}

	public void renderln(String value, String tabRender) {
		UIRenderUtils.renderln(context, value, tabRender);
	}

	public void renderln(String value) {
		UIRenderUtils.renderln(context, value);
	}

	public void renderln() {
		UIRenderUtils.renderln(context, "\n");
	}

	public void renderln(WebContext context) {
		UIRenderUtils.renderln(context);
	}

	public void renderSpace(int nbSpace) {
		UIRenderUtils.renderSpace(context, nbSpace);
	}

	public void renderSpace(WebContext context) {
		UIRenderUtils.renderSpace(context);
	}

	public void renderSrcJs(String src, String tabRender) {
		UIRenderUtils.renderSrcJs(context, src, tabRender);
	}

	public void renderStartJs(String tabRender) {
		UIRenderUtils.renderStartJs(context, tabRender);
	}

	public void setAppContainerLayout(String appContainerLayout) {
		desktopJs.setAppContainerLayout(appContainerLayout);
	}

	public void setAppContainerLayoutConfig(JsMap appContainerLayoutConfig) {
		desktopJs.setAppContainerLayoutConfig(appContainerLayoutConfig);
	}

	public void setCollapsedModule(boolean collapsedModule) {
		desktopJs.setCollapsedModule(collapsedModule);
	}

	public void setDisplayToolbar(boolean displayToolbar) {
		desktopJs.setDisplayToolbar(displayToolbar);
	}

	public void setSociteLabel(String sociteLabel) {
		desktopJs.setSociteLabel(sociteLabel);
	}

	public void setPath(String path) {
		desktopJs.setPath(path);
	}
}
