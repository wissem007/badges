package tn.com.smartsoft.framework.presentation.formater;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class TimeFormater extends Formater {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object getAsObject(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return null;
		try {

			SimpleDateFormat dateformatter = getSimpleDateFormat();
			Date date = dateformatter.parse(target.toString(), new ParsePosition(0));
			return new java.sql.Time(date.getTime());
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	public String getAsString(Object target) {
		if (target == null)
			return "";
		try {
			SimpleDateFormat dateformatter = getSimpleDateFormat();
			StringBuffer buf = new StringBuffer();
			dateformatter.format(target, buf, new FieldPosition(0));
			return buf.toString();
		} catch (Exception e) {}
		return "";
	}

	public boolean validate(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return true;
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
