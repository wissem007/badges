package tn.com.smartsoft.commons.web.markup.html;

import tn.com.smartsoft.commons.web.markup.Element;
import tn.com.smartsoft.commons.web.markup.KeyEvents;
import tn.com.smartsoft.commons.web.markup.MouseEvents;
import tn.com.smartsoft.commons.web.markup.MultiPartElement;
import tn.com.smartsoft.commons.web.markup.Printable;

public class Var extends MultiPartElement implements Printable, MouseEvents, KeyEvents {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	{
		setElementType("var");
	}

	public Var() {
	}

	public Var(Element element) {
		addElement(element);
	}

	public Var(String element) {
		addElement(element);
	}

	public Var addElement(Element element) {
		addElementToRegistry(element);
		return (this);
	}

	public Var addElement(String element) {
		addElementToRegistry(element);
		return (this);
	}

	public Var addElement(String hashcode, Element element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	public Var addElement(String hashcode, String element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	public Var removeElement(String hashcode) {
		removeElementFromRegistry(hashcode);
		return (this);
	}

	public void setOnClick(String script) {
		addAttribute("onClick", script);
	}

	public void setOnDblClick(String script) {
		addAttribute("onDblClick", script);
	}

	public void setOnKeyDown(String script) {
		addAttribute("onKeyDown", script);
	}

	public void setOnKeyPress(String script) {
		addAttribute("onKeyPress", script);
	}

	public void setOnKeyUp(String script) {
		addAttribute("onKeyUp", script);
	}

	public void setOnMouseDown(String script) {
		addAttribute("onMouseDown", script);
	}

	public void setOnMouseMove(String script) {
		addAttribute("onMouseMove", script);
	}

	public void setOnMouseOut(String script) {
		addAttribute("onMouseOut", script);
	}

	public void setOnMouseOver(String script) {
		addAttribute("onMouseOver", script);
	}

	public void setOnMouseUp(String script) {
		addAttribute("onMouseUp", script);
	}
}
