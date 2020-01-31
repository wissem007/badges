package tn.com.smartsoft.commons.csv;

public interface CsvPopulateRow {
	public void populate(int row, int col, boolean isValidData, Object value);
}
