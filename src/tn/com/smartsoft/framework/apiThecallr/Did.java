package tn.com.smartsoft.framework.apiThecallr;

import java.io.Serializable;

public class Did implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String intl_number;
	private String hash;
	private String type;
	private String classe;
	private Boolean contract_auto_renew;
	private String local_number;
	private String country_code;
	
	public String getIntl_number() {
		return intl_number;
	}
	
	public void setIntl_number(String intl_number) {
		this.intl_number = intl_number;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getClasse() {
		return classe;
	}
	
	public void setClasse(String classe) {
		this.classe = classe;
	}
	
	public Boolean getContract_auto_renew() {
		return contract_auto_renew;
	}
	
	public void setContract_auto_renew(Boolean contract_auto_renew) {
		this.contract_auto_renew = contract_auto_renew;
	}
	
	public String getLocal_number() {
		return local_number;
	}
	
	public void setLocal_number(String local_number) {
		this.local_number = local_number;
	}
	
	public String getCountry_code() {
		return country_code;
	}
	
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	
	public String toString() {
		return "{intl_number=" + intl_number + ", hash=" + hash + ", type=" + type + ", classe=" + classe + ", contract_auto_renew=" + contract_auto_renew + ", local_number=" + local_number
				+ ", country_code=" + country_code + "}\n";
	}
	
}
