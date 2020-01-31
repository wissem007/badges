package tn.com.smartsoft.framework.presentation.view.desktop;

import java.util.List;

import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEventHandler;

public interface UIActionLink extends UIObject, UIEventHandler {
	public String getId();

	public String getLibelle();

	public String getIconUrl();

	public String getHelp();

	public List<String> getTitle();

	public Long getRang();

	public ComponentId getUserActionId();

	public String resolveEventPath(WebContext context, String name);
}
