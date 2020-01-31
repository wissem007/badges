package tn.com.smartsoft.framework.presentation.view.tags.xtpl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class XTplBodyNode implements XTplNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<XTplNode> childrens = new ArrayList<XTplNode>();
	private String contenent;

	public int childrenSize() {
		return childrens.size();
	}

	public XTplNode getChildren(int index) {
		return childrens.get(index);
	}

	public void addChildren(XTplNode XTplNode) {
		childrens.add(XTplNode);
	}

	public String getContenent() {
		return contenent;
	}

	public void setContenent(String contenent) {
		this.contenent = contenent;
	}

	public void writeChildren(StringWriter buffer) {
		for (int i = 0; i < childrenSize(); i++) {
			getChildren(i).write(buffer);
		}
	}
}
