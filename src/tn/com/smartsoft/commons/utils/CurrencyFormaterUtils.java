package tn.com.smartsoft.commons.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import tn.com.smartsoft.commons.log.Logger;

public class CurrencyFormaterUtils{
	
	protected Logger	log	= Logger.getLogger(getClass());
	
	public static Number getAsNumber(String target, String format, int scale) throws FormatException {
		if (isEmptyString(target)) return null;
		try {
			DecimalFormat decimalformatter = new DecimalFormat(format);
			decimalformatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.FRANCE));
			Number parsedNumber = decimalformatter.parse(target.trim());
			BigDecimal value = new BigDecimal(parsedNumber.toString());
			value.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
			return value;
		} catch (NumberFormatException e) {
			throw new FormatException(e);
		} catch (ParseException e) {
			throw new FormatException(e);
		}
	}
	public static String getAsString(Object target, String format, int scale) throws FormatException {
		if (target == null || ((Number) target).equals(new Double(0))) return "";
		try {
			Number parsedNumber = (Number) target;
			BigDecimal value = new BigDecimal(parsedNumber.doubleValue());
			value.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
			DecimalFormat decimalformatter = new DecimalFormat(format);
			decimalformatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.FRANCE));
			String stringValue = decimalformatter.format(value.doubleValue());
			return stringValue;
		} catch (NumberFormatException e) {
			throw new FormatException(e);
		}
	}
	public static boolean isEmptyString(String s) {
		return s == null || s.trim().length() == 0;
	}
	public static void main(String[] args) throws Exception {
		DecimalFormat decimalformatter = new DecimalFormat("#.00");
		decimalformatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
		String stringValue = decimalformatter.format(new Double(1222.053).doubleValue());
		BigDecimal value = new BigDecimal("12121.84");
		value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		System.out.println(stringValue);
	}
}
