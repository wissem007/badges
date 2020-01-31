package tn.com.smartsoft.framework.presentation.formater;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateFormater extends Formater {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object getAsObject(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return null;
		try {
			SimpleDateFormat dateformatter = getSimpleDateFormat();
			if (target.toString().length() > 10) {
				dateformatter = new SimpleDateFormat("yyyy-MM-dd");
				dateformatter.setLenient(false);
				target = StringUtils.substring(target.toString(), 0, 10);
			}
			Date date = dateformatter.parse(target.toString(), new ParsePosition(0));
			if (date == null)
				return null;
			return new java.sql.Date(date.getTime());
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	public String getAsString(Object target) {
		if (target == null)
			return "";
		try {
			StringBuffer buf = new StringBuffer();
			SimpleDateFormat dateformatter = getSimpleDateFormat();
			dateformatter.format(target, buf, new FieldPosition(0));
			return buf.toString();
		} catch (Exception e) {
		}
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
