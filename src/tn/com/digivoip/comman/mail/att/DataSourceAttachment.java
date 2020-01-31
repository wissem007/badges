package tn.com.digivoip.comman.mail.att;

import javax.activation.DataSource;
import tn.com.digivoip.comman.mail.EmailAttachment;

/** Generic <code>DataSource</code> adapter for attachments. */
public class DataSourceAttachment extends EmailAttachment{

	protected final DataSource	dataSource;

	public DataSourceAttachment(DataSource dataSource, String name, String contentId) {
		super(name, contentId);
		this.dataSource = dataSource;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
}
