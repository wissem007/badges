package tn.com.smartsoft.commons.csv.setting;

import tn.com.smartsoft.commons.csv.AbstractCvsSettingColumn;

public class CvsSettingColumnDelimiter extends AbstractCvsSettingColumn {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int index;

	public CvsSettingColumnDelimiter(int index) {
		super();
		this.index = index;
	}

	public CvsSettingColumnDelimiter(int index, int type) {
		super();
		this.index = index;
		this.setType(type);
	}

	public String getValue() {
		if (getHeader() == null)
			setHeader(getCsvSettings().getCsvReader().getStringValue(index));
		return getCsvSettings().getCsvReader().getStringValue(index);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
