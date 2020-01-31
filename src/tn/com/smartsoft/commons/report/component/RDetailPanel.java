package tn.com.smartsoft.commons.report.component;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JasperDesign;
import tn.com.smartsoft.commons.report.JasperDesignHelper;
import tn.com.smartsoft.commons.report.utils.LayoutUtils;

public class RDetailPanel extends RConstant implements Cloneable {

	private static final long serialVersionUID = 1L;
	private List<RCompositeFieldText> fieldTextDetails = new ArrayList<RCompositeFieldText>();
	private int width = 780;
	private int height = 20;
	private int widthSpace = 10;
	private int heightSpace = 10;
	private int ypos = 20;
	private String labelSeparator = ":";
	private int xpos = 0;
	private RHorizontalAlign labelAlign = RHorizontalAlign.LEFT;
	private JRStyle style;
	private JRStyle styleLabel;

	public RDetailPanel() {
		super();
	}

	public RCompositeFieldText addCompositeFieldText(RCompositeFieldText fieldText) {
		fieldTextDetails.add(fieldText);
		return fieldText;
	}

	public void setStyleById(JasperDesign jasperDesign, String styleId) {
		this.style = JasperDesignHelper.getStyle(jasperDesign, styleId);
	}

	public void setStyleLabelById(JasperDesign jasperDesign, String styleId) {
		this.styleLabel = JasperDesignHelper.getStyle(jasperDesign, styleId);
	}

	public void addToJasperDesign(JasperDesign jasperDesign, JRDesignBand panel) {
		int panelWhith = LayoutUtils.findYOffset(panel);
		int fieldSize = fieldTextDetails.size();
		for (int i = 0; i < fieldSize; i++) {
			RCompositeFieldText fieldText = fieldTextDetails.get(i);
			panelWhith = LayoutUtils.findYOffset(panel);
			fieldText.setHeight(height);
			fieldText.setLabelSeparator(getLabelSeparator());
			fieldText.setLabelAlign(labelAlign);
		 	panel.setHeight(panelWhith + height + heightSpace);
			fieldText.setYpos(panelWhith);
			fieldText.setXpos(getXpos());
			if (fieldText.getStyle() == null)
				fieldText.setStyle(style);
			if (fieldText.getStyleLabel() == null)
				fieldText.setStyleLabel(styleLabel);
			fieldText.addToJasperDesign(jasperDesign, panel);
		}
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

	public int getWidthSpace() {
		return widthSpace;
	}

	public void setWidthSpace(int widthSpace) {
		this.widthSpace = widthSpace;
	}

	public int getYpos() {
		return ypos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
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

}
