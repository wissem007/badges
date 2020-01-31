package tn.com.smartsoft.commons.web.markup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import tn.com.smartsoft.commons.web.markup.util.ECSDefaults;

public class ConcreteElement extends ElementAttributes implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String lineSeparator = System.getProperty("line.separator");
	private Hashtable registry = new Hashtable(4); // keep a list of elements
	// that need to be added to
	// the element
	private Vector registryList = new Vector(2);

	public ConcreteElement() {
	}

	public ConcreteElement getElement(String element) {
		if (registry.containsKey(element)) {
			return (ConcreteElement) registry.get(element);
		}
		return null;
	}

	public Element addElementToRegistry(Element element) {
		if (element == null)
			return (this);
		addElementToRegistry(Integer.toString(element.hashCode()), element);
		return (this);
	}

	public Element addElementToRegistry(String hashcode, Element element) {
		if (hashcode == null || element == null)
			return (this);

		element.setFilterState(getFilterState());
		if (ECSDefaults.getDefaultPrettyPrint() != element.getPrettyPrint())
			element.setPrettyPrint(getPrettyPrint());
		registry.put(hashcode, element);
		if (!registryList.contains(hashcode))
			registryList.addElement(hashcode);
		return (this);
	}

	public Element addElementToRegistry(Element element, boolean filter) {
		if (element == null)
			return (this);
		setFilterState(filter);
		addElementToRegistry(Integer.toString(element.hashCode()), element);
		return (this);
	}

	public Element addElementToRegistry(String hashcode, Element element, boolean filter) {
		if (hashcode == null)
			return (this);
		setFilterState(filter);
		addElementToRegistry(hashcode, element);
		return (this);
	}

	public Element addElementToRegistry(String value, boolean filter) {
		if (value == null)
			return (this);
		setFilterState(filter);
		addElementToRegistry(Integer.toString(value.hashCode()), value);
		return (this);
	}

	public Element addElementToRegistry(String hashcode, String value, boolean filter) {
		if (hashcode == null)
			return (this);
		setFilterState(filter);
		addElementToRegistry(hashcode, value);
		return (this);
	}

	public Element addElementToRegistry(String value) {
		if (value == null)
			return (this);
		addElementToRegistry(new StringElement(value));
		return (this);
	}

	public Element addElementToRegistry(String hashcode, String value) {
		if (hashcode == null)
			return (this);

		StringElement se = new StringElement(value);
		se.setFilterState(getFilterState());
		se.setFilter(getFilter());
		se.setPrettyPrint(getPrettyPrint());
		addElementToRegistry(hashcode, se);
		return (this);
	}

	public Element removeElementFromRegistry(Element element) {
		removeElementFromRegistry(Integer.toString(element.hashCode()));
		return (this);
	}

	public Element removeElementFromRegistry(String hashcode) {
		registry.remove(hashcode);
		registryList.removeElement(hashcode);
		return (this);
	}

	public boolean registryHasElement(Element element) {
		return (registry.contains(element));
	}

	public Enumeration keys() {
		return (registryList.elements());
	}

	public Enumeration elements() {
		return (registry.elements());
	}

	public boolean registryHasElement(String hashcode) {
		return (registry.containsKey(hashcode));
	}

	public static void output(OutputStream out, ConcreteElement ce) {
		// use the encoding for the given element
		String encoding = ce.getCodeSet();
		if (encoding == null) {
			// By default use Big Endian Unicode.
			// In this way we will not loose any information.
			encoding = "UTF-16BE";
		}

		boolean prettyPrint = ce.getPrettyPrint();
		int tabLevel = ce.getTabLevel();
		try {
			if (ce.registry.size() == 0) {
				ce.output(out);
			} else {
				if ((prettyPrint && ce instanceof Printable) && (tabLevel > 0))
					ce.putTabs(tabLevel, out);

				out.write(ce.createStartTag().getBytes(encoding));

				// If this is a StringElement that has ChildElements still print
				// the TagText
				if (ce.getTagText() != null)
					out.write(ce.getTagText().getBytes(encoding));

				Enumeration enume = ce.registryList.elements();

				while (enume.hasMoreElements()) {
					Object obj = ce.registry.get((String) enume.nextElement());
					if (obj instanceof GenericElement) {
						Element e = (Element) obj;
						if (prettyPrint && ce instanceof Printable) {
							if (ce.getNeedLineBreak()) {
								out.write(ce.lineSeparator.getBytes(encoding));
								e.setTabLevel(tabLevel + 1);
							}
						}

						e.output(out);

					} else {
						if (prettyPrint && ce instanceof Printable) {
							if (ce.getNeedLineBreak()) {
								out.write(ce.lineSeparator.getBytes(encoding));
								ce.putTabs(tabLevel + 1, out);
							}
						}
						String string = obj.toString();
						out.write(string.getBytes(encoding));
					}
				}
				if (ce.getNeedClosingTag()) {
					if (prettyPrint && ce instanceof Printable) {
						if (ce.getNeedLineBreak()) {
							out.write(ce.lineSeparator.getBytes(encoding));
							if (tabLevel > 0)
								ce.putTabs(tabLevel, out);
						}
					}

					out.write(ce.createEndTag().getBytes(encoding));

				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace(new PrintWriter(out));
		}
	}

	/**
	 * Override output(OutputStream) incase any elements are in the registry.
	 * 
	 * @param output
	 *            OutputStream to write to.
	 */
	public void output(OutputStream out) {
		if (this.registry.size() == 0) {
			int tabLevel = getTabLevel();
			if ((getPrettyPrint() && this instanceof Printable) && (tabLevel > 0)) {
				try {
					this.putTabs(tabLevel, out);
				} catch (IOException ioe) {
					ioe.printStackTrace(new PrintWriter(out));
				}
			}
			super.output(out);
		} else {
			output(out, this);
		}
	}

	/**
	 * Writer version of this method.
	 */
	public void output(Writer out) {
		PrintWriter pw = new PrintWriter(out);
		output(pw);
		pw.flush();
	}

	/**
	 * Override output(BufferedWriter) incase any elements are in the registry.
	 * 
	 * @param output
	 *            OutputStream to write to.
	 */
	public void output(PrintWriter out) {
		boolean prettyPrint = getPrettyPrint();
		int tabLevel = getTabLevel();
		if (registry.size() == 0) {
			if ((prettyPrint && this instanceof Printable) && (tabLevel > 0))
				putTabs(tabLevel, out);

			super.output(out);
		} else {
			if ((prettyPrint && this instanceof Printable) && (tabLevel > 0))
				putTabs(tabLevel, out);

			out.write(createStartTag());
			// If this is a StringElement that has ChildElements still print the
			// TagText
			if (getTagText() != null)
				out.write(getTagText());

			Enumeration enume = registryList.elements();
			while (enume.hasMoreElements()) {
				Object obj = registry.get((String) enume.nextElement());
				if (obj instanceof GenericElement) {
					Element e = (Element) obj;
					if (prettyPrint && this instanceof Printable) {
						if (getNeedLineBreak()) {
							out.write(lineSeparator);
							e.setTabLevel(tabLevel + 1);
						}
					}
					e.output(out);
				} else {
					if (prettyPrint && this instanceof Printable) {
						if (getNeedLineBreak()) {
							out.write(lineSeparator);
							putTabs(tabLevel + 1, out);
						}
					}
					String string = obj.toString();
					if (getFilterState())
						out.write(getFilter().process(string));
					else
						out.write(string);
				}
			}
			if (getNeedClosingTag()) {
				if (prettyPrint && this instanceof Printable) {
					if (getNeedLineBreak()) {
						out.write(lineSeparator);
						if (tabLevel > 0)
							putTabs(tabLevel, out);
					}
				}
				out.write(createEndTag());
			}
		}
	}

	/**
	 * Allows all Elements the ability to be cloned.
	 */
	public Object clone() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(this);
			out.close();
			ByteArrayInputStream bin = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
			Object clone = in.readObject();
			in.close();
			return (clone);
		} catch (ClassNotFoundException cnfe) {
			throw new InternalError(cnfe.toString());
		} catch (StreamCorruptedException sce) {
			throw new InternalError(sce.toString());
		} catch (IOException ioe) {
			throw new InternalError(ioe.toString());
		}
	}

	public boolean isEmpty() {
		return registryList.isEmpty();
	}
}
