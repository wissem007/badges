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
import tn.com.smartsoft.commons.web.markup.KeyEvents;
import tn.com.smartsoft.commons.web.markup.MouseEvents;
import tn.com.smartsoft.commons.web.markup.MultiPartElement;
import tn.com.smartsoft.commons.web.markup.Printable;
import tn.com.smartsoft.commons.web.markup.util.HtmlColor;

/**
 * This class creates a &lt;TABLE&gt; object.
 * 
 * @version $Id: Table.java,v 1.1 2013/10/08 12:37:23 oracle Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
 */
public class Table extends MultiPartElement implements Printable, MouseEvents, KeyEvents {
	/**
	 * Private iniitialization routine
	 */
	{
		setElementType("table");
	}

	public Table() {
	}

	/**
	 * Allows one to set the border size.
	 * 
	 * @param border
	 *            sets the BORDER="" attribute.
	 */
	public Table(int border) {
		setBorder(border);
	}

	/**
	 * Allows one to set the border size.
	 * 
	 * @param border
	 *            sets the BORDER="" attribute.
	 */
	public Table(String border) {
		setBorder(border);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public Table addElement(Element element) {
		addElementToRegistry(element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public Table addElement(String element) {
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
	public Table addElement(String hashcode, Element element) {
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
	public Table addElement(String hashcode, String element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	/**
	 * Removes an Element from the element.
	 * 
	 * @param hashcode
	 *            the name of the element to be removed.
	 */
	public Table removeElement(String hashcode) {
		removeElementFromRegistry(hashcode);
		return (this);
	}

	/**
	 * Sets the ALIGN="" attribute.
	 * 
	 * @param align
	 *            sets the ALIGN="" attribute. You can use the AlignType.*
	 *            variables for convience.
	 */
	public Table setAlign(String align) {
		addAttribute("align", align);
		return (this);
	}

	/**
	 * Sets the BGCOLOR="" attribute
	 * 
	 * @param color
	 *            the BGCOLOR="" attribute
	 */
	public Table setBgColor(String color) {
		addAttribute("bgcolor", HtmlColor.convertColor(color));
		return this;
	}

	/**
	 * Sets the BORDER="" attribute.
	 * 
	 * @param border
	 *            sets the BORDER="" attribute.
	 */
	public Table setBorder(int border) {
		addAttribute("border", Integer.toString(border));
		return (this);
	}

	/**
	 * Sets the BORDER="" attribute.
	 * 
	 * @param border
	 *            sets the BORDER="" attribute.
	 */
	public Table setBorder(String border) {
		addAttribute("border", border);
		return (this);
	}

	/**
	 * Sets the CELLPADING="" attribute.
	 * 
	 * @param cellpadding
	 *            sets the CELLPADING="" attribute.
	 */
	public Table setCellPadding(int cellpadding) {
		addAttribute("cellpadding", Integer.toString(cellpadding));
		return (this);
	}

	/**
	 * Sets the CELLPADING="" attribute.
	 * 
	 * @param cellpadding
	 *            sets the CELLPADING="" attribute.
	 */
	public Table setCellPadding(String cellpadding) {
		addAttribute("cellpadding", cellpadding);
		return (this);
	}

	/**
	 * Sets the CELLSPACING="" attribute.
	 * 
	 * @param spacing
	 *            sets the CELLSPACING="" attribute.
	 */
	public Table setCellSpacing(int cellspacing) {
		addAttribute("cellspacing", Integer.toString(cellspacing));
		return (this);
	}

	/**
	 * Sets the CELLSPACING="" attribute.
	 * 
	 * @param spacing
	 *            sets the CELLSPACING="" attribute.
	 */
	public Table setCellSpacing(String cellspacing) {
		addAttribute("cellspacing", cellspacing);
		return (this);
	}

	/**
	 * Sets the COLS="" attribute.
	 * 
	 * @param width
	 *            sets the COLS="" attribute.
	 */
	public Table setCols(int cols) {
		addAttribute("cols", Integer.toString(cols));
		return (this);
	}

	/**
	 * Sets the COLS="" attribute.
	 * 
	 * @param width
	 *            sets the COLS="" attribute.
	 */
	public Table setCols(String cols) {
		addAttribute("cols", cols);
		return (this);
	}

	/**
	 * Sets the FRAME="" attribute.
	 * 
	 * @param frame
	 *            sets the FRAME="" attribute.
	 */
	public Table setFrame(String frame) {
		addAttribute("frame", frame);
		return (this);
	}

	/**
	 * Sets the HEIGHT="" attribute.
	 * 
	 * @param width
	 *            sets the HEIGHT="" attribute.
	 */
	public Table setHeight(int height) {
		addAttribute("height", Integer.toString(height));
		return (this);
	}

	/**
	 * Sets the HEIGHT="" attribute.
	 * 
	 * @param width
	 *            sets the HEIGHT="" attribute.
	 */
	public Table setHeight(String height) {
		addAttribute("height", height);
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

	/**
	 * Sets the RULES="" attribute.
	 * 
	 * @param rules
	 *            sets the RULES="" attribute.
	 */
	public Table setRules(String rules) {
		addAttribute("rules", rules);
		return (this);
	}

	/**
	 * Set the SUMMARY="" attribue.
	 * 
	 * @param summary
	 *            sets the SUMMARY="" attribute.
	 */
	public Table setSummary(String summary) {
		addAttribute("summary", summary);
		return (this);
	}

	/**
	 * Sets the WIDTH="" attribute.
	 * 
	 * @param width
	 *            sets the WIDTH="" attribute.
	 */
	public Table setWidth(int width) {
		addAttribute("width", Integer.toString(width));
		return (this);
	}

	/**
	 * Sets the WIDTH="" attribute.
	 * 
	 * @param width
	 *            sets the WIDTH="" attribute.
	 */
	public Table setWidth(String width) {
		addAttribute("width", width);
		return (this);
	}
}
