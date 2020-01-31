package tn.com.smartsoft.commons.report.component;

public class RTransparency extends RConstant {

	private static final long serialVersionUID = 1L;

	public static RTransparency TRANSPARENT = new RTransparency((byte) 2);
	public static RTransparency OPAQUE = new RTransparency((byte) 1);

	private byte value;

	public byte getValue() {
		return value;
	}

	private RTransparency(byte value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof RTransparency))
			return false;

		return this.value == ((RTransparency) obj).value;
	}

}
