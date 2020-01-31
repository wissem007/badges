package tn.com.digivoip.comman.string;

import java.util.ArrayList;
import java.util.List;

/** Helps with CSV strings. See: http://en.wikipedia.org/wiki/Comma-separated_values */
public class CsvUtil{

	protected static final char		FIELD_SEPARATOR	= ',';
	protected static final char		FIELD_QUOTE		= '"';
	protected static final String	DOUBLE_QUOTE	= "\"\"";
	protected static final String	SPECIAL_CHARS	= "\r\n";

	/** Parse fields as csv string, */
	public static String toCsvString(Object... elements) {
		StringBuilder line = new StringBuilder();
		int last = elements.length - 1;
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] == null) {
				if (i != last) {
					line.append(CsvUtil.FIELD_SEPARATOR);
				}
				continue;
			}
			String field = elements[i].toString();
			// check for special cases
			int ndx = field.indexOf(CsvUtil.FIELD_SEPARATOR);
			if (ndx == -1) {
				ndx = field.indexOf(CsvUtil.FIELD_QUOTE);
			}
			if (ndx == -1) {
				if (field.startsWith(StringPool.SPACE) || field.endsWith(StringPool.SPACE)) {
					ndx = 1;
				}
			}
			if (ndx == -1) {
				ndx = StringUtil.indexOfChars(field, CsvUtil.SPECIAL_CHARS);
			}
			// add field
			if (ndx != -1) {
				line.append(CsvUtil.FIELD_QUOTE);
			}
			field = StringUtil.replace(field, StringPool.QUOTE, CsvUtil.DOUBLE_QUOTE);
			line.append(field);
			if (ndx != -1) {
				line.append(CsvUtil.FIELD_QUOTE);
			}
			// last
			if (i != last) {
				line.append(CsvUtil.FIELD_SEPARATOR);
			}
		}
		return line.toString();
	}
	/** Converts CSV line to string array. */
	public static String[] toStringArray(String line) {
		List<String> row = new ArrayList<String>();
		boolean inQuotedField = false;
		int fieldStart = 0;
		final int len = line.length();
		for (int i = 0; i < len; i++) {
			char c = line.charAt(i);
			if (c == CsvUtil.FIELD_SEPARATOR) {
				if (!inQuotedField) { // ignore we are quoting
					CsvUtil.addField(row, line, fieldStart, i, inQuotedField);
					fieldStart = i + 1;
				}
			} else if (c == CsvUtil.FIELD_QUOTE) {
				if (inQuotedField) {
					if (i + 1 == len || line.charAt(i + 1) == CsvUtil.FIELD_SEPARATOR) { // we are already quoting - peek to see if this is the end of the field
						CsvUtil.addField(row, line, fieldStart, i, inQuotedField);
						fieldStart = i + 2;
						i++; // and skip the comma
						inQuotedField = false;
					}
				} else if (fieldStart == i) {
					inQuotedField = true; // this is a beginning of a quote
					fieldStart++; // move field start
				}
			}
		}
		// add last field - but only if string was not empty
		if (len > 0 && fieldStart <= len) {
			CsvUtil.addField(row, line, fieldStart, len, inQuotedField);
		}
		return row.toArray(new String[row.size()]);
	}
	private static void addField(List<String> row, String line, int startIndex, int endIndex, boolean inQuoted) {
		String field = line.substring(startIndex, endIndex);
		if (inQuoted) {
			field = StringUtil.replace(field, CsvUtil.DOUBLE_QUOTE, "\"");
		}
		row.add(field);
	}
}
