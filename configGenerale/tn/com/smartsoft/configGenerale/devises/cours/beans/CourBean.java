package tn.com.smartsoft.configGenerale.devises.cours.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import java.io.Serializable;
import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;
import java.util.Date;

public class CourBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long coursId;
	private Long deviseId;
	private Date dateCours;
	private Date dateCoursFin;
	private Double cours;
	private DeviseBean devise;
	

	public CourBean() {
		super();
	}

	public Serializable getId() {
		return this.getCoursId();
	}

	public void setId(Long id) {
		this.setCoursId(id);
	}

	public Long getCoursId() {
		return this.coursId;
	}

	public void setCoursId(Long coursId) {
		this.coursId = coursId;
	}

	public Long getDeviseId() {
		return this.deviseId;
	}

	public void setDeviseId(Long deviseId) {
		this.deviseId = deviseId;
	}

	public Date getDateCours() {
		return this.dateCours;
	}

	public void setDateCours(Date dateCours) {
		this.dateCours = dateCours;
	}

	public DeviseBean getDevise() {
		return this.devise;
	}

	public void setDevise(DeviseBean devise) {
		this.devise = devise;
	}

	public Double getCours() {
		return cours;
	}

	public void setCours(Double cours) {
		this.cours = cours;
	}

	public Date getDateCoursFin() {
		return dateCoursFin;
	}

	public void setDateCoursFin(Date dateCoursFin) {
		this.dateCoursFin = dateCoursFin;
	}
}