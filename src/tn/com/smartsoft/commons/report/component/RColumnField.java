package tn.com.smartsoft.commons.report.component;

import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import tn.com.smartsoft.commons.report.JasperDesignHelper;

public class RColumnField extends RConstant implements Cloneable {

	private static final long serialVersionUID = 1L;
	private String title;
	private int xpos;
	private int ypos = 0;
	private int width;
	private int height = 14;
	private String fieldClassName = String.class.getName();
	private String property;
	private RVerticalAlign verticalAlign = RVerticalAlign.MIDDLE;
	private RHorizontalAlign horizontalAlign = RHorizontalAlign.LEFT;
	private JRStyle style;
	private RBorder border = RBorder.PEN_1_POINT;
	private RVerticalAlign verticalAlignHead = RVerticalAlign.MIDDLE;
	private RHorizontalAlign horizontalAlignHead = RHorizontalAlign.CENTER;
	private JRStyle styleHead;
	private RBorder borderHead = RBorder.PEN_1_POINT;
	private int heightHead = 19;
	private float ajustWhith = 1;

	public RColumnField() {
		super();
	}

	public RColumnField(String title, String property, String fieldClassName, int width) {
		super();
		this.title = title;
		this.property = property;
		this.width = width;
		this.fieldClassName = fieldClassName;
	}

	public float getAjustWhith() {
		return ajustWhith;
	}

	public void setAjustWhith(float ajustWhith) {
		this.ajustWhith = ajustWhith;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getFieldClassName() {
		return fieldClassName;
	}

	public void setFieldClassName(String fieldClassName) {
		this.fieldClassName = fieldClassName;
	}

	public void setFieldClass(Class<?> fieldClass) {
		this.fieldClassName = fieldClass.getName();
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

	public RBorder getBorder() {
		return border;
	}

	public void setBorder(RBorder border) {
		this.border = border;
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

	public RVerticalAlign getVerticalAlign() {
		return verticalAlign;
	}

	public void setVerticalAlign(RVerticalAlign verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

	public RHorizontalAlign getHorizontalAlign() {
		return horizontalAlign;
	}

	public void setHorizontalAlign(RHorizontalAlign horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
	}

	public RVerticalAlign getVerticalAlignHead() {
		return verticalAlignHead;
	}

	public void setVerticalAlignHead(RVerticalAlign verticalAlignHead) {
		this.verticalAlignHead = verticalAlignHead;
	}

	public RHorizontalAlign getHorizontalAlignHead() {
		return horizontalAlignHead;
	}

	public void setHorizontalAlignHead(RHorizontalAlign horizontalAlignHead) {
		this.horizontalAlignHead = horizontalAlignHead;
	}

	public JRStyle getStyleHead() {
		return styleHead;
	}

	public void setStyleHead(JRStyle styleHead) {
		this.styleHead = styleHead;
	}

	public void setStyleHeadById(JasperDesign jasperDesign, String styleHeadId) {
		this.styleHead = JasperDesignHelper.getStyle(jasperDesign, styleHeadId);
	}

	public RBorder getBorderHead() {
		return borderHead;
	}

	public void setBorderHead(RBorder borderHead) {
		this.borderHead = borderHead;
	}

	public int getHeightHead() {
		return heightHead;
	}

	public void setHeightHead(int heightHead) {
		this.heightHead = heightHead;
	}

	public int getXOffset() {
		return getXpos() + getWidth();
	}

	public void addToJasperDesign(JasperDesign jasperDesign, JRElementGroup head, JRElementGroup detail) {
		JasperDesignHelper.addField(jasperDesign, property, getFieldClassName(), property);
		int calWhith = calWhith();
		JasperDesignHelper.addElement(head, JasperDesignHelper.createLabelText(getXpos(), getYpos(), calWhith, getHeightHead(), property + "Lable", getTitle(),
				getVerticalAlignHead(), getHorizontalAlignHead(), getStyleHead(), getBorderHead()));
		JasperDesignHelper.addElement(detail, JasperDesignHelper.createTextField(getXpos(), getYpos(), calWhith, getHeight(), getProperty(), String.class.getName(), "$F{"
				+ getProperty() + "}.toString()", getVerticalAlign(), getHorizontalAlign(), getStyle(), getBorder()));
	}

	private int calWhith() {
		return getWidth();
	}
}
