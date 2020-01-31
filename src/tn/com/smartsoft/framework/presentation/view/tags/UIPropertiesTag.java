package tn.com.smartsoft.framework.presentation.view.tags;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.xml.schema.XsAttribute;
import tn.com.smartsoft.commons.xml.schema.XsComplexType;
import tn.com.smartsoft.commons.xml.schema.XsElement;
import tn.com.smartsoft.commons.xml.schema.XsSchema;
import tn.com.smartsoft.framework.configuration.definition.IDefinition;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIParseException;
import tn.com.smartsoft.framework.presentation.view.tags.utils.ParserBeanAccessorUtils;

public class UIPropertiesTag implements IDefinition {
	private static final long serialVersionUID = 1L;
	private String tag;
	private String methodeName;
	protected List<UIPropertiesParamTag> propertiesParams = new ArrayList<UIPropertiesParamTag>();

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getMethodeName() {
		return methodeName;
	}

	public void setMethodeName(String methodeName) {
		this.methodeName = methodeName;
	}

	public void addParam(UIPropertiesParamTag propertiesParamsTag) {
		propertiesParams.add(propertiesParamsTag);
	}

	public List<UIPropertiesParamTag> getParams() {
		return propertiesParams;
	}

	public void addXsd(XsSchema xsSchema, XsComplexType xsComplexType, Object parentBean) throws UIParseException {
		try {
			XsElement attElement = new XsElement(this.getTag(), "0", null);
			xsComplexType.addElement(attElement);
			XsComplexType attXsComplexType = new XsComplexType();
			attElement.setComplexType(attXsComplexType);
			for (int i = 0; i < propertiesParams.size(); i++) {
				UIPropertiesParamTag param = (UIPropertiesParamTag) propertiesParams.get(i);
				attXsComplexType.addAttribute(new XsAttribute(param.getAttribute(), param.getType(), true, param.getDefaultValue()));
			}
		} catch (Exception e) {
			throw new UIParseException(e);
		}
	}

	public void parse(Object component, TagHandler tagHandler) throws UIParseException {
		try {
			List<UIPropertiesParamTag> propertiesParams = this.getParams();
			Object[] args = new Object[propertiesParams.size()];
			Class<?>[] parameterTypes = new Class[propertiesParams.size()];
			for (int i = 0; i < propertiesParams.size(); i++) {
				UIPropertiesParamTag param = (UIPropertiesParamTag) propertiesParams.get(i);
				String value = tagHandler.getAttributeValue(param.getAttribute());
				if (StringUtils.isBlank(value))
					value = param.getDefaultValue();
				args[i] = ParserBeanAccessorUtils.convertValue(value, param.getType());
				parameterTypes[i] = param.getType();
			}
			ParserBeanAccessorUtils.invokeMethod(component, this.getMethodeName(), args, parameterTypes);
		} catch (Exception e) {
			throw new UIParseException("Erreur load Properties from tag :" + this.getTag() + " and attribute" + " to component :" + component, e);
		}
	}
}
