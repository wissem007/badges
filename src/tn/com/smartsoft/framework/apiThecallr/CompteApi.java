package tn.com.smartsoft.framework.apiThecallr;

import java.io.Serializable;

public class CompteApi implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String hash;
	private Boolean archived;
	private Double credit;
	private Boolean disabled;
	
	public String getUsername() {
		return username;
	}
	
	public String getUsernameMin() {
		if (this.username != null)
			return this.username.toLowerCase();
		return null;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public Boolean getArchived() {
		return archived;
	}
	
	public void setArchived(Boolean archived) {
		this.archived = archived;
	}
	
	public Double getCredit() {
		return credit;
	}
	
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	
	public Boolean getDisabled() {
		return disabled;
	}
	
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	public String toString() {
		return "CompteApi [username=" + username + ", hash=" + hash + ", archived=" + archived + ", credit=" + credit + ", disabled=" + disabled + "]\n";
	}
	
}
