package tn.com.smartsoft.commons.utils;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import tn.com.smartsoft.commons.log.Logger;

public class DateFormaterUtils{
	
	protected Logger	log	= Logger.getLogger(getClass());
	
	public static Date getAsDate(String target, String format) throws FormatException {
		try {
			if (isEmptyString(target)) return null;
			SimpleDateFormat dateformatter = new SimpleDateFormat(format);
			dateformatter.setLenient(false);
			Date date;
			date = dateformatter.parse(target.trim());
			return date;
		} catch (Exception e) {
			throw new FormatException(e);
		}
	}
	public static String getAsString(Object target, String format) throws FormatException {
		if (target == null) return "";
		try {
			SimpleDateFormat dateformatter = new SimpleDateFormat(format);
			dateformatter.setLenient(false);
			StringBuffer buf = new StringBuffer();
			dateformatter.format(target, buf, new FieldPosition(0));
			return buf.toString();
		} catch (Exception e) {
			throw new FormatException(e);
		}
	}
	public static boolean isEmptyString(String s) {
		return s == null || s.trim().length() == 0;
	}
	public static void main(String[] args) throws FormatException {
	}
}
