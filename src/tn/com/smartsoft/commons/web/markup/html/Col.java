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
import tn.com.smartsoft.commons.web.markup.Printable;
import tn.com.smartsoft.commons.web.markup.SinglePartElement;

/**
 * This class creates a &lt;COL&gt; object.
 * 
 * @version $Id: Col.java,v 1.1 2013/10/08 12:37:22 oracle Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
 */
public class Col extends SinglePartElement implements Printable {
	/**
	 * private initializer.
	 */
	{
		setElementType("col");
	}

	public Col() {
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public Col addElement(Element element) {
		addElementToRegistry(element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public Col addElement(String element) {
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
	public Col addElement(String hashcode, Element element) {
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
	public Col addElement(String hashcode, String element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	/**
	 * Removes an Element from the element.
	 * 
	 * @param hashcode
	 *            the name of the element to be removed.
	 */
	public Col removeElement(String hashcode) {
		removeElementFromRegistry(hashcode);
		return (this);
	}

	/**
	 * Sets the ALIGN="" attribute convience variables are provided in the
	 * AlignType interface
	 * 
	 * @param align
	 *            Sets the ALIGN="" attribute
	 */
	public Col setAlign(String align) {
		addAttribute("align", align);
		return (this);
	}

	/**
	 * Sets the CHAR="" attribute.
	 * 
	 * @param character
	 *            the character to use for alignment.
	 */
	public Col setChar(String character) {
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
	public Col setCharOff(int char_off) {
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
	public Col setCharOff(String char_off) {
		addAttribute("charoff", char_off);
		return (this);
	}

	/**
	 * Sets the SPAN="" attribute.
	 * 
	 * @param span
	 *            sets the SPAN="" attribute.
	 */
	public Col setSpan(int span) {
		addAttribute("span", Integer.toString(span));
		return (this);
	}

	/**
	 * Sets the SPAN="" attribute.
	 * 
	 * @param span
	 *            sets the SPAN="" attribute.
	 */
	public Col setSpan(String span) {
		addAttribute("span", span);
		return (this);
	}

	/**
	 * Sets the VALIGN="" attribute convience variables are provided in the
	 * AlignType interface
	 * 
	 * @param valign
	 *            Sets the ALIGN="" attribute
	 */
	public Col setVAlign(String valign) {
		addAttribute("valign", valign);
		return (this);
	}

	/**
	 * Supplies user agents with a recommended cell width. (Pixel Values)
	 * 
	 * @param width
	 *            how many pixels to make cell
	 */
	public Col setWidth(int width) {
		addAttribute("width", Integer.toString(width));
		return (this);
	}

	/**
	 * Supplies user agents with a recommended cell width. (Pixel Values)
	 * 
	 * @param width
	 *            how many pixels to make cell
	 */
	public Col setWidth(String width) {
		addAttribute("width", width);
		return (this);
	}
}
