package tn.com.smartsoft.commons.csv.setting;

import tn.com.smartsoft.commons.csv.AbstractCvsSettingColumn;

public class CvsSettingColumnPosition extends AbstractCvsSettingColumn {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int start;
	private int end;

	public CvsSettingColumnPosition(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}

	public CvsSettingColumnPosition(int start, int end, int type) {
		super();
		this.start = start;
		this.end = end;
		this.setType(type);
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}
 
	public void setEnd(int end) {
		this.end = end;
	}

	public String getValue() {
		if (getHeader() == null)
			setHeader(getCsvSettings().getCsvReader().getStringValue(start, end));
		return getCsvSettings().getCsvReader().getStringValue(start, end);
	}

}
