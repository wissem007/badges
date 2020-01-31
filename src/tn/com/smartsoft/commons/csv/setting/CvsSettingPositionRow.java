package tn.com.smartsoft.commons.csv.setting;

import tn.com.smartsoft.commons.csv.CsvSettings;
import tn.com.smartsoft.commons.exceptions.TechnicalException;

public class CvsSettingPositionRow extends CvsSettingAbstractRow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int lastEnd = 0;

	public CvsSettingPositionRow(CsvSettings csvSettings, int indicaterColumn, Object indicaterValue) {
		super(csvSettings, indicaterColumn, indicaterValue);
	}

	public CvsSettingPositionRow(CsvSettings csvSettings, int indicaterColumn, Object[] indicaterValues) {
		super(csvSettings, indicaterColumn, indicaterValues);
	}

	public CvsSettingPositionRow(CsvSettings csvSettings) {
		super(csvSettings);
	}

	public void addColumn(int start, int end, int type) {
		if (start < lastEnd)
			throw new TechnicalException("invalid Column Position Setting");
		lastEnd = start;
		addSettingColumn(new CvsSettingColumnPosition(start, end, type));
	}

	public void addColumn(int start, int end) {
		if (start < lastEnd)
			throw new TechnicalException("invalid Column Position Setting");
		lastEnd = start;
		addSettingColumn(new CvsSettingColumnPosition(start, end));
	}

}
