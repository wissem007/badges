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
import tn.com.smartsoft.commons.web.markup.FormEvents;
import tn.com.smartsoft.commons.web.markup.KeyEvents;
import tn.com.smartsoft.commons.web.markup.MouseEvents;
import tn.com.smartsoft.commons.web.markup.MultiPartElement;
import tn.com.smartsoft.commons.web.markup.Printable;

/**
 * This class creates a &lt;OptGroup&gt; tag.
 * 
 * @version $Id: OptGroup.java,v 1.1 2013/10/08 12:37:21 oracle Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
 */
public class OptGroup extends MultiPartElement implements Printable, FocusEvents, FormEvents, MouseEvents, KeyEvents {

	/**
	 * Private initialization routine.
	 */
	{
		setElementType("optgroup");
	}

	/**
	 * Basic constructor. Use the set* methods to set the values of the
	 * attributes.
	 */
	public OptGroup() {
	}

	/**
	 * Basic constructor. Use the set* methods to set the values of the
	 * attributes.
	 * 
	 * @param label
	 *            sets the attribute LABEL=""
	 */
	public OptGroup(String label) {
		setLabel(label);
	}

	/**
	 * Basic constructor. Use the set* methods to set the values of the
	 * attributes.
	 * 
	 * @param label
	 *            sets the attribute LABEL=""
	 * @param disabled
	 *            sets the attribute DISABLED=
	 */
	public OptGroup(String label, boolean disabled) {
		setLabel(label);
		setDisabled(disabled);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public OptGroup addElement(Element element) {
		addElementToRegistry(element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public OptGroup addElement(String element) {
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
	public OptGroup addElement(String hashcode, Element element) {
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
	public OptGroup addElement(String hashcode, String element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	/**
	 * Removes an Element from the element.
	 * 
	 * @param hashcode
	 *            the name of the element to be removed.
	 */
	public OptGroup removeElement(String hashcode) {
		removeElementFromRegistry(hashcode);
		return (this);
	}

	/**
	 * Sets the disabled value
	 * 
	 * @param disabled
	 *            true or false
	 */
	public OptGroup setDisabled(boolean disabled) {
		if (disabled == true)
			addAttribute("disabled", NO_ATTRIBUTE_VALUE);
		else
			removeAttribute("disabled");

		return (this);
	}

	/**
	 * Sets the LABEL="" attribute
	 * 
	 * @param label
	 *            the LABEL="" attribute
	 */
	public OptGroup setLabel(String label) {
		addAttribute("label", label);
		return this;
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
	 * The onchange event occurs when a control loses the input focus and its
	 * value has been modified since gaining focus. This attribute applies to
	 * the following elements: INPUT, SELECT, and TEXTAREA.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnChange(String script) {
		addAttribute("onChange", script);
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
	 * The onreset event occurs when a form is reset. It only applies to the
	 * FORM element.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnReset(String script) {
		addAttribute("onReset", script);
	}

	/**
	 * The onselect event occurs when a user selects some text in a text field.
	 * This attribute may be used with the INPUT and TEXTAREA elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnSelect(String script) {
		addAttribute("onSelect", script);
	}

	/**
	 * The onsubmit event occurs when a form is submitted. It only applies to
	 * the FORM element.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnSubmit(String script) {
		addAttribute("onSubmit", script);
	}

	/**
	 * Sets the VALUE="" attribute
	 * 
	 * @param value
	 *            the VALUE="" attribute
	 */
	public OptGroup setValue(String value) {
		addAttribute("value", value);
		return this;
	}
}
