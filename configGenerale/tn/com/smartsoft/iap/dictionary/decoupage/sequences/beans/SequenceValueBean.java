package tn.com.smartsoft.iap.dictionary.decoupage.sequences.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import java.io.Serializable;

public class SequenceValueBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String sequenceValueId;
	private String sequenceId;
	private Long lastValue;

	public SequenceValueBean() {
		super();
	}

	public Serializable getId() {
		return new SequenceValueBeanId(this);
	}

	public void setId(SequenceValueBeanId id) {
		id.copyValue(this);
	}

	public String getSequenceValueId() {
		return this.sequenceValueId;
	}

	public void setSequenceValueId(String sequenceValueId) {
		this.sequenceValueId = sequenceValueId;
	}

	public String getSequenceId() {
		return this.sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Long getLastValue() {
		return this.lastValue;
	}

	public void setLastValue(Long lastValue) {
		this.lastValue = lastValue;
	}
}