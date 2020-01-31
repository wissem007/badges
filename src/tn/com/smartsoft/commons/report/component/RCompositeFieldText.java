package tn.com.smartsoft.commons.report.component;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.commons.report.JasperDesignHelper;
import tn.com.smartsoft.commons.utils.NumberUtils;

import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JasperDesign;

public class RCompositeFieldText extends RConstant implements Cloneable {

	private static final long serialVersionUID = 1L;
	private List<RFieldText> fieldTextDetails = new ArrayList<RFieldText>();
	private int width = 780;
	private int height = 20;
	private int widthSpace = 10;
	private int ypos = 20;
	private String labelSeparator = ":";
	private int xpos = 0;
	private RHorizontalAlign labelAlign = RHorizontalAlign.LEFT;
	private JRStyle style;
	private JRStyle styleLabel;

	public RCompositeFieldText() {
		super();
	}

	public RCompositeFieldText(int xpos, int ypos) {
		super();
		this.xpos = xpos;
		this.ypos = ypos;
	}

	public void addFieldText(String label, String property, int width, int labelWidth, String fieldClassName) {
		fieldTextDetails.add(new RFieldText(property, label, width, labelWidth, fieldClassName));
	}

	public void addFieldText(String label, String property, int width, int labelWidth, Class<?> fieldClass) {
		fieldTextDetails.add(new RFieldText(property, label, width, labelWidth, fieldClass.getName()));
	}

	public void addFieldText(RFieldText fieldText) {
		fieldTextDetails.add(fieldText);
	}

	public void setStyleById(JasperDesign jasperDesign, String styleId) {
		this.style = JasperDesignHelper.getStyle(jasperDesign, styleId);
	}

	public void setStyleLabelById(JasperDesign jasperDesign, String styleId) {
		this.styleLabel = JasperDesignHelper.getStyle(jasperDesign, styleId);
	}

	public int getYpos() {
		return ypos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}

	public void addToJasperDesign(JasperDesign jasperDesign, JRElementGroup panel) {
		int sumWhith = 0;
		for (int i = 0; i < fieldTextDetails.size(); i++) {
			RFieldText fieldText = fieldTextDetails.get(i);
			sumWhith = sumWhith + fieldText.getWidth() + getWidthSpace();
		}
		RFieldText lastfieldText = null;
		for (int i = 0; i < fieldTextDetails.size(); i++) {
			RFieldText fieldText = fieldTextDetails.get(i);
			fieldText.setHeight(height);
			if (sumWhith > getWidth()) {
				fieldText.setAjustWhith(NumberUtils.diviser(new Double(getWidth()), new Double(sumWhith)).floatValue());
			}
			fieldText.setYpos(getYpos());
			fieldText.setLabelSeparator(getLabelSeparator());
			fieldText.setLabelAlign(labelAlign);
			if (lastfieldText != null)
				fieldText.setXpos(lastfieldText.getXOffset() + getWidthSpace());
			else
				fieldText.setXpos(getXpos());
			if (fieldText.getStyle() == null)
				fieldText.setStyle(style);
			if (fieldText.getStyleLabel() == null)
				fieldText.setStyleLabel(styleLabel);
			fieldText.addToJasperDesign(jasperDesign, panel);
			lastfieldText = fieldText;
		}
	}

	public RHorizontalAlign getLabelAlign() {
		return labelAlign;
	}

	public void setLabelAlign(RHorizontalAlign labelAlign) {
		this.labelAlign = labelAlign;
	}

	public JRStyle getStyle() {
		return style;
	}

	public void setStyle(JRStyle style) {
		this.style = style;
	}

	public JRStyle getStyleLabel() {
		return styleLabel;
	}

	public void setStyleLabel(JRStyle styleLabel) {
		this.styleLabel = styleLabel;
	}

	public String getLabelSeparator() {
		return labelSeparator;
	}

	public void setLabelSeparator(String labelSeparator) {
		this.labelSeparator = labelSeparator;
	}

	public int getXpos() {
		return xpos;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public int getYOffset() {
		return getYpos() + getHeight();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidthSpace() {
		return widthSpace;
	}

	public void setWidthSpace(int widthSpace) {
		this.widthSpace = widthSpace;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
