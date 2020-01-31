package tn.com.smartsoft.framework.presentation.view.tags.xtpl;

import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.tags.xtpl.utils.XTplUtils;

public class XTplIf extends XTplBodyNode implements XTplCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String condition;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public void writeStarNode(StringWriter xml) {
		xml.append("#if(");
		xml.append(XTplUtils.formatAttributeValue(condition));
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
