package tn.com.smartsoft.framework.presentation.view.tags;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.xml.schema.XsComplexType;
import tn.com.smartsoft.commons.xml.schema.XsElement;
import tn.com.smartsoft.commons.xml.schema.XsSchema;
import tn.com.smartsoft.framework.configuration.definition.IDefinition;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIParseException;
import tn.com.smartsoft.framework.presentation.view.tags.utils.ParserBeanAccessorUtils;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;

public class UIChildTag implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tag;
	private String addedMethode;
	private Class<?> parmeterTypeMethode;
	private String ref;

	public UIChildTag() {
		super();
	}

	public UIChildTag(String tag, String addedMethode, Class<?> parmeterTypeMethode, String ref) {
		super();
		this.tag = tag;
		this.addedMethode = addedMethode;
		this.parmeterTypeMethode = parmeterTypeMethode;
		this.ref = ref;
	}

	public String getAddedMethode() {
		return addedMethode;
	}

	public void setAddedMethode(String addedMethode) {
		this.addedMethode = addedMethode;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getRef() {
		if (StringUtils.isBlank(this.ref))
			this.ref = this.tag;
		return this.ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Class<?> getParmeterTypeMethode() {
		return parmeterTypeMethode;
	}

	public void setParmeterTypeMethode(Class<?> parmeterTypeMethode) {
		this.parmeterTypeMethode = parmeterTypeMethode;
	}

	public void addXsd(XsSchema xsSchema, XsComplexType xsComplexType, UIParserContext context, Object parentBean) throws UIParseException {
		try {
			if (xsSchema.containsType(getRef())) {
				XsComplexType childXsComplexType = (XsComplexType) xsSchema.getType(getRef());
				XsElement attElement = new XsElement(this.getTag(), childXsComplexType.getName(), "0");
				childXsComplexType.addElement(attElement);
			} else {
				context.getTagComponent(getRef()).addXsd(xsSchema, xsComplexType, context);
			}

		} catch (Exception e) {
			throw new UIParseException(e);
		}
	}

	public void parseChildTag(Object component, UIParserContext context, TagHandler tagHandlerChild) throws UIParseException {
		Object childComponent = context.parse(tagHandlerChild, getRef());
		Class<?> parmeterTypeMethode = this.getParmeterTypeMethode() != null ? this.getParmeterTypeMethode() : childComponent.getClass();
		ParserBeanAccessorUtils.invokeMethod(component, this.getAddedMethode(), childComponent, parmeterTypeMethode);
		if (childComponent instanceof UIComponent) {
			context.attachedToWindow((UIComponent) childComponent);
		}
	}
}
