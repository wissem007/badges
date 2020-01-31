package tn.com.smartsoft.framework.presentation.view.script.processor;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;

import tn.com.smartsoft.framework.presentation.definition.GroupScriptDefinition;

public interface ResourcesProcessor extends Serializable{
	public final static String DEFAULT_ENCODING = "ISO-8859-1";

	public void process(GroupScriptDefinition group, List<String> resourcesName, Writer out, String location) throws IOException;
}
