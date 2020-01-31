package tn.com.digivoip.comman;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;

public class Formateur{

	public static DecimalFormat	decimalFormat;
	public static DecimalFormat	integerFormat;
	static {
		DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
		Formateur.decimalFormat = new DecimalFormat("##0.00", unusualSymbols);
		unusualSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
		Formateur.integerFormat = new DecimalFormat("##0", unusualSymbols);
	}

	public static String getFormatedValue(Integer value) {
		if (value == null) { return "0"; }
		return Formateur.integerFormat.format(value);
	}
	public static String getFormatedValue(Float value) {
		if (value == null) { return "0"; }
		return Formateur.decimalFormat.format(value);
	}
	public static String getFormatedValue(Double value) {
		if (value == null) { return "0"; }
		return Formateur.decimalFormat.format(value);
	}
	public static String getFormatedValue(BigDecimal value) {
		try {
			if (value == null) {
				value = BigDecimal.ZERO;
			}
			return Formateur.decimalFormat.format(value);
		} catch (Exception e) {
			return "0";
		}
	}
	public static String getFormatedValueIfZeroPoint(BigDecimal value) {
		try {
			if (value != null && !value.equals(BigDecimal.ZERO)) { return Formateur.decimalFormat.format(value); }
		} catch (Exception e) {}
		return "0";
	}
	public static String getFormatedDateValue(String date) {
		try {
			return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
		} catch (Exception e) {
			return "";
		}
	}
	public static String getFormatedDateTimeValue(String date, String time) {
		return Formateur.getFormatedDateValue(date) + " " + Formateur.getFormatedTimeValue(time);
	}
	public static String getFormatedTimeValueNotS(String time) {
		try {
			String h = time.length() > 1 ? time.substring(0, 2) : "00";
			String m = time.length() > 3 ? time.substring(2, 4) : "00";
			return h + ":" + m;
		} catch (Exception e) {
			return "";
		}
	}
	public static Integer convertTimeToValue(String time) {
		try {
			int value = 0;
			if (time.length() > 1) {
				value = value + Integer.parseInt(time.substring(0, 2)) * 3600;
			}
			if (time.length() > 3) {
				value = value + Integer.parseInt(time.substring(2, 4)) * 60;
			}
			if (time.length() > 5) {
				value = value + Integer.parseInt(time.substring(4, 6));
			}
			return value;
		} catch (Exception e) {
			return 0;
		}
	}
	public static String getFormatedTimeValue(String time) {
		try {
			if (StringUtils.isBlank(time) || time.length() < 6) { return ""; }
			return time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4);
		} catch (Exception e) {
			return "";
		}
	}
}
