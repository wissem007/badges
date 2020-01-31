package tn.com.smartsoft.framework.presentation.view.report;

public class RTemplatetModel extends ReportModel {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private RTemplate			template;
	
	public RTemplatetModel(RTemplate template, String propertyDataSource, String fileName) {
		super(propertyDataSource, fileName, (String) null);
		this.template = template;
	}
	
	public RTemplate getTemplate() {
		return template;
	}
}
