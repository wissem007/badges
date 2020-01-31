package tn.com.smartsoft.framework.apiThecallr.mig;

import java.io.Serializable;
import java.sql.Timestamp;

import tn.com.smartsoft.commons.utils.NumberUtils;

public class TransactionBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long transactionId = 0L;
	public Double amount = 0.0;
	public Double tauxChange;
	public Timestamp dateTransaction;
	public Long status;
	public String refPay = "";
	public String description = "";
	public Long payMode = 0L;
	
	public TransactionBean() {
		super();
	}

	public TransactionBean(Long transactionId, Double amount, Double tauxChange, Timestamp dateTransaction, Long status, String refPay, String description, Long payMode) {
		super();
		this.transactionId = transactionId;
		this.amount = amount;
		this.tauxChange = tauxChange;
		this.dateTransaction = dateTransaction;
		this.status = status;
		this.refPay = refPay;
		this.description = description;
		this.payMode = payMode;
	}
	
	public Long getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getTauxChange() {
		return tauxChange;
	}
	
	public void setTauxChange(Double tauxChange) {
		this.tauxChange = tauxChange;
	}
	
	public Timestamp getDateTransaction() {
		return dateTransaction;
	}
	
	public void setDateTransaction(Timestamp dateTransaction) {
		this.dateTransaction = dateTransaction;
	}
	
	public Long getStatus() {
		return status;
	}
	
	public void setStatus(Long status) {
		this.status = status;
	}
	
	public String getRefPay() {
		return refPay;
	}
	
	public void setRefPay(String refPay) {
		this.refPay = refPay;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getPayMode() {
		return payMode;
	}
	
	public void setPayMode(Long payMode) {
		this.payMode = payMode;
	}
	
	public Double getAmountTND() {
		return NumberUtils.multiplier(amount, tauxChange);
	}
	
	public void setAmountTND(Double amountTND) {
	}
	
}
