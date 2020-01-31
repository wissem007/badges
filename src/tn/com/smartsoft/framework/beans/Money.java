package tn.com.smartsoft.framework.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;

public class Money implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal montant;
	private DeviseBean devise;

	public Money(DeviseBean devise, double montant) {
		this.devise = devise;
		this.montant = setScale(new BigDecimal(montant));
	}

	public Money(DeviseBean devise, String montant) {
		this.devise = devise;
		if (montant == null || "".equals(montant.trim()))
			this.montant = null;
		else
			this.montant = setScale(new BigDecimal(montant));
	}

	public Money(String montant) {
		devise = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getApplicationBean().getDeviseBase();
		if (montant == null || "".equals(montant.trim()))
			this.montant = null;
		else
			this.montant = setScale(new BigDecimal(montant));
	}

	public Money(DeviseBean devise, BigDecimal montant) {
		this.devise = devise;
		this.montant = setScale(montant);
	}

	public Money(BigDecimal montant) {
		devise = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getApplicationBean().getDeviseBase();
		this.montant = montant;
	}

	public Money() {
		devise = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getApplicationBean().getDeviseBase();
		montant = setScale(new BigDecimal(0));
	}

	public BigDecimal getMontant() {
		return montant;
	}

	public double getMontantDoubleValue() {
		return montant.doubleValue();
	}

	public DeviseBean getDevise() {
		return devise;
	}

	private boolean isDeviseDifferent(Money money) {
		return !devise.getId().equals(money.getDevise().getId());
	}

	public Money add(Money money) {
		if (null != money && isDeviseDifferent(money))
			throw new TechnicalException("The desired operation on money class is not possible because the two money objects are of different currencies.");
		else
			return money != null && money.getMontant() != null && money.getDevise() != null ? new Money(devise, setScale(montant.add(money.getMontant()))) : this;
	}

	public Money subtract(Money money) {
		if (null != money && isDeviseDifferent(money))
			throw new TechnicalException("The desired operation on money class is not possible because the two money objects are of different currencies.");
		else
			return money != null && money.getMontant() != null && money.getDevise() != null ? new Money(devise, setScale(montant.subtract(money.getMontant()))) : this;
	}

	public Money multiply(Money money) {
		if (null != money && isDeviseDifferent(money))
			throw new TechnicalException("The desired operation on money class is not possible because the two money objects are of different currencies.");
		else
			return money != null && money.getMontant() != null && money.getDevise() != null ? new Money(devise, setScale(montant.multiply(money.getMontant()))) : this;
	}

	public Money multiply(Double factor) {
		if (factor != null)
			return new Money(devise, setScale(montant.multiply(new BigDecimal(factor.doubleValue()))));
		else
			throw new TechnicalException("The desired operation on money class is not possible because the two money objects are of different currencies.");
	}

	public Money divide(Money money) {
		if (null != money && isDeviseDifferent(money))
			throw new TechnicalException("The desired operation on money class is not possible because the two money objects are of different currencies.");
		else
			return money != null && money.getMontant() != null && money.getDevise() != null ? new Money(devise, setScale(montant.divide(money.getMontant()))) : this;
	}

	public Money negate() {
		BigDecimal tempAmnt = montant.negate();
		return new Money(devise, setScale(tempAmnt));
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Money))
			return false;
		if (obj == this) {
			return true;
		} else {
			Money money = (Money) obj;
			return devise.equals(money.getDevise()) && montant.compareTo(money.getMontant()) == 0;
		}
	}

	public int hashCode() {
		if (montant == null || montant == null)
			return System.identityHashCode(null);
		else
			return devise.getDeviseId().intValue() * 100 + montant.intValue();
	}

	private BigDecimal setScale(BigDecimal montant) {
		if (null != montant && null != devise)
			montant = montant.setScale(devise.getNombreDecimales().shortValue(), RoundingMode.HALF_UP);
		return montant;
	}

	public static Money round(Money money) {
		if (null != money) {
			BigDecimal roundingAmount = new BigDecimal(money.getDevise().getArrondissementMontant().doubleValue());
			BigDecimal nearestFactor = money.getMontant().divide(roundingAmount);
			if (money.getDevise().getArrondissementMode().equals(Short.valueOf((short) 1)))
				nearestFactor = nearestFactor.setScale(0, RoundingMode.CEILING);
			else if (money.getDevise().getArrondissementMode().equals(Short.valueOf((short) 2)))
				nearestFactor = nearestFactor.setScale(0, RoundingMode.FLOOR);
			BigDecimal roundedAmount = nearestFactor.multiply(roundingAmount);
			return new Money(money.getDevise(), roundedAmount);
		} else {
			return money;
		}
	}

	public String toString() {
		return "montant :" + montant.toString() + " devise :" + devise.getLibelle();
	}
}
