package tn.com.smartsoft.framework.presentation.view.tags;

import java.util.Map;

import tn.com.smartsoft.commons.xml.utils.ParserDefinition;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIWindowParser;
import tn.com.smartsoft.framework.presentation.view.window.UIDefaultWindow;

public abstract class UIAbstractTagManager implements UITagManager {
	private String encoding;
	private String locale;
	private String windowResponseView;
	private UITags uiTags = new UITags();
	private UIWindowParser windowParser = new UIWindowParser();

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void addTag(UITag uiTag) {
		uiTags.addTag(uiTag);
	}

	public UITag getTag(String name) {
		return uiTags.getTag(name);
	}

	public void addTags(UITags uiTag) {
		uiTags.addTags(uiTag);
	}

	public String getWindowResponseView() {
		return windowResponseView;
	}

	public void setWindowResponseView(String windowResponseView) {
		this.windowResponseView = windowResponseView;
	}

	public UIDefaultWindow parse(String path, Map<String, String> parameter, ParserDefinition parserDefinition) throws Exception {
		return windowParser.parse(this, path, parameter, parserDefinition);
	}
}
