package tn.com.smartsoft.commons.csv;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;

import tn.com.smartsoft.commons.csv.setting.CvsSettingColumn;
import tn.com.smartsoft.commons.csv.setting.CvsSettingDelimiterRow;
import tn.com.smartsoft.commons.csv.setting.CvsSettingPositionRow;
import tn.com.smartsoft.commons.utils.CurrencyFormaterUtils;
import tn.com.smartsoft.commons.utils.DateFormaterUtils;

public class Utils {

	public static boolean isValidLong(String value) {
		try {
			Long.parseLong(value.trim());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Long getLongValue(String value) {
		return Long.parseLong(value.trim());
	}

	public static boolean isValidDate(String value, String format) {
		try {
			DateFormaterUtils.getAsDate(value, format);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Date getDateValue(String value, String format) {
		try {
			return DateFormaterUtils.getAsDate(value, format);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isValidDecimal(String value, String format) {
		try {
			CurrencyFormaterUtils.getAsNumber(value, format, 3);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Number getDecimalValue(String value, String format) {
		try {
			return CurrencyFormaterUtils.getAsNumber(value, format, 3);
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}
//	public static void main(String[] args) throws Exception {
//		InputStream inputStream = new FileInputStream("c:/test.txt");
//		CsvSettings csvSettings = new CsvSettings(inputStream,';', Charset.forName("UTF8"));
////		 CvsSettingDelimiterRow settingRow =
////		 csvSettings.addDelimiterSettingRow();
//		CvsSettingDelimiterRow settingRow = csvSettings.addDelimiterSettingRow();
//		// for (int i = 0; i < 8; i++) {
//		// settingRow.addColumn();
//		// }
//		settingRow.addColumn(CvsSettingColumn.NUMBER_TYPE);
//		settingRow.addColumn(CvsSettingColumn.NUMBER_TYPE);
//		settingRow.addColumn(CvsSettingColumn.NUMBER_TYPE);
//		settingRow.addColumn();
//		settingRow.addColumn();
//		settingRow.addColumn();
//		settingRow.addColumn();
////		settingRow.addColumn(9, 15);
////		settingRow.addColumn(15, 18);
//		csvSettings.setHeaderRow(false);
//		CsvManager csvManager = CsvManager.createCsvManager(csvSettings);
//		settingRow.setPopulateRow(new CsvPopulateRow() {
//			public void populate(int row, int col, boolean isValidData, Object value) {
//				add(row, col, isValidData, value);
//			}
//		});
//		csvManager.runIterator();
//	}

	
	public static void main(String[] args) throws Exception {
		InputStream inputStream = new FileInputStream("d:/front.ope");
		CsvSettings csvSettings = new CsvSettings(inputStream, Charset.forName("UTF8"));
//		 CvsSettingDelimiterRow settingRow =
//		 csvSettings.addDelimiterSettingRow();
		CvsSettingPositionRow settingRow = csvSettings.addPositionSettingRow();
		// for (int i = 0; i < 8; i++) {
		// settingRow.addColumn();
		// }
		settingRow.addColumn(2, 3);
		settingRow.addColumn(3, 4);
		settingRow.addColumn(4, 6);
		csvSettings.setHeaderRow(false);
		CsvManager csvManager = CsvManager.createCsvManager(csvSettings);
		settingRow.setPopulateRow(new CsvPopulateRow() {
			public void populate(int row, int col, boolean isValidData, Object value) {
				add(row, col, isValidData, value);
			}
		});
		csvManager.runIterator();
	}

	public final static void add(int row, int col, boolean isValidData, Object value) {
		System.out.println("row: " + row + " col: " + col + "  isValidData: " + isValidData + " value: " + value);
	}
}
