/*
 * ====================================================================
 * 
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:  
 *       "This product includes software developed by the 
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Jakarta Element Construction Set", 
 *    "Jakarta ECS" , and "Apache Software Foundation" must not be used 
 *    to endorse or promote products derived
 *    from this software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Jakarta Element Construction Set" nor "Jakarta ECS" nor may "Apache" 
 *    appear in their names without prior written permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package tn.com.smartsoft.commons.web.markup.html;

import tn.com.smartsoft.commons.web.markup.Element;
import tn.com.smartsoft.commons.web.markup.FocusEvents;
import tn.com.smartsoft.commons.web.markup.KeyEvents;
import tn.com.smartsoft.commons.web.markup.MouseEvents;
import tn.com.smartsoft.commons.web.markup.Printable;
import tn.com.smartsoft.commons.web.markup.SinglePartElement;

/**
 * This class creates a &lt;Area&gt; tag.
 * 
 * @version $Id: Area.java,v 1.1 2013/10/08 12:37:22 oracle Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
 */
public class Area extends SinglePartElement implements Printable, FocusEvents, MouseEvents, KeyEvents {

	public static final String DEFAULT = "DEFAULT";

	public static final String RECT = "RECT";
	public static final String CIRCLE = "CIRCLE";
	public static final String POLY = "POLY";

	public static final String rect = "rect";
	public static final String circle = "circle";
	public static final String poly = "poly";

	/**
	 * Private initialization routine.
	 */
	{
		setElementType("area");
		setNoHref(true);
	}

	/**
	 * Basic constructor. Use the set* methods to set the values of the
	 * attributes.
	 */
	public Area() {
	}

	/**
	 * Use the set* methods to set the values of the attributes.
	 * 
	 * @param shape
	 *            the SHAPE="" attribute
	 */
	public Area(String shape) {
		setShape(shape);
	}

	/**
	 * Use the set* methods to set the values of the attributes.
	 * 
	 * @param shape
	 *            the SHAPE="" attribute
	 * @param coords
	 *            the COORDS="" attribute
	 */
	public Area(String shape, int[] coords) {
		setShape(shape);
		setCoords(coords);
	}

	/**
	 * Use the set* methods to set the values of the attributes.
	 * 
	 * @param shape
	 *            the SHAPE="" attribute
	 * @param coords
	 *            the COORDS="" attribute
	 * @param href
	 *            the HREF="" attribute
	 */
	public Area(String shape, int[] coords, String href) {
		setShape(shape);
		setCoords(coords);
		setHref(href);
	}

	/**
	 * Use the set* methods to set the values of the attributes.
	 * 
	 * @param shape
	 *            the SHAPE="" attribute
	 * @param coords
	 *            the COORDS="" attribute
	 */
	public Area(String shape, String coords) {
		setShape(shape);
		setCoords(coords);
	}

	/**
	 * Use the set* methods to set the values of the attributes.
	 * 
	 * @param shape
	 *            the SHAPE="" attribute
	 * @param coords
	 *            the COORDS="" attribute
	 * @param href
	 *            the HREF="" attribute
	 */
	public Area(String shape, String coords, String href) {
		setShape(shape);
		setCoords(coords);
		setHref(href);
	}

	/**
	 * Add an element to the element
	 * 
	 * @param element
	 *            an element to add
	 */
	public Area addElement(Element element) {
		addElementToRegistry(element);
		return (this);
	}

	/**
	 * Add an element to the element
	 * 
	 * @param element
	 *            a string representation of the element
	 */
	public Area addElement(String element) {
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
	public Area addElement(String hashcode, Element element) {
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
	public Area addElement(String hashcode, String element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	/**
	 * Removes an Element from the element.
	 * 
	 * @param hashcode
	 *            the name of the element to be removed.
	 */
	public Area removeElement(String hashcode) {
		removeElementFromRegistry(hashcode);
		return (this);
	}

	/**
	 * Sets the ALT="" attribute
	 * 
	 * @param alt
	 *            the ALT="" attribute
	 */
	public Area setAlt(String alt) {
		addAttribute("alt", alt);
		return this;
	}

	/**
	 * Sets the COORDS="" attribute
	 * 
	 * @param coords
	 *            the COORDS="" attribute
	 */
	public Area setCoords(int[] coords) {
		addAttribute("coords", coords[0] + "," + coords[1] + "," + coords[2] + "," + coords[3]);
		return this;
	}

	/**
	 * Sets the COORDS="" attribute
	 * 
	 * @param coords
	 *            the COORDS="" attribute
	 */
	public Area setCoords(String coords) {
		addAttribute("coords", coords);
		return this;
	}

	/**
	 * Sets the HREF="" attribute
	 * 
	 * @param href
	 *            the HREF="" attribute
	 */
	public Area setHref(String href) {
		addAttribute("href", href);
		setNoHref(false);
		return this;
	}

	/**
	 * Sets the nohref
	 * 
	 * @param href
	 *            true or false
	 */
	public Area setNoHref(boolean href) {
		if (href == true)
			addAttribute("nohref", NO_ATTRIBUTE_VALUE);
		else
			removeAttribute("nohref");

		return (this);
	}

	/**
	 * The onblur event occurs when an element loses focus either by the
	 * pointing device or by tabbing navigation. It may be used with the same
	 * elements as onfocus.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnBlur(String script) {
		addAttribute("onBlur", script);
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
	 * The onfocus event occurs when an element receives focus either by the
	 * pointing device or by tabbing navigation. This attribute may be used with
	 * the following elements: LABEL, INPUT, SELECT, TEXTAREA, and BUTTON.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnFocus(String script) {
		addAttribute("onFocus", script);
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

	/**
	 * Sets the SHAPE="" attribute
	 * 
	 * @param shape
	 *            the SHAPE="" attribute
	 */
	public Area setShape(String shape) {
		addAttribute("shape", shape);
		return this;
	}

	/**
	 * Sets the TABINDEX="" attribute
	 * 
	 * @param alt
	 *            the TABINDEX="" attribute
	 */
	public Area setTabindex(int index) {
		setTabindex(Integer.toString(index));
		return this;
	}

	/**
	 * Sets the TABINDEX="" attribute
	 * 
	 * @param alt
	 *            the TABINDEX="" attribute
	 */
	public Area setTabindex(String index) {
		addAttribute("tabindex", index);
		return this;
	}
}
