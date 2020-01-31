package tn.com.smartsoft.commons.csv.setting;

import tn.com.smartsoft.commons.csv.CsvSettings;

public class CvsSettingDelimiterRow extends CvsSettingAbstractRow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CvsSettingDelimiterRow(CsvSettings csvSettings, int indicaterColumn, Object indicaterValue) {
		super(csvSettings, indicaterColumn, indicaterValue);
	}

	public CvsSettingDelimiterRow(CsvSettings csvSettings, int indicaterColumn, Object[] indicaterValues) {
		super(csvSettings, indicaterColumn, indicaterValues);
	}

	public CvsSettingDelimiterRow(CsvSettings csvSettings) {
		super(csvSettings);
	}

	public void addColumn(int type) {
		addSettingColumn(new CvsSettingColumnDelimiter(columnSize(), type));
	}

	public void addColumn() {
		addSettingColumn(new CvsSettingColumnDelimiter(columnSize()));
	}

}
