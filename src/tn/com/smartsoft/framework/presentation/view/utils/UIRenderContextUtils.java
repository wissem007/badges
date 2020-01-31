package tn.com.smartsoft.framework.presentation.view.utils;

import tn.com.smartsoft.framework.configuration.ApplicationCacheDictionaryManager;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.definition.WebDefinition;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIUserModule;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.utils.ApplicationCacheUtils;

public class UIRenderContextUtils {

	public static UserContext userContext(UIRenderContext context) {
		return userDesktop(context).userContext();
	}

	public static UserDesktop userDesktop(UIRenderContext context) {
		return webContext(context).userDesktop();
	}

	public static WebContext webContext(UIRenderContext context) {
		return context.webContext();
	}

	public static UIUserModule curentUserModule(UIRenderContext context) {
		return userDesktop(context).curentUserModule();
	}

	public static WebDefinition webDefinition() {
		return ApplicationCacheUtils.webDefinition();
	}

	public static ApplicationCacheDictionaryManager dictionaryManager() {
		return ApplicationCacheUtils.dictionaryManager();
	}

}
