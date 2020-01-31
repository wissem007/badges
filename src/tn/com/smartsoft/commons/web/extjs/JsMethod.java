package tn.com.smartsoft.commons.web.extjs;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class JsMethod implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<String> params = new ArrayList<String>();
	private String script;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getScript() {
		return script;
	}
	
	public void setScript(String script) {
		this.script = script;
	}
	
	public void addParam(String name) {
		this.params.add(name);
	}
	
	public ArrayList<String> getParams() {
		return params;
	}
	
	public void setJoinParams(String params) {
		if (StringUtils.isNotBlank(params)) {
			String[] ps = StringUtils.split(params, ",");
			for (int i = 0; i < ps.length; i++) {
				addParam(ps[i]);
			}
		}
	}
}
