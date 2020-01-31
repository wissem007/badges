package tn.com.smartsoft.framework.apiThecallr;

import java.io.Serializable;
import java.sql.Date;

import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.commons.utils.NumberUtils;

public class Inbound implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long duration;
	private Long aloc;
	private Long count;
	private String billing_label;
	private String intl;// non pour getOutboundDestinations,CallsHistory
	private Double credit;
	private Double debit;
	private String hash;// non pour getOutboundDestinations ,CallsHistory
	
	private Long id;
	private Date date;
	
	public Inbound() {
		super();
	}
	
	public Long getDuration() {
		return duration;
	}
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public Long getAloc() {
		return aloc;
	}
	
	public void setAloc(Long aloc) {
		this.aloc = aloc;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
	
	public String getBilling_label() {
		return billing_label;
	}
	
	public void setBilling_label(String billing_label) {
		this.billing_label = billing_label;
	}
	
	public String getIntl() {
		return intl;
	}
	
	public void setIntl(String intl) {
		this.intl = intl;
	}
	
	public Double getCredit() {
		return credit;
	}
	
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	
	public Double getDebit() {
		return debit;
	}
	
	public void setDebit(Double debit) {
		this.debit = debit;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Double getPrixUniteInEuro() {
		return NumberUtils.convertInEuro(NumberUtils.diviserNoArrondi(getDebit(), DateUtils.convertInSecond(getDuration())));
	}
	
}
