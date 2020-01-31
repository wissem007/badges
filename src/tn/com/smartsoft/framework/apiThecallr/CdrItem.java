package tn.com.smartsoft.framework.apiThecallr;

import java.io.Serializable;
import java.util.Date;

public class CdrItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long callid;
	private Long callid_in;
	private String customer_field;
	private String gateway_codec;
	private Double billing_customer_debit_eur;
	private String scenario_name;
	private String hangupcause;
	private String number_type;
	private Double billing_customer_credit_eur;
	private Date hangup;
	private Long duration;
	private String number;
	private String scenario_hash;
	private String cli_name;
	private String dialstatus;
	private String cli_number;
	private String number_country_code;
	private String billing_customer_cost_label;
	private String hangupsource;
	private String package_name;
	private String allowed_passed_screen;
	private Long duration_answered;
	private Long duration_billed;
	private Date start;
	private Date answer;
	
	public void setCallid(Long callid) {
		this.callid = callid;
	}

	public void setCallid_in(Long callid_in) {
		this.callid_in = callid_in;
	}

	public void setCustomer_field(String customer_field) {
		this.customer_field = customer_field;
	}

	public void setGateway_codec(String gateway_codec) {
		this.gateway_codec = gateway_codec;
	}

	public void setBilling_customer_debit_eur(Double billing_customer_debit_eur) {
		this.billing_customer_debit_eur = billing_customer_debit_eur;
	}

	public void setScenario_name(String scenario_name) {
		this.scenario_name = scenario_name;
	}

	public void setHangupcause(String hangupcause) {
		this.hangupcause = hangupcause;
	}

	public void setNumber_type(String number_type) {
		this.number_type = number_type;
	}

	public void setBilling_customer_credit_eur(Double billing_customer_credit_eur) {
		this.billing_customer_credit_eur = billing_customer_credit_eur;
	}

	public void setHangup(Date hangup) {
		this.hangup = hangup;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setScenario_hash(String scenario_hash) {
		this.scenario_hash = scenario_hash;
	}

	public void setCli_name(String cli_name) {
		this.cli_name = cli_name;
	}

	public void setDialstatus(String dialstatus) {
		this.dialstatus = dialstatus;
	}

	public void setCli_number(String cli_number) {
		this.cli_number = cli_number;
	}

	public void setNumber_country_code(String number_country_code) {
		this.number_country_code = number_country_code;
	}

	public void setBilling_customer_cost_label(String billing_customer_cost_label) {
		this.billing_customer_cost_label = billing_customer_cost_label;
	}

	public void setHangupsource(String hangupsource) {
		this.hangupsource = hangupsource;
	}

	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}

	public void setAllowed_passed_screen(String allowed_passed_screen) {
		this.allowed_passed_screen = allowed_passed_screen;
	}

	public void setDuration_answered(Long duration_answered) {
		this.duration_answered = duration_answered;
	}

	public void setDuration_billed(Long duration_billed) {
		this.duration_billed = duration_billed;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setAnswer(Date answer) {
		this.answer = answer;
	}

	public Long getCallid() {
		return callid;
	}
	
	public Long getCallid_in() {
		return callid_in;
	}
	
	public String getCustomer_field() {
		return customer_field;
	}
	
	public String getGateway_codec() {
		return gateway_codec;
	}
	
	public Double getBilling_customer_debit_eur() {
		return billing_customer_debit_eur;
	}
	
	public String getScenario_name() {
		return scenario_name;
	}
	
	public String getHangupcause() {
		return hangupcause;
	}
	
	public String getNumber_type() {
		return number_type;
	}
	
	public Double getBilling_customer_credit_eur() {
		return billing_customer_credit_eur;
	}
	
	public Date getHangup() {
		return hangup;
	}
	
	public Long getDuration() {
		return duration;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getScenario_hash() {
		return scenario_hash;
	}
	
	public String getCli_name() {
		return cli_name;
	}
	
	public String getDialstatus() {
		return dialstatus;
	}
	
	public String getCli_number() {
		return cli_number;
	}
	
	public String getNumber_country_code() {
		return number_country_code;
	}
	
	public String getBilling_customer_cost_label() {
		return billing_customer_cost_label;
	}
	
	public String getHangupsource() {
		return hangupsource;
	}
	
	public String getPackage_name() {
		return package_name;
	}
	
	public String getAllowed_passed_screen() {
		return allowed_passed_screen;
	}
	
	public Long getDuration_answered() {
		return duration_answered;
	}
	
	public Long getDuration_billed() {
		return duration_billed;
	}
	
	public Date getStart() {
		return start;
	}
	
	public Date getAnswer() {
		return answer;
	}
	
	public String toString() {
		return " [callid=" + callid + ", callid_in=" + callid_in + ", customer_field=" + customer_field + ", gateway_codec=" + gateway_codec + ", billing_customer_debit_eur="
				+ billing_customer_debit_eur + ", scenario_name=" + scenario_name + ", hangupcause=" + hangupcause + ", number_type=" + number_type + ", billing_customer_credit_eur="
				+ billing_customer_credit_eur + ", hangup=" + hangup + ", duration=" + duration + ", number=" + number + ", scenario_hash=" + scenario_hash + ", cli_name=" + cli_name
				+ ", dialstatus=" + dialstatus + ", cli_number=" + cli_number + ", number_country_code=" + number_country_code + ", billing_customer_cost_label=" + billing_customer_cost_label
				+ ", hangupsource=" + hangupsource + ", package_name=" + package_name + ", allowed_passed_screen=" + allowed_passed_screen + ", duration_answered=" + duration_answered
				+ ", duration_billed=" + duration_billed + ", start=" + start + ", answer=" + answer + "]\n";
	}
	
}
