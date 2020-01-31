package tn.com.smartsoft.framework.presentation.formater;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.beans.Money;
import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;

public class CurrencyFormater extends Formater {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object getAsObject(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return null;
		try {
			DecimalFormat decimalformatter = getDecimalFormat(new Money().getDevise());
			Number parsedNumber = decimalformatter.parse(target.toString().trim());
			Money value = new Money(parsedNumber.toString());
			if (targetClass == null || targetClass.equals(Money.class))
				return value;
			else if (targetClass.equals(BigDecimal.class))
				return new BigDecimal(value.getMontantDoubleValue());
			else if (targetClass.equals(Double.class))
				return new Double(value.getMontantDoubleValue());
			else if (targetClass.equals(Float.class))
				return new Float(value.getMontantDoubleValue());
			else if (targetClass.equals(Integer.class))
				return new Integer((int) value.getMontantDoubleValue());
			else
				return new String(value.toString());
		} catch (NumberFormatException e) {
			log.error(e);
		} catch (ParseException e) {
			log.error(e);
		}
		return null;
	}

	public String getAsString(Object target) {
		if (target == null)
			return "";
		try {
			Money moneyValue;
			if (!(target instanceof Money)) {
				moneyValue = new Money(target.toString());
			} else {
				moneyValue = (Money) target;
			}
			BigDecimal value = new BigDecimal(moneyValue.getMontantDoubleValue());
			String stringValue = this.getDecimalFormat(moneyValue.getDevise()).format(value.doubleValue());
			return stringValue;
		} catch (Exception e) {
			log.error(e);
		}
		return "";
	}

	public boolean validate(Object target, Class<?> targetClass) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return true;
		try {
			DecimalFormat decimalformatter = getDecimalFormat(new Money().getDevise());
			decimalformatter.parse(target.toString().trim());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private DecimalFormat getDecimalFormat(DeviseBean devise) {
		String format = "#,##0.";
		int nombreDecimales = devise.getNombreDecimales().intValue();
		for (int i = 0; i < nombreDecimales; i++) {
			format = format + "0";
		}
		DecimalFormat decimalformatter = new DecimalFormat(format);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.FRANCE);
		dfs.setGroupingSeparator(' ');
		decimalformatter.setDecimalFormatSymbols(dfs);
		return decimalformatter;
	}
	public static void main(String[] args) {
		DecimalFormat decimalformatter = new DecimalFormat("#,##0.0000");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.FRANCE);
		dfs.setGroupingSeparator(' ');
		decimalformatter.setDecimalFormatSymbols(dfs);
	}
}
