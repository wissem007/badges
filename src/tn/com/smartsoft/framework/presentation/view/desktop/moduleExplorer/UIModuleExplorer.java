package tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.framework.presentation.context.RequestEventInfo;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.utils.ResolveEventPathUtils;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopElement;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopPartType;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.render.UIModuleExplorerRender;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;

public class UIModuleExplorer extends UIComponent implements UIRendrableComponent,DesktopElement{

	/**
	 * 
	 */
	private static final long			serialVersionUID	= 1L;
	private boolean						isDisplayed			= true;
	private String						libelleId			= "moduleExplorer";
	private Map<String, UIUserModule>	userModules;
	private boolean						collapsed			= true;

	public void setRendred(boolean rendred) {}
	public boolean isCollapsed() {
		return collapsed;
	}
	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
	public boolean isDisplayed() {
		if (!isDisplayed) return isDisplayed;
		return userModules != null && userModules.size() > 1;
	}
	public void setDisplayed(boolean isDisplayed) {
		this.isDisplayed = isDisplayed;
	}
	public String getLibelleId() {
		return libelleId;
	}
	public void setLibelleId(String libelleId) {
		this.libelleId = libelleId;
	}
	public Map<String, UIUserModule> getUserModules() {
		return userModules;
	}
	public UIUserModule getUserModule(String id) {
		if (userModules.containsKey(id)) return userModules.get(id);
		if (userModules.keySet().iterator().hasNext()) return userModules.get(userModules.keySet().iterator().next());
		else return 
		null;
	}
	public void setUserModules(Map<String, UIUserModule> userModules) {
		this.userModules = userModules;
	}
	public UIRender getRender() {
		return new UIModuleExplorerRender(this);
	}
	public void fireActionEvent(WebContext context) {
		RequestEventInfo requestEventInfo = context.request().requestEventInfo();
		if (StringUtils.equals(requestEventInfo.componentSourceEvent(), "collapse")) this.collapsed = context.request().requestParameter().getParameterAsBoolean("collapse", false);
		else getUserModule(requestEventInfo.componentSourceEvent()).fireActionEvent(context);
	}
	public String resolvePath(WebContext context, String page, String componentId, String event) {
		return ResolveEventPathUtils.resolveEventPath(this, context, page, componentId, event);
	}
	public DesktopPartType desktopPartType() {
		return DesktopPartType.MODULE_EXPLORER_PART;
	}
	public boolean isRendred() {
		return true;
	}
}
