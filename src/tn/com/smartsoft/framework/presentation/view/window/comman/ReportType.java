package tn.com.smartsoft.framework.presentation.view.window.comman;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.presentation.UIObject;

public class ReportType implements UIObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static ReportType PDF = new ReportType("pdf");
	public final static ReportType XSL = new ReportType("xsl");
	public final static ReportType HTML = new ReportType("html");
	public final static ReportType CSV = new ReportType("csv");
	private String type;
	private static final ReportType ALL[] = (new ReportType[] { PDF, XSL, HTML, CSV });

	private ReportType(String type) {
		this.type = type;
	}

	public String toString() {
		return type;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof String)
			return type.equalsIgnoreCase((String) obj);
		if (obj instanceof ReportType)
			return type.equalsIgnoreCase(((ReportType) obj).type);
		else
			return false;
	}

	public static ReportType parse(String s) {
		for (int i = 0; i < ALL.length; i++)
			if (ALL[i].equals(s))
				return ALL[i];
		throw new TechnicalException("Invalid ReportType: " + s);
	}
}
