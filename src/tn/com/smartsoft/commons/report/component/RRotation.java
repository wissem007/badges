package tn.com.smartsoft.commons.report.component;

public class RRotation extends RConstant {

	private static final long serialVersionUID = 1L;

	public static RRotation NONE = new RRotation((byte) 0);
	public static RRotation LEFT = new RRotation((byte) 1);
	public static RRotation RIGHT = new RRotation((byte) 2);

	private byte value;

	private RRotation(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

}
