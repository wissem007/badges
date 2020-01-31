package tn.com.smartsoft.framework.presentation.view.tags;

import java.util.List;

import tn.com.smartsoft.framework.presentation.view.tags.parser.UIParserParameter;

public interface TagHandler {
	public String getName();

	public String getValue();

	public String getAttributeValue(String name);

	public List<String> getAttributeNames();

	public UIParserParameter getParserParameter();

	public void setParserParameter(UIParserParameter parserParameter);

	public int childrenSize();

	public TagHandler getChildren(int index);

	public String getRessourceLocation();
}
