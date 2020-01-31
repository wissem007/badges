package tn.com.smartsoft.commons.report.component;

import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import tn.com.smartsoft.commons.report.JasperDesignHelper;
import tn.com.smartsoft.commons.report.utils.LayoutUtils;

public class RFieldText extends RConstant implements Cloneable {

	private static final long serialVersionUID = 1L;
	private String label;
	private String labelSeparator = ":";
	private int xpos = 0;
	private int ypos = 0;
	private int width = 100;
	private int height = 20;
	private RHorizontalAlign labelAlign = RHorizontalAlign.LEFT;
	private int labelWidth = 100;
	private String fieldClassName = String.class.getName();
	private String property;
	private JRStyle style;
	private JRStyle styleLabel;
	private String description;
	protected Boolean hideLabel = Boolean.FALSE;
	private float ajustWhith = 1;

	public Boolean getHideLabel() {
		return hideLabel;
	}

	public void setHideLabel(Boolean hideLabel) {
		this.hideLabel = hideLabel;
	}

	public float getAjustWhith() {
		return ajustWhith;
	}

	public void setAjustWhith(float ajustWhith) {
		this.ajustWhith = ajustWhith;
	}

	public RFieldText() {
		super();
	}

	public RFieldText(String property, String label, int width, int labelWidth, String fieldClassName) {
		super();
		this.label = label;
		this.width = width;
		this.labelWidth = labelWidth;
		this.fieldClassName = fieldClassName;
		this.property = property;
	}

	public String getLabelSeparator() {
		return labelSeparator;
	}

	public void setLabelSeparator(String labelSeparator) {
		this.labelSeparator = labelSeparator;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getXpos() {
		return xpos;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public RHorizontalAlign getLabelAlign() {
		return labelAlign;
	}

	public void setLabelAlign(RHorizontalAlign labelAlign) {
		this.labelAlign = labelAlign;
	}

	public int getLabelWidth() {
		return labelWidth;
	}

	public void setLabelWidth(int labelWidth) {
		this.labelWidth = labelWidth;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public JRStyle getStyle() {
		return style;
	}

	public void setStyle(JRStyle style) {
		this.style = style;
	}

	public void setStyleById(JasperDesign jasperDesign, String styleId) {
		this.style = JasperDesignHelper.getStyle(jasperDesign, styleId);
	}

	public JRStyle getStyleLabel() {
		return styleLabel;
	}

	public void setStyleLabelById(JasperDesign jasperDesign, String styleId) {
		this.styleLabel = JasperDesignHelper.getStyle(jasperDesign, styleId);
	}

	public void setStyleLabel(JRStyle styleLabel) {
		this.styleLabel = styleLabel;
	}

	public String getFieldClassName() {
		return fieldClassName;
	}

	public void setFieldClassName(String fieldClassName) {
		this.fieldClassName = fieldClassName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFieldClass(Class<?> fieldClass) {
		this.fieldClassName = fieldClass.getName();
	}

	public int getXOffset() {
		int xPosText = getXpos() + getWidth();
		if (!hideLabel) {
			xPosText = getXpos() + getLabelWidth() + 20 + xPosText;
		}
		return xPosText;
	}

	public int getYOffset() {
		return getYpos() + getHeight();
	}

	public void addToJasperDesign(JasperDesign jasperDesign, JRElementGroup panel) {
		int xPosText = getXpos();
		if (!hideLabel) {
			JasperDesignHelper.addParameter(jasperDesign, property, getFieldClassName(), getDescription());
			int width2 = LayoutUtils.arand(getLabelWidth(), getAjustWhith());
			JasperDesignHelper.addElement(panel, JasperDesignHelper.createLabelText(getXpos(), getYpos(), width2, getHeight(), getProperty() + "Text", getLabel(), RVerticalAlign.MIDDLE, getLabelAlign(), getStyleLabel(), RBorder.NO_BORDER));
			int width3 = LayoutUtils.arand(5, getAjustWhith());
			JasperDesignHelper.addElement(panel, JasperDesignHelper.createLabelText(getXpos() + getLabelWidth() + 10, getYpos(), width3, getHeight(), getProperty() + "Sap", getLabelSeparator(), RVerticalAlign.MIDDLE, getLabelAlign(), getStyleLabel(), RBorder.NO_BORDER));
			xPosText = getXpos() + getLabelWidth() + 20;
		}
		int width4 = LayoutUtils.arand(getWidth(), getAjustWhith());
		JasperDesignHelper.addElement(panel, JasperDesignHelper.createTextField(xPosText, getYpos(), width4, getHeight(), getProperty(), getFieldClassName(), "$P{" + getProperty() + "}", RVerticalAlign.MIDDLE, RHorizontalAlign.LEFT, getStyle(), RBorder.NO_BORDER));
	}
}
