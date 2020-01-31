package tn.com.smartsoft.framework.presentation.view.tags.parser;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.commons.xml.utils.ParserDefinition;
import tn.com.smartsoft.framework.presentation.view.tags.TagHandler;
import tn.com.smartsoft.framework.presentation.view.tags.UIParserContext;
import tn.com.smartsoft.framework.presentation.view.tags.UITagManager;
import tn.com.smartsoft.framework.presentation.view.tags.handler.XmlTagHandler;
import tn.com.smartsoft.framework.presentation.view.tags.utils.ResourceParserUtils;
import tn.com.smartsoft.framework.presentation.view.window.UIDefaultWindow;

public class UIWindowParser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<UIWindowRessourceId, TagHandler> windows = new HashMap<UIWindowRessourceId, TagHandler>();

	public UIWindowParser() {
		super();
	}

	public UIDefaultWindow parse(UITagManager tagContainer, String path, Map<String, String> parameter, ParserDefinition parserDefinition) throws UIParseException {
		try {
			UIWindowRessourceId resourceid = new UIWindowRessourceId(parserDefinition.parse(path), parameter, parserDefinition);
			TagHandler tagHandler = (TagHandler) windows.get(resourceid);
			windows = new HashMap<UIWindowRessourceId, TagHandler>();
			if (tagHandler == null) {
				tagHandler = new XmlTagHandler(ResourceParserUtils.getXmlRootElement(parserDefinition.parse(path), null));
				windows.put(resourceid, tagHandler);
			}
			UIParserContext parserContext = new UIDefauldParserContext(tagContainer, new UIParserParameter(parameter, parserDefinition));
			UIDefaultWindow uiWindow = new UIDefaultWindow(parserContext);
			uiWindow = (UIDefaultWindow) parserContext.parse(uiWindow, tagHandler);
			return uiWindow;
		} catch (Exception e) {
			throw new UIParseException(e);
		}
	}
}
