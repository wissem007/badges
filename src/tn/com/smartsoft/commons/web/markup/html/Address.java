package tn.com.smartsoft.commons.web.markup.html;

import tn.com.smartsoft.commons.web.markup.Element;
import tn.com.smartsoft.commons.web.markup.KeyEvents;
import tn.com.smartsoft.commons.web.markup.MouseEvents;
import tn.com.smartsoft.commons.web.markup.MultiPartElement;
import tn.com.smartsoft.commons.web.markup.Printable;

public class Address extends MultiPartElement implements Printable, MouseEvents, KeyEvents {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Private initialization routine.
	 */
	{
		setElementType("address");
	}

	/**
	 * Basic constructor. You need to set the attributes using the set* methods.
	 */
	public Address() {
	}

	/**
	 * Use the set* methods to set the values of the attributes.
	 * 
	 * @param element
	 *            set the value of &lt;ADDRESS&gt;value&lt;/ADDRESS&gt;
	 */
	public Address(Span element) {
		addElement(element);
	}

	/**
	 * Use the set* methods to set the values of the attributes.
	 * 
	 * @param value
	 *            set the value of &lt;ADDRESS&gt;value&lt;/ADDRESS&gt;
	 */
	public Address(String value) {
		addElement(value);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public Address addElement(Element element) {
		addElementToRegistry(element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public Address addElement(String element) {
		addElementToRegistry(element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param hashcode
	 *            name of element for hash table
	 * @param element
	 *            Adds an Element to the element.
	 */
	public Address addElement(String hashcode, Element element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param hashcode
	 *            name of element for hash table
	 * @param element
	 *            Adds an Element to the element.
	 */
	public Address addElement(String hashcode, String element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	/**
	 * Removes an Element from the element.
	 * 
	 * @param hashcode
	 *            the name of the element to be removed.
	 */
	public Address removeElement(String hashcode) {
		removeElementFromRegistry(hashcode);
		return (this);
	}

	/**
	 * The onclick event occurs when the pointing device button is clicked over
	 * an element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnClick(String script) {
		addAttribute("onClick", script);
	}

	/**
	 * The ondblclick event occurs when the pointing device button is double
	 * clicked over an element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnDblClick(String script) {
		addAttribute("onDblClick", script);
	}

	/**
	 * The onkeydown event occurs when a key is pressed down over an element.
	 * This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnKeyDown(String script) {
		addAttribute("onKeyDown", script);
	}

	/**
	 * The onkeypress event occurs when a key is pressed and released over an
	 * element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnKeyPress(String script) {
		addAttribute("onKeyPress", script);
	}

	/**
	 * The onkeyup event occurs when a key is released over an element. This
	 * attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnKeyUp(String script) {
		addAttribute("onKeyUp", script);
	}

	/**
	 * The onmousedown event occurs when the pointing device button is pressed
	 * over an element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnMouseDown(String script) {
		addAttribute("onMouseDown", script);
	}

	/**
	 * The onmousemove event occurs when the pointing device is moved while it
	 * is over an element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnMouseMove(String script) {
		addAttribute("onMouseMove", script);
	}

	/**
	 * The onmouseout event occurs when the pointing device is moved away from
	 * an element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnMouseOut(String script) {
		addAttribute("onMouseOut", script);
	}

	/**
	 * The onmouseover event occurs when the pointing device is moved onto an
	 * element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnMouseOver(String script) {
		addAttribute("onMouseOver", script);
	}

	/**
	 * The onmouseup event occurs when the pointing device button is released
	 * over an element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnMouseUp(String script) {
		addAttribute("onMouseUp", script);
	}
}
