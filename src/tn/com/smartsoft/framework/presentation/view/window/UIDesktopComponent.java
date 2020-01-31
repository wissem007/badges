package tn.com.smartsoft.framework.presentation.view.window;

import tn.com.smartsoft.commons.web.js.JsMap;

public interface UIDesktopComponent {

	public boolean isDisplayedMenuBar();

	public boolean isDisplayedModuleBar();

	public boolean isDisplayedStatusBar();

	public boolean isDisplayedActionBar();

	public boolean isDisplayedToolbar();

	public String getFirstFocus();

	public String getLayout();

	public JsMap getLayoutConfigs();

	public String getInstanceName();

	public String getId();

	public String getLabelTreeAction();

	public String getLabelModuleId();
}
