package tn.com.smartsoft.iap.dictionary.decoupage.sequences.beans;

import java.io.Serializable;
import tn.com.smartsoft.commons.utils.ValueUtils;

public class SequenceValueBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sequenceValueId;
	private String sequenceId;

	public SequenceValueBeanId() {
		super();
	}

	public SequenceValueBeanId(String sequenceValueId, String sequenceId) {
		super();
		this.sequenceValueId = sequenceValueId;
		this.sequenceId = sequenceId;
	}

	public SequenceValueBeanId(SequenceValueBean sequenceValueBean) {
		super();
		this.setSequenceValueId(sequenceValueBean.getSequenceValueId());
		this.setSequenceId(sequenceValueBean.getSequenceId());
	}

	public void copyValue(SequenceValueBean objectValue) {
		objectValue.setSequenceValueId(this.getSequenceValueId());
		objectValue.setSequenceId(this.getSequenceId());

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

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getSequenceValueId() == null ? 0 : this.getSequenceValueId().hashCode());
		result = 37 * result + (this.getSequenceId() == null ? 0 : this.getSequenceId().hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SequenceValueBeanId))
			return false;
		SequenceValueBeanId castOther = (SequenceValueBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getSequenceValueId(), castOther.getSequenceValueId());
		result = result && ValueUtils.equals(this.getSequenceId(), castOther.getSequenceId());
		return result;
	}
}