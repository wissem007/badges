package tn.com.smartsoft.commons.report.component;

import net.sf.jasperreports.engine.JRGraphicElement;

public class RBorder extends RConstant {

	private static final long serialVersionUID = 1L;

	public static RBorder NO_BORDER = new RBorder(JRGraphicElement.PEN_NONE);
	public static RBorder THIN = new RBorder(JRGraphicElement.PEN_THIN);

	public static RBorder PEN_1_POINT = new RBorder(JRGraphicElement.PEN_1_POINT);
	public static RBorder PEN_2_POINT = new RBorder(JRGraphicElement.PEN_2_POINT);
	public static RBorder PEN_4_POINT = new RBorder(JRGraphicElement.PEN_4_POINT);
	public static RBorder DOTTED = new RBorder(JRGraphicElement.PEN_DOTTED);

	private byte value;

	public byte getValue() {
		return value;
	}

	private RBorder(byte value) {
		this.value = value;
	}

}
