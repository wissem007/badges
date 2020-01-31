package tn.com.smartsoft.iap.dictionary.graphique.userType.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.graphique.message.beans.MessageBean;
import java.io.Serializable;

public class UserTypeBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String userTypeId;
	private String messagesId;
	private Boolean isLtr;
	private Boolean isComparable;
	private String patern;
	private MessageBean message;

	public Boolean getIsLtr() {
		return isLtr;
	}

	public void setIsLtr(Boolean isLtr) {
		this.isLtr = isLtr;
	}

	public Boolean getIsComparable() {
		return isComparable;
	}

	public void setIsComparable(Boolean isComparable) {
		this.isComparable = isComparable;
	}

	public UserTypeBean() {
		super();
	}

	public Serializable getId() {
		return this.getUserTypeId();
	}

	public void setId(String id) {
		this.setUserTypeId(id);
	}

	public String getUserTypeId() {
		return this.userTypeId;
	}

	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getMessagesId() {
		return this.messagesId;
	}

	public void setMessagesId(String messagesId) {
		this.messagesId = messagesId;
	}

	public Boolean isLtr() {
		return this.isLtr;
	}

	public void setLtr(Boolean isLtr) {
		this.isLtr = isLtr;
	}

	public Boolean isComparable() {
		return this.isComparable;
	}

	public void setComparable(Boolean isComparable) {
		this.isComparable = isComparable;
	}

	public String getPatern() {
		return this.patern;
	}

	public void setPatern(String patern) {
		this.patern = patern;
	}

	public MessageBean getMessage() {
		return this.message;
	}

	public void setMessage(MessageBean message) {
		this.message = message;
	}
}