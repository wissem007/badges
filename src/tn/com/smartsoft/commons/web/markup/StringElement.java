package tn.com.smartsoft.commons.web.markup;

public class StringElement extends ConcreteElement implements Printable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StringElement() {
	}

	public StringElement(String string) {
		if (string != null)
			setTagText(string);
		else
			setTagText("");
	}

	public StringElement(Element element) {
		addElement(element);
	}

	public StringElement append(String string) {
		setTagText(getTagText() + string);
		return this;
	}

	public StringElement reset() {
		setTagText("");
		return this;
	}

	public StringElement addElement(String hashcode, Element element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	public StringElement addElement(String hashcode, String element) {
		StringElement se = new StringElement(element);
		se.setFilterState(getFilterState());
		se.setFilter(getFilter());
		addElementToRegistry(hashcode, se);
		return (this);
	}

	public StringElement addElement(String element) {
		addElement(Integer.toString(element.hashCode()), element);
		return (this);
	}

	public StringElement addElement(Element element) {
		addElementToRegistry(element);
		return (this);
	}

	public StringElement removeElement(String hashcode) {
		removeElementFromRegistry(hashcode);
		return (this);
	}

	public String createStartTag() {
		return ("");
	}

	public String createEndTag() {
		return ("");
	}
}
