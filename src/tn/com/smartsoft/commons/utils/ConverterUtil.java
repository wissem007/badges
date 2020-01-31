package tn.com.smartsoft.commons.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import tn.com.smartsoft.iap.administration.referentiel.file.beans.FileBean;

public class ConverterUtil{
	
	public static int		TYPE_STRING					= 0;
	public static int		TYPE_INTEGER				= 1;
	public static int		TYPE_DATE					= 2;
	public static int		TYPE_DOUBLE					= 3;
	public static int		TYPE_POURCENTAGE			= 4;
	public static String	DEFAULT_DATE_FORMAT			= "dd/MM/yyyy";
	public static String	DEFAULT_DATE_TIME_FORMAT	= "dd/MM/yyyy HH:mm:ss";
	public static String	DEFAULT_DOUBLE_FORMAT		= "#,##0.000";
	public static String	DOUBLE_FORMAT_ENTIER		= "#0";
	public static String	DEFAULT_POURCENTAGE_FORMAT	= "##0.###%";
	public static String	DEFAULT_INT_FORMAT			= "###,###,###,###";
	
	public ConverterUtil() {}
	public static Object convert(String value, int type, String format) {
		if (TYPE_DATE == type) return getDate(format, value);
		if (TYPE_INTEGER == type) return getInteger(value);
		if (TYPE_DOUBLE == type) return getDouble(format, value);
		return value;
	}
	public static String convertToString(Object value, int type, String format) {
		if (value == null) return "";
		if (TYPE_DATE == type) return getDateString(format, (Date) value);
		if (TYPE_INTEGER == type) return getIntegerString(format, (Number) value);
		if (TYPE_DOUBLE == type) return getDoubleString(format, (Number) value);
		if (TYPE_POURCENTAGE == type) return getPourcentageString(format, (Number) value);
		return value.toString();
	}
	public static int getValideDate(String format, String dateString) {
		try {
			if (dateString == null || dateString.equals("")) {
				return -1;
			} else {
				Date date = new SimpleDateFormat(format).parse(dateString);
				return 1;
			}
		} catch (ParseException e) {
			return -1;
		}
	}
	public static String convertToString(Object value, int type) {
		if (value == null) return "";
		if (value instanceof String) return value.toString();
		if (TYPE_DATE == type) return getDateString(DEFAULT_DATE_FORMAT, (Date) value);
		if (TYPE_INTEGER == type) return getIntegerString(DEFAULT_INT_FORMAT, (Number) value);
		if (TYPE_DOUBLE == type) return getDoubleString(DEFAULT_DOUBLE_FORMAT, (Number) value);
		if (TYPE_POURCENTAGE == type) return getPourcentageString(DEFAULT_POURCENTAGE_FORMAT, (Number) value);
		return value.toString();
	}
	public static String getFormat(int type) {
		if (TYPE_DATE == type) return DEFAULT_DATE_FORMAT;
		if (TYPE_INTEGER == type) return DEFAULT_INT_FORMAT;
		if (TYPE_DOUBLE == type) return DEFAULT_DOUBLE_FORMAT;
		if (TYPE_POURCENTAGE == type) return "##0.###";
		return null;
	}
	public static String convertToString(Object value) {
		if (value == null) return "";
		if (value instanceof Date || value instanceof java.sql.Date) return getDateString(DEFAULT_DATE_FORMAT, (Date) value);
		if (value instanceof Timestamp) return getDateString(DEFAULT_DATE_FORMAT, (Date) value);
		if (value instanceof Integer) return getIntegerString(DEFAULT_INT_FORMAT, (Number) value);
		if (value instanceof Long) return getIntegerString(DEFAULT_INT_FORMAT, (Number) value);
		if (value instanceof BigInteger) return getIntegerString(DEFAULT_INT_FORMAT, (Number) value);
		if (value instanceof Double) return getDoubleString(DEFAULT_DOUBLE_FORMAT, (Number) value);
		if (value instanceof BigDecimal) return getDoubleString(DEFAULT_DOUBLE_FORMAT, (Number) value);
		return value.toString();
	}
	public static Date getDate(String format, String dateString) {
		try {
			if (dateString == null || dateString.equals("")) return null;
			return new SimpleDateFormat(format).parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException("Error parsing default null value date.  Format must be '" + format + "'. Cause: " + e);
		}
	}
	public static String getDateString(String format, Date dateObject) {
		try {
			if (dateObject == null) return null;
			return new SimpleDateFormat(format).format(dateObject);
		} catch (Exception e) {
			throw new RuntimeException("Error parsing default null value date.  Format must be '" + format + "'. Cause: " + e);
		}
	}
	public static Double getDouble(String format, String doubleString) {
		try {
			if (doubleString == null || doubleString.equals("")) return null;
			DecimalFormat decimalFormat = new DecimalFormat(format);
			decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.FRANCE));
			Number number = decimalFormat.parse(doubleString.replace('\u0020', '\u00A0'));
			return new Double(number.doubleValue());
		} catch (ParseException e) {
			throw new RuntimeException("Error parsing default null value date.  Format must be '" + format + "'. Cause: " + e);
		}
	}
	public static Double getDouble(String doubleString) {
		return getDouble(DEFAULT_DOUBLE_FORMAT, doubleString);
	}
	public static String getDoubleString(String format, Number doubleObject) {
		try {
			if (doubleObject == null || doubleObject.doubleValue() == 0) return "";
			DecimalFormat decimalFormat = new DecimalFormat(format);
			decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.FRANCE));
			return decimalFormat.format(doubleObject).replace('\u00A0', '\u0020');
		} catch (Exception e) {
			throw new RuntimeException("Error parsing default null value date.  Format must be '" + format + "'. Cause: " + e);
		}
	}
	public static Integer getInteger(String integerString) {
		if (integerString == null || integerString.equals("")) return null;
		return Integer.valueOf(integerString);
	}
	public static String getIntegerString(String format, Number integerObject) {
		try {
			if (integerObject == null || integerObject.intValue() == 0) return "";
			DecimalFormat decimalFormat = new DecimalFormat(format);
			decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.FRANCE));
			return decimalFormat.format(integerObject).replace('\u00A0', '\u0020');
		} catch (Exception e) {
			throw new RuntimeException("Error parsing default null value integer.  Format must be '" + format + "'. Cause: " + e);
		}
	}
	public static String getPourcentageString(String format, Number doubleObject) {
		try {
			if (doubleObject == null || Double.isNaN(doubleObject.doubleValue()) || Double.isInfinite(doubleObject.doubleValue())) return "";
			DecimalFormat decimalFormat = new DecimalFormat(format);
			decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.FRANCE));
			return decimalFormat.format(doubleObject).replace('\u00A0', '\u0020');
		} catch (Exception e) {
			throw new RuntimeException("Error parsing default null value pourcentage.  Format must be '" + format + "'. Cause: " + e);
		}
	}
	public static int compare(String value1, String value2, int type, String format) {
		Object ovalue1 = convert(value1, type, format);
		Object ovalue2 = convert(value2, type, format);
		if (ovalue1 == null) return (ovalue2 == null ? 0 : -1);
		if (ovalue2 == null) return 1;
		if (TYPE_DATE == type) return ((Date) ovalue1).compareTo((Date) ovalue2);
		if (TYPE_INTEGER == type) return ((Integer) ovalue1).compareTo((Integer) ovalue2);
		if (TYPE_DOUBLE == type) return ((Double) ovalue1).compareTo((Double) ovalue2);
		return ((String) ovalue1).compareTo((String) ovalue2);
	}
	public static Double getDoubleMontant(String montant) {
		Double result = null;
		try {
			result = new Double(montant);
		} catch (Exception e1) {
			try {
				if (montant == null || montant.equals("")) result = new Double(0);
				else {
					String temp = montant.replaceAll("\\s+", "").replace(',', '.');
					result = new Double(temp);
				}
			} catch (Exception e2) {
				throw new RuntimeException("Error parsing value Double from String <" + montant + ">. Cause: " + e2);
			}
		}
		return result;
	}
	public static String getFormatedMontant(String montant) {
		String result = "";
		Double mntDbl = getDoubleMontant(montant);
		result = (String) getDoubleString(DEFAULT_DOUBLE_FORMAT, (Number) mntDbl);
		return result;
	}
	public static String cleanEspace(String chaine) { // / supprimer un espace dans une chaine
		String car = "";
		String temp = "";
		if (chaine == null) {
			chaine = "";
		}
		for (int l = 0; l < chaine.length(); l++) {
			car = chaine.substring(l, l + 1);
			if (car.equals("0") || car.equals("1") || car.equals("2") || car.equals("3") || car.equals("4") || car.equals("5") || car.equals("6") || car.equals("7") || car.equals("8") || car.equals("9")
					|| car.equals(".") || car.equals(",") || car.equals("-")) {
				temp = temp + car;
			}
		}
		chaine = temp;
		return chaine;
	}
	public static boolean isFiledType(Class<?> propertyType) {
		if (propertyType == null) return false;
		return propertyType.isPrimitive() || Number.class.isAssignableFrom(propertyType) || java.util.Date.class.isAssignableFrom(propertyType) || Boolean.class.isAssignableFrom(propertyType)
				|| String.class == propertyType || java.util.Calendar.class.isAssignableFrom(propertyType) || FileBean.class.isAssignableFrom(propertyType);
	}
}
