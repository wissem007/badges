package tn.com.smartsoft.framework.presentation.view.tags.xtpl;

import java.io.StringWriter;

public class XTplRoot extends XTplBodyNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void write(StringWriter buffer) {
		writeChildren(buffer);

	}
}
