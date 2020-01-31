package tn.com.smartsoft.framework.presentation.view.tags.xtpl;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.xml.exception.ParseException;
import tn.com.smartsoft.commons.xml.parser.XmlElement;
import tn.com.smartsoft.commons.xml.utils.ParserBeanAccessorUtils;
import tn.com.smartsoft.framework.presentation.view.tags.utils.ResourceParserUtils;

public class MainTes {
	public static Map<String, Class<?>> xtpl = new HashMap<String, Class<?>>();
	static {

		xtpl.put("xtpl", XTplRoot.class);
		xtpl.put("xtpl-set", XTplSetValue.class);

		xtpl.put("xtpl-else", XTplElse.class);
		xtpl.put("xtpl-elseif", XTplElseif.class);
		xtpl.put("xtpl-if", XTplIf.class);

		xtpl.put("xtpl-foreach", XTplForEach.class);

	}

	public static void main(String[] args) throws ParseException {
		XmlElement xmlElement = ResourceParserUtils.getXmlRootElement("tn/com/smartsoft/commons/xml/xtpl/formDetailData.xml", null);
		XTplNode node = dispalNode(xmlElement);
		StringWriter buffer = new StringWriter();
		node.write(buffer);
		System.out.println(buffer);
	}

	public static XTplNode dispalXtpl(XmlElement xmlElement) throws ParseException {
		Class<?> clazz = xtpl.get(xmlElement.getName());
		XTplNode node = (XTplNode) ParserBeanAccessorUtils.newInstance(clazz);
		List<String> atts = xmlElement.getAttributeNames();
		for (int i = 0; i < atts.size(); i++) {
			if (ParserBeanAccessorUtils.isWriteableProperty(node, atts.get(i)))
				ParserBeanAccessorUtils.setPropertyValue(node, atts.get(i), xmlElement.getAttribute(atts.get(i)));
		}
		if (node instanceof XTplBodyNode) {
			((XTplBodyNode) node).setContenent(xmlElement.getValue());
		} else if (node instanceof XTplSetValue) {
			if (StringUtils.isNotBlank(xmlElement.getValue()))
				((XTplSetValue) node).setValue(xmlElement.getValue());
		}
		return node;
	}

	public static XTplNode dispalTag(XmlElement xmlElement) throws ParseException {
		XTplTagNode xTplTagNode = new XTplTagNode(xmlElement.getName());
		List<String> atts = xmlElement.getAttributeNames();
		for (int i = 0; i < atts.size(); i++) {
			xTplTagNode.addAttributeValue(atts.get(i), xmlElement.getAttribute(atts.get(i)));
		}
		xTplTagNode.setContenent(xmlElement.getValue());
		return xTplTagNode;
	}

	public static XTplNode dispalNode(XmlElement xmlElement) throws ParseException {
		XTplNode node;
		if (xtpl.containsKey(xmlElement.getName())) {
			node = dispalXtpl(xmlElement);
		} else {
			node = dispalTag(xmlElement);
		}
		if (node instanceof XTplBodyNode) {
			List<XmlElement> child = xmlElement.getChildren();
			for (int i = 0; i < child.size(); i++) {
				XmlElement childNode = child.get(i);
				if (childNode.getName().equalsIgnoreCase("xtpl-condition") && node instanceof XTplCondition) {
					((XTplCondition) node).setCondition(childNode.getValue());
				} else
					((XTplBodyNode) node).addChildren(dispalNode(childNode));
			}
		}
		return node;
	}

}
