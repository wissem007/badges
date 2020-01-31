package tn.com.smartsoft.commons.data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import tn.com.smartsoft.commons.utils.DateUtils;

public class DateBorne implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Timestamp from;
	private Timestamp to;
	
	public DateBorne(Timestamp from, Timestamp to) {
		this.from = from;
		this.to = to;
	}
	
	public DateBorne(Date from, Date to) {
		if (from != null) {
			this.from = DateUtils.getTimestamp(from);
		}
		if (to != null) {
			this.to = DateUtils.getTimestamp(to);
		}
	}
	
	public Timestamp getFrom() {
		return from;
	}
	
	public void setFrom(Timestamp from) {
		this.from = from;
	}
	
	public Timestamp getTo() {
		return to;
	}
	
	public void setTo(Timestamp to) {
		this.to = to;
	}
	
	public int hashCode() {
		int result = 1;
		result = 31 * result + (from != null ? from.hashCode() : 0);
		result = 31 * result + (to != null ? to.hashCode() : 0);
		return result;
	}
	
	public boolean isValid() {
		return DateUtils.isInferieurOuEgale(getFrom(), getTo());
	}
	
	public boolean isIn(DateBorne date) {
		Timestamp toDateThis = getTo() != null ? getTo() : DateUtils.getCourantTimestamp();
		Timestamp toDate = date.getTo() != null ? date.getTo() : DateUtils.getCourantTimestamp();
		return DateUtils.isInferieurOuEgale(toDateThis, toDate) && DateUtils.isInferieurOuEgale(date.getFrom(), getFrom());
	}
	
	public boolean isIn(Date date) {
		Timestamp toDateThis = getTo() != null ? getTo() : DateUtils.getCourantTimestamp();
		return DateUtils.isInferieurOuEgale(toDateThis, date) && DateUtils.isInferieurOuEgale(date, getFrom());
	}
	
	public static DateBorne getBorneDateTime(java.util.Date d, int n) {
		Timestamp to = DateUtils.getTimestampEndDate(DateUtils.soustraitJours(d, 1));
		Timestamp from = DateUtils.getTimestampStartDate(DateUtils.soustraitJours(to, n - 1));
		return new DateBorne(from, to);
	}
	
	public static DateBorne getBorneDateTime(int n) {
		return getBorneDateTime(DateUtils.getCourantDate(), n);
	}
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DateBorne other = (DateBorne) obj;
		if (from == null) {
			if (other.from != null) {
				return false;
			}
		} else if (!from.equals(other.from)) {
			return false;
		}
		if (to == null) {
			if (other.to != null) {
				return false;
			}
		} else if (!to.equals(other.to)) {
			return false;
		}
		return true;
	}
	
}
