package tn.com.smartsoft.commons.csv;

import java.io.IOException;
import java.util.Date;

import tn.com.smartsoft.commons.csv.setting.CvsSettingRow;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.CurrencyFormaterUtils;
import tn.com.smartsoft.commons.utils.DateFormaterUtils;

public class CsvManager {
	private CsvSettings csvSettings;
	private CsvReader csvReader;
	private boolean isReadRecord = true;
	private boolean readRecord;

	private CsvManager(CsvSettings csvSettings) throws IOException {
		this.csvSettings = csvSettings;
		this.csvReader = new CsvReader(csvSettings.getInputStream(), csvSettings.getDelimiter(), csvSettings.getCharset());
		csvSettings.setCsvReader(this.csvReader);
		if (csvSettings.isHeaderRow()) {
			csvReader.readRecord();
		}
	}

	public boolean next() throws IOException {
		if (isReadRecord)
			return csvReader.readRecord();
		isReadRecord = true;
		return readRecord;
	}

	public static CsvManager createCsvManager(CsvSettings csvSettings) throws TechnicalException {
		try {
			return new CsvManager(csvSettings);
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}

	public String getStringValue(int columnIndex) {
		try {
			return csvReader.get(columnIndex);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getStringValue(int pos1, int pos2) {
		try {
			return csvReader.get(pos1, pos2);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isValidInt(int pos1, int pos2) {
		try {
			Integer.parseInt(getStringValue(pos1, pos2).trim());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isValidLong(int pos1, int pos2) {
		try {
			Long.parseLong(getStringValue(pos1, pos2).trim());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isValidLong(int columnIndex) {
		try {
			Long.parseLong(getStringValue(columnIndex).trim());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isValidInt(int columnIndex) {
		try {
			Integer.parseInt(getStringValue(columnIndex).trim());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public int getIntValue(int pos1, int pos2) {
		return Integer.parseInt(getStringValue(pos1, pos2).trim());
	}

	public Long getLongValue(int pos1, int pos2) {
		return Long.parseLong(getStringValue(pos1, pos2).trim());
	}

	public Long getLongValue(int columnIndex) {
		return Long.parseLong(getStringValue(columnIndex).trim());
	}

	public int getIntValue(int columnIndex) {
		return Integer.parseInt(getStringValue(columnIndex).trim());
	}

	public boolean isValidDate(int columnIndex) {
		try {
			DateFormaterUtils.getAsDate(getStringValue(columnIndex).trim(), csvSettings.getDateFormat());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isValidDate(int pos1, int pos2) {
		try {
			DateFormaterUtils.getAsDate(getStringValue(pos1, pos2).trim(), csvSettings.getDateFormat());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Date getDateValue(int columnIndex) {
		try {
			return DateFormaterUtils.getAsDate(getStringValue(columnIndex).trim(), csvSettings.getDateFormat());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Date getDateValue(int pos1, int pos2) {
		try {
			return DateFormaterUtils.getAsDate(getStringValue(pos1, pos2).trim(), csvSettings.getDateFormat());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isValidDecimal(int columnIndex) {
		try {
			CurrencyFormaterUtils.getAsNumber(getStringValue(columnIndex).trim(), csvSettings.getDecimalFormat(), 3);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isValidDecimal(int pos1, int pos2) {
		try {
			CurrencyFormaterUtils.getAsNumber(getStringValue(pos1, pos2).trim(), csvSettings.getDecimalFormat(), 3);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Number getNumberValue(int columnIndex) {
		try {
			return CurrencyFormaterUtils.getAsNumber(getStringValue(columnIndex).trim(), csvSettings.getDecimalFormat(), 3);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Number getNumberValue(int pos1, int pos2) {
		try {
			return CurrencyFormaterUtils.getAsNumber(getStringValue(pos1, pos2).trim(), csvSettings.getDecimalFormat(), 3);
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}

	

	public void runIterator() throws IOException {
		int row = 0;
		while (next()) {
			for (int j = 0; j < csvSettings.getSettingRows().size(); j++) {
				CvsSettingRow cvsSettingRow = csvSettings.getSettingRows().get(j);
				if (cvsSettingRow.isHere()) {
					for (int i = 0; i < cvsSettingRow.columnSize(); i++) {
						boolean isValidData = cvsSettingRow.isValidCurrentData(i);
						Object data;
						if (isValidData)
							data = cvsSettingRow.getCurrentValue(i);
						else
							data = cvsSettingRow.getCurrentValue(i);
						cvsSettingRow.getPopulateRow().populate(row, i, isValidData, data);
					}
				}
			}
			row++;
		}
	}

}
