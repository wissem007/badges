package tn.com.smartsoft.commons.report.component;

public class RLabelPosition extends RConstant {

	private static final long serialVersionUID = 1L;

	public static RLabelPosition TOP = new RLabelPosition((byte) 1);
	public static RLabelPosition BOTTOM = new RLabelPosition((byte) 3);
	public static RLabelPosition LEFT = new RLabelPosition((byte) 2);
	public static RLabelPosition RIGHT = new RLabelPosition((byte) 4);

	private byte value;

	public byte getValue() {
		return value;
	}

	private RLabelPosition(byte value) {
		this.value = value;
	}

}
