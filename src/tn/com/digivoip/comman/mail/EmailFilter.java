package tn.com.digivoip.comman.mail;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.NotTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.RecipientStringTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

public class EmailFilter{

	/** Creates new Email filter. */
	public static EmailFilter filter() {
		return new EmailFilter();
	}

	boolean					operatorAnd	= true;
	boolean					nextIsNot;
	protected SearchTerm	searchTerm;

	/** Changes concatenation mode to AND. */
	public EmailFilter and() {
		this.operatorAnd = true;
		return this;
	}
	/** Defines AND group of filters. */
	public EmailFilter and(EmailFilter[] emailFilters) {
		SearchTerm[] searchTerms = new SearchTerm[emailFilters.length];
		for (int i = 0; i < emailFilters.length; i++) {
			searchTerms[i] = emailFilters[i].searchTerm;
		}
		concat(new AndTerm(searchTerms));
		return this;
	}
	protected void and(SearchTerm searchTerm) {
		if (this.searchTerm == null) {
			this.searchTerm = searchTerm;
			return;
		}
		this.searchTerm = new AndTerm(this.searchTerm, searchTerm);
	}
	/** Defines filter for BCC field. */
	public EmailFilter bcc(String bccAddress) {
		SearchTerm toTerm = new RecipientStringTerm(Message.RecipientType.BCC, bccAddress);
		concat(toTerm);
		return this;
	}
	/** Defines filter for CC field. */
	public EmailFilter cc(String ccAddress) {
		SearchTerm toTerm = new RecipientStringTerm(Message.RecipientType.CC, ccAddress);
		concat(toTerm);
		return this;
	}
	/** Concatenates last search term with new one. */
	protected void concat(SearchTerm searchTerm) {
		if (nextIsNot) {
			searchTerm = new NotTerm(searchTerm);
			nextIsNot = false;
		}
		if (operatorAnd) {
			and(searchTerm);
		} else {
			or(searchTerm);
		}
	}
	/** Defines filter for single flag. */
	public EmailFilter flag(Flags.Flag flag, boolean value) {
		Flags flags = new Flags();
		flags.add(flag);
		return flags(flags, value);
	}
	/** Defines filter for many flags at once. */
	public EmailFilter flags(Flags flags, boolean value) {
		SearchTerm flagTerm = new FlagTerm(flags, value);
		concat(flagTerm);
		return this;
	}
	/** Defines filter for FROM field. */
	public EmailFilter from(String fromAddress) {
		SearchTerm fromTerm = new FromStringTerm(fromAddress);
		concat(fromTerm);
		return this;
	}
	// ---------------------------------------------------------------- boolean
	/** Returns search term. */
	public SearchTerm getSearchTerm() {
		return searchTerm;
	}
	/** Defines filter for message id. */
	public EmailFilter messageId(int messageId) {
		return messageId(String.valueOf(messageId));
	}
	/** Defines filter for message id. */
	public EmailFilter messageId(String messageId) {
		SearchTerm msgIdTerm = new MessageIDTerm(messageId);
		concat(msgIdTerm);
		return this;
	}
	/** Marks next condition to be NOT. */
	public EmailFilter not() {
		this.nextIsNot = true;
		return this;
	}
	/** Appends single filter as NOT. */
	public EmailFilter not(EmailFilter emailFilter) {
		SearchTerm searchTerm = new NotTerm(emailFilter.searchTerm);
		concat(searchTerm);
		return this;
	}
	/** Changes concatenation mode to OR. */
	public EmailFilter or() {
		this.operatorAnd = false;
		return this;
	}
	// ---------------------------------------------------------------- concat
	/** Defines OR group of filters. */
	public EmailFilter or(EmailFilter[] emailFilters) {
		SearchTerm[] searchTerms = new SearchTerm[emailFilters.length];
		for (int i = 0; i < emailFilters.length; i++) {
			searchTerms[i] = emailFilters[i].searchTerm;
		}
		concat(new OrTerm(searchTerms));
		return this;
	}
	protected void or(SearchTerm searchTerm) {
		if (this.searchTerm == null) {
			this.searchTerm = searchTerm;
			return;
		}
		this.searchTerm = new OrTerm(this.searchTerm, searchTerm);
	}
	/** Defines filter for SUBJECT field. */
	public EmailFilter subject(String subject) {
		SearchTerm subjectTerm = new SubjectTerm(subject);
		concat(subjectTerm);
		return this;
	}
	// ---------------------------------------------------------------- term
	/** Defines filter for TO field. */
	public EmailFilter to(String toAddress) {
		SearchTerm toTerm = new RecipientStringTerm(Message.RecipientType.TO, toAddress);
		concat(toTerm);
		return this;
	}
}