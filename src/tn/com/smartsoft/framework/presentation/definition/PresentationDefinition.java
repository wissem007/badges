package tn.com.smartsoft.framework.presentation.definition;

import java.util.Map;

import tn.com.smartsoft.framework.configuration.ApplicationCacheDictionaryManager;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.configuration.definition.ModuleDefinition;

public interface PresentationDefinition {
	public WindowDefinition getWindowDefinition(ComponentId windowId);

	public BindingModelDefinition getBindingModelDefinition(ComponentId bindingModelId);

	public ControleBeanDefinition getControleBeanDefinition(ComponentId controleBeanId);

	public UserActionDefinition getUserActionDefinition(ComponentId applicationActionId);

	public ModuleDefinition getModuleDefinition(String moduleId);

	public ApplicationCacheDictionaryManager getApplicationCacheDictionaryManager();

	public Map<String, ModuleDefinition> getModuleDefinitions();

	public WebDefinition getWebDefinition();

	public ComponentId getDefaultUserActionId();

	public ComponentId getLoginUserActionId();

	public boolean isExistUserAction(ComponentId userActionId);
}
