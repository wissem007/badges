package tn.com.smartsoft.commons.web.markup;

import java.io.OutputStream;
import java.io.PrintWriter;

public interface Element extends ElementRegistry {
	public static final int UPPERCASE = 1;

	public static final int LOWERCASE = 2;

	public static final int MIXEDCASE = 3;

	public static final int CENTER = 4;

	public static final int LEFT = 5;

	public static final int RIGHT = 6;

	public void setCase(int type);

	public int getCase();

	public String getVersion();

	public void setElementType(String element_type);

	public String getElementType();

	public void setNeedClosingTag(boolean close_tag);

	public boolean getNeedClosingTag();

	public boolean getNeedLineBreak();

	public void setTagPosition(int position);

	public int getTagPosition();

	public void setStartTagChar(char start_tag);

	public char getStartTagChar();

	public void setEndTagChar(char end_tag);

	public char getEndTagChar();

	public Element setBeginStartModifier(char start_modifier);

	public char getBeginStartModifier();

	public Element setBeginEndModifier(char start_modifier);

	public char getBeginEndModifier();

	public Element setEndStartModifier(char start_modifier);

	public char getEndStartModifier();

	public Element setEndEndModifier(char start_modifier);

	public char getEndEndModifier();

	public Element setFilterState(boolean state);

	public boolean getFilterState();

	public Element setPrettyPrint(boolean pretty_print);

	public boolean getPrettyPrint();

	public void setTabLevel(int tabLevel);

	public int getTabLevel();

	public void output(OutputStream out);

	public void output(PrintWriter out);

}
