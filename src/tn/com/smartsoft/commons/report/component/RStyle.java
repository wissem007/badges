package tn.com.smartsoft.commons.report.component;

import java.awt.Color;
import java.io.Serializable;

import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignConditionalStyle;
import net.sf.jasperreports.engine.design.JRDesignStyle;

/**
 * Class that should be used to define the different styles in a friendly </br>
 * and strict way.</br> </br> Usage example:</br> Style headerStyle = new
 * Style();</br> headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);</br>
 * headerStyle.setBorder(Border.PEN_2_POINT);</br>
 * headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);</br>
 * headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);</br>
 */
public class RStyle implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String parentStyleName;

	private Color backgroundColor = Color.WHITE;
	private Color textColor = Color.BLACK;
	private Color borderColor = Color.BLACK;

	private RFont font = RFont.ARIAL_MEDIUM;

	private RBorder border = RBorder.NO_BORDER;

	private RBorder borderTop = null;
	private RBorder borderBottom = null;
	private RBorder borderLeft = null;
	private RBorder borderRight = null;

	private Integer paddingBottom, paddingTop, paddingLeft, paddingRight;

	private Integer padding = new Integer(2);
	private Integer radius = new Integer(0);

	private RTransparency transparency = RTransparency.TRANSPARENT;

	private RVerticalAlign verticalAlign = RVerticalAlign.BOTTOM;
	private RHorizontalAlign horizontalAlign = RHorizontalAlign.LEFT;
	private RRotation rotation = RRotation.NONE;

	private RStretching streching = RStretching.RELATIVE_TO_TALLEST_OBJECT;

	private boolean stretchWithOverflow = true;
	private boolean blankWhenNull = true;

	private String pattern;

	/**
	 * If true and another style exists in the design with the same name, this
	 * style overrides the existing one.
	 */
	private boolean overridesExistingStyle = false;

	public boolean isOverridesExistingStyle() {
		return overridesExistingStyle;
	}

	public void setOverridesExistingStyle(boolean overridesExistingStyle) {
		this.overridesExistingStyle = overridesExistingStyle;
	}

	public RStyle() {
	}

	public RStyle(String name) {
		this.name = name;
	}

	public RStyle(String name, String parentName) {
		this.name = name;
		this.parentStyleName = parentName;
	}

	public boolean isBlankWhenNull() {
		return blankWhenNull;
	}

	public void setBlankWhenNull(boolean blankWhenNull) {
		this.blankWhenNull = blankWhenNull;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public RBorder getBorder() {
		return border;
	}

	public void setBorder(RBorder border) {
		this.border = border;
	}

	public RFont getFont() {
		return font;
	}

	public void setFont(RFont font) {
		if (font != null)
			this.font = (RFont) font.clone();
		else
			this.font = null;
	}

	public RHorizontalAlign getHorizontalAlign() {
		return horizontalAlign;
	}

	public void setHorizontalAlign(RHorizontalAlign horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
	}

	public Integer getPadding() {
		return padding;
	}

	public void setPadding(Integer padding) {
		this.padding = padding;
	}

	public RStretching getStreching() {
		return streching;
	}

	public void setStreching(RStretching streching) {
		this.streching = streching;
	}

	public boolean isStretchWithOverflow() {
		return stretchWithOverflow;
	}

	public void setStretchWithOverflow(boolean stretchWithOverflow) {
		this.stretchWithOverflow = stretchWithOverflow;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public RTransparency getTransparency() {
		return transparency;
	}

	public void setTransparency(RTransparency transparency) {
		this.transparency = transparency;
	}

	public void setTransparent(boolean transparent) {
		if (transparent)
			this.setTransparency(RTransparency.TRANSPARENT);
		else
			this.setTransparency(RTransparency.OPAQUE);
	}

	public boolean isTransparent() {
		return this.transparency.equals(RTransparency.TRANSPARENT);
	}

	public RVerticalAlign getVerticalAlign() {
		return verticalAlign;
	}

	public void setVerticalAlign(RVerticalAlign verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

	public JRDesignConditionalStyle transformAsConditinalStyle() {
		JRDesignConditionalStyle ret = new JRDesignConditionalStyle();
		setJRBaseStyleProperties(ret);
		return ret;

	}

	public JRDesignStyle transform() {
		JRDesignStyle transformedStyle = new JRDesignStyle();
		transformedStyle.setName(this.name);
		transformedStyle.setParentStyleNameReference(this.parentStyleName);
		setJRBaseStyleProperties(transformedStyle);
		return transformedStyle;
	}

	@SuppressWarnings("deprecation")
	protected void setJRBaseStyleProperties(JRBaseStyle transformedStyle) {
		if (getBorder() != null)
			transformedStyle.setBorder(getBorder().getValue());

		// Borders
		if (getBorderBottom() != null)
			transformedStyle.setBottomBorder(getBorderBottom().getValue());
		if (getBorderTop() != null)
			transformedStyle.setTopBorder(getBorderTop().getValue());
		if (getBorderLeft() != null)
			transformedStyle.setLeftBorder(getBorderLeft().getValue());
		if (getBorderRight() != null)
			transformedStyle.setRightBorder(getBorderRight().getValue());

		// Padding
		transformedStyle.setPadding(getPadding());

		if (paddingBottom != null)
			transformedStyle.setBottomPadding(paddingBottom);
		if (paddingTop != null)
			transformedStyle.setTopPadding(paddingTop);
		if (paddingLeft != null)
			transformedStyle.setLeftPadding(paddingLeft);
		if (paddingRight != null)
			transformedStyle.setRightPadding(paddingRight);

		// Aligns
		if (getHorizontalAlign() != null)
			transformedStyle.setHorizontalAlignment(getHorizontalAlign().getValue());

		if (getVerticalAlign() != null)
			transformedStyle.setVerticalAlignment(getVerticalAlign().getValue());

		transformedStyle.setBlankWhenNull(blankWhenNull);

		// Font
		if (font != null) {
			transformedStyle.setFontName(font.getFontName());
			transformedStyle.setFontSize(font.getFontSize());
			transformedStyle.setBold(font.isBold());
			transformedStyle.setItalic(font.isItalic());
			transformedStyle.setUnderline(font.isUnderline());
			transformedStyle.setPdfFontName(font.getPdfFontName());
			transformedStyle.setPdfEmbedded(font.isPdfFontEmbedded());
			transformedStyle.setPdfEncoding(font.getPdfFontEncoding());
		}

		transformedStyle.setBackcolor(getBackgroundColor());
		transformedStyle.setForecolor(getTextColor());
		transformedStyle.setBorderColor(borderColor);
		if (getTransparency() != null)
			transformedStyle.setMode(getTransparency().getValue());

		if (getRotation() != null)
			transformedStyle.setRotation(getRotation().getValue());

		if (getRadius() != null)
			transformedStyle.setRadius(getRadius().intValue());

		transformedStyle.setPattern(this.pattern);

		/**
		 * This values are needed when exporting to JRXML
		 */
		transformedStyle.setPen((byte) 0);
		transformedStyle.setFill((byte) 1);
		transformedStyle.setScaleImage(RImageScaleMode.NO_RESIZE.getValue());

	}

	public RBorder getBorderBottom() {
		return borderBottom;
	}

	public void setBorderBottom(RBorder borderBottom) {
		this.borderBottom = borderBottom;
	}

	public RBorder getBorderLeft() {
		return borderLeft;
	}

	public void setBorderLeft(RBorder borderLeft) {
		this.borderLeft = borderLeft;
	}

	public RBorder getBorderRight() {
		return borderRight;
	}

	public void setBorderRight(RBorder borderRight) {
		this.borderRight = borderRight;
	}

	public RBorder getBorderTop() {
		return borderTop;
	}

	public void setBorderTop(RBorder borderTop) {
		this.borderTop = borderTop;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public RRotation getRotation() {
		return rotation;
	}

	public void setRotation(RRotation rotation) {
		this.rotation = rotation;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	/**
	 * @deprecated due to miss spelling
	 * @return
	 */
	// public Integer getPaddingBotton() {
	// return paddingBottom;
	// }

	public Integer getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(Integer paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	/**
	 * @deprecated due to miss spelling
	 * @param paddingBotton
	 */
	// public void setPaddingBotton(Integer paddingBotton) {
	// this.paddingBottom = paddingBotton;
	// }

	public Integer getPaddingTop() {
		return paddingTop;
	}

	public void setPaddingTop(Integer paddingTop) {
		this.paddingTop = paddingTop;
	}

	public Integer getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(Integer paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	public Integer getPaddingRight() {
		return paddingRight;
	}

	public void setPaddingRight(Integer paddingRight) {
		this.paddingRight = paddingRight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentStyleName() {
		return parentStyleName;
	}

	public void setParentStyleName(String parentStyleName) {
		this.parentStyleName = parentStyleName;
	}

	/**
	 * Creates a blank style (no default values). Usefull when we need a style
	 * with a parent style, not defined properties (null ones) will be inherited
	 * from parent style
	 * 
	 * @param name
	 * @return
	 */
	public static RStyle createBlankStyle(String name) {
		RStyle style = new RStyle(name);

		style.setBackgroundColor(null);
		style.setBorderColor(null);
		style.setTransparency(null);
		style.setTextColor(null);
		style.setBorder(null);
		style.setFont(null);
		style.setPadding(null);
		style.setRadius(null);
		style.setVerticalAlign(null);
		style.setHorizontalAlign(null);
		style.setRotation(null);
		style.setStreching(null);

		return style;

	}

	public static RStyle createBlankStyle(String name, String parent) {
		RStyle s = createBlankStyle(name);
		s.setParentStyleName(parent);
		return s;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Object clone() throws CloneNotSupportedException {
		RStyle style = (RStyle) super.clone();
		style.setFont(this.font);
		return style;
	}
}
