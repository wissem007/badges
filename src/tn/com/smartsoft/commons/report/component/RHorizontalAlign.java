package tn.com.smartsoft.commons.report.component;

public class RHorizontalAlign extends RConstant {

	private static final long serialVersionUID = 1L;

	public static RHorizontalAlign LEFT = new RHorizontalAlign((byte) 1);
	public static RHorizontalAlign RIGHT = new RHorizontalAlign((byte) 3);
	public static RHorizontalAlign CENTER = new RHorizontalAlign((byte) 2);
	public static RHorizontalAlign JUSTIFY = new RHorizontalAlign((byte) 4);

	private final byte value;

	public byte getValue() {
		return value;
	}

	private RHorizontalAlign(byte value) {
		this.value = value;
	}

}
