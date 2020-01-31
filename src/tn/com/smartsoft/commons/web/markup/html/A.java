package tn.com.smartsoft.commons.web.markup.html;

import java.util.Enumeration;

import tn.com.smartsoft.commons.web.markup.Element;
import tn.com.smartsoft.commons.web.markup.FocusEvents;
import tn.com.smartsoft.commons.web.markup.KeyEvents;
import tn.com.smartsoft.commons.web.markup.MouseEvents;
import tn.com.smartsoft.commons.web.markup.MultiPartElement;
import tn.com.smartsoft.commons.web.markup.Printable;

public class A extends MultiPartElement implements Printable, FocusEvents, MouseEvents, KeyEvents {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	{
		setElementType("a");
	}

	public A() {
	}

	public A(String href) {
		setHref(href);
	}

	public A(String href, Element value) {
		setHref(href);
		addElement(value);
	}

	public A(String href, String value) {
		setHref(href);
		addElement(value);
	}

	public A(String href, String name, Element value) {
		setHref(href);
		setName(name);
		addElement(value);
	}

	public A(String href, String name, String value) {
		setHref(href);
		setName(name);
		addElement(value);
	}

	public A(String href, String name, String target, Element value) {
		setHref(href);
		setName(name);
		setTarget(target);
		addElement(value);
	}

	public A(String href, String name, String target, String value) {
		setHref(href);
		setName(name);
		setTarget(target);
		addElement(value);
	}

	public A(String href, String name, String target, String lang, Element value) {
		setHref(href);
		setName(name);
		setTarget(target);
		setLang(lang);
		addElement(value);
	}

	public A(String href, String name, String target, String lang, String value) {
		setHref(href);
		setName(name);
		setTarget(target);
		setLang(lang);
		addElement(value);
	}

	public A addElement(Element element) {
		addElementToRegistry(element);
		return (this);
	}

	public A addElement(String element) {
		addElementToRegistry(element);
		return (this);
	}

	public A addElement(String hashcode, Element element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	public A addElement(String hashcode, String element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	
	public boolean getNeedLineBreak() {
		Enumeration enume = elements();
		int i = 0;
		int j = 0;
		while (enume.hasMoreElements()) {
			j++;
			Object obj = enume.nextElement();
			if (obj instanceof IMG)
				i++;
		}
		if (i == j)
			return false;
		return true;
	}

	public A removeElement(String hashcode) {
		removeElementFromRegistry(hashcode);
		return (this);
	}

	public A setFolder(String folder) {
		addAttribute("folder", folder);
		return this;
	}

	public A setHref(String href) {
		addAttribute("href", href);
		return this;
	}

	public A setName(String name) {
		addAttribute("name", name);
		return this;
	}

	public void setOnBlur(String script) {
		addAttribute("onBlur", script);
	}

	public void setOnClick(String script) {
		addAttribute("onClick", script);
	}

	public void setOnDblClick(String script) {
		addAttribute("onDblClick", script);
	}

	public void setOnFocus(String script) {
		addAttribute("onFocus", script);
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

	public A setRel(String rel) {
		addAttribute("rel", rel);
		return this;
	}

	public A setRev(String rev) {
		addAttribute("rev", rev);
		return this;
	}

	public A setTarget(String target) {
		addAttribute("target", target);
		return this;
	}
}
