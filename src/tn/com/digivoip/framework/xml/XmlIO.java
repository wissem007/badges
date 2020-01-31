// The contents of this file are subject to the Mozilla Public License Version 1.1
// (the "License"); you may not use this file except in compliance with the
// License. You may obtain a copy of the License at http://www.mozilla.org/MPL/
//
// Software distributed under the License is distributed on an "AS IS" basis,
// WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
// for the specific language governing rights and
// limitations under the License.
//
// The Original Code is "The Columba Project"
//
// The Initial Developers of the Original Code are Frederik Dietz and Timo Stich.
// Portions created by Frederik Dietz and Timo Stich are Copyright (C) 2003.
//
// All Rights Reserved.
package tn.com.digivoip.framework.xml;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlIO extends DefaultHandler{

	private static final Logger	LOG						= Logger.getLogger("org.columba.core.xml");
	private static final String	ROOT_XML_ELEMENT_NAME	= "__COLUMBA_XML_TREE_TOP__";
	// Top level element (Used to hold everything else)
	private XmlElement			rootElement;
	// The current element you are working on
	private XmlElement			currentElement;
	// For writing out the data
	// Indent for each level
	private int					writeIndent				= 2;
	// Maximum data to put on a "one liner"
	private int					maxOneLineData			= 20;
	// Buffer for collecting data from
	// the "characters" SAX event.
	private CharArrayWriter		contents				= new CharArrayWriter();
	private URL					url						= null;

	/* // Default constructor public XmlIO() { } */
	/*
	 * // setup and load constructor public XmlIO(String FilePath) { currentElement = null; } */
	public XmlIO(URL url) {
		super();
		this.url = url;
	}
	// setup and load constructor
	public XmlIO() {
		currentElement = null;
	}
	// setup and load constructor
	/** Creates a XmlIO object with the specified element at the top.
	 * @param element the element at the top. */
	public XmlIO(XmlElement element) {
		rootElement = new XmlElement(XmlIO.ROOT_XML_ELEMENT_NAME);
		rootElement.addElement(element);
	}
	public void setURL(URL url) {
		this.url = url;
	}
	public boolean load() {
		// this.file = F;
		return load(url);
	}
	// Load a file. This is what starts things off.
	/** Loads from the InputStream into the root Xml Element.
	 * @param input the input stream to load from. */
	public boolean load(InputStream input) {
		rootElement = new XmlElement(XmlIO.ROOT_XML_ELEMENT_NAME);
		currentElement = rootElement;
		try {
			// Create the XML reader...
			// xr = XMLReaderFactory.createXMLReader();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// Set the ContentHandler...
			// xr.setContentHandler( this );
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(input, this);
		} catch (javax.xml.parsers.ParserConfigurationException ex) {
			XmlIO.LOG.severe("XML config error while attempting to read from the input stream \n'" + input + "'");
			XmlIO.LOG.severe(ex.toString());
			ex.printStackTrace();
			return (false);
		} catch (SAXException ex) {
			// Error
			XmlIO.LOG.severe("XML parse error while attempting to read from the input stream \n'" + input + "'");
			XmlIO.LOG.severe(ex.toString());
			ex.printStackTrace();
			return (false);
		} catch (IOException ex) {
			XmlIO.LOG.severe("I/O error while attempting to read from the input stream \n'" + input + "'");
			XmlIO.LOG.severe(ex.toString());
			ex.printStackTrace();
			return (false);
		}
		// XmlElement.printNode( getRoot(), "");
		return (true);
	}
	/** Load a file. This is what starts things off.
	 * @param inputURL the URL to load XML from. */
	public boolean load(URL inputURL) {
		rootElement = new XmlElement(XmlIO.ROOT_XML_ELEMENT_NAME);
		currentElement = rootElement;
		try {
			// Create the XML reader...
			// xr = XMLReaderFactory.createXMLReader();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// Set the ContentHandler...
			// xr.setContentHandler( this );
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(inputURL.toString(), this);
		} catch (javax.xml.parsers.ParserConfigurationException ex) {
			XmlIO.LOG.severe("XML config error while attempting to read XML file \n'" + inputURL + "'");
			XmlIO.LOG.severe(ex.toString());
			ex.printStackTrace();
			return (false);
		} catch (SAXException ex) {
			// Error
			XmlIO.LOG.severe("XML parse error while attempting to read XML file \n'" + inputURL + "'");
			XmlIO.LOG.severe(ex.toString());
			ex.printStackTrace();
			return (false);
		} catch (IOException ex) {
			XmlIO.LOG.severe("I/O error while attempting to read XML file \n'" + inputURL + "'");
			XmlIO.LOG.severe(ex.toString());
			ex.printStackTrace();
			return (false);
		}
		return (true);
	}
	public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) throws SAXException {
		try {
			contents.reset();
			String name = localName; // element name
			if (name.equals("")) {
				name = qName; // namespaceAware = false
			}
			XmlElement p = currentElement;
			currentElement = currentElement.addSubElement(name);
			currentElement.setParent(p);
			if (attrs != null) {
				for (int i = 0; i < attrs.getLength(); i++) {
					String aName = attrs.getLocalName(i); // Attr name
					if (aName.equals("")) {
						aName = attrs.getQName(i);
					}
					currentElement.addAttribute(aName, attrs.getValue(i));
				}
			}
		} catch (java.lang.NullPointerException ex) {
			XmlIO.LOG.severe("Null!!!");
			XmlIO.LOG.severe(ex.toString());
			ex.printStackTrace();
		}
	}
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		currentElement.setData(contents.toString().trim());
		contents.reset();
		currentElement = currentElement.getParent();
	}
	public void characters(char[] ch, int start, int length) throws SAXException {
		// accumulate the contents into a buffer.
		contents.write(ch, start, length);
	}
	public XmlElement getRoot() {
		return (rootElement);
	}
	public void errorDialog(String Msg) {
		JOptionPane.showMessageDialog(null, "Error: " + Msg);
	}
	public void warningDialog(String Msg) {
		JOptionPane.showMessageDialog(null, "Warning: " + Msg);
	}
	public void infoDialog(String Msg) {
		JOptionPane.showMessageDialog(null, "Info: " + Msg);
	}
	public void save() throws Exception {
		write(new FileOutputStream(url.getPath()));
	}
	//
	// Writer interface
	//
	public void write(OutputStream out) throws IOException {
		BufferedWriter PW = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
		PW.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		if (rootElement.subElements.size() > 0) {
			for (int i = 0; i < rootElement.subElements.size(); i++) {
				_writeSubNode(PW, (XmlElement) rootElement.subElements.get(i), 0);
			}
		}
		PW.flush();
	}
	private void _writeSubNode(Writer out, XmlElement element, int indent) throws IOException {
		_writeSpace(out, indent);
		out.write("<");
		out.write(element.getName());
		for (Enumeration e = element.getAttributeNames(); e.hasMoreElements();) {
			String K = (String) e.nextElement();
			out.write(" " + K + "=\"" + TextUtils.escapeText(element.getAttribute(K)) + "\"");
		}
		out.write(">");
		String data = element.getData();
		if ((data != null) && !data.equals("")) {
			if (data.length() > maxOneLineData) {
				out.write("\n");
				_writeSpace(out, indent + writeIndent);
			}
			out.write(TextUtils.escapeText(data));
		}
		List subElements = element.getElements();
		if (subElements.size() > 0) {
			out.write("\n");
			for (Iterator it = subElements.iterator(); it.hasNext();) {
				_writeSubNode(out, (XmlElement) it.next(), indent + writeIndent);
			}
			_writeSpace(out, indent);
		}
		if (data.length() > maxOneLineData) {
			out.write("\n");
			_writeSpace(out, indent);
		}
		out.write("</" + TextUtils.escapeText(element.getName()) + ">\n");
	}
	private void _writeSpace(Writer out, int numSpaces) throws IOException {
		for (int i = 0; i < numSpaces; i++) {
			out.write(" ");
		}
	}
}
// End class XmlIO
