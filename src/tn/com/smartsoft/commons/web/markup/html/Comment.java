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
import tn.com.smartsoft.commons.web.markup.MultiPartElement;
import tn.com.smartsoft.commons.web.markup.Printable;

/**
 * This class defines a comment &lt;-- VALUE --&gt; Element.
 * 
 * @version $Id: Comment.java,v 1.2 2013/11/27 16:31:19 oracle Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
 */
public class Comment extends MultiPartElement implements Printable {

	/**
	 * private initializer.
	 */
	{
		setElementType("--");
		setBeginStartModifier('!');
	}

	/**
	 * Default constructor use set* methods.
	 */
	public Comment() {
	}

	/**
	 * Create a comment object containg this element.
	 */
	public Comment(Element element) {
		addElement(element);
	}

	/**
	 * Create a comment object containg this element.
	 */
	public Comment(String element) {
		addElement(element);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public Comment addElement(Element element) {
		addElementToRegistry(element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public Comment addElement(String element) {
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
	public Comment addElement(String hashcode, Element element) {
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
	public Comment addElement(String hashcode, String element) {
		addElementToRegistry(hashcode, element);
		return (this);
	}

	
	public String createEndTag() {
		StringBuffer out = new StringBuffer();
		setStartTagChar(' ');
		setEndStartModifier(' ');

		out.append(getStartTagChar());

		if (getEndStartModifierDefined()) {
			out.append(getEndStartModifier());
		}
		out.append(getElementType());

		if (getEndEndModifierDefined()) {
			out.append(getEndEndModifier());
		}
		out.append(getEndTagChar());
		setStartTagChar('<'); // put back the tag start character

		return (out.toString());
	}

	
	public String createStartTag() {
		setEndTagChar(' ');
		StringBuffer out = new StringBuffer();
		out.append(getStartTagChar());

		if (getBeginStartModifierDefined()) {
			out.append(getBeginStartModifier());
		}
		out.append(getElementType());

		if (getBeginEndModifierDefined()) {
			out.append(getBeginEndModifier());
		}
		out.append(getEndTagChar());

		setEndTagChar('>'); // put back the end tag character.
		return (out.toString());
	}

	/**
	 * Removes an Element from the element.
	 * 
	 * @param hashcode
	 *            the name of the element to be removed.
	 */
	public Comment removeElement(String hashcode) {
		removeElementFromRegistry(hashcode);
		return (this);
	}
}
