package tn.com.digivoip.framework.xml;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

public class XmlElement extends Observable implements Cloneable{

	private static final java.util.logging.Logger	LOG	= java.util.logging.Logger.getLogger("org.columba.core.xml");	//$NON-NLS-1$
	String											name;
	String											data;
	Hashtable										attributes;
	List											subElements;
	XmlElement										parent;

	/** **FIXME** This function needs documentation Constructor */
	public XmlElement() {
		subElements = new Vector();
		this.attributes = new Hashtable(10);
	}
	public XmlElement(String name) {
		this.name = name;
		this.attributes = new Hashtable(10);
		subElements = new Vector();
		data = "";
	}
	public XmlElement(String name, Hashtable attributes) {
		this.name = name;
		this.attributes = attributes;
		subElements = new Vector();
	}
	public XmlElement(String name, String data) {
		this.name = name;
		this.data = data;
		subElements = new Vector();
		this.attributes = new Hashtable(10);
	}
	public Object addAttribute(String name, String value) {
		if ((value != null) && (name != null)) {
			Object returnValue = (attributes.put(name, value));
			return returnValue;
		}
		return null;
	}
	public String getAttribute(String name) {
		return ((String) attributes.get(name));
	}
	public String getAttribute(String name, String defaultValue) {
		if (getAttribute(name) == null) {
			addAttribute(name, defaultValue);
		}
		return getAttribute(name);
	}
	/** **FIXME** This function needs documentation
	 * @return String
	 * @param String Name */
	public Hashtable getAttributes() {
		return attributes;
	}
	/** **FIXME** This function needs documentation
	 * @param Attrs Hashtable to use as the attributes */
	public void setAttributes(Hashtable attrs) {
		attributes = attrs;
	}
	/** **FIXME** This function needs documentation
	 * @return Enumeration */
	public Enumeration getAttributeNames() {
		return (attributes.keys());
	}
	/** **FIXME** This function needs documentation
	 * @return boolean
	 * @param XmlElement E */
	public boolean addElement(XmlElement e) {
		e.setParent(this);
		return (subElements.add(e));
	}
	public XmlElement removeElement(XmlElement e) {
		XmlElement child = null;
		for (int i = 0; i < subElements.size(); i++) {
			child = (XmlElement) subElements.get(i);
			// FIXME -- This will most likely not work.
			// You want the element removed if the contents are the same
			// Not just if the element reference is the same.
			if (child == e) {
				subElements.remove(i);
			}
		}
		return (child);
	}
	public XmlElement removeElement(int index) {
		return (XmlElement) subElements.remove(index);
	}
	public void removeAllElements() {
		subElements.clear();
	}
	/** convienience method for the TreeView this method is modeled after the DefaultMutableTreeNode-class DefaultMutableTreeNode wraps XmlElement for this purpose */
	public void removeFromParent() {
		if (parent == null) { return; }
		parent.removeElement(this);
		parent = null;
	}
	public void append(XmlElement e) {
		e.removeFromParent();
		addElement(e);
	}
	/** convienience method for the TreeView
	 * @param e
	 * @param index */
	public void insertElement(XmlElement e, int index) {
		e.removeFromParent();
		subElements.add(index, e);
		e.setParent(this);
	}
	/** **FIXME** This function needs documentation
	 * @return Vector */
	public List getElements() {
		return subElements;
	}
	public int count() {
		return subElements.size();
	}
	/** **FIXME** This function needs documentation
	 * @return XmlElement
	 * @param String Path */
	public XmlElement getElement(String path) {
		int i = path.indexOf('/');
		String topName;
		String subName;
		if (i == 0) {
			path = path.substring(1);
			i = path.indexOf('/');
		}
		if (i > 0) {
			topName = path.substring(0, i);
			subName = path.substring(i + 1);
		} else {
			topName = path;
			subName = null;
		}
		int j;
		for (j = 0; j < subElements.size(); j++) {
			if (((XmlElement) subElements.get(j)).getName().equals(topName)) {
				if (subName != null) {
					return (((XmlElement) subElements.get(j)).getElement(subName));
				} else {
					return ((XmlElement) subElements.get(j));
				}
			}
		}
		return null;
	}
	public XmlElement getElement(int index) {
		return (XmlElement) subElements.get(index);
	}
	/** Adds a sub element to this one
	 * @return XmlElement
	 * @param Name The subpath of the sub element to add */
	public XmlElement addSubElement(String path) {
		XmlElement parent = this;
		XmlElement child;
		String name;
		while (path.indexOf('/') != -1) {
			name = path.substring(0, path.indexOf('/'));
			path = path.substring(path.indexOf('/') + 1);
			// if path startsWith "/" -> skip
			if (name.length() == 0) {
				continue;
			}
			if (parent.getElement(name) != null) {
				parent = parent.getElement(name);
			} else {
				child = new XmlElement(name);
				parent.addElement(child);
				parent = child;
			}
		}
		child = new XmlElement(path);
		parent.addElement(child);
		return child;
	}
	/** Adds a sub element to this one
	 * @return XmlElement
	 * @param element The XmlElement to add */
	public XmlElement addSubElement(XmlElement e) {
		e.setParent(this);
		subElements.add(e);
		return e;
	}
	/** Adds a sub element to this one
	 * @return XmlElement
	 * @param Name The name of the sub element to add
	 * @param Data String Data for this element */
	public XmlElement addSubElement(String name, String data) {
		XmlElement e = new XmlElement(name);
		e.setData(data);
		e.setParent(this);
		subElements.add(e);
		return e;
	}
	/** Sets the parent element
	 * @param Parent The XmlElement that contains this one */
	public void setParent(XmlElement parent) {
		this.parent = parent;
	}
	/** Gives the XmlElement containing the current element
	 * @return XmlElement */
	public XmlElement getParent() {
		return parent;
	}
	/** Sets the data for this element
	 * @param D The String representation of the data */
	public void setData(String d) {
		data = d;
	}
	/** Returns the data associated with the current Xml element
	 * @return String */
	public String getData() {
		return data;
	}
	/** Returns the name of the current Xml element
	 * @return String */
	public String getName() {
		return name;
	}
	/** **FIXME** This function needs documentation
	 * @param out OutputStream to print the data to */
	/* public void write(OutputStream out) throws IOException { PrintWriter PW = new PrintWriter(out); PW.println(" <?xml version=\"1.0\" encoding=\"UTF-8\"?>"); if (SubElements.size() > 0) { for (int
	 * i = 0; i < SubElements.size(); i++) { ((XmlElement) SubElements.get(i))._writeSubNode(PW, 4); } } PW.flush(); } */
	/** Prints sub nodes to the given data stream
	 * @param out PrintWriter to use for printing
	 * @param indent Number of spaces to indent things */
	/* private void _writeSubNode(PrintWriter out, int indent) throws IOException { _writeSpace(out, indent); out.print(" <" + Name); //if ( Attributes.size()>1) out.print(" "); for (Enumeration e =
	 * Attributes.keys(); e.hasMoreElements();) { String K = (String) e.nextElement(); out.print(K + "=\"" + Attributes.get(K) + "\" b"); } out.print(">"); if (Data != null && !Data.equals("")) { if
	 * (Data.length() > 20) { out.println(""); _writeSpace(out, indent + 2); } out.print(Data); } if (SubElements.size() > 0) { out.println(""); for (int i = 0; i < SubElements.size(); i++) {
	 * ((XmlElement) SubElements.get(i))._writeSubNode( out, indent + 4); } _writeSpace(out, indent); } out.println(" </" + Name + ">"); } */
	/** Prints out a given number of spaces
	 * @param out PrintWriter to use for printing
	 * @param numSpaces Number of spaces to print */
	/* private void _writeSpace(PrintWriter out, int numSpaces) throws IOException { for (int i = 0; i < numSpaces; i++) out.print(" "); } public static void printNode(XmlElement Node, String indent)
	 * { String Data = Node.getData(); if (Data == null || Data.equals("")) { System.out.println(indent + Node.getName()); } else { System.out.println(indent + Node.getName() + " = '" + Data + "'"); }
	 * Vector Subs = Node.getElements(); int i, j; for (i = 0; i < Subs.size(); i++) { printNode((XmlElement) Subs.get(i), indent + " "); } } */
	public static void printNode(XmlElement node, String indent) {
		String data = node.getData();
		if ((data == null) || data.equals("")) {
			XmlElement.LOG.info(indent + node.getName());
		} else {
			XmlElement.LOG.info(indent + node.getName() + " = '" + data + "'"); //$NON-NLS-1$
		}
		// print attributes
		for (Enumeration enumeration = node.getAttributes().keys(); enumeration.hasMoreElements();) {
			String key = (String) enumeration.nextElement();
			String value = node.getAttribute(key);
			XmlElement.LOG.info(indent + key + ":" + value); //$NON-NLS-1$
		}
		List subs = node.getElements();
		for (Iterator it = subs.iterator(); it.hasNext();) {
			XmlElement.printNode((XmlElement) it.next(), indent + "    ");
			// for (i = 0; i < subs.size(); i++) {
			// printNode((XmlElement) subs.get(i), indent + " ");
		}
	}
	/** {@inheritDoc} */
	public Object clone() {
		try {
			XmlElement clone = (XmlElement) super.clone(); // creates a shallow
															// copy of this
															// object
			if (attributes != null) {
				clone.setAttributes((Hashtable) getAttributes().clone());
			}
			if (subElements != null) {
				clone.subElements = new Vector();
				List childs = getElements();
				XmlElement child;
				for (Iterator it = childs.iterator(); it.hasNext();) {
					child = (XmlElement) it.next();
					// for( int i=0; i<childs.size(); i++ ) {
					// child = (XmlElement) childs.get(i);
					clone.addSubElement((XmlElement) child.clone());
				}
			}
			return clone;
		} catch (CloneNotSupportedException cnse) {
			throw new InternalError("Could not clone XmlElement: " + cnse);
		}
	}
	/** Sets the name.
	 * @param name The name to set */
	public void setName(String name) {
		this.name = name;
	}
	/** Notify all Observers.
	 * @see java.util.Observable#notifyObservers() */
	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}
	/** Returns true if the specified objects are equal. They are equal if they are both null OR if the <code>equals()</code> method return true. ( <code>obj1.equals(obj2)</code>).
	 * @param obj1 first object to compare with.
	 * @param obj2 second object to compare with.
	 * @return true if they represent the same object; false if one of them is null or the <code>equals()</code> method returns false. */
	private boolean equals(Object obj1, Object obj2) {
		boolean equal = false;
		if ((obj1 == null) && (obj2 == null)) {
			equal = true;
		} else if ((obj1 != null) && (obj2 != null)) {
			equal = obj1.equals(obj2);
		}
		return equal;
	}
	/** {@inheritDoc} */
	public boolean equals(Object obj) {
		boolean equal = false;
		if ((obj != null) && (obj instanceof XmlElement)) {
			XmlElement other = (XmlElement) obj;
			if (equals(attributes, other.attributes) && equals(data, other.data) && equals(name, other.name) && equals(subElements, other.subElements)) {
				equal = true;
			}
		}
		return equal;
	}
	/** {@inheritDoc} */
	public int hashCode() {
		// Hashcode value should be buffered.
		int hashCode = 23;
		if (attributes != null) {
			hashCode += (attributes.hashCode() * 13);
		}
		if (data != null) {
			hashCode += (data.hashCode() * 17);
		}
		if (name != null) {
			hashCode += (name.hashCode() * 29);
		}
		if (subElements != null) {
			hashCode += (subElements.hashCode() * 57);
		}
		return hashCode;
	}
	/** @see java.lang.Object#toString() */
	public String toString() {
		return getName();
	}
}
// END public class XmlElement
