package tn.com.smartsoft.commons.web.extjs;

import java.io.Serializable;

public interface JsVar extends Serializable {
	public void append(ScriptBuffer sb);
	
	public boolean isNull();
}
