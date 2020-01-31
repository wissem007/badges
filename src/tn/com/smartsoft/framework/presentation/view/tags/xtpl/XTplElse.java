package tn.com.smartsoft.framework.presentation.view.tags.xtpl;

import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.tags.xtpl.utils.XTplUtils;

public class XTplElse extends XTplBodyNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void writeStarNode(StringWriter xml) {
		xml.append("#else\n");
		if (StringUtils.isNotBlank(getContenent())) {
			xml.append(XTplUtils.formatValue(getContenent()));
			xml.append("\n");
		}
	}

	public void write(StringWriter buffer) {
		writeStarNode(buffer);
		writeChildren(buffer);
	}
}
