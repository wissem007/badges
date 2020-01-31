package tn.com.smartsoft.framework.presentation.view.tags;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIParseException;
import tn.com.smartsoft.framework.presentation.view.tags.utils.ParserBeanAccessorUtils;

public class UIParameterTag implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String property;
	private String value;

	public UIParameterTag() {
		super();
	}

	public UIParameterTag(String property, String value) {
		super();
		this.property = property;
		this.value = value;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void parse(Object component, TagHandler tagHandler) throws UIParseException {
		try {
			if (StringUtils.isNotBlank(this.getValue())) {
				ParserBeanAccessorUtils.setPropertyValue(component, this.getProperty(), this.getValue());
			}
		} catch (Exception e) {
			throw new UIParseException("Erreur load Property :" + this.getProperty() + component, e);
		}
	}
}
