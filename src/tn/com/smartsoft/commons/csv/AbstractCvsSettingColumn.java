package tn.com.smartsoft.commons.csv;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.csv.setting.CvsSettingColumn;

public abstract class AbstractCvsSettingColumn implements Serializable, CvsSettingColumn {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String header;
	private CsvSettings csvSettings;
	private int type = STRING_TYPE;

	public CsvSettings getCsvSettings() {
		return csvSettings;
	}

	public void setCsvSettings(CsvSettings csvSettings) {
		this.csvSettings = csvSettings;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public abstract String getValue();

	public boolean isValidData() {
		String value = getValue();
		value = StringUtils.trim(value);
		switch (type) {
		case DATE_TYPE:
			return Utils.isValidDate(value, csvSettings.getDateFormat());
		case NUMBER_TYPE:
			return Utils.isValidLong(value);
		case CURENCY_TYPE:
			return Utils.isValidDecimal(value, csvSettings.getDecimalFormat());
		default:
			return true;
		}
	}

	public Object getDataValue() {
		if (!isValidData())
			return getValue();
		String value = getValue();
		value = StringUtils.trim(value);
		switch (type) {
		case DATE_TYPE:
			return Utils.getDateValue(value, csvSettings.getDateFormat());
		case NUMBER_TYPE:
			return Utils.getLongValue(value);
		case CURENCY_TYPE:
			return Utils.getDecimalValue(value, csvSettings.getDecimalFormat());
		default:
			return getValue();
		}
	}
}
