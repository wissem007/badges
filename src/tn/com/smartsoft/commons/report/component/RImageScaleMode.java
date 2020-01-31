package tn.com.smartsoft.commons.report.component;

import net.sf.jasperreports.engine.design.JRDesignImage;

public class RImageScaleMode extends RConstant {

	private static final long serialVersionUID = 1L;

	private byte value = JRDesignImage.SCALE_IMAGE_RETAIN_SHAPE;

	public static RImageScaleMode NO_RESIZE = new RImageScaleMode(JRDesignImage.SCALE_IMAGE_CLIP);
	public static RImageScaleMode FILL = new RImageScaleMode(JRDesignImage.SCALE_IMAGE_FILL_FRAME);
	public static RImageScaleMode FILL_PROPORTIONALLY = new RImageScaleMode(JRDesignImage.SCALE_IMAGE_RETAIN_SHAPE);

	public byte getValue() {
		return value;
	}

	private RImageScaleMode(byte mode) {
		this.value = mode;
	}
}
