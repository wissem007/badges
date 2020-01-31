package tn.com.smartsoft.commons.csv.setting;

import tn.com.smartsoft.commons.csv.CsvSettings;

public interface CvsSettingColumn {
	public static final int STRING_TYPE = 1;
	public static final int DATE_TYPE = 2;
	public static final int NUMBER_TYPE = 3;
	public static final int CURENCY_TYPE = 4;

	public void setCsvSettings(CsvSettings csvSettings);

	public abstract String getHeader();

	public abstract int getType();

	public abstract String getValue();

	public abstract Object getDataValue();

	public abstract boolean isValidData();

}