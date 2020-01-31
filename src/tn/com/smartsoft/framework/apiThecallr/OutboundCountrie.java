package tn.com.smartsoft.framework.apiThecallr;

import java.io.Serializable;

public class OutboundCountrie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long duration;
	private String country_code;
	private Long aloc;
	private Long count;
	private Double credit;
	private Double debit;
	
	public Long getDuration() {
		return duration;
	}
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public String getCountry_code() {
		return country_code;
	}
	
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
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
	 
	public String toString() {
		return "[duration=" + duration + ", country_code=" + country_code + ", aloc=" + aloc + ", count=" + count + ", credit=" + credit + ", debit=" + debit + "]\n";
	}
	
}
