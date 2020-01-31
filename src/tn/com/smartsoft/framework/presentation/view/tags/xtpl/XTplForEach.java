package tn.com.smartsoft.framework.presentation.view.tags.xtpl;

import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.tags.xtpl.utils.XTplUtils;

public class XTplForEach extends XTplBodyNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String exp;

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public void writeStarNode(StringWriter xml) {
		xml.append("#foreach(");
		xml.append(XTplUtils.formatAttributeValue(exp));
		xml.append(")\n");
		if (StringUtils.isNotBlank(getContenent())) {
			xml.append(XTplUtils.formatValue(getContenent()));
			xml.append("\n");
		}
	}

	public void writeEndNode(StringWriter xml) {
		xml.append("#end\n");
	}

	public void write(StringWriter buffer) {
		writeStarNode(buffer);
		writeChildren(buffer);
		writeEndNode(buffer);

	}
}
