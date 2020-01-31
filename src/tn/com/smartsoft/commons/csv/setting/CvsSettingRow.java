package tn.com.smartsoft.commons.csv.setting;

import java.io.Serializable;

import tn.com.smartsoft.commons.csv.CsvPopulateRow;

public interface CvsSettingRow extends Serializable {
	public boolean isHere();

	public CsvPopulateRow getPopulateRow();

	public CvsSettingColumn getColumn(int column);

	public int columnSize();

	public boolean isValidCurrentData(int column);

	public String getCurrentValue(int column);

	public Object getCurrentDataValue(int column);

}
