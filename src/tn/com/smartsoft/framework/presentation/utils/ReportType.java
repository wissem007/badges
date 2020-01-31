package tn.com.smartsoft.framework.presentation.utils;

import java.io.Serializable;

public class ReportType implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	public final static ReportType PDF = new ReportType("pdf", "pdf");
	public final static ReportType XLS = new ReportType("xls", "xls");
	public final static ReportType HTML = new ReportType("html", "html");
	public final static ReportType CSV = new ReportType("csv", "csv");
	private String type;
	private static final ReportType ALL[] = (new ReportType[] { PDF, XLS, HTML, CSV });
	private String responseType;

	private ReportType(String type, String responseType) {
		this.type = type;
		this.responseType = responseType;
	}

	public String getResponseType() {
		return responseType;
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
			return type.equals(((ReportType) obj).type);
		else
			return false;
	}

	public static ReportType parse(String s) {
		for (int i = 0; i < ALL.length; i++)
			if (ALL[i].equals(s))
				return ALL[i];
		throw new RuntimeException("Invalid ReportType: " + s);
	}
}
