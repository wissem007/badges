package tn.com.smartsoft.commons.report.component;

public class RVerticalAlign extends RConstant {

	private static final long serialVersionUID = 1L;

	public static RVerticalAlign TOP = new RVerticalAlign((byte) 1);
	public static RVerticalAlign BOTTOM = new RVerticalAlign((byte) 3);
	public static RVerticalAlign MIDDLE = new RVerticalAlign((byte) 2);
	public static RVerticalAlign JUSTIFIED = new RVerticalAlign((byte) 4);

	private byte value;

	public byte getValue() {
		return value;
	}

	private RVerticalAlign(byte value) {
		this.value = value;
	}

}
