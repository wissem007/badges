package tn.com.smartsoft.commons.xml.utils;

import java.io.Serializable;

public interface ParserDefinition extends Serializable{
	public String parse(String value);

	public String getVariableSystem(String name);
}
