package tn.com.smartsoft.framework.apiThecallr;

import java.io.Serializable;
import java.util.List;

public class CallsHistory implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Inbound> in;
	private List<Inbound> out;
	private String type;
	
	public List<Inbound> getIn() {
		return in;
	}
	
	public void setIn(List<Inbound> in) {
		this.in = in;
	}
	
	public List<Inbound> getOut() {
		return out;
	}
	
	public void setOut(List<Inbound> out) {
		this.out = out;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String toString() {
		return "[in=" + in + " \n, out=" + out + ", \n type=" + type + "]";
	}
}
