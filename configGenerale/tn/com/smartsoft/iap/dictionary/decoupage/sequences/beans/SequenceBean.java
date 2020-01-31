package tn.com.smartsoft.iap.dictionary.decoupage.sequences.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import java.io.Serializable;

public class SequenceBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String sequenceId;
	private Long startNumber;
	private String valueExpression;
	private String help;
	private Long lengthNumber;
	private String valueGroupExpression;

	public SequenceBean() {
		super();
	}

	public Serializable getId() {
		return this.getSequenceId();
	}

	public void setId(String id) {
		this.setSequenceId(id);
	}

	public String getValueGroupExpression() {
		return valueGroupExpression;
	}

	public void setValueGroupExpression(String valueGroupExpression) {
		this.valueGroupExpression = valueGroupExpression;
	}

	public Long getLengthNumber() {
		return lengthNumber;
	}

	public void setLengthNumber(Long lengthNumber) {
		this.lengthNumber = lengthNumber;
	}

	public String getSequenceId() {
		return this.sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Long getStartNumber() {
		return this.startNumber;
	}

	public void setStartNumber(Long startNumber) {
		this.startNumber = startNumber;
	}

	public String getValueExpression() {
		return valueExpression;
	}

	public void setValueExpression(String valueExpression) {
		this.valueExpression = valueExpression;
	}

	public String getHelp() {
		return this.help;
	}

	public void setHelp(String help) {
		this.help = help;
	}
}