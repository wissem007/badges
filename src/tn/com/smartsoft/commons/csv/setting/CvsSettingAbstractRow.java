package tn.com.smartsoft.commons.csv.setting;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import tn.com.smartsoft.commons.csv.CsvPopulateRow;
import tn.com.smartsoft.commons.csv.CsvSettings;

public abstract class CvsSettingAbstractRow implements CvsSettingRow {
	private List<CvsSettingColumn> listColumn = new ArrayList<CvsSettingColumn>();
	private CsvSettings csvSettings;
	protected int indicaterColumn;
	protected Object[] indicaterValues;
	protected CsvPopulateRow populateRow;

	public CvsSettingAbstractRow(CsvSettings csvSettings, int indicaterColumn, Object[] indicaterValues) {
		super();
		this.csvSettings = csvSettings;
		this.indicaterColumn = indicaterColumn;
		this.indicaterValues = indicaterValues;
	}

	public CvsSettingAbstractRow(CsvSettings csvSettings, int indicaterColumn, Object indicaterValue) {
		this(csvSettings, indicaterColumn, new Object[] { indicaterValue });
	}

	public CvsSettingAbstractRow(CsvSettings csvSettings) {
		super();
		this.csvSettings = csvSettings;
		this.indicaterColumn = -1;
		this.indicaterValues = null;
	}

	public CsvPopulateRow getPopulateRow() {
		return populateRow;
	}

	public void setPopulateRow(CsvPopulateRow populateRow) {
		this.populateRow = populateRow;
	}

	public void setCsvSettings(CsvSettings csvSettings) {
		this.csvSettings = csvSettings;
	}

	protected void addSettingColumn(CvsSettingColumn columnPosition) {
		columnPosition.setCsvSettings(csvSettings);
		listColumn.add(columnPosition);
	}

	public int columnSize() {
		return listColumn.size();
	}

	public CvsSettingColumn getColumn(int column) {
		return listColumn.get(column);
	}

	public String getCurrentValue(int column) {
		return getColumn(column).getValue();
	}

	public Object getCurrentDataValue(int column) {
		return getColumn(column).getDataValue();
	}

	public boolean isValidCurrentData(int column) {
		return getColumn(column).isValidData();
	}

	public boolean isHere() {
		if (indicaterColumn == -1 || indicaterValues == null || indicaterValues.length == 0)
			return true;
		for (int i = 0; i < indicaterValues.length; i++) {
			if (ObjectUtils.equals(getCurrentDataValue(indicaterColumn), indicaterValues[i]))
				return true;
		}
		return false;
	}
}
