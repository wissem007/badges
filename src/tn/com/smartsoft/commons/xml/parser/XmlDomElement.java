package tn.com.smartsoft.commons.xml.parser;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import tn.com.smartsoft.commons.exceptions.ConfigurationException;

public class XmlDomElement extends XmlElement {

	private Document document;
	private Element element;
	private ArrayList<XmlElement> children = null;

	public XmlDomElement() throws ConfigurationException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = builder.newDocument();
			element = document.createElement("configuration");
			document.appendChild(element);
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}

	public XmlDomElement(String name) throws ConfigurationException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = builder.newDocument();
			element = document.createElement(name);
			document.appendChild(element);
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}

	public XmlDomElement(InputStream in) throws ConfigurationException {
		load(in);
	}

	public XmlDomElement(Reader reader) throws ConfigurationException {
		load(reader);
	}

	public XmlDomElement(Document document) {
		this(document, document.getDocumentElement());
	}

	public XmlDomElement(Element element) {
		this(null, element);
	}

	protected XmlDomElement(Document document, Element element) {
		if (document == null) {
			document = findDocument(element);
		}
		this.document = document;
		this.element = element;
	}

	public String getName() {
		return element.getTagName();
	}

	public void load(InputStream in) throws ConfigurationException {
		load(new InputSource(in));
	}

	public void load(Reader reader) throws ConfigurationException {
		load(new InputSource(reader));
	}

	public void load(InputSource inputSource) throws ConfigurationException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = builder.parse(inputSource);
			element = document.getDocumentElement();
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}

	private Document findDocument(Node node) {
		Node parent;
		for (parent = node.getParentNode(); parent != null && !(parent instanceof Document); parent = parent.getParentNode()) {}
		return (Document) parent;
	}

	public String getAttribute(String name, String defaultValue) {
		String value = getAttribute(name);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	public String getAttribute(String name) {
		if (name == null)
			return null;
		else {
			Attr node = element.getAttributeNode(name);
			if (node == null) {
				return null;
			} else {
				return parseValue(node.getValue());
			}
		}
	}

	public List<String> getAttributeNames() {
		ArrayList<String> attributeNames = new ArrayList<String>();
		NamedNodeMap attributes = element.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Node node = attributes.item(i);
			attributeNames.add(node.getNodeName());
		}
		return attributeNames;
	}

	public XmlElement getChild(String name) {
		List<XmlElement> children = getChildren(name);
		if (children.size() == 0) {
			return null;
		} else {
			return (XmlElement) children.get(0);
		}
	}

	public List<XmlElement> getChildren() {
		if (this.children != null)
			return children;
		this.children = new ArrayList<XmlElement>();
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node instanceof Element) {
				children.add(new XmlDomElement((Element) node).setRessourceLocation(getRessourceLocation()).setParserDefinition(getParserDefinition()));
			}
		}
		return children;
	}

	public List<XmlElement> getChildren(String name) {
		ArrayList<XmlElement> children = new ArrayList<XmlElement>();
		Iterator<XmlElement> iter = getChildren().iterator();
		do {
			if (!iter.hasNext()) {
				break;
			}
			XmlElement child = (XmlElement) iter.next();
			if (child.getName().equals(name)) {
				children.add(child);
			}
		} while (true);
		return children;
	}

	public String getChildValue(String name, String defaultValue) {
		String value = getChildValue(name);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	public String getChildValue(String name) {
		XmlElement child = getChild(name);
		if (child == null) {
			return null;
		} else {
			return child.getValue();
		}
	}

	public XmlElement getParent() {
		Node parentNode = element.getParentNode();
		if (parentNode != null) {
			return new XmlDomElement((Element) parentNode);
		} else {
			return null;
		}
	}

	public String getValue() {
		return parseValue(getText(element));
	}

	private String getText(Element element) {
		return getText(element.getChildNodes());
	}

	private String getText(NodeList nodes) {
		boolean hasText = false;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if ((node instanceof Text) || (node instanceof CDATASection)) {
				buffer.append(node.getNodeValue());
				hasText = true;
				continue;
			}
			if (node instanceof Entity) {
				buffer.append(node.getNodeName());
				hasText = true;
			}
		}

		if (hasText) {
			return buffer.toString();
		} else {
			return null;
		}
	}

}
