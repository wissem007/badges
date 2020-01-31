package tn.com.smartsoft.commons.report.component;

public class RPosition extends RConstant {

	private static final long serialVersionUID = 1L;

	private byte value;

	public static RPosition POSITION_TYPE_FIX_RELATIVE_TO_TOP = new RPosition((byte) 2);
	public static RPosition POSITION_TYPE_FIX_RELATIVE_TO_BOTTOM = new RPosition((byte) 3);
	public static RPosition POSITION_TYPE_FLOAT = new RPosition((byte) 1);

	private RPosition(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}
}
