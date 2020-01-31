package tn.com.smartsoft.framework.presentation.view.report;

import net.sf.jasperreports.engine.design.JasperDesign;
import tn.com.smartsoft.commons.report.JasperDesignHelper;
import tn.com.smartsoft.framework.presentation.UIObject;

public abstract class RTemplate implements UIObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JasperDesign jasperDesign;

	public RTemplate(JasperDesign jasperDesign) {
		super();
		this.jasperDesign = jasperDesign;
	}

	public RTemplate(String template) {
		this(JasperDesignHelper.createJasperDesign(template));
	}

	public JasperDesign jasperDesign() {
		return jasperDesign;
	}

	public abstract JasperDesign build();
}
