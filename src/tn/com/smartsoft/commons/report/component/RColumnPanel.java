package tn.com.smartsoft.commons.report.component;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import tn.com.smartsoft.commons.report.JasperDesignHelper;
import tn.com.smartsoft.commons.report.utils.LayoutUtils;
import tn.com.smartsoft.commons.utils.NumberUtils;

public class RColumnPanel extends RConstant implements Cloneable {
	
	private static final long serialVersionUID = 1L;
	private RVerticalAlign verticalAlign = RVerticalAlign.MIDDLE;
	private RHorizontalAlign horizontalAlign = RHorizontalAlign.LEFT;
	private int height = 14;
	private JRStyle style;
	private RBorder border = RBorder.PEN_1_POINT;
	private RVerticalAlign verticalAlignHead = RVerticalAlign.MIDDLE;
	private RHorizontalAlign horizontalAlignHead = RHorizontalAlign.CENTER;
	private JRStyle styleHead;
	private RBorder borderHead = RBorder.PEN_1_POINT;
	private int heightHead = 19;
	private List<RColumnField> columnFields = new ArrayList<RColumnField>();
	private int width = 782;
	
	public RColumnField addColumnField(RColumnField columnField) {
		columnFields.add(columnField);
		return columnField;
	}
	
	public RColumnField addColumnField(String title, String property, String fieldClassName, int width) {
		return addColumnField(new RColumnField(title, property, fieldClassName, width));
	}
	
	public RColumnField addColumnField(String title, String property, Class<?> fieldClassName, int width) {
		return addColumnField(new RColumnField(title, property, fieldClassName.getName(), width));
	}
	
	public void setStyleById(JasperDesign jasperDesign, String styleId) {
		this.style = JasperDesignHelper.getStyle(jasperDesign, styleId);
	}
	
	public void setStyleHeadById(JasperDesign jasperDesign, String styleId) {
		this.styleHead = JasperDesignHelper.getStyle(jasperDesign, styleId);
	}
	
	public void addToJasperDesign(JasperDesign jasperDesign, JRElementGroup head, JRElementGroup detail) {
		RColumnField lastfieldText = null;
		int sumWhith = 0;
		for (int i = 0; i < columnFields.size(); i++) {
			RColumnField columnField = columnFields.get(i);
			sumWhith = sumWhith + columnField.getWidth();
		}
		float ajst = NumberUtils.diviser(new Double(getWidth()), new Double(sumWhith)).floatValue();
		for (int i = 0; i < columnFields.size(); i++) {
			RColumnField columnField = columnFields.get(i);
			columnField.setWidth((int) LayoutUtils.arand(columnField.getWidth(), ajst));
			if (lastfieldText != null)
				columnField.setXpos(lastfieldText.getXOffset());
			else
				columnField.setXpos(0);
			if (i == columnFields.size() - 1) {
				columnField.setWidth(getWidth() - columnField.getXpos());
			}
			columnField.setHeight(height);
			columnField.setHeightHead(getHeightHead());
			if (columnField.getStyle() == null)
				columnField.setStyle(getStyle());
			if (columnField.getStyleHead() == null)
				columnField.setStyleHead(getStyleHead());
			
			if (columnField.getHorizontalAlign() == null)
				columnField.setHorizontalAlign(horizontalAlign);
			if (columnField.getVerticalAlign() == null)
				columnField.setVerticalAlign(verticalAlign);
			
			if (columnField.getHorizontalAlignHead() == null)
				columnField.setHorizontalAlignHead(horizontalAlignHead);
			if (columnField.getVerticalAlignHead() == null)
				columnField.setVerticalAlignHead(verticalAlignHead);
			
			if (columnField.getBorder() == null)
				columnField.setBorder(border);
			if (columnField.getBorderHead() == null)
				columnField.setBorderHead(borderHead);
			columnField.addToJasperDesign(jasperDesign, head, detail);
			lastfieldText = columnField;
		}
	}
	
	public RVerticalAlign getVerticalAlign() {
		return verticalAlign;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
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
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public JRStyle getStyle() {
		return style;
	}
	
	public void setStyle(JRStyle style) {
		this.style = style;
	}
	
	public RBorder getBorder() {
		return border;
	}
	
	public void setBorder(RBorder border) {
		this.border = border;
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
	
	public List<RColumnField> getColumnFields() {
		return columnFields;
	}
	
	public void setColumnFields(List<RColumnField> columnFields) {
		this.columnFields = columnFields;
	}
	
}
