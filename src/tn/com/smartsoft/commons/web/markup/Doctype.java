package tn.com.smartsoft.commons.web.markup;

public class Doctype extends SinglePartElement implements Printable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String elementName = "!DOCTYPE";
	public static final String PUBLIC = "PUBLIC";

	protected String name;
	protected String visibility;
	protected String identifier;
	protected String uri;

	{
		setElementType(elementName);
		setCase(Element.UPPERCASE);
	}

	/**
	 * Basic Constructor.
	 * 
	 */
	public Doctype() {
		updateElementType();
	}

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            Root element of the XML document.
	 * @param id
	 *            Public identifier.
	 * @param uri
	 *            URI of the DTD.
	 */
	public Doctype(String name, String id, String uri) {
		this.name = name;
		this.visibility = PUBLIC;
		this.identifier = id;
		this.uri = uri;
		updateElementType();
	}

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            Root element of the XML document.
	 * @param id
	 *            Public identifier.
	 * @param uri
	 *            URI of the DTD.
	 */
	public Doctype(String name, String visibility, String id, String uri) {
		this.name = name;
		this.visibility = visibility;
		this.identifier = id;
		this.uri = uri;
		updateElementType();
	}

	/**
	 * Should be called when any of the fields are changed.
	 * 
	 */
	protected void updateElementType() {
		setElementType(elementName + " " + name + " " + visibility + " " + identifier + " " + uri);
	}

	/**
	 * Updates the name of the root element.
	 * 
	 * @param name
	 *            Name of the root element.
	 * @return a value of type 'Doctype'
	 */
	public Doctype setName(String name) {
		this.name = name;
		updateElementType();
		return (this);
	}

	/**
	 * Updates the name of the root element.
	 * 
	 * @param name
	 *            Name of the root element.
	 * @return a value of type 'Doctype'
	 */
	public Doctype setVisibility(String visibility) {
		this.visibility = visibility;
		updateElementType();
		return (this);
	}

	/**
	 * Updates the name of the public identifier.
	 * 
	 * @param identifier
	 *            The public identifier.
	 * @return a value of type 'Doctype'
	 */
	public Doctype setIdentifier(String identifier) {
		this.identifier = identifier;
		updateElementType();
		return (this);
	}

	/**
	 * Updates the URI of the dtd.
	 * 
	 * @param uri
	 *            URI of the dtd.
	 * @return a value of type 'Doctype'
	 */
	public Doctype setUri(String uri) {
		this.uri = uri;
		updateElementType();
		return (this);
	}

	/**
	 * Adds and Element to the element.
	 * 
	 * @param hashcode
	 *            name of the element for hash table.
	 * @param element
	 *            Adds an Element to the element.
	 * @return a value of type 'Doctype'
	 */
	public Doctype addElement(String hashcode, Element element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param hashcode
	 *            name of the element for the hash table.
	 * @param element
	 *            Adds an Element to the element.
	 * @return a value of type 'Doctype'
	 */
	public Doctype addElement(String hashcode, String element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 * @return a value of type 'Doctype'
	 */
	public Doctype addElement(Element element) {
		addElementToRegistry(element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 * @return a value of type 'Doctype'
	 */
	public Doctype addElement(String element) {
		addElementToRegistry(element);
		return (this);
	}

	/**
	 * Removes an Element from the element.
	 * 
	 * @param hashcode
	 *            the name of the element to be removed.
	 * @return a value of type 'Doctype'
	 */
	public Doctype removeElement(String hashcode) {
		removeElementFromRegistry(hashcode);
		return (this);
	}

	/**
	 * The HTML 4.0 Strict DTD includes all elements and attributes that have
	 * not been deprecated or do not appear in frameset documents.
	 * <p>
	 * See: <a href="http://www.w3.org/TR/REC-html40/sgml/dtd.html">
	 * http://www.w3.org/TR/REC-html40/sgml/dtd.html</a>
	 */
	public static class Html40Strict extends Doctype {

		public Html40Strict() {
			this.name = "HTML";
			this.visibility = PUBLIC;
			this.identifier = "\"-//W3C//DTD HTML 4.0//EN\"";
			this.uri = "\"http://www.w3.org/TR/REC-html40/strict.dtd\"";
			this.updateElementType();
		}
	}

	/**
	 * The HTML 4.0 Transitional DTD includes everything in the strict DTD plus
	 * deprecated elements and attributes (most of which concern visual
	 * presentation).
	 * <p>
	 * See: <a href="http://www.w3.org/TR/REC-html40/sgml/loosedtd.html">
	 * http://www.w3.org/TR/REC-html40/sgml/loosedtd.html</a>
	 */
	public static class Html40Transitional extends Doctype {

		public Html40Transitional() {
			this.name = "HTML";
			this.visibility = PUBLIC;
			this.identifier = "\"-//W3C//DTD HTML 4.0 Transitional//EN\"";
			this.uri = "\"http://www.w3.org/TR/REC-html40/loose.dtd\"";
			this.updateElementType();
		}
	}

	/**
	 * The HTML 4.0 Frameset DTD includes everything in the transitional DTD
	 * plus frames as well.
	 * <p>
	 * See: <a href="http://www.w3.org/TR/REC-html40/sgml/framesetdtd.html">
	 * http://www.w3.org/TR/REC-html40/sgml/framesetdtd.html</a>
	 */
	public static class Html40Frameset extends Doctype {

		public Html40Frameset() {
			this.name = "HTML";
			this.visibility = PUBLIC;
			this.identifier = "\"-//W3C//DTD HTML 4.0 Frameset//EN\"";
			this.uri = "\"http://www.w3.org/TR/REC-html40/frameset.dtd\"";
			this.updateElementType();
		}
	}

	/**
	 * The HTML 4.01 Strict DTD includes all elements and attributes that have
	 * not been deprecated or do not appear in frameset documents.
	 * <p>
	 * See: <a href="http://www.w3.org/TR/html4/">
	 * http://www.w3.org/TR/html4/</a>
	 */
	public static class Html401Strict extends Doctype {

		public Html401Strict() {
			this.name = "HTML";
			this.visibility = PUBLIC;
			this.identifier = "\"-//W3C//DTD HTML 4.01//EN\"";
			this.uri = "\"http://www.w3.org/TR/html4/strict.dtd\"";
			this.updateElementType();
		}
	}

	/**
	 * The HTML 4.01 Transitional DTD includes everything in the strict DTD plus
	 * deprecated elements and attributes (most of which concern visual
	 * presentation).
	 * <p>
	 * See: <a href="http://www.w3.org/TR/html4/">
	 * http://www.w3.org/TR/html4/</a>
	 */
	public static class Html401Transitional extends Doctype {

		public Html401Transitional() {
			this.name = "HTML";
			this.visibility = PUBLIC;
			this.identifier = "\"-//W3C//DTD HTML 4.01 Transitional//EN\"";
			this.uri = "\"http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd\"";
			this.updateElementType();
		}
	}

	/**
	 * The HTML 4.01 Frameset DTD includes everything in the transitional DTD
	 * plus frames as well.
	 * <p>
	 * See: <a href="http://www.w3.org/TR/html4/">
	 * http://www.w3.org/TR/html4/</a>
	 */
	public static class Html401Frameset extends Doctype {

		public Html401Frameset() {
			this.name = "HTML";
			this.visibility = PUBLIC;
			this.identifier = "\"-//W3C//DTD HTML 4.01 Frameset//EN\"";
			this.uri = "\"http://www.w3.org/TR/1999/REC-html401-19991224/frameset.dtd\"";
			this.updateElementType();
		}
	}

	/**
	 * The XHTML 1.0 Strict DTD This is the same as HTML 4.0 Strict except for
	 * changes due to the differences between XML and SGML.
	 * <p>
	 * See: <a href="http://www.w3.org/TR/xhtml1">
	 * http://www.w3.org/TR/xhtml1</a>
	 */
	public static class XHtml10Strict extends Doctype {

		public XHtml10Strict() {
			this.name = "html";
			this.visibility = PUBLIC;
			this.identifier = "\"-//W3C//DTD XHTML 1.0 Strict//EN\"";
			this.uri = "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"";
			this.updateElementType();
		}
	}

	/**
	 * The XHTML 1.0 Transitional DTD This is the same as HTML 4.0 Transitional
	 * except for changes due to the differences between XML and SGML.
	 * <p>
	 * See: <a href="http://www.w3.org/TR/xhtml1">
	 * http://www.w3.org/TR/xhtml1</a>
	 */
	public static class XHtml10Transitional extends Doctype {

		public XHtml10Transitional() {
			this.name = "html";
			this.visibility = PUBLIC;
			this.identifier = "\"-//W3C//DTD XHTML 1.0 Transitional//EN\"";
			this.uri = "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"";
			this.updateElementType();
		}
	}

	/**
	 * The XHTML 1.0 Frameset DTD This is the same as HTML 4.0 Frameset except
	 * for changes due to the differences between XML and SGML.
	 * <p>
	 * See: <a href="http://www.w3.org/TR/xhtml1">
	 * http://www.w3.org/TR/xhtml1</a>
	 */
	public static class XHtml10Frameset extends Doctype {

		public XHtml10Frameset() {
			this.name = "html";
			this.visibility = PUBLIC;
			this.identifier = "\"-//W3C//DTD XHTML 1.0 Frameset//EN\"";
			this.uri = "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\"";
			this.updateElementType();
		}
	}
}
