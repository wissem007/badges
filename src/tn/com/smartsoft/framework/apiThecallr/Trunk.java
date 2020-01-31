package tn.com.smartsoft.framework.apiThecallr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trunk implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date date_creation;
	private String type;
	private String name;
	private Boolean has_did;
	private String username;
	private String hash;
	private List<Did> dids = new ArrayList<Did>();
	
	public void addDid(Did did) {
		dids.add(did);
	}
	
	public Date getDate_creation() {
		return date_creation;
	}
	
	public Date getDateCreationSql() {
		if (date_creation == null)
			return date_creation;
		return new java.sql.Date(date_creation.getTime());
	}
	
	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getHas_did() {
		return has_did;
	}
	
	public void setHas_did(Boolean has_did) {
		this.has_did = has_did;
	}
	
	public String getUsername() {
		return username;
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
	
	public List<Did> getDids() {
		return dids;
	}
	
	public void setDids(List<Did> dids) {
		this.dids = dids;
	}
	
	public String toString() {
		return "Trunk{date_creation=" + date_creation + ", type=" + type + ", name=" + name + ", has_did=" + has_did + ", username=" + username + ", hash=" + hash + ", \ndids=" + dids + "}\n";
	}
}
