package tn.com.smartsoft.framework.presentation.view.tags.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.framework.presentation.view.tags.TagHandler;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIParserParameter;

public class GenericTagHandler implements TagHandler {
	private Map<String, String> attributes = new HashMap<String, String>();
	private List<TagHandler> childrens = new ArrayList<TagHandler>();
	private String value = "";
	private String name = "";
	private UIParserParameter parserParameter;
	private String ressourceLocation = "";

	public GenericTagHandler(String tagName, String[] names, Object values[]) {
		super();
		this.name = tagName;
		for (int i = 0; i < names.length; i++) {
			addAttributeValue(names[i], values[i]);
		}
	}

	public GenericTagHandler(String tagName, String name0, String name1, String name2, Object value0, Object value1, Object value2) {
		this(tagName, new String[] { name0, name1, name2 }, new Object[] { value0, value1, value2 });
	}

	public GenericTagHandler(String tagName, String name0, String name1, Object value0, Object value1) {
		this(tagName, new String[] { name0, name1 }, new Object[] { value0, value1 });
	}

	public GenericTagHandler(String tagName, String name0, Object value0) {
		this(tagName, new String[] { name0 }, new Object[] { value0 });
	}

	public GenericTagHandler(String tagName, String value) {
		this.name = tagName;
		this.value = value;
	}

	public GenericTagHandler(String tagName) {
		this.name = tagName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAttributeNames() {
		return new ArrayList<String>(attributes.keySet());
	}

	public GenericTagHandler addChildren(String tagName, String[] names, Object values[]) {
		return addChildren(new GenericTagHandler(tagName, names, values));
	}

	public GenericTagHandler addChildren(String tagName, String name0, String name1, String name2, Object value0, Object value1, Object value2) {
		return addChildren(new GenericTagHandler(tagName, name0, name1, name2, value0, value1, value2));
	}

	public GenericTagHandler addChildren(String tagName, String name0, String name1, Object value0, Object value1) {
		return addChildren(new GenericTagHandler(tagName, name0, name1, value0, value1));
	}

	public GenericTagHandler addChildren(String tagName, String name0, Object value0) {
		return addChildren(new GenericTagHandler(tagName, name0, value0));
	}

	public GenericTagHandler addChildren(String tagName, String value) {
		return addChildren(new GenericTagHandler(tagName, value));
	}

	public GenericTagHandler addChildren(TagHandler tagHandler) {
		childrens.add(tagHandler);
		return this;
	}

	public GenericTagHandler addAttributeValue(String name, Object value) {
		if (value != null)
			attributes.put(name, value.toString());
		else
			attributes.put(name, null);
		return this;
	}

	public String getAttributeValue(String name) {
		return attributes.get(name);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public UIParserParameter getParserParameter() {
		return this.parserParameter;
	}

	public void setParserParameter(UIParserParameter parserParameter) {
		this.parserParameter = parserParameter;
	}

	public int childrenSize() {
		return childrens.size();
	}

	public TagHandler getChildren(int index) {
		return childrens.get(index);
	}

	public String getRessourceLocation() {
		return ressourceLocation;
	}

	public void setRessourceLocation(String ressourceLocation) {
		this.ressourceLocation = ressourceLocation;
	}

}
