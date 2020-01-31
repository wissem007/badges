package tn.com.smartsoft.commons.csv;

import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.commons.csv.setting.CvsSettingDelimiterRow;
import tn.com.smartsoft.commons.csv.setting.CvsSettingPositionRow;
import tn.com.smartsoft.commons.csv.setting.CvsSettingRow;

public class CsvSettings implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_DECIMAL_FORMAT = "###,###,##0.000";
	public static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";
	private InputStream inputStream;
	private char delimiter = ';';
	private Charset charset = Charset.forName("ISO-8859-1");
	private String dateFormat = DEFAULT_DATE_FORMAT;
	private String decimalFormat = DEFAULT_DECIMAL_FORMAT;
	private boolean isHeaderRow = false;
	private List<CvsSettingRow> settingRows = new ArrayList<CvsSettingRow>();
	private CsvReader csvReader;

	public CsvSettings(InputStream inputStream, char delimiter, Charset charset) {
		super();
		this.inputStream = inputStream;
		this.delimiter = delimiter;
		this.charset = charset;
	}

	public CsvSettings(InputStream inputStream, Charset charset) {
		this(inputStream, ';', charset);
	}

	public CsvSettings(InputStream inputStream) {
		this(inputStream, ';', Charset.forName("ISO-8859-1"));
	}

	public CsvSettings(InputStream inputStream, char delimiter) {
		this(inputStream, delimiter, Charset.forName("ISO-8859-1"));
	}

	public CsvSettings(InputStream inputStream, char delimiter, String charsetName) {
		this(inputStream, delimiter, Charset.forName(charsetName));
	}

	public CsvReader getCsvReader() {
		return csvReader;
	}

	public void setCsvReader(CsvReader csvReader) {
		this.csvReader = csvReader;
	}

	protected CvsSettingRow addSettingRow(CvsSettingRow cvsSettingRow) {
		settingRows.add(cvsSettingRow);
		return cvsSettingRow;
	}

	public CvsSettingDelimiterRow addDelimiterSettingRow(int indicaterColumn, Object indicaterValue) {
		return (CvsSettingDelimiterRow) addSettingRow(new CvsSettingDelimiterRow(this, indicaterColumn, indicaterValue));
	}

	public CvsSettingDelimiterRow addDelimiterSettingRow(int indicaterColumn, Object[] indicaterValues) {
		return (CvsSettingDelimiterRow) addSettingRow(new CvsSettingDelimiterRow(this, indicaterColumn, indicaterValues));
	}

	public CvsSettingDelimiterRow addDelimiterSettingRow() {
		return (CvsSettingDelimiterRow) addSettingRow(new CvsSettingDelimiterRow(this));
	}

	public CvsSettingPositionRow addPositionSettingRow(int indicaterColumn, Object indicaterValue) {
		return (CvsSettingPositionRow) addSettingRow(new CvsSettingPositionRow(this, indicaterColumn, indicaterValue));
	}

	public CvsSettingPositionRow addPositionSettingRow(int indicaterColumn, Object[] indicaterValues) {
		return (CvsSettingPositionRow) addSettingRow(new CvsSettingPositionRow(this, indicaterColumn, indicaterValues));
	}

	public CvsSettingPositionRow addPositionSettingRow() {
		return (CvsSettingPositionRow) addSettingRow(new CvsSettingPositionRow(this));
	}

	public List<CvsSettingRow> getSettingRows() {
		return settingRows;
	}

	public boolean isHeaderRow() {
		return isHeaderRow;
	}

	public void setHeaderRow(boolean isHeaderRow) {
		this.isHeaderRow = isHeaderRow;
	}

	public CsvSettings(InputStream inputStream, String charsetName) {
		this(inputStream, Letters.COMMA, Charset.forName(charsetName));
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public char getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getDecimalFormat() {
		return decimalFormat;
	}

	public void setDecimalFormat(String decimalFormat) {
		this.decimalFormat = decimalFormat;
	}
}
