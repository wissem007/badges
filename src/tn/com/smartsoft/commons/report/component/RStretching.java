package tn.com.smartsoft.commons.report.component;

public class RStretching extends RConstant {

	private static final long serialVersionUID = 1L;

	public static RStretching NO_STRETCH = new RStretching((byte) 0);
	public static RStretching RELATIVE_TO_TALLEST_OBJECT = new RStretching((byte) 1);
	public static RStretching RELATIVE_TO_BAND_HEIGHT = new RStretching((byte) 2);

	private byte value;

	public byte getValue() {
		return value;
	}

	private RStretching(byte value) {
		this.value = value;
	}

}
