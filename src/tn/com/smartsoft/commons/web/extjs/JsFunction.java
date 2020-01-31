package tn.com.smartsoft.commons.web.extjs;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JSEncodeUtil;

public class JsFunction implements JsVar {
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
	
	public void append(ScriptBuffer sb) {
		String scrip = JSEncodeUtil.stripBlankLines(this.script);
		sb.appendHead("function ").appendHead(name);
		sb.appendHead("(").appendHead(StringUtils.join(getParams().iterator(), ",")).appendHead("){\n");
		sb.appendHead(scrip).appendHead("\n").appendHead("}\n");
	}
	
	public boolean isNull() {
		return StringUtils.isBlank(script);
	}
	
	public static void main(String[] args) {
		JsFunction jsFunction = new JsFunction();
		jsFunction.setName("close");
		jsFunction.addParam("key");
		jsFunction.addParam("value");
		JsArray params = new JsArray();
		params.addObjectValue("value");
		params.addStringValue("value");
		params.addObjectValue(true);
		ScriptBuffer sb = new ScriptBuffer();
		sb.appendVariable("name", params);
		sb.appendEndLigneChar();
		jsFunction.setScript(sb.getBodyBuffer().toString());
		sb = new ScriptBuffer();
		jsFunction.append(sb);
		System.out.println(sb.getHeadBuffer());
	}
}
