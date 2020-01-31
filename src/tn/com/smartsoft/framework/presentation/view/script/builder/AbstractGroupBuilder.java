package tn.com.smartsoft.framework.presentation.view.script.builder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

import tn.com.smartsoft.framework.presentation.definition.GroupScriptDefinition;

public abstract class AbstractGroupBuilder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static AbstractGroupBuilder instance = new ProdGroupBuilder();

	public static AbstractGroupBuilder getInstance() {
		return instance;
	}

	protected abstract boolean mustBuildJsGroup(GroupScriptDefinition group);

	protected abstract boolean mustBuildCssGroup(GroupScriptDefinition group);

	public synchronized boolean buildGroupJsIfNeeded(GroupScriptDefinition group, OutputStream out) throws IOException {
		if (mustBuildJsGroup(group)) {
			group.getJSProcessor().process(group, group.getJsNames(), new OutputStreamWriter(out), group.getLocation());
			group.setLastJsLoadTime(System.currentTimeMillis());
			out.close();
			return true;
		}
		return false;
	}

	public synchronized boolean buildGroupCssIfNeeded(GroupScriptDefinition group, OutputStream out) throws IOException {
		if (mustBuildCssGroup(group)) {
			group.getCSSProcessor().process(group, group.getCssNames(), new OutputStreamWriter(out), group.getLocation());
			group.setLastCssLoadTime(System.currentTimeMillis());
			out.close();
			return true;
		}
		return false;
	}
}
