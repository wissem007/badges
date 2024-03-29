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
 * This class creates a &lt;TH&gt; object.
 * 
 * @version $Id: TH.java,v 1.1 2013/10/08 12:37:22 oracle Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
 */
public class TH extends MultiPartElement implements Printable, MouseEvents, KeyEvents {
	/**
	 * private initializer.
	 */
	{
		setElementType("th");
	}

	/**
	 * Basic constructor. Use set* methods.
	 */
	public TH() {
	}

	/**
	 * Basic Constructor use set* methods.
	 * 
	 * @param close
	 *            . Print the closing tag or not.
	 */
	public TH(boolean close) {
		setNeedClosingTag(close);
	}

	/**
	 * Basic Constructor use set* methods.
	 */
	public TH(Element element) {
		addElement(element);
	}

	/**
	 * Basic Constructor use set* methods.
	 */
	public TH(String element) {
		addElement(element);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public TH addElement(Element element) {
		addElementToRegistry(element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public TH addElement(String element) {
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
	public TH addElement(String hashcode, Element element) {
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
	public TH addElement(String hashcode, String element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	/**
	 * Removes an Element from the element.
	 * 
	 * @param hashcode
	 *            the name of the element to be removed.
	 */
	public TH removeElement(String hashcode) {
		removeElementFromRegistry(hashcode);
		return (this);
	}

	/**
	 * Sets the ABBR="" attribute.
	 * 
	 * @param cdata
	 *            sets the ABBR="" attribute.
	 */
	public TH setAbbr(String cdata) {
		addAttribute("abbr", cdata);
		return (this);
	}

	/**
	 * Sets the ALIGN="" attribute convience variables are provided in the
	 * AlignType interface
	 * 
	 * @param align
	 *            Sets the ALIGN="" attribute
	 */
	public TH setAlign(String align) {
		addAttribute("align", align);
		return (this);
	}

	/**
	 * Sets the AXES="" attribute
	 * 
	 * @param id_refs
	 *            list of id's for header cells
	 */
	public TH setAxes(String id_refs) {
		addAttribute("axes", id_refs);
		return (this);
	}

	/**
	 * Sets the AXIS="" attribute
	 * 
	 * @param cdata
	 *            sets the AXIS="" attribute
	 */
	public TH setAxis(String cdata) {
		addAttribute("axis", cdata);
		return (this);
	}

	/**
	 * Sets the BGCOLOR="" attribute
	 * 
	 * @param color
	 *            sets the background color of the cell.
	 */
	public TH setBgColor(String color) {
		addAttribute("bgcolor", HtmlColor.convertColor(color));
		return (this);
	}

	/**
	 * Sets the CHAR="" attribute.
	 * 
	 * @param character
	 *            the character to use for alignment.
	 */
	public TH setChar(String character) {
		addAttribute("char", character);
		return (this);
	}

	/**
	 * Sets the CHAROFF="" attribute.
	 * 
	 * @param char_off
	 *            When present this attribute specifies the offset of the first
	 *            occurrence of the alignment character on each line.
	 */
	public TH setCharOff(int char_off) {
		addAttribute("charoff", Integer.toString(char_off));
		return (this);
	}

	/**
	 * Sets the CHAROFF="" attribute.
	 * 
	 * @param char_off
	 *            When present this attribute specifies the offset of the first
	 *            occurrence of the alignment character on each line.
	 */
	public TH setCharOff(String char_off) {
		addAttribute("charoff", char_off);
		return (this);
	}

	/**
	 * Sets the COLSPAN="" attribute
	 * 
	 * @param span
	 *            Number of columns spanned by cell
	 */
	public TH setColSpan(int span) {
		addAttribute("colspan", Integer.toString(span));
		return (this);
	}

	/**
	 * Sets the COLSPAN="" attribute
	 * 
	 * @param span
	 *            Number of columns spanned by cell
	 */
	public TH setColSpan(String span) {
		addAttribute("colspan", span);
		return (this);
	}

	/**
	 * Supplies user agents with a recommended cell height. (Pixel Values)
	 * 
	 * @param height
	 *            how many pixels to make cell
	 */
	public TH setHeight(int height) {
		addAttribute("height", Integer.toString(height));
		return (this);
	}

	/**
	 * Supplies user agents with a recommended cell height. (Pixel Values)
	 * 
	 * @param height
	 *            how many pixels to make cell
	 */
	public TH setHeight(String height) {
		addAttribute("height", height);
		return (this);
	}

	/**
	 * Sets word wrap on or off.
	 * 
	 * @param wrap
	 *            turn word wrap on or off.
	 */
	public TH setNoWrap(boolean wrap) {
		if (wrap == true)
			addAttribute("nowrap", NO_ATTRIBUTE_VALUE);
		else
			removeAttribute("nowrap");

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
	 * Sets the ROWSPAN="" attribute
	 * 
	 * @param span
	 *            Number of rows spaned by cell
	 */
	public TH setRowSpan(int span) {
		addAttribute("rowspan", Integer.toString(span));
		return (this);
	}

	/**
	 * Sets the ROWSPAN="" attribute
	 * 
	 * @param span
	 *            Number of rows spaned by cell
	 */
	public TH setRowSpan(String span) {
		addAttribute("rowspan", span);
		return (this);
	}

	/**
	 * Sets the VALIGN="" attribute convience variables are provided in the
	 * AlignType interface
	 * 
	 * @param valign
	 *            Sets the ALIGN="" attribute
	 */
	public TH setVAlign(String valign) {
		addAttribute("valign", valign);
		return (this);
	}

	/**
	 * Supplies user agents with a recommended cell width. (Pixel Values)
	 * 
	 * @param width
	 *            how many pixels to make cell
	 */
	public TH setWidth(int width) {
		addAttribute("width", Integer.toString(width));
		return (this);
	}

	/**
	 * Supplies user agents with a recommended cell width. (Pixel Values)
	 * 
	 * @param width
	 *            how many pixels to make cell
	 */
	public TH setWidth(String width) {
		addAttribute("width", width);
		return (this);
	}
}
