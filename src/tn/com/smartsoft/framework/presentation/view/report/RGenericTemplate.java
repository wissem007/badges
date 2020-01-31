package tn.com.smartsoft.framework.presentation.view.report;

import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JasperDesign;
import tn.com.smartsoft.commons.report.JasperDesignHelper;
import tn.com.smartsoft.commons.report.component.RColumnField;
import tn.com.smartsoft.commons.report.component.RColumnPanel;
import tn.com.smartsoft.commons.report.component.RCompositeFieldText;
import tn.com.smartsoft.commons.report.component.RDetailPanel;

public class RGenericTemplate extends RTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DETAIL_BAR_ID = "detailBar";
	private static final String DYNAJASPER_TEMPLATE_JRXML = "tn/com/smartsoft/framework/presentation/view/report/genericTemplate2.jrxml";
	private RColumnPanel columnPanel = new RColumnPanel();
	private RDetailPanel detailPanel = new RDetailPanel();

	public RGenericTemplate() {
		super(DYNAJASPER_TEMPLATE_JRXML);
	}

	public RGenericTemplate(String template) {
		super(template);
	}

	public RCompositeFieldText addCompositeFieldText(RCompositeFieldText fieldText) {
		return detailPanel.addCompositeFieldText(fieldText);
	}

	public RCompositeFieldText addCompositeFieldText(int xpos, int ypos) {
		return addCompositeFieldText(new RCompositeFieldText(xpos, ypos));

	}

	public RCompositeFieldText addCompositeFieldText() {
		return addCompositeFieldText(new RCompositeFieldText());

	}

	public RColumnField addColumnField(RColumnField columnField) {
		return columnPanel.addColumnField(columnField);
	}

	public RColumnField addColumnField(String title, String property, String fieldClassName, int width) {
		return columnPanel.addColumnField(title, property, fieldClassName, width);
	}

	public RColumnField addColumnField(String title, String property, Class<?> fieldClassName, int width) {
		return columnPanel.addColumnField(new RColumnField(title, property, fieldClassName.getName(), width));
	}

	public JRDesignBand getTitle() {
		JRDesignBand titleBand = (JRDesignBand) jasperDesign.getTitle();
		return titleBand;
	}

	public JasperDesign build() {
		jasperDesign.addImport(RValue.class.getName());
		columnPanel.setStyleById(jasperDesign, "Detail");
		columnPanel.setStyleHeadById(jasperDesign, "Column header");
		detailPanel.setStyleById(jasperDesign, "FilterValue");
		detailPanel.setStyleLabelById(jasperDesign, "FilterLable");
		detailPanel.addToJasperDesign(jasperDesign, getTitle());
		columnPanel.addToJasperDesign(jasperDesign, getHead(), getColumnFrame());
		return jasperDesign;
	}

	public JRDesignBand getHead() {
		JRDesignBand head = (JRDesignBand) jasperDesign.getColumnHeader();
		if (head == null) {
			head = new JRDesignBand();
		}
		if (head.getHeight() <= 19)
			head.setHeight(19);
		return head;
	}

	public JRDesignFrame getColumnFrame() {
		return (JRDesignFrame) JasperDesignHelper.getBandFromSection(jasperDesign.getDetailSection()).getElementByKey(DETAIL_BAR_ID);
	}
}
