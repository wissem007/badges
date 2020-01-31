package tn.com.smartsoft.framework.presentation.view.tags;

import java.util.Map;

import tn.com.smartsoft.commons.xml.utils.ParserDefinition;
import tn.com.smartsoft.framework.presentation.view.window.UIDefaultWindow;

public interface UITagManager {
	public abstract String getEncoding();

	public abstract String getLocale();

	public abstract UITag getTag(String name);

	public UIDefaultWindow parse(String path, Map<String, String> parameter, ParserDefinition parserDefinition) throws Exception;
}
