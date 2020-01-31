package tn.com.smartsoft.framework.presentation.view.tags.handler;

import java.util.List;

import tn.com.smartsoft.commons.xml.parser.XmlElement;
import tn.com.smartsoft.framework.presentation.view.tags.TagHandler;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIParserParameter;

public class XmlTagHandler implements TagHandler {
	private UIParserParameter parserParameter;
	private XmlElement xmlTag;

	public XmlTagHandler(XmlElement xmlTag) {
		super();
		this.xmlTag = xmlTag;
	}

	public XmlTagHandler(UIParserParameter parserParameter, XmlElement xmlTag) {
		super();
		this.parserParameter = parserParameter;
		this.xmlTag = xmlTag;
	}

	public List<String> getAttributeNames() {
		return xmlTag.getAttributeNames();
	}

	public String getAttributeValue(String key) {
		return xmlTag.getAttribute(key);
	}

	public String getValue() {
		return xmlTag.getValue();
	}

	public String getName() {
		return xmlTag.getName();
	}

	public UIParserParameter getParserParameter() {
		return this.parserParameter;
	}

	public void setParserParameter(UIParserParameter parserParameter) {
		this.parserParameter = parserParameter;
		xmlTag.setParserDefinition(parserParameter);
	}

	public int childrenSize() {
		return xmlTag.getChildren().size();
	}

	public TagHandler getChildren(int index) {
		return new XmlTagHandler(parserParameter, (XmlElement) xmlTag.getChildren().get(index));
	}

	public String getRessourceLocation() {
		return xmlTag.getRessourceLocation();
	}
}
