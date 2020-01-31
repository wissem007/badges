package tn.com.smartsoft.framework.presentation.formater;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.lang.StringUtils;

public class DateTimeFormater extends Formater{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public Object getAsObject(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()) || target.toString().equals("__/__/____")) return null;
		try {
			SimpleDateFormat dateformatter = getSimpleDateFormat();
			Date date;
			if (target.toString().length() == 10) {
				dateformatter = new SimpleDateFormat(getUserType().getPatern().substring(0, 10));
				dateformatter.setLenient(false);
				date = dateformatter.parse(target.toString(), new ParsePosition(0));
				Calendar calender = new GregorianCalendar();
				calender.setTime(date);
				date = new Date(calender.getTimeInMillis());
			} else {
				date = dateformatter.parse(target.toString(), new ParsePosition(0));
			}
			if (targetClass == null || targetClass.equals(Date.class)) return date;
			else if (targetClass.equals(java.sql.Timestamp.class)) return new java.sql.Timestamp(date.getTime());
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	public String getAsString(Object target) {
		if (target == null) return "";
		try {
			SimpleDateFormat dateformatter = getSimpleDateFormat();
			StringBuffer buf = new StringBuffer();
			dateformatter.format(target, buf, new FieldPosition(0));
			return buf.toString();
		} catch (Exception e) {}
		return "";
	}
	public boolean validate(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString())) return true;
		try {
			SimpleDateFormat dateformatter = getSimpleDateFormat();
			dateformatter.parse(target.toString(), new ParsePosition(0));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public SimpleDateFormat getSimpleDateFormat() {
		SimpleDateFormat dateformatter = new SimpleDateFormat(getUserType().getPatern());
		dateformatter.setLenient(false);
		return dateformatter;
	}
}
