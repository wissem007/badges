package tn.com.smartsoft.commons.web.markup;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Hashtable;

import tn.com.smartsoft.commons.web.markup.filter.CharacterFilter;
import tn.com.smartsoft.commons.web.markup.util.ECSDefaults;

public abstract class GenericElement implements Element, Serializable {

	private static Filter _defaultFilter = new CharacterFilter();

	private Filter filter = _defaultFilter;

	private int position = ECSDefaults.getDefaultPosition();

	private boolean filter_state = ECSDefaults.getDefaultFilterState();

	private boolean pretty_print = ECSDefaults.getDefaultPrettyPrint();

	private static final String VERSION = "1.4.2";

	private int case_type = ECSDefaults.getDefaultCaseType();

	private Hashtable element = new Hashtable(4);

	private boolean end_element = ECSDefaults.getDefaultEndElement();

	private String codeset = ECSDefaults.getDefaultCodeset();

	private char begin_start_modifier = ECSDefaults.getDefaultBeginStartModifier();

	private char end_start_modifier = ECSDefaults.getDefaultEndStartModifier();

	private char begin_end_modifier = ECSDefaults.getDefaultBeginEndModifier();

	private char end_end_modifier = ECSDefaults.getDefaultEndEndModifier();

	private boolean start_modifier_defined = false;

	private boolean begin_start_modifier_defined = false;

	private boolean end_start_modifier_defined = false;

	private boolean begin_end_modifier_defined = false;

	private boolean end_end_modifier_defined = false;

	private boolean end_modifier_defined = false;

	protected int tabLevel = 0;

	/** @serial start_tag start_tag */
	private char start_tag = ECSDefaults.getDefaultStartTag();
	/** @serial end_tag end_tag */
	private char end_tag = ECSDefaults.getDefaultEndTag();
	/** @serial __text __text */
	private String __text = new String();

	/**
	 * Location to store element_type at.
	 * 
	 * @serial element_type element_type
	 */
	private String element_type = new String();

	/**
	 * Base class for defining all elements.
	 */
	public GenericElement() {
	}

	/**
	 * Do we want to do pretty printing?
	 */
	public boolean getPrettyPrint() {
		return (pretty_print);
	}

	/**
	 * Do we want to pretty print?
	 * 
	 * @param pretty_print
	 *            turn pretty printing on or off.
	 */
	public Element setPrettyPrint(boolean pretty_print) {
		this.pretty_print = pretty_print;
		return (this);
	}

	/**
	 * Get the Hashtable representation of an element.
	 */
	public Hashtable getElementHashEntry() {
		return (element);
	}

	/**
	 * set the filter state of the element.
	 * 
	 * @param filter_state
	 *            (true|false) do we want to use a filter? default true;
	 */
	public Element setFilterState(boolean filter_state) {
		this.filter_state = filter_state;
		return (this);
	}

	/**
	 * get the filter state of the element.
	 */
	public boolean getFilterState() {
		return (filter_state);
	}

	/**
	 * Get the filter that is currently in use.
	 */
	public Filter getFilter() {
		return (filter);
	}

	/**
	 * Override the default filter with a user supplied filter.
	 */
	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	/**
	 * What version of htmlGen is this?
	 */
	public String getVersion() {
		return (VERSION);
	}

	/**
	 * Determine what case the html element should be rendered. Default is
	 * MIXEDCASE
	 */
	public void setCase(int case_type) {
		this.case_type = case_type;
	}

	/**
	 * Find out what case the element is supposed to be rendered in.
	 */
	public int getCase() {
		return (case_type);
	}

	/**
	 * Determine what type of element this is A,BR,EM,ACRONYM
	 */
	public void setElementType(String element_type) {
		this.element_type = element_type;
	}

	/**
	 * Find out what type of element this is.
	 */
	public String getElementType() {
		return (element_type);
	}

	/**
	 * Determine what codeset should be used.
	 */
	public void setCodeSet(String codeset) {
		this.codeset = codeset;
	}

	/**
	 * Find out what the current codeset is.
	 */
	public String getCodeSet() {
		return (codeset);
	}

	/**
	 * Does this element need a closing tag. Default is true
	 */
	public void setNeedClosingTag(boolean close) {
		end_element = close;
	}

	/**
	 * Determine if this element needs to be closed.
	 */
	public boolean getNeedClosingTag() {
		return (end_element);
	}

	/**
	 * Determine if this element needs a line break, if pretty printing.
	 */
	public boolean getNeedLineBreak() {
		return true;
	}

	/**
	 * Set the position of the tag to be rendered at in relation to
	 * start_element_char
	 */
	public void setTagPosition(int position) {
		this.position = position;
	}

	/**
	 * Get the position the tag is to be rendered at.
	 */
	public int getTagPosition() {
		return (position);
	}

	/**
	 * Alter the case of this element so that it is rendered according to
	 * whatever the value of case_type is. Default is MIXEDCASE
	 */
	protected String alterCase(String value) {
		switch (getCase()) {
		case UPPERCASE:
			return value.toUpperCase();
		case LOWERCASE:
			return value.toLowerCase();
		default:
			return value;
		}
	}

	/**
	 * Set the element modifier the default is no modifier.
	 * 
	 * @param modifier
	 *            the modifier for the element a ? would result <br>
	 *            in &lt;? ?&gt;
	 */
	public Element setBeginModifier(char modifier) {
		setBeginStartModifier(modifier);
		setBeginEndModifier(modifier);
		setBeginStartModifierDefined(true);
		setBeginEndModifierDefined(true);
		return (this);
	}

	/**
	 * Set the element modifier the default is no modifier.
	 * 
	 * @param modifier
	 *            the modifier for the element a ? would result <br>
	 *            in &lt;? &gt;
	 */
	public Element setBeginStartModifier(char modifier) {
		this.begin_start_modifier = modifier;
		setBeginStartModifierDefined(true);
		return (this);
	}

	/**
	 * Set the element modifier the default is no modifier.
	 * 
	 * @param modifier
	 *            the modifier for the element a ? would result <br>
	 *            in &lt; ?&gt;
	 */
	public Element setBeginEndModifier(char modifier) {
		this.begin_end_modifier = modifier;
		setBeginEndModifierDefined(true);
		return (this);
	}

	/**
	 * Set the element modifier the default is no modifier.
	 * 
	 * @param modifier
	 *            the modifier for the element a ? would result <br>
	 *            in &lt;? ?&gt;
	 */
	public Element setBeginModifier(char start_modifier, char end_modifier) {
		this.begin_start_modifier = start_modifier;
		this.begin_end_modifier = end_modifier;
		setBeginStartModifierDefined(true);
		setBeginEndModifierDefined(true);
		return (this);
	}

	/**
	 * What is the element modifier for this tag.
	 */
	public char getBeginStartModifier() {
		return (begin_start_modifier);
	}

	/**
	 * What is the element modifier for this tag.
	 */
	public char getBeginEndModifier() {
		return (begin_end_modifier);
	}

	/**
	 * Remove the element modifier for this tag.
	 */
	public Element removeBeginStartModifier() {
		setBeginStartModifierDefined(false);
		return (this);
	}

	/**
	 * Remove the element modifier for this tag.
	 */
	public Element removeBeginEndModifier() {
		setBeginEndModifierDefined(false);
		return (this);
	}

	/**
	 * Modifier optimization
	 */
	private void setBeginStartModifierDefined(boolean val) {
		this.begin_start_modifier_defined = val;
	}

	/**
	 * Modifier optimization
	 */
	private void setBeginEndModifierDefined(boolean val) {
		this.begin_end_modifier_defined = val;
	}

	/**
	 * Modifier optimization
	 */
	protected boolean getEndStartModifierDefined() {
		return this.end_start_modifier_defined;
	}

	/**
	 * Modifier optimization
	 */
	protected boolean getEndEndModifierDefined() {
		return this.end_end_modifier_defined;
	}

	/**
	 * Set the element modifier the default is no modifier.
	 * 
	 * @param modifier
	 *            the modifier for the element a ? would result <br>
	 *            in &lt;? ?&gt;
	 */
	public Element setEndModifier(char modifier) {
		setEndStartModifier(modifier);
		setEndEndModifier(modifier);
		setEndStartModifierDefined(true);
		setEndEndModifierDefined(true);
		return (this);
	}

	/**
	 * Set the element modifier the default is no modifier.
	 * 
	 * @param modifier
	 *            the modifier for the element a ? would result <br>
	 *            in &lt;? &gt;
	 */
	public Element setEndStartModifier(char modifier) {
		this.end_start_modifier = modifier;
		setEndStartModifierDefined(true);
		return (this);
	}

	/**
	 * Set the element modifier the default is no modifier.
	 * 
	 * @param modifier
	 *            the modifier for the element a ? would result <br>
	 *            in &lt; ?&gt;
	 */
	public Element setEndEndModifier(char modifier) {
		this.end_end_modifier = modifier;
		setEndEndModifierDefined(true);
		return (this);
	}

	/**
	 * Set the element modifier the default is no modifier.
	 * 
	 * @param modifier
	 *            the modifier for the element a ? would result <br>
	 *            in &lt;? ?&gt;
	 */
	public Element setEndModifier(char start_modifier, char end_modifier) {
		this.end_start_modifier = start_modifier;
		this.end_end_modifier = end_modifier;
		setEndStartModifierDefined(true);
		setEndEndModifierDefined(true);
		return (this);
	}

	/**
	 * What is the element modifier for this tag.
	 */
	public char getEndStartModifier() {
		return (end_start_modifier);
	}

	/**
	 * What is the element modifier for this tag.
	 */
	public char getEndEndModifier() {
		return (end_end_modifier);
	}

	/**
	 * Remove the element modifier for this tag.
	 */
	public Element removeEndStartModifier() {
		setEndStartModifierDefined(false);
		return (this);
	}

	/**
	 * Remove the element modifier for this tag.
	 */
	public Element removeEndEndModifier() {
		setEndEndModifierDefined(false);
		return (this);
	}

	/**
	 * Modifier optimization
	 */
	private void setEndStartModifierDefined(boolean val) {
		this.end_start_modifier_defined = val;
	}

	/**
	 * Modifier optimization
	 */
	private void setEndEndModifierDefined(boolean val) {
		this.end_end_modifier_defined = val;
	}

	/**
	 * Modifier optimization
	 */
	protected boolean getBeginStartModifierDefined() {
		return this.begin_start_modifier_defined;
	}

	/**
	 * Modifier optimization
	 */
	protected boolean getBeginEndModifierDefined() {
		return this.begin_end_modifier_defined;
	}

	/**
	 * Set the start tag character.
	 */
	public void setStartTagChar(char start_tag) {
		this.start_tag = start_tag;
	}

	/**
	 * Get the start tag character.
	 */
	public char getStartTagChar() {
		return (start_tag);
	}

	/**
	 * Set the end tag character.
	 */
	public void setEndTagChar(char end_tag) {
		this.end_tag = end_tag;
	}

	/**
	 * Get the end tag character.
	 */
	public char getEndTagChar() {
		return (end_tag);
	}

	public String getTagText() {
		return (__text);
	}

	public Element setTagText(String text) {
		this.__text = (text);
		return (this);
	}

	public String createStartTag() {
		StringBuffer sb = new StringBuffer();
		sb.append(getStartTagChar());

		if (getBeginStartModifierDefined()) {
			sb.append(getBeginStartModifier());
		}
		sb.append(getElementType());

		if (getBeginEndModifierDefined()) {
			sb.append(getBeginEndModifier());
		}
		sb.append(getEndTagChar());
		return (sb.toString());
	}

	public String createEndTag() {
		setEndStartModifier('/');
		StringBuffer sb = new StringBuffer();
		sb.append(getStartTagChar());

		if (getEndStartModifierDefined()) {
			sb.append(getEndStartModifier());
		}
		sb.append(getElementType());

		if (getEndEndModifierDefined()) {
			sb.append(getEndEndModifier());
		}
		sb.append(getEndTagChar());
		return (sb.toString());
	}

	/**
	 * Override toString() method to print something meaningful
	 */
	public final String toString() {
		StringWriter write = new StringWriter();
		String out = null;
		try {
			output(write);
			write.flush();
			out = write.toString();
			write.close();
		} catch (UnsupportedEncodingException use) {
			use.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return (out);
	}

	/**
	 * Override toString(encode) method to print something meaningful
	 */
	public final String toString(String codeset) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(baos);
		String out = null;
		try {
			output(bos);
			bos.flush();
			out = baos.toString(codeset);
			bos.close();
			baos.close();
		} catch (UnsupportedEncodingException use) {} catch (IOException ioe) {}
		return (out);
	}

	/**
	 * Add the element to the designated OutputStream.
	 */
	public void output(OutputStream out) {
		String encoding = null;
		if (getCodeSet() != null) {
			encoding = getCodeSet();
		} else {
			// By default use Big Endian Unicode.
			// In this way we will not loose any information.
			encoding = "UTF-16BE";
		}

		try {
			out.write(createStartTag().getBytes(encoding));

			if (getFilterState())
				out.write(getFilter().process(getTagText()).getBytes(encoding));
			else
				out.write(getTagText().getBytes(encoding));

			if (getNeedClosingTag())
				out.write(createEndTag().getBytes(encoding));

		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace(new PrintWriter(out));
		} catch (IOException ioe) {
			ioe.printStackTrace(new PrintWriter(out));
		}
	}

	/**
	 * Add element to the designated PrintWriter.
	 */
	public void output(PrintWriter out) {

		out.write(createStartTag());

		if (getFilterState())
			out.write(getFilter().process(getTagText()));
		else
			out.write(getTagText());

		if (getNeedClosingTag())
			out.write(createEndTag());

	}

	/**
	 * Writer version of this method.
	 */
	public void output(Writer out) {
		output(new PrintWriter(out));
	}

	/**
	 * Set the tab level for pretty printing
	 */
	public void setTabLevel(int tabLevel) {
		this.tabLevel = tabLevel;
	}

	public int getTabLevel() {
		return tabLevel;
	}

	protected void putTabs(int tabCount, OutputStream out) throws IOException {
		for (int i = 0; i < tabCount; i++)
			out.write('\t');
	}

	protected void putTabs(int tabCount, PrintWriter out) {
		for (int i = 0; i < tabCount; i++)
			out.print('\t');
	}

}